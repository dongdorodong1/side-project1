package play.board1.post.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import play.board1.common.exception.MemberException;
import play.board1.common.session.SessionConst;
import play.board1.post.dto.PostCommentDto;
import play.board1.post.dto.PostDto;
import play.board1.post.dto.Paging;
import play.board1.post.service.PostService;
import play.board1.common.dto.MemberDto;

import java.util.HashMap;
import java.util.List;

/**
 * 게시글 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/post",method = {RequestMethod.GET,RequestMethod.POST})
public class PostController {

    private final PostService postService;

    @GetMapping("/postList")
    public String postList() {
        return "post/postList";
    }
    @GetMapping("/postMain")
    public String postMain() {
        return "post/postMain";
    }

    /**
     * 게시글 전체목록을 가져온다.
     */
    @PostMapping("/selectPostList")
    @ResponseBody
    public HashMap<String,Object> selectPostList(Paging paging) {

        return postService.selectAllAtcl(paging);
    }

    /**
     * 게시글 작성 화면으로 이동한다.
     * @param model
     * @return
     */
    @GetMapping("/postReg")
    public String postReg(Model model) {
        model.addAttribute("post",new PostDto());
        return "post/postReg";
    }

    /**
     * 게시글을 등록한다.
     */
    @PostMapping("/insertPost")
    public @ResponseBody String insertPost(PostDto postDto, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // TODO 예외 리팩토링
        if(null == loginMember) throw new MemberException("존재하지 않는 사용자");
        postDto.setMember(loginMember);
        postService.insertPost(postDto);
        return "ok";
    }

    /**
     * 게시글을 조회한다.
     * @return
     */
    @GetMapping("/postView/{postId}")
    public String postView(@PathVariable Long postId,HttpSession session, Model model) {
        PostDto postDto = postService.findPost(postId);
        postDto.setSession(session);
        postService.insertPostViewLog(postDto);
        model.addAttribute("post",postDto);
        return "post/postView";
    }

    /**
     * 게시물을 삭제한다.
     * @param postId
     * @param model
     * @return
     */
    @PostMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long postId,Model model) {
        postService.deletePost(postId);
        return "redirect:/post/postList";
    }

    /**
     * 게시물 수정화면
     * @param postId
     * @param model
     * @return
     */
    @GetMapping("/updatePost/{postId}")
    public String postUpdate(@PathVariable Long postId,Model model) {
        PostDto post = postService.findPost(postId);
        model.addAttribute("post",post);
        return "post/postReg";
    }

    /**
     * 게시물을 수정한다.
     * @param postDto
     * @return
     */
    @PostMapping("/updatePost")
    public @ResponseBody String updatePost(PostDto postDto) {
        postService.updatePost(postDto);
        return "ok";
    }

    /**
     * 댓글을 등록한다.
     * @param cmntDto
     * @return
     */
    @PostMapping("/insertComment")
    public @ResponseBody ResponseEntity<Long> insertComment(@RequestBody PostCommentDto cmntDto, HttpSession session) {
        cmntDto.setSession(session);
        Long commentId = postService.insertComment(cmntDto);
        return new ResponseEntity<>(commentId, HttpStatusCode.valueOf(200));
    }

    /**
     * 댓글을 삭제한다.
     * @param cmntId
     * @return
     */
    @PostMapping("/deleteComment")
    public @ResponseBody ResponseEntity<String> deleteComment(@RequestBody String cmntId, HttpSession session) {

        postService.deleteComment(cmntId);
        return new ResponseEntity<>(cmntId, HttpStatusCode.valueOf(200));
    }

    /**
     * 게시글에 해당하는 댓글을 가져온다.
     * @param postId
     * @return
     */
    @GetMapping("/selectComment")
    public ResponseEntity<List<PostCommentDto>> selectComment(Long postId) {
        List<PostCommentDto> comments = postService.selectComment(postId);
        return new ResponseEntity<>(comments, HttpStatusCode.valueOf(200));
    }

    /**
     * 게시글 추천수를 업데이트한다.
     * @param postDto
     * @param session
     * @return
     */
    @GetMapping("/addPostLike")
    public ResponseEntity<Integer> addPostLike(PostDto postDto,HttpSession session) {
        postDto.setSession(session);
        return ResponseEntity.ok(postService.addPostLike(postDto));
    }
}
