package net.javaguides.Springboot_application.mapper;

import net.javaguides.Springboot_application.dto.CommentDto;
import net.javaguides.Springboot_application.entity.Comment;

public class CommentMapper {

    //convert comment entity to comment DTO

    public static CommentDto mapToCommentDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .content(comment.getEmail())
                .createdOn(comment.getCreatedOn())
                .updatedOn(comment.getUpdatedOn())

                .build();
    }

    //convert comment dto to comment entity

    public static Comment mapToCommentEntity(CommentDto commentDto){
        return Comment.builder()
                .id(commentDto.getId())
                .name(commentDto.getName())
                .email(commentDto.getEmail())
                .content(commentDto.getContent())
                .createdOn(commentDto.getCreatedOn())
                .updatedOn(commentDto.getUpdatedOn())
                .build();
    }
}
