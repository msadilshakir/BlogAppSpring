package net.javaguides.Springboot_application.service.impl;

import net.javaguides.Springboot_application.dto.CommentDto;
import net.javaguides.Springboot_application.dto.PostDto;
import net.javaguides.Springboot_application.entity.Comment;
import net.javaguides.Springboot_application.entity.Post;
import net.javaguides.Springboot_application.mapper.CommentMapper;
import net.javaguides.Springboot_application.mapper.PostMapper;
import net.javaguides.Springboot_application.repository.CommentRepository;
import net.javaguides.Springboot_application.repository.PostRepository;
import net.javaguides.Springboot_application.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void createComment(String postUrl, CommentDto commentDto) {
        Post post = postRepository.findByUrl(postUrl).get();
        Comment comment = CommentMapper.mapToCommentEntity(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);

    }

    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(CommentMapper::mapToCommentDto).collect(Collectors.toList());
    }

    @Override
    public String deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return null;
    }

    @Override
    public List<CommentDto> searchComments(String query) {
        List<Comment> comments = commentRepository.searchComments(query);
        return comments.stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }


}
