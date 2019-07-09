package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA Repository for {@link Comment} entity.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * Finds all {@link Comment} for a review.
     *
     * @param review The {@link Review} object.
     * @return The list of comments for the review.
     */
    List<Comment> findAllByReview(Review review);
}