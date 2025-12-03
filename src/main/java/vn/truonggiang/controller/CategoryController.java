package vn.truonggiang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.truonggiang.entity.Category;
import vn.truonggiang.service.CategoryService;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    public String listCategories(Model model, 
                               @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("categories", categoryService.search(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("categories", categoryService.findAll());
        }
        return "category/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/form";
    }
    
    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("category") Category category,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "category/form";
        }
        
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Thêm danh mục thành công!");
        return "redirect:/categories";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isEmpty()) {
            return "redirect:/categories";
        }
        model.addAttribute("category", category.get());
        return "category/form";
    }
    
    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Integer id,
                               @Valid @ModelAttribute("category") Category category,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "category/form";
        }
        
        category.setCategoryId(id);
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thành công!");
        return "redirect:/categories";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id,
                               RedirectAttributes redirectAttributes) {
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
        return "redirect:/categories";
    }
}