package org.goods.goods.repository;

import org.goods.goods.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p FROM Product AS p
            WHERE ((:query) IS NULL OR lower(p.name) LIKE lower(concat('%', :query, '%')))
            ORDER BY p.name asc
            """)
    List<Product> findProductsSortByName(String query, Pageable pageable);

    @Query("""
            SELECT p FROM Product AS p
            WHERE ((:query) IS NULL OR lower(p.name) LIKE lower(concat('%', :query, '%')))
            ORDER BY p.cost ASC
            """)
    List<Product> findProductsSortByCost(String query, Pageable pageable);
}
