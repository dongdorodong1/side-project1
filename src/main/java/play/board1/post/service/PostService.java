package play.board1.post.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.board1.common.dto.MemberDto;
import play.board1.common.exception.MemberException;
import play.board1.common.session.SessionConst;
import play.board1.post.dto.PostCommentDto;
import play.board1.post.dto.PostDto;
import play.board1.post.dto.Paging;
import play.board1.post.dto.PostViewLogDto;
import play.board1.post.entity.Post;
import play.board1.post.entity.PostComment;
import play.board1.post.entity.Member;
import play.board1.post.entity.PostViewLog;
import play.board1.post.repository.PostCommentRepository;
import play.board1.common.login.repository.MemberRepository;
import play.board1.post.repository.PostLikeRepository;
import play.board1.post.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
//    private final PostRepository postRepository;
    private final PostRepository postRepository;
    private final PostCommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void insertPost(PostDto postDto) {

        Optional<Member> findMember = memberRepository.findByUserId(postDto.getMember().getUserId());
        findMember.orElseThrow(() -> new IllegalStateException());

        Post post = Post.builder()
                .subject(postDto.getSubject())
                .content(postDto.getContent())
                .member(findMember.get())
                .regDt(LocalDateTime.now())
                .build();

        postRepository.save(post);
    }

    /**
     * 모든 게시글을 가져온다.
     * @param paging
     * @return
     */
    public HashMap<String, Object> selectAllAtcl(Paging paging) {
        PageRequest pageRequest = PageRequest.of(paging.getPageNumber(), paging.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));

        Page<Post> postList = postRepository.findByPage(pageRequest);
        //Entity -> DTO
        List<PostDto> result = postList.getContent().stream()
                .map(b -> new PostDto(b))
                .collect(toList());

        HashMap<String, Object> resultMap = new HashMap<>();
        paging.setPageSize(postList.getSize());
        paging.setTotalPages(postList.getTotalPages());
        paging.setFirst(postList.isFirst());
        paging.setTotalCount(postList.getTotalElements());

        paging.setNext(postList.hasNext());
        resultMap.put("postList",result);
        resultMap.put("paging",paging);

        resultMap.put("paging",paging);
        return resultMap;
    }

    /**
     * 게시글 고유번호로 특정 게시글을 찾는다
     * @param postId
     * @return
     */
    public PostDto findPost(Long postId) {
        // TODO 예외 처리 공부필요
        PostDto postDto = postRepository.findById(postId)
                .map(PostDto::new)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
        int likeCount = postLikeRepository.countLikesByPostId(postId);
        postDto.setLikeCnt(likeCount);

        return postDto;
    }

    /**
     * 게시물을 삭제한다
     * @param id
     * @return
     */
    @Transactional
    public Long deletePost(Long id) {
        postRepository.deleteById(id);
        return id;
    }

    /**
     * 게시물을 수정한다.
     * @param postDto
     * @return
     */
    @Transactional
    public Long updatePost(PostDto postDto) {
        Optional<Post> findPostOptional = postRepository.findById(postDto.getPostId());
        findPostOptional.ifPresent(post -> {
            post.updatePost(postDto);
        });
        return 1L;
    }

    /**
     * 게시물에 댓글을 입력한다
     * @param cmtDto
     * @return
     */
    @Transactional
    public Long insertComment(PostCommentDto cmtDto) {
        Optional<Post> findPost = postRepository.findById(cmtDto.getPostId());
        MemberDto sessionMember = (MemberDto) cmtDto.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Member member = memberRepository.findByUserId(sessionMember.getUserId())
                .orElseThrow(() -> new MemberException("세션이 존재하지 않습니다."));
        if(findPost.isPresent()) {
            PostComment comment = new PostComment(cmtDto.getContent(), findPost.get(),member);
            commentRepository.save(comment);
            return 1L;
        }
        return 0L;
    }

    /**
     * 댓글을 가져온다.
     * @param postId
     * @return
     */
    public List<PostCommentDto> selectComment(Long postId) {
        List<PostComment> comments = commentRepository.findCommentListByPostId(postId);
        return comments.stream().map(PostCommentDto::new).collect(toList());
    }

    /**
     * 게시물에 좋아요를 추가한다.
     * @param postDto
     * @return
     */
    @Transactional
    public Integer addPostLike(PostDto postDto) {
        Optional<Post> postOpt = postRepository.findByPostId(postDto.getPostId());

        HttpSession session = postDto.getSession();
        Optional<MemberDto> sessionMemberOpt = Optional.ofNullable((MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER));
        Optional<Member> loginMember = memberRepository.findByUserId(sessionMemberOpt.get().getUserId());
        Post post = postOpt
                .orElseThrow(() -> new IllegalStateException("없는 게시물입니다."));
        boolean existLikeMember = postLikeRepository.existsByPostIdAndMemberId(post.getId(), loginMember.get().getId());

        if(existLikeMember) {
            postRepository.deletePostLike(post.getId(),loginMember.get().getId());
            return 2;
        } else {
            return postRepository.addPostLike(post.getId(),loginMember.get().getId());
        }
    }

    /**
     * 게시물에 조회로그를 입력한다.
     * @param postDto
     */
    @Transactional
    public void insertPostViewLog(PostDto postDto) {
        Optional<Post> postOpt = postRepository.findByPostId(postDto.getPostId());
        HttpSession session = postDto.getSession();
        Optional<PostViewLog> postViewLogOpt;
        Member loginMember;
        Optional<MemberDto> sessionMemberOpt = Optional.ofNullable((MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER));
        Optional<Member> loginMemberOpt = sessionMemberOpt
                .flatMap(sessionMember -> memberRepository.findByUserId(sessionMember.getUserId()));

        Post post = postOpt
                .orElseThrow(() -> new IllegalStateException("없는 게시물입니다."));
        if (loginMemberOpt.isPresent()) {
            loginMember = loginMemberOpt.get();
            postViewLogOpt = postRepository.existViewLog(post.getId(), loginMember.getId());
        } else {
            postViewLogOpt = postRepository.existViewLog(post.getId(), null);
            loginMember = null;
        }

        // 조회수 추가
        post.addViewCnt();
        postDto.setViewCnt(post.getViewCnt());

        if(postViewLogOpt.isPresent()) {
            PostViewLog postViewLog = postViewLogOpt.get();
            postRepository.updateViewLog(postViewLog);
            postDto.setPostViewLogDto(new PostViewLogDto(postViewLog.getViewCnt()));
        } else {
            postRepository.saveViewLog(new PostViewLog(loginMember,post,LocalDateTime.now(),LocalDateTime.now()));
            postDto.setPostViewLogDto(new PostViewLogDto(1));
        }
    }
}
