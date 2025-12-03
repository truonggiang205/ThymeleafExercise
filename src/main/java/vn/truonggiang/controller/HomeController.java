package vn.truonggiang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.truonggiang.service.CategoryService;
import vn.truonggiang.service.VideoService;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final VideoService videoService;
    private final CategoryService categoryService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("videos", videoService.findTopVideos());
        model.addAttribute("categories", categoryService.findActiveCategories());
        return "index";
    }
}