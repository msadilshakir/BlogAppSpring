package net.javaguides.Springboot_application.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import net.javaguides.Springboot_application.dto.CommentDto;
import net.javaguides.Springboot_application.dto.PostDto;
import net.javaguides.Springboot_application.entity.Comment;
import net.javaguides.Springboot_application.entity.Post;
import net.javaguides.Springboot_application.service.CommentService;
import net.javaguides.Springboot_application.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@Controller
public class PostController {

    private PostService postService;
    private CommentService commentService;

    public PostController(PostService postService,CommentService commentService) {

        this.postService = postService;
        this.commentService = commentService;
    }

    // create handler method, Get Request and return model and view
    @GetMapping("/admin/posts")
    public String posts(Model model){
        List<PostDto> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "/admin/posts";
    }
    @GetMapping("/admin/posts/newPost")
    public String newPostForm(Model model){
         PostDto postDto = new PostDto();
         model.addAttribute("post", postDto);
         return "admin/create_post";
    }

    //handler method for post creation and save it to database
    @PostMapping("/admin/posts")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("post",postDto);
            return "admin/create_post";
        }
        postDto.setUrl((getUrl(postDto.getTitle())));
        postService.createPost(postDto);
        return "redirect:/admin/posts";
    }

    //handler method for Editing the post

    @GetMapping("/admin/posts/{postId}/edit")
    private String editPostForm(@PathVariable("postId") Long postId, Model model)
    {
        PostDto postDto = postService.findPostById(postId);
        model.addAttribute("post", postDto);
        return "/admin/edit_post";
    }
    @PostMapping("/admin/posts/{postId}")
    private String updatePost(@PathVariable("postId") Long postId,
                              @Valid @ModelAttribute("post") PostDto post,
                              BindingResult result,
                              Model model){
        if(result.hasErrors()){
            model.addAttribute("post", post);
            return "admin/edit_post";
        }
        post.setId(postId);
        postService.updatePost(post);
        return "redirect:/admin/posts";
    }

    //handle method to delete post request

    @GetMapping("admin/posts/{postId}/delete")
    private String deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    //handle method to view post request

    @GetMapping("admin/posts/{postUrl}/view")
    public String viewPost(@PathVariable("postUrl") String postUrl,
                            Model model){
        PostDto postDto = postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);
        return "admin/view_post";

    }


    //handler method to handle search  post request
    @GetMapping("admin/posts/search")
    public String  searchPosts(@RequestParam(value = "query") String query, Model model){
        List<PostDto> posts = postService.searchPosts(query);
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    @GetMapping("admin/posts/comments/search")
    public String  commentSearchPosts(@RequestParam(value = "query") String query, Model model){
        List<CommentDto> comments = commentService.searchComments(query);
        model.addAttribute("comments", comments);
        return "admin/comments";
    }


    //handler method to handle view all comment section
    @GetMapping("/admin/posts/comments")
    public String viewComments(Model model){

        List<CommentDto> comments = commentService.findAllComments();
        model.addAttribute("comments", comments);
        return "/admin/comments";
    }

    @PostMapping("/admin/posts/{commentId}")
    private String findPostFromComments(@RequestParam Long commentId, Model model){
        PostDto postDto = postService.findPostByCommentId(commentId);
        model.addAttribute("post", postDto);
        return "/admin/comments";
    }

    @GetMapping("/admin/posts/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return "redirect:/admin/posts/comments";
    }
    //SEO-friendly URL slug. It ensures the resulting URL is easy to read,
    // properly formatted, and adheres to standard URL conventions
    private static String getUrl(String postTitle) {
        String title = postTitle.trim().toLowerCase();
        String url = title.replaceAll("\\s+", "-").replaceAll("[^a-z0-9-]", "");
        url = url.replaceAll("-{2,}", "-"); // Collapse multiple hyphens into one
        return url.replaceAll("^-|-$", ""); // Remove leading and trailing hyphens
    }





}
