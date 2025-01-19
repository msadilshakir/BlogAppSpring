package net.javaguides.Springboot_application.controller;

import net.javaguides.Springboot_application.config.WebSpringSecurity;
import net.javaguides.Springboot_application.dto.CommentDto;
import net.javaguides.Springboot_application.dto.PostDto;
import net.javaguides.Springboot_application.service.PostService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {

    private PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    //handler method to handle view all post
    @GetMapping("/")
    public String viewBlogPosts(Model model) {
        List<PostDto> postsResponse = postService.findAllPosts();
        model.addAttribute("postsResponse", postsResponse);
        if(WebSpringSecurity.isUserLoggedIn()){
            return "/blog/view_posts_after_login";
        }else{
            return "blog/view_posts";
        }

    }

    @GetMapping("/post/{postUrl}")
    private String showPost(@PathVariable("postUrl") String postUrl, Model model){
        PostDto post = postService.findPostByUrl(postUrl);

        CommentDto commentDto = new CommentDto();
        model.addAttribute("post", post);
        model.addAttribute("comment", commentDto);
        return "/blog/blog_post";
    }

    //handler method to handle blog search
    @GetMapping("/page/search")
    public String searchPosts(@RequestParam(value= "query") String query, Model model){
        List<PostDto> postsResponse = postService.searchPosts(query);
        model.addAttribute("postsResponse", postsResponse);
        return "blog/view_posts";
    }

    @GetMapping("/test")
    public String test(){
        return "blog/test";
    }


}


