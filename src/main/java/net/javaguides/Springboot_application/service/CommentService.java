package net.javaguides.Springboot_application.service;

import net.javaguides.Springboot_application.dto.CommentDto;
import net.javaguides.Springboot_application.dto.PostDto;
import net.javaguides.Springboot_application.entity.Post;

import java.util.List;

public interface CommentService {
    void createComment(String postUrl, CommentDto commentDto);
    List<CommentDto> findAllComments();
    String deleteComment(Long commentId);

    List<CommentDto> searchComments(String query);

}


