package net.javaguides.Springboot_application.repository;

import net.javaguides.Springboot_application.dto.PostDto;
import net.javaguides.Springboot_application.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByUrl(String url);

    //Spring Data JPA query using the @Query
    @Query("Select p from Post p WHERE " +  //custom JPQL (Java Persistence Query Language)
            "p.title LIKE CONCAT('%', :query, '%') OR " +
            "p.shortDescription LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query);

    @Query("SELECT c.post FROM Comment c WHERE c.id = :commentId")
    Post findPostByCommentId(Long commentId);

}
