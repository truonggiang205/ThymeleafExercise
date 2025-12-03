package vn.truonggiang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.truonggiang.entity.Video;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findByActiveTrue();
    List<Video> findByCategoryCategoryId(Integer categoryId);
    List<Video> findByTitleContainingIgnoreCase(String keyword);
    
    @Query("SELECT v FROM Video v WHERE v.active = true ORDER BY v.views DESC")
    List<Video> findTopVideos();
}