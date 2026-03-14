package com.example.repository;

import com.example.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {

    @Query("SELECT c FROM Catalog c LEFT JOIN FETCH c.subCatalogs WHERE c.catalogParent IS NULL")
    List<Catalog> findAllCatalogParent();

}
