package com.example.repository;

import com.example.model.BookCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCatalogRepository extends JpaRepository<BookCatalog, Integer> {
}
