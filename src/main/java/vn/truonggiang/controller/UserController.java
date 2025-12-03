package vn.truonggiang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.truonggiang.entity.User;
import vn.truonggiang.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user/form";
    }
    
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") User user, 
                           BindingResult result,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (result.hasErrors()) {
            return "user/form";
        }
        
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("emailError", "Email đã tồn tại");
            return "user/form";
        }
        
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "Thêm người dùng thành công!");
        return "redirect:/users";
    }
    
    @GetMapping("/edit/{username}")
    public String showEditForm(@PathVariable String username, Model model) {
        Optional<User> user = userService.findById(username);
        if (user.isEmpty()) {
            return "redirect:/users";
        }
        model.addAttribute("user", user.get());
        return "user/form";
    }
    
    @PostMapping("/edit/{username}")
    public String updateUser(@PathVariable String username,
                           @Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "user/form";
        }
        
        user.setUsername(username);
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "Cập nhật người dùng thành công!");
        return "redirect:/users";
    }
    
    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username, 
                           RedirectAttributes redirectAttributes) {
        userService.deleteById(username);
        redirectAttributes.addFlashAttribute("message", "Xóa người dùng thành công!");
        return "redirect:/users";
    }
}