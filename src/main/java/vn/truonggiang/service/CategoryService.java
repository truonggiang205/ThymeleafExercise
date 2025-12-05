package vn.truonggiang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.truonggiang.entity.Category;
import vn.truonggiang.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    public List<Category> findActiveCategories() {
        return categoryRepository.findByStatusTrue();
    }
    
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }
    
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
    
    public List<Category> search(String keyword) {
        return categoryRepository.findByCategorynameContainingIgnoreCase(keyword);
    }
    
    // Pagination methods
    public Page<Category> findAllPaginated(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
    
    public Page<Category> searchWithPagination(String keyword, Pageable pageable) {
        return categoryRepository.findByCategorynameContainingIgnoreCase(keyword, pageable);
    }
}