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
import vn.truonggiang.entity.Video;
import vn.truonggiang.model.Response;
import vn.truonggiang.service.CategoryService;
import vn.truonggiang.service.IStorageService;
import vn.truonggiang.service.VideoService;

@RestController
@RequestMapping(path = "/api/videos")
public class VideoApiController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private IStorageService storageService;
    
    // Get all videos
    @GetMapping
    public ResponseEntity<?> getAllVideos() {
        List<Video> videos = videoService.findAll();
        return new ResponseEntity<>(new Response(true, "Thành công", videos), HttpStatus.OK);
    }
    
    // Get video by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable Integer id) {
        Optional<Video> video = videoService.findById(id);
        if (video.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", video.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(false, "Không tìm thấy video", null), HttpStatus.NOT_FOUND);
    }
    
    // Search videos with pagination
    @GetMapping("/search")
    public ResponseEntity<?> searchVideos(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "videoId") String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Video> videoPage;
        
        if (keyword != null && !keyword.isEmpty()) {
            videoPage = videoService.searchByTitle(keyword, pageable);
        } else if (categoryId != null) {
            videoPage = videoService.findByCategoryId(categoryId, pageable);
        } else {
            videoPage = videoService.findAllPaginated(pageable);
        }
        
        return new ResponseEntity<>(new Response(true, "Thành công", videoPage), HttpStatus.OK);
    }
    
    // Add new video
    @PostMapping
    public ResponseEntity<?> addVideo(
            @Validated @RequestParam("title") String title,
            @RequestParam(value = "poster", required = false) MultipartFile poster,
            @RequestParam(value = "description", required = false) String description,
            @Validated @RequestParam("categoryId") Integer categoryId,
            @RequestParam(value = "active", defaultValue = "true") Boolean active) {
        
        Optional<Category> category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Category không tồn tại", null), HttpStatus.BAD_REQUEST);
        }
        
        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setActive(active);
        video.setViews(0);
        video.setCategory(category.get());
        
        // Handle poster upload
        if (poster != null && !poster.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String filename = storageService.getStorageFilename(poster, uuid.toString());
            storageService.store(poster, filename);
            video.setPoster("/uploads/" + filename);
        }
        
        Video savedVideo = videoService.save(video);
        return new ResponseEntity<>(new Response(true, "Thêm video thành công", savedVideo), HttpStatus.CREATED);
    }
    
    // Update video
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVideo(
            @PathVariable Integer id,
            @Validated @RequestParam("title") String title,
            @RequestParam(value = "poster", required = false) MultipartFile poster,
            @RequestParam(value = "description", required = false) String description,
            @Validated @RequestParam("categoryId") Integer categoryId,
            @RequestParam(value = "active", defaultValue = "true") Boolean active) {
        
        Optional<Video> optVideo = videoService.findById(id);
        if (optVideo.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy video", null), HttpStatus.NOT_FOUND);
        }
        
        Optional<Category> category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Category không tồn tại", null), HttpStatus.BAD_REQUEST);
        }
        
        Video video = optVideo.get();
        video.setTitle(title);
        video.setDescription(description);
        video.setActive(active);
        video.setCategory(category.get());
        
        // Handle poster upload
        if (poster != null && !poster.isEmpty()) {
            // Delete old poster if exists
            if (video.getPoster() != null) {
                try {
                    String oldFilename = video.getPoster().substring(video.getPoster().lastIndexOf("/") + 1);
                    storageService.delete(oldFilename);
                } catch (Exception e) {
                    // Ignore if file doesn't exist
                }
            }
            
            UUID uuid = UUID.randomUUID();
            String filename = storageService.getStorageFilename(poster, uuid.toString());
            storageService.store(poster, filename);
            video.setPoster("/uploads/" + filename);
        }
        
        Video updatedVideo = videoService.save(video);
        return new ResponseEntity<>(new Response(true, "Cập nhật thành công", updatedVideo), HttpStatus.OK);
    }
    
    // Delete video
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable Integer id) {
        Optional<Video> video = videoService.findById(id);
        if (video.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy video", null), HttpStatus.NOT_FOUND);
        }
        
        // Delete poster file if exists
        if (video.get().getPoster() != null) {
            try {
                String filename = video.get().getPoster().substring(video.get().getPoster().lastIndexOf("/") + 1);
                storageService.delete(filename);
            } catch (Exception e) {
                // Ignore if file doesn't exist
            }
        }
        
        videoService.deleteById(id);
        return new ResponseEntity<>(new Response(true, "Xóa thành công", video.get()), HttpStatus.OK);
    }
}