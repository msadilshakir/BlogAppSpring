package net.javaguides.Springboot_application.repository;


import net.javaguides.Springboot_application.entity.Comment;
import net.javaguides.Springboot_application.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("Select p from Comment p WHERE " +
            "p.name LIKE CONCAT('%', :query, '%') OR " +
            "p.email LIKE CONCAT('%', :query, '%')")
    List<Comment> searchComments(String query);


}
