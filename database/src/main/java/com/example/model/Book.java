package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_publication")
    private Integer bookPublication;

    @Column(name = "book_isbn")
    private String bookIsbn;

    @Column(name = "book_description")
    private String bookDescription;

    @Column(name = "book_available")
    private boolean bookAvailable;

    @Column(name = "book_quantity")
    private int bookQuantity;


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookCatalog> catalogs;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Rental> rentals;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Request> requests;
}
