package vn.truonggiang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.truonggiang.entity.User;
import vn.truonggiang.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public Optional<User> findById(String username) {
        return userRepository.findById(username);
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
    
    public void deleteById(String username) {
        userRepository.deleteById(username);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
