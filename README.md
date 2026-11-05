# Project 1 MB – Máy tính đơn giản

Project Android Java thực hành tạo giao diện bằng XML và xây dựng máy tính đơn giản.

## Tác giả

**zunk**

## Chức năng

- Nhập hai số.
- Thực hiện cộng, trừ, nhân và chia.
- Kiểm tra dữ liệu trống, dữ liệu không hợp lệ và phép chia cho 0.
- Có `demo.html` để chạy thử trên trình duyệt khi chưa cài Android Studio.

## Chạy bản demo bằng VS Code

Mở Terminal tại thư mục project rồi chạy:

```powershell
start demo.html
```

## Chạy ứng dụng Android

1. Mở thư mục project bằng Android Studio.
2. Đồng bộ Gradle.
3. Tạo thiết bị ảo trong Device Manager.
4. Chọn thiết bị và nhấn **Run**.

## Đưa project lên GitHub

Tạo repository mới tên `project_1_MB` trên GitHub. Không chọn tạo thêm README, `.gitignore` hoặc License vì project đã có sẵn file cục bộ.

Mở Terminal của VS Code tại thư mục project và chạy:

```powershell
git init -b main
git add .
git commit -m "Khoi tao project calculator"
git remote add origin https://github.com/TEN_TAI_KHOAN/project_1_MB.git
git push -u origin main
```

Thay `TEN_TAI_KHOAN` bằng tên tài khoản GitHub của bạn. Không đưa mật khẩu, token hoặc khóa bí mật vào project.
