package vn.truonggiang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.truonggiang.entity.Video;
import vn.truonggiang.repository.VideoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {
    
    private final VideoRepository videoRepository;
    
    public List<Video> findAll() {
        return videoRepository.findAll();
    }
    
    public List<Video> findActiveVideos() {
        return videoRepository.findByActiveTrue();
    }
    
    public Optional<Video> findById(Integer id) {
        return videoRepository.findById(id);
    }
    
    public Video save(Video video) {
        return videoRepository.save(video);
    }
    
    public void deleteById(Integer id) {
        videoRepository.deleteById(id);
    }
    
    public List<Video> findByCategory(Integer categoryId) {
        return videoRepository.findByCategoryCategoryId(categoryId);
    }
    
    public List<Video> search(String keyword) {
        return videoRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    public List<Video> findTopVideos() {
        return videoRepository.findTopVideos();
    }
    
    public void incrementViews(Integer videoId) {
        Optional<Video> videoOpt = videoRepository.findById(videoId);
        if (videoOpt.isPresent()) {
            Video video = videoOpt.get();
            video.setViews(video.getViews() + 1);
            videoRepository.save(video);
        }
    }
}