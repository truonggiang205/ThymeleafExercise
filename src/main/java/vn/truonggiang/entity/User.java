package vn.truonggiang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User implements Serializable {
    
    @Id
    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 50, message = "Username phải từ 3-50 ký tự")
    @Column(name = "Username", nullable = false, length = 50)
    private String username;
    
    @NotBlank(message = "Password không được để trống")
    @Size(min = 6, message = "Password phải có ít nhất 6 ký tự")
    @Column(name = "Password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "Phone", length = 20)
    private String phone;
    
    @NotBlank(message = "Họ tên không được để trống")
    @Column(name = "Fullname", nullable = false, length = 100)
    private String fullname;
    
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Column(name = "Email", nullable = false, length = 100)
    private String email;
    
    @Column(name = "Admin", nullable = false)
    private Boolean admin = false;
    
    @Column(name = "Active", nullable = false)
    private Boolean active = true;
    
    @Column(name = "Images", length = 255)
    private String images;
}