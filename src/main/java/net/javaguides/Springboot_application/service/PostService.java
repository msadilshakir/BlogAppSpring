package net.javaguides.Springboot_application.service;

import net.javaguides.Springboot_application.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> findAllPosts();
    void createPost(PostDto postDto);
    void updatePost(PostDto postDto);
    PostDto findPostById(Long postId);
    void  deletePost(Long postId);

    PostDto findPostByUrl(String postUrl);

    List<PostDto> searchPosts(String query);
    public String fetchPostNameFromComment(Long commentId);
    PostDto findPostByCommentId(Long commentId);
}