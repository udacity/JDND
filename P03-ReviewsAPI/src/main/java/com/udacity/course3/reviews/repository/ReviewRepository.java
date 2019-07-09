package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for {@link Review} entity.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    /**
     * Finds all {@link Review} for a product.
     *
     * @param product The {@link Product} object.
     * @return The list of reviews for the product.
     */
    List<Review> findAllByProduct(Product product);
}