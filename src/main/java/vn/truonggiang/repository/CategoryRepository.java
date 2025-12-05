package vn.truonggiang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.truonggiang.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByStatusTrue();
    List<Category> findByCategorynameContainingIgnoreCase(String keyword);
    
    // Pagination
    Page<Category> findByCategorynameContainingIgnoreCase(String keyword, Pageable pageable);
}