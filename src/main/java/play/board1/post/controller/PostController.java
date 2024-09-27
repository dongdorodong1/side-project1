package play.board1.post.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/selectPostList")
    @ResponseBody
    public HashMap<String,Object> selectPostList(Paging paging) {

        return postService.selectAllAtcl(paging);
    }
    @GetMapping("/postReg")
    public String postReg() {
        return "post/postReg";
    }

    @PostMapping("/insertPost")
    public @ResponseBody String insertPost(PostDto postDto, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        // TODO 예외 리팩토링
        if(null == loginMember) throw new IllegalStateException("잘못된 접근");
        postDto.setMember(loginMember);
        postService.insertPost(postDto);
        return "ok";
    }
    @PostMapping("/postView/{id}")
    public String postView(@PathVariable Long id,Model model) {
        PostDto post = postService.findPost(id);
        model.addAttribute("post",post);
        return "post/postView";
    }
    @PostMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long id,Model model) {
        Long deletedPost = postService.deletePost(id);
        return "redirect:/post/postList";
    }
    @GetMapping("/updatePost/{id}")
    public String postUpdate(@PathVariable Long id,Model model) {
        PostDto post = postService.findPost(id);
        model.addAttribute("post",post);
        return "post/updatePost";
    }
    @PostMapping("/updateAtcl")
    public @ResponseBody String updateAtcl(PostDto postDto) {
        Long updatePost = postService.updatePost(postDto);
        return "ok";
    }
    @PostMapping("/insertComment")
    public @ResponseBody ResponseEntity<Long> insertComment(@RequestBody PostCmtInsertDto cmtDto) {
        Long commentId = postService.insertComment(cmtDto);
        return new ResponseEntity<>(commentId, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/selectComment")
    public ResponseEntity<List<PostCmtInsertDto>> selectComment(Long postId) {
        List<PostCmtInsertDto> comments = postService.selectComment(postId);
        return new ResponseEntity<>(comments, HttpStatusCode.valueOf(200));
    }
}
