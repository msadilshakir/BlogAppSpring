package net.javaguides.Springboot_application.service.impl;

import net.javaguides.Springboot_application.dto.PostDto;
import net.javaguides.Springboot_application.entity.Comment;
import net.javaguides.Springboot_application.entity.Post;
import net.javaguides.Springboot_application.mapper.PostMapper;
import net.javaguides.Springboot_application.repository.CommentRepository;
import net.javaguides.Springboot_application.repository.PostRepository;
import net.javaguides.Springboot_application.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository,CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<PostDto> findAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(PostDto postDto) {
        Post post = PostMapper.mapToPost(postDto);
        postRepository.save(post);

    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = PostMapper.mapToPost(postDto);
        postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post  = postRepository.findById(postId).get();
        return PostMapper.mapToPostDto(post);

    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto findPostByUrl(String postUrl) {

        Post post = postRepository.findByUrl(postUrl).get();
        return PostMapper.mapToPostDto(post);
    }

    //When list of objects received from Database through query searched from View
    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        return posts.stream() //Streams provide a functional approach to process and transform collections.
                .map(PostMapper::mapToPostDto) //mapToPostDto is likely responsible for converting a Post entity into a PostDto object.
                .collect(Collectors.toList()); //The collect() method gathers the transformed stream elements into a new List<PostDto>.
    }

    @Override
    public String fetchPostNameFromComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("comment not found"));
        Post post = postRepository.findById(comment.getPost().getId()).orElseThrow(()-> new RuntimeException("Post not found"));
        return post.getTitle();
    }

    @Override
    public PostDto findPostByCommentId(Long commentId) {
        Post post = postRepository.findPostByCommentId(commentId);
        return PostMapper.mapToPostDto(post);
    }
}

