package vn.truonggiang;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import vn.truonggiang.config.StorageProperties;
import vn.truonggiang.service.IStorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ThymeleafTestApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ThymeleafTestApplication.class, args);
    }
    
    @Bean
    CommandLineRunner init(IStorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
}