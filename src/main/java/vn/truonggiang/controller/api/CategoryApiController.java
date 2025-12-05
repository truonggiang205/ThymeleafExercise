package vn.truonggiang.controller.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import vn.truonggiang.entity.Category;
import vn.truonggiang.model.Response;
import vn.truonggiang.service.CategoryService;
import vn.truonggiang.service.IStorageService;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryApiController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private IStorageService storageService;
    
    // Get all categories
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", category.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(false, "Không tìm thấy category", null), HttpStatus.NOT_FOUND);
    }
    
    // Search with pagination
    @GetMapping("/search")
    public ResponseEntity<?> searchCategories(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("categoryId").descending());
        Page<Category> categoryPage;
        
        if (keyword != null && !keyword.isEmpty()) {
            categoryPage = categoryService.searchWithPagination(keyword, pageable);
        } else {
            categoryPage = categoryService.findAllPaginated(pageable);
        }
        
        return new ResponseEntity<>(new Response(true, "Thành công", categoryPage), HttpStatus.OK);
    }
    
    // Add category
    @PostMapping
    public ResponseEntity<?> addCategory(
            @Validated @RequestParam("categoryname") String categoryname,
            @Validated @RequestParam("categorycode") String categorycode,
            @RequestParam(value = "images", required = false) MultipartFile images,
            @RequestParam(value = "status", defaultValue = "true") Boolean status) {
        
        Category category = new Category();
        category.setCategoryname(categoryname);
        category.setCategorycode(categorycode);
        category.setStatus(status);
        
        // Handle image upload
        if (images != null && !images.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String filename = storageService.getStorageFilename(images, uuid.toString());
            storageService.store(images, filename);
            category.setImages("/uploads/" + filename);
        }
        
        Category savedCategory = categoryService.save(category);
        return new ResponseEntity<>(new Response(true, "Thêm category thành công", savedCategory), HttpStatus.CREATED);
    }
    
    // Update category
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Integer id,
            @Validated @RequestParam("categoryname") String categoryname,
            @Validated @RequestParam("categorycode") String categorycode,
            @RequestParam(value = "images", required = false) MultipartFile images,
            @RequestParam(value = "status", defaultValue = "true") Boolean status) {
        
        Optional<Category> optCategory = categoryService.findById(id);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy category", null), HttpStatus.NOT_FOUND);
        }
        
        Category category = optCategory.get();
        category.setCategoryname(categoryname);
        category.setCategorycode(categorycode);
        category.setStatus(status);
        
        // Handle image upload
        if (images != null && !images.isEmpty()) {
            // Delete old image if exists
            if (category.getImages() != null) {
                try {
                    String oldFilename = category.getImages().substring(category.getImages().lastIndexOf("/") + 1);
                    storageService.delete(oldFilename);
                } catch (Exception e) {
                    // Ignore if file doesn't exist
                }
            }
            
            UUID uuid = UUID.randomUUID();
            String filename = storageService.getStorageFilename(images, uuid.toString());
            storageService.store(images, filename);
            category.setImages("/uploads/" + filename);
        }
        
        Category updatedCategory = categoryService.save(category);
        return new ResponseEntity<>(new Response(true, "Cập nhật thành công", updatedCategory), HttpStatus.OK);
    }
    
    // Delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy category", null), HttpStatus.NOT_FOUND);
        }
        
        // Delete image file if exists
        if (category.get().getImages() != null) {
            try {
                String filename = category.get().getImages().substring(category.get().getImages().lastIndexOf("/") + 1);
                storageService.delete(filename);
            } catch (Exception e) {
                // Ignore if file doesn't exist
            }
        }
        
        categoryService.deleteById(id);
        return new ResponseEntity<>(new Response(true, "Xóa thành công", category.get()), HttpStatus.OK);
    }
}