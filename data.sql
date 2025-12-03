-- =============================================
-- Video Management System - MySQL Database
-- Fixed for MySQL Syntax
-- =============================================

-- Drop database if exists
DROP DATABASE IF EXISTS videomanagement;

-- Create database
CREATE DATABASE videomanagement 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Use database
USE videomanagement;

-- =============================================
-- Table: Users
-- =============================================
CREATE TABLE Users (
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(255) NOT NULL,
    Phone VARCHAR(20),
    Fullname VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Admin BOOLEAN DEFAULT FALSE,
    Active BOOLEAN DEFAULT TRUE,
    Images VARCHAR(255),
    INDEX idx_email (Email),
    INDEX idx_active (Active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- Table: Category
-- =============================================
CREATE TABLE Category (
    CategoryId INT AUTO_INCREMENT PRIMARY KEY,
    Categoryname VARCHAR(100) NOT NULL,
    Categorycode VARCHAR(50),
    Images VARCHAR(255),
    Status BOOLEAN DEFAULT TRUE,
    INDEX idx_status (Status),
    INDEX idx_categoryname (Categoryname)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- Table: Videos
-- =============================================
CREATE TABLE Videos (
    VideoId INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(200) NOT NULL,
    Poster VARCHAR(255),
    Views INT DEFAULT 0,
    Description TEXT,
    Active BOOLEAN DEFAULT TRUE,
    CategoryId INT NOT NULL,
    FOREIGN KEY (CategoryId) REFERENCES Category(CategoryId) ON DELETE CASCADE,
    INDEX idx_categoryid (CategoryId),
    INDEX idx_active (Active),
    INDEX idx_views (Views),
    INDEX idx_title (Title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- Insert Sample Data - Users
-- =============================================
INSERT INTO Users (Username, Password, Phone, Fullname, Email, Admin, Active, Images) VALUES
('admin', '123456', '0901234567', 'Nguyễn Văn Admin', 'admin@gmail.com', TRUE, TRUE, 'https://i.pravatar.cc/150?img=12'),
('user01', '123456', '0912345678', 'Trần Thị Hương', 'huong@gmail.com', FALSE, TRUE, 'https://i.pravatar.cc/150?img=5'),
('user02', '123456', '0923456789', 'Lê Văn Nam', 'nam@gmail.com', FALSE, TRUE, 'https://i.pravatar.cc/150?img=8'),
('user03', '123456', '0934567890', 'Phạm Thị Mai', 'mai@gmail.com', FALSE, FALSE, 'https://i.pravatar.cc/150?img=9'),
('user04', '123456', '0945678901', 'Hoàng Văn Đức', 'duc@gmail.com', FALSE, TRUE, 'https://i.pravatar.cc/150?img=11');

-- =============================================
-- Insert Sample Data - Category
-- =============================================
INSERT INTO Category (Categoryname, Categorycode, Images, Status) VALUES
('Công nghệ', 'TECH', 'https://images.unsplash.com/photo-1518770660439-4636190af475?w=400', TRUE),
('Giải trí', 'ENT', 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=400', TRUE),
('Giáo dục', 'EDU', 'https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=400', TRUE),
('Thể thao', 'SPORT', 'https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=400', TRUE),
('Du lịch', 'TRAVEL', 'https://images.unsplash.com/photo-1488646953014-85cb44e25828?w=400', TRUE),
('Ẩm thực', 'FOOD', 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=400', TRUE),
('Âm nhạc', 'MUSIC', 'https://images.unsplash.com/photo-1511379938547-c1f69419868d?w=400', TRUE),
('Phim ảnh', 'MOVIE', 'https://images.unsplash.com/photo-1485846234645-a62644f84728?w=400', TRUE);

-- =============================================
-- Insert Sample Data - Videos
-- =============================================
INSERT INTO Videos (Title, Poster, Views, Description, Active, CategoryId) VALUES
-- Technology Videos
('Lập trình Python cơ bản cho người mới', 'https://images.unsplash.com/photo-1526379095098-d400fd0bf935?w=600', 15420, 'Khóa học Python từ cơ bản đến nâng cao, phù hợp cho người mới bắt đầu học lập trình.', TRUE, 1),
('Hướng dẫn React JS 2024', 'https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=600', 12350, 'Học React JS hiện đại với hooks, context API và các best practices mới nhất.', TRUE, 1),
('AI và Machine Learning', 'https://images.unsplash.com/photo-1677442136019-21780ecad995?w=600', 9876, 'Giới thiệu về trí tuệ nhân tạo và machine learning trong thời đại 4.0.', TRUE, 1),
('DevOps cho người mới', 'https://images.unsplash.com/photo-1667372393119-3d4c48d07fc9?w=600', 7654, 'Tìm hiểu về DevOps, CI/CD và automation trong phát triển phần mềm.', TRUE, 1),

-- Entertainment Videos
('Top 10 phim hay nhất 2024', 'https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=600', 25000, 'Review và giới thiệu 10 bộ phim được đánh giá cao nhất năm 2024.', TRUE, 2),
('Nhạc Việt remix hay nhất', 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=600', 18765, 'Tuyển tập những ca khúc remix Việt Nam được yêu thích nhất.', TRUE, 2),
('Stand-up Comedy Việt Nam', 'https://images.unsplash.com/photo-1585699324551-f6c309eedeca?w=600', 14321, 'Chương trình hài độc thoại với những nghệ sĩ nổi tiếng Việt Nam.', TRUE, 2),

-- Education Videos
('Tiếng Anh giao tiếp cơ bản', 'https://images.unsplash.com/photo-1546410531-bb4caa6b424d?w=600', 22100, 'Khóa học tiếng Anh giao tiếp cho người đi làm và sinh viên.', TRUE, 3),
('Toán học lớp 12 - Ôn thi THPT', 'https://images.unsplash.com/photo-1509228468518-180dd4864904?w=600', 16543, 'Ôn tập và luyện đề toán học cho kỳ thi THPT Quốc gia.', TRUE, 3),
('Lịch sử Việt Nam qua các thời kỳ', 'https://images.unsplash.com/photo-1461360370896-922624d12aa1?w=600', 11234, 'Tìm hiểu về lịch sử dân tộc Việt Nam từ xa xưa đến nay.', TRUE, 3),

-- Sports Videos
('Kỹ thuật bóng đá cơ bản', 'https://images.unsplash.com/photo-1574629810360-7efbbe195018?w=600', 19876, 'Hướng dẫn các kỹ thuật bóng đá cơ bản cho người mới chơi.', TRUE, 4),
('Tập Gym hiệu quả tại nhà', 'https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=600', 13450, 'Bài tập gym không cần dụng cụ, có thể tập tại nhà hiệu quả.', TRUE, 4),
('Yoga buổi sáng năng lượng', 'https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=600', 15678, 'Bài tập yoga buổi sáng giúp tràn đầy năng lượng cả ngày.', TRUE, 4),

-- Travel Videos
('Du lịch Đà Lạt tự túc', 'https://images.unsplash.com/photo-1583417319070-4a69db38a482?w=600', 21000, 'Hướng dẫn du lịch Đà Lạt tự túc với chi phí tiết kiệm.', TRUE, 5),
('Khám phá Hà Nội 36 phố phường', 'https://images.unsplash.com/photo-1509023464722-18d996393ca8?w=600', 17890, 'Khám phá văn hóa và ẩm thực tại khu phố cổ Hà Nội.', TRUE, 5),
('Review resort Phú Quốc 5 sao', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600', 14567, 'Review chi tiết các resort 5 sao tại đảo ngọc Phú Quốc.', TRUE, 5),

-- Food Videos
('Cách làm phở bò chuẩn vị', 'https://images.unsplash.com/photo-1555126634-323283e090fa?w=600', 24567, 'Hướng dẫn nấu phở bò Hà Nội truyền thống chuẩn vị.', TRUE, 6),
('Top 10 món ăn vặt Sài Gòn', 'https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=600', 19234, 'Review 10 món ăn vặt ngon và nổi tiếng tại Sài Gòn.', TRUE, 6),
('Làm bánh mì Việt Nam tại nhà', 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=600', 16789, 'Hướng dẫn làm bánh mì Việt Nam thơm ngon giòn tan.', TRUE, 6),

-- Music Videos
('Guitar cơ bản cho người mới', 'https://images.unsplash.com/photo-1510915361894-db8b60106cb1?w=600', 18900, 'Khóa học guitar cơ bản từ A-Z cho người mới bắt đầu.', TRUE, 7),
('Top Vpop hits 2024', 'https://images.unsplash.com/photo-1507838153414-b4b713384a76?w=600', 22345, 'Tuyển tập những ca khúc Vpop hay nhất năm 2024.', TRUE, 7),
('Piano cover hits Châu Á', 'https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?w=600', 15432, 'Piano cover những bản hit nổi tiếng của Châu Á.', TRUE, 7),

-- Movie Videos
('Review phim Marvel mới nhất', 'https://images.unsplash.com/photo-1536440136628-849c177e76a1?w=600', 28900, 'Review chi tiết bộ phim Marvel mới nhất trong vũ trụ điện ảnh.', TRUE, 8),
('Phân tích phim kinh dị Hàn Quốc', 'https://images.unsplash.com/photo-1574267432553-4b4628081c31?w=600', 20100, 'Phân tích và đánh giá các bộ phim kinh dị Hàn Quốc hay nhất.', TRUE, 8),
('Những bộ phim hoạt hình kinh điển', 'https://images.unsplash.com/photo-1478720568477-152d9b164e26?w=600', 17654, 'Tổng hợp những bộ phim hoạt hình kinh điển của Disney và Pixar.', TRUE, 8);

-- =============================================
-- Verify Data
-- =============================================
SELECT '========================================' as '';
SELECT 'DATABASE CREATED SUCCESSFULLY!' as 'Status';
SELECT '========================================' as '';

SELECT 'TABLES SUMMARY:' as '';
SELECT 'Users Table' as TableName, COUNT(*) as RecordCount FROM Users
UNION ALL
SELECT 'Category Table', COUNT(*) FROM Category
UNION ALL
SELECT 'Videos Table', COUNT(*) FROM Videos;

SELECT '========================================' as '';
SELECT 'SAMPLE DATA:' as '';
SELECT '========================================' as '';

-- Show sample users
SELECT 'Sample Users (password = 123456):' as Info;
SELECT Username, Fullname, Email, 
       CASE WHEN Admin = 1 THEN 'Admin' ELSE 'User' END as Role,
       CASE WHEN Active = 1 THEN 'Active' ELSE 'Inactive' END as Status 
FROM Users;

SELECT '========================================' as '';

-- Show categories
SELECT 'Categories:' as Info;
SELECT CategoryId, Categoryname, Categorycode, 
       CASE WHEN Status = 1 THEN 'Active' ELSE 'Inactive' END as Status 
FROM Category;

SELECT '========================================' as '';

-- Show top videos
SELECT 'Top 10 Videos by Views:' as Info;
SELECT v.VideoId, v.Title, c.Categoryname, v.Views, 
       CASE WHEN v.Active = 1 THEN 'Active' ELSE 'Inactive' END as Status 
FROM Videos v 
JOIN Category c ON v.CategoryId = c.CategoryId 
ORDER BY v.Views DESC 
LIMIT 10;

SELECT '========================================' as '';
SELECT 'DATABASE SETUP COMPLETE!' as 'Status';
SELECT 'You can now start your Spring Boot application!' as 'Next Step';
SELECT '========================================' as '';

DESCRIBE Users;

-- Kiểm tra số lượng
SELECT COUNT(*) FROM Users;