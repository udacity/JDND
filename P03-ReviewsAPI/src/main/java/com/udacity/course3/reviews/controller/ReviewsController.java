package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with {@link Review} entity.
 */
@RestController
public class ReviewsController {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewsController(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    /**
     * Creates a {@link Review} for a {@link Product}.
     *
     * @param productId The id of the product.
     * @param review The {@link Review} to create.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Review> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody Review review) {
        Optional<Product> optional = productRepository.findById(productId);
        if (optional.isPresent()) {
            review.setProduct(optional.get());
            return ResponseEntity.ok(reviewRepository.save(review));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(reviewRepository.findAllByProduct(new Product(productId)));
    }
}