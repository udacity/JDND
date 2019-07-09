package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with {@link Comment} entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    public CommentsController(ReviewRepository reviewRepository, CommentRepository commentRepository) {
        this.reviewRepository = reviewRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * Creates {@link Comment} for a {@link Review}.
     *
     * @param reviewId The id of the review.
     * @param comment The comment to create.
     * @return the created comment or NOT_FOUND if review is not found.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<Comment> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment comment) {
        Optional<Review> optional = reviewRepository.findById(reviewId);
        if (optional.isPresent()) {
            comment.setReview(optional.get());
            return ResponseEntity.ok(commentRepository.save(comment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * List {@link Comment}s for a {@link Review}.
     *
     * @param reviewId The id of the review.
     * @return The list of comments.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        return ResponseEntity.ok(commentRepository.findAllByReview(new Review(reviewId)));
    }
}