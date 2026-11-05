# Simple Calculator

![Android](https://img.shields.io/badge/Android-Java-3DDC84?logo=android&logoColor=white)
![minSdk](https://img.shields.io/badge/minSdk-24-2196F3)
![targetSdk](https://img.shields.io/badge/targetSdk-35-FF9F0A)

Ứng dụng máy tính Android viết bằng Java và XML, lấy cảm hứng từ giao diện Calculator trên iPhone.

## Điểm nổi bật

- Giao diện nền đen tối giản với các phím số hình tròn.
- Phân nhóm nút bằng màu xám, xám nhạt và cam đặc trưng.
- Hỗ trợ cộng, trừ, nhân, chia và số thập phân.
- Có các thao tác `AC`, đổi dấu `+/−` và phần trăm `%`.
- Hiển thị lỗi khi chia cho `0`.
- Không sử dụng thư viện giao diện bên thứ ba.

## Bố cục bàn phím

| Nhóm | Chức năng |
| --- | --- |
| `AC` · `+/−` · `%` | Xóa, đổi dấu và tính phần trăm |
| `÷` · `×` · `−` · `+` | Các phép toán cơ bản |
| `0`–`9` · `.` | Nhập số |
| `=` | Hiển thị kết quả |

## Công nghệ

- Java
- Android XML Layout
- Android SDK 35
- Hỗ trợ từ Android 7.0 (`minSdk 24`)

## Cấu trúc chính

```text
app/src/main/
├── java/com/example/simplecalculator/
│   ├── MainActivity.java       # Xử lý bàn phím và trạng thái màn hình
│   └── Calculator.java         # Thực hiện các phép tính
└── res/
    ├── layout/activity_main_2.xml
    ├── drawable/calculator_button.xml
    └── values/                 # Màu sắc, chuỗi và giao diện
```

## Chạy ứng dụng

1. Clone repository:

   ```bash
   git clone https://github.com/KHNeJu/Building-a-Simple-Android-Calculator-Application-in-Java.git
   ```

2. Mở thư mục vừa clone bằng Android Studio.
3. Chờ Android Studio đồng bộ Gradle.
4. Chọn máy ảo hoặc thiết bị Android thật.
5. Nhấn **Run** để khởi động ứng dụng.

## Repository

[github.com/KHNeJu/Building-a-Simple-Android-Calculator-Application-in-Java](https://github.com/KHNeJu/Building-a-Simple-Android-Calculator-Application-in-Java)
