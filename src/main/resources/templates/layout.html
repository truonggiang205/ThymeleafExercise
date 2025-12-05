package vn.truonggiang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.truonggiang.entity.Video;
import vn.truonggiang.service.CategoryService;
import vn.truonggiang.service.VideoService;

import java.util.Optional;

@Controller
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
    
    private final VideoService videoService;
    private final CategoryService categoryService;
    
    @GetMapping
    public String listVideos(Model model,
                           @RequestParam(required = false) String keyword,
                           @RequestParam(required = false) Integer categoryId) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("videos", videoService.search(keyword));
            model.addAttribute("keyword", keyword);
        } else if (categoryId != null) {
            model.addAttribute("videos", videoService.findByCategory(categoryId));
            model.addAttribute("categoryId", categoryId);
        } else {
            model.addAttribute("videos", videoService.findAll());
        }
        model.addAttribute("categories", categoryService.findAll());
        return "video/list";
    }
    
    // AJAX Page
    @GetMapping("/ajax")
    public String ajaxPage(Model model) {
        return "video/ajax";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("video", new Video());
        model.addAttribute("categories", categoryService.findActiveCategories());
        return "video/form";
    }
    
    @PostMapping("/create")
    public String createVideo(@Valid @ModelAttribute("video") Video video,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findActiveCategories());
            return "video/form";
        }
        
        videoService.save(video);
        redirectAttributes.addFlashAttribute("message", "Thêm video thành công!");
        return "redirect:/videos";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Video> video = videoService.findById(id);
        if (video.isEmpty()) {
            return "redirect:/videos";
        }
        model.addAttribute("video", video.get());
        model.addAttribute("categories", categoryService.findActiveCategories());
        return "video/form";
    }
    
    @PostMapping("/edit/{id}")
    public String updateVideo(@PathVariable Integer id,
                            @Valid @ModelAttribute("video") Video video,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findActiveCategories());
            return "video/form";
        }
        
        video.setVideoId(id);
        videoService.save(video);
        redirectAttributes.addFlashAttribute("message", "Cập nhật video thành công!");
        return "redirect:/videos";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteVideo(@PathVariable Integer id,
                            RedirectAttributes redirectAttributes) {
        videoService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa video thành công!");
        return "redirect:/videos";
    }
    
    @GetMapping("/view/{id}")
    public String viewVideo(@PathVariable Integer id, Model model) {
        Optional<Video> video = videoService.findById(id);
        if (video.isEmpty()) {
            return "redirect:/videos";
        }
        
        videoService.incrementViews(id);
        model.addAttribute("video", video.get());
        return "video/view";
    }
}