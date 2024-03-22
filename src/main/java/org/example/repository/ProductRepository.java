package org.example.repository;

import org.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
//named query
    List<Product> findByTitle(String title);

    @Query("from Product where title=:title")
    List<Product> findByTitle2(@Param("title") String title);
}