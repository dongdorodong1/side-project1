package play.board1.post.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import play.board1.common.session.SessionConst;
import play.board1.post.dto.PostCmtInsertDto;
import play.board1.post.dto.PostDto;
import play.board1.post.dto.Paging;
import play.board1.post.service.PostService;
import play.board1.common.dto.MemberDto;

import java.util.HashMap;
import java.util.List;

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
    @GetMapping("/postReg")
    public String postReg() {
        return "post/postReg";
    }

    /**
     * 게시글을 등록한다.
     */
    @PostMapping("/insertPost")
    public @ResponseBody String insertPost(PostDto postDto, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // TODO 예외 리팩토링
        if(null == loginMember) throw new IllegalStateException("잘못된 접근");
        postDto.setMember(loginMember);
        postService.insertPost(postDto);
        return "ok";
    }

    /**
     * 게시글을 조회한다.
     * @return
     */
    @PostMapping("/postView/{postId}")
    public String postView(@PathVariable Long postId,Model model) {
        PostDto post = postService.findPost(postId);
        model.addAttribute("post",post);
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
        Long deletedPost = postService.deletePost(postId);
        return "redirect:/post/postList";
    }

    /**
     * 게시물 수정화면
     * @param postId
     * @param model
     * @return
     */
    @GetMapping("/updatePost/{id}")
    public String postUpdate(@PathVariable Long postId,Model model) {
        PostDto post = postService.findPost(postId);
        model.addAttribute("post",post);
        return "post/postUpdate";
    }

    /**
     * 게시물을 수정한다.
     * @param postDto
     * @return
     */
    @PostMapping("/updatePost")
    public @ResponseBody String updatePost(PostDto postDto) {
        Long updatePost = postService.updatePost(postDto);
        return "ok";
    }

    /**
     * 댓글을 등록한다.
     * @param cmtDto
     * @return
     */
    @PostMapping("/insertComment")
    public @ResponseBody ResponseEntity<Long> insertComment(@RequestBody PostCmtInsertDto cmtDto) {
        Long commentId = postService.insertComment(cmtDto);
        return new ResponseEntity<>(commentId, HttpStatusCode.valueOf(200));
    }

    /**
     * 게시글에 해당하는 댓글을 가져온다.
     * @param postId
     * @return
     */
    @GetMapping("/selectComment")
    public ResponseEntity<List<PostCmtInsertDto>> selectComment(Long postId) {
        List<PostCmtInsertDto> comments = postService.selectComment(postId);
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
