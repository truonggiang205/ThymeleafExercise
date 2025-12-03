package vn.truonggiang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
public class Category implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private Integer categoryId;
    
    @NotBlank(message = "Tên danh mục không được để trống")
    @Column(name = "Categoryname", nullable = false, length = 100)
    private String categoryname;
    
    @Column(name = "Categorycode", length = 50)
    private String categorycode;
    
    @Column(name = "Images", length = 255)
    private String images;
    
    @Column(name = "Status", nullable = false)
    private Boolean status = true;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Video> videos;
}