package com.example.repository;

import com.example.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("""
            SELECT r FROM Request r
            LEFT JOIN FETCH r.user
            WHERE r.book.bookId = :bookId
            ORDER BY r.requestDate DESC
            """
    )
    List<Request> findRequestsByBookId(@Param("bookId") int bookId);
}
