package vn.truonggiang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`Videos`")
public class Video implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VideoId")
    private Integer videoId;
    
    @NotBlank(message = "Tiêu đề không được để trống")
    @Column(name = "Title", nullable = false, length = 200)
    private String title;
    
    @Column(name = "Poster", length = 255)
    private String poster;
    
    @Column(name = "Views")
    private Integer views = 0;
    
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "Active", nullable = false)
    private Boolean active = true;
    
    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category category;
}