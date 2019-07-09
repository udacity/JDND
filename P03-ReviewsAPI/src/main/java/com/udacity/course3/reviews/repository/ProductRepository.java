package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA Repository for {@link Product} entity.
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

}