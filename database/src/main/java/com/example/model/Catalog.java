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
@Table(name = "catalogs")
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_id")
    private int catalogId;

    @Column(name = "catalog_title", nullable = false)
    private String catalogTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_parent_id")
    private Catalog catalogParent;


    @OneToMany(mappedBy = "catalogParent", fetch = FetchType.LAZY)
    private List<Catalog> subCatalogs;

    @OneToMany(mappedBy = "catalog",fetch = FetchType.LAZY)
    private List<BookCatalog> books;

}
