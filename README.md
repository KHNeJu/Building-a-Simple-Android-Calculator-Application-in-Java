# Scientific Calculator for Android

![Android](https://img.shields.io/badge/Android-Java-3DDC84?logo=android&logoColor=white)
![minSdk](https://img.shields.io/badge/minSdk-24-2196F3)
![targetSdk](https://img.shields.io/badge/targetSdk-35-FF9F0A)
![Build](https://img.shields.io/badge/build-passing-brightgreen)

Ứng dụng máy tính khoa học viết bằng Java và Android XML, sử dụng giao diện tối lấy cảm hứng từ Calculator trên iPhone. Ứng dụng hỗ trợ nhập biểu thức hoàn chỉnh, dấu ngoặc và đúng thứ tự ưu tiên phép tính.

## Tính năng

### Phép tính và biểu thức

- Cộng, trừ, nhân, chia và số thập phân.
- Dấu ngoặc và thứ tự ưu tiên phép tính.
- Phần trăm, đổi dấu âm/dương và xóa từng ký tự.
- Ký hiệu khoa học `EE`.
- Ví dụ: `(5 × 9) + sin(90) = 46`.

### Hàm khoa học

| Nhóm | Chức năng |
| --- | --- |
| Lũy thừa | `x²`, `x³`, `xʸ`, `eˣ`, `10ˣ` |
| Căn và nghịch đảo | `1/x`, `√x`, `∛x`, `ʸ√x` |
| Logarit | `ln`, `log₁₀` |
| Lượng giác | `sin`, `cos`, `tan` |
| Hyperbolic | `sinh`, `cosh`, `tanh` |
| Khác | `x!`, `Rand`, `π`, `e`, `Rad/Deg` |

### Bộ nhớ và điều khiển

- `mc`: xóa bộ nhớ.
- `m+` và `m−`: cộng hoặc trừ giá trị khỏi bộ nhớ.
- `mr`: gọi lại giá trị trong bộ nhớ.
- `AC`: xóa toàn bộ biểu thức.
- Nút menu: ẩn hoặc hiện nhóm chức năng khoa học.
- Nút lịch sử: xem lại các phép tính trong phiên hiện tại.

## Giao diện

- Nền đen, nút chức năng màu xám và nút phép toán màu cam.
- Lưới khoa học 6 cột và bàn phím cơ bản 4 cột.
- Biểu thức và kết quả được hiển thị riêng, căn phải.
- Tự xử lý vùng status bar và navigation bar trên Android mới.

## Công nghệ

- Java
- Android XML Layout
- Android SDK 35
- Android 7.0 trở lên (`minSdk 24`)
- Không sử dụng thư viện bên thứ ba

## Cấu trúc chính

```text
app/src/main/
├── java/com/example/simplecalculator/
│   ├── MainActivity.java       # Bàn phím, bộ nhớ, lịch sử và trạng thái UI
│   └── Calculator.java         # Parser biểu thức và các hàm toán học
└── res/
    ├── layout/activity_main_2.xml
    ├── drawable/calculator_button.xml
    └── values/                 # Màu sắc, chuỗi và style
```

## Chạy ứng dụng

1. Clone repository:

   ```bash
   git clone https://github.com/KHNeJu/Building-a-Simple-Android-Calculator-Application-in-Java.git
   ```

2. Mở thư mục vừa clone bằng Android Studio.
3. Chờ Gradle và Android SDK đồng bộ.
4. Chọn máy ảo hoặc thiết bị Android thật.
5. Nhấn **Run**.

## Kiểm tra

Parser có self-check cho thứ tự ưu tiên, lượng giác, lũy thừa, căn, giai thừa và logarit. Bản debug gần nhất đã build thành công và được chạy thử trực tiếp trên Android Emulator.

## Repository

[github.com/KHNeJu/Building-a-Simple-Android-Calculator-Application-in-Java](https://github.com/KHNeJu/Building-a-Simple-Android-Calculator-Application-in-Java)
