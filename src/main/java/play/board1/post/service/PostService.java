package play.board1.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.board1.post.dto.PostCmtInsertDto;
import play.board1.post.dto.PostDto;
import play.board1.post.dto.Paging;
import play.board1.post.entity.Post;
import play.board1.post.entity.PostComment;
import play.board1.post.entity.Member;
import play.board1.post.repository.PostCommentRepository;
import play.board1.post.repository.PostJpaRepository;
import play.board1.common.login.repository.MemberRepository;

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
    private final PostJpaRepository postRepository;
    private final PostCommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void insertPost(PostDto postDto) {

        Member findMember = memberRepository.findByUserId(postDto.getMember().getUserId());
        Post post = Post.builder()
                .subject(postDto.getSubject())
                .content(postDto.getContent())
                .member(findMember)
                .regDt(LocalDateTime.now())
                .build();

        postRepository.save(post);
    }

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

    public PostDto findPost(Long postId) {
        return postRepository.findById(postId)
                .map(PostDto::new)
                .orElse(new PostDto());
    }

    @Transactional
    public Long deletePost(Long id) {
        postRepository.deleteById(id);
        return id;
    }

    @Transactional
    public Long updatePost(PostDto postDto) {
        Optional<Post> findPostOptional = postRepository.findById(postDto.getId());
        findPostOptional.ifPresent(post -> {
            post.updateAtcl(postDto);
        });
        return 1L;
    }
    @Transactional
    public Long insertComment(PostCmtInsertDto cmtDto) {
        Optional<Post> findPost = postRepository.findById(cmtDto.getPostId());
        PostComment comment = new PostComment();
        if(findPost.isPresent()) {
            comment = new PostComment(cmtDto.getContent(), findPost.get());
            commentRepository.save(comment);
            return 1L;
        }
        return 0L;
    }

    public List<PostCmtInsertDto> selectComment(Long postId) {
        List<PostComment> comments = commentRepository.findCommentListByPostId(postId);
        List<PostCmtInsertDto> cDtos = comments.stream().map(c -> new PostCmtInsertDto(c)).collect(toList());
        return cDtos;
    }
}
