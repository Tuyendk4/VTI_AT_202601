# Các bạn có thể ôn luyện các câu hỏi phỏng vấn qua các app trên thiết bị android

```text
1. Selenium Interview Questions
2. AutomationInterviewQuestions
```

# Java

## 1. Sự khác biệt giữa JDK, JRE và JVM là gì?

```text
JVM (Java Virtual Machine) cung cấp môi trường thực thi các chương trình Java.
JRE (Java Runtime Enviroment) bao gồm các thư viện và các file mà JVM sử dụng lúc thực thi.
JDK (Java Development Kit) gồm JRE + công cụ phát triển ứng dụng Java.
```

## 2. Tại sao Java là độc lập nền tảng?

```text
Chúng ta có thể chạy mã nguồn Java trên mọi hệ điều hành, nghĩa là nếu chúng ta viết và biên dịch Java code trên hệ điều hành windows thì cũng có thể chạy cùng code này trên hệ điều hành khác như Linux, MacOS.
```

## 3. Bạn có thể giải thích "public static void main(String[] args)"?

```text
public là một Access modifier mà có thể được truy cập ở mọi nơi
static là một từ khóa đặc biệt, được cấp phát bộ nhớ ngay khi chạy chương trình và hàm này được gọi bởi class
void là kiểu trả về
main là tên method, có mức ưu tiên cao nhất khi JVM thực thi mã nguồn, và chương trình sẽ không được thực thi nếu thiếu hàm này.
agrs là đối số truyền vào có kiểu dữ liệu dạng mảng String
```

## 4. Chúng ta có thể override hàm main không?

```text
Chúng ta có thể overload hàm main nhưng không override hàm main vì hàm main là hàm static
```

## 5. Trong Java, truyền tham chiếu (pass by reference) hay truyền tham trị (pass by value)?

```text
Java luôn luôn là truyền tham trị (pass by value). Tuy nhiên, khi chúng ta truyền tham trị của một đối tượng tức là chúng ta đang truyền tham chiếu tới nó
```

## 6. Heap memory, Stack memory là gì?

```text
Java heap memory được sử dụng vởi JRE để chỉ định bộ nhớ cho các Object và class. Bất cứ khi nào, chúng ta tạo bất kỳ Object nào thì nó luôn luôn được tạo trong Heap. Dung lượng sử dụng của Heap sẽ tăng giảm phụ thuộc vào Objects sử dụng. Dung lượng Heap thường lớn hơn Stack.
Java stack memory được sử dụng trong khi thực thi một thread. Stack được sử dụng để lưu các biến local trong hàm và lời gọi hàm ở runtime. Các biến local bao gồm loại nguyên thuỷ (primitive) và loại tham chiếu tới đối tượng trong heap (reference) khai báo trong hàm, hoặc đối số được truyền vào hàm, thường có thời gian sống ngắn. Bộ nhớ stack thường nhỏ.
```

## 7. Auto-boxing và un-boxing là gì?

```text
Auto-boxing: là quá trình mà trình biên dịch của Java tự động chuyển đổi giữa kiểu dữ liệu nguyên thủy (Primitive type) về đối tượng tương ứng với lớp Wrapper của kiểu dữ liệu đó. Ví dụ, trình biên dịch sẽ chuyển đổi kiểu dữ liệu int sang Integer, kiểu double sang Double,...
Un-boxing: là quá trình ngược lại với Boxing, chuyển đổi một đối tượng tương ứng với lớp Wrapper về kiểu dữ liệu nguyên thủy.
```

## 8. Chuyển đổi String sang Integer và Integer sang String như thế nào?
```
String a = 10;
int number = Integer.parseInt(a); // Chuyển String sang Integer
a = Integer.toString(number); // Chuyển Integer sang String
```
## 9. Enum là gì?
```
Enum là một kiểu dữ liệu đặc biệt dùng để định nghĩa tập các hằng số. Một enum có thể bao gồm các hằng số, các method, ...
```
## 10. Constructor là gì? Có bao nhiêu kiểu contructor?
```
Constructor là một phương thức đặc biệt, nó được dùng để khởi tạo và trả về đối tượng của lớp mà nó được định nghĩa. Constructor sẽ có tên trùng với tên của lớp mà nó được định nghĩa và chúng không được định nghĩa một kiểu giá trị trả về.
Các kiểu của Contructor:
- Không có đối số
- Có đối số
- Mặc định
```
## 11. this là gì? super là gì?
```
super được sử dụng để truy cập các hàm của class cha.
this được sử dụng để truy cập các hàm hoặc các trường (data) của class hiện tại.
```
## 12. Các biến instance và class là gì?
| Instance variables       | Static (class) variables |
| ------------- |-------------|
| Các biến thể hiện được khai báo trong một lớp, nhưng bên ngoài một phương thức, hàm tạo hoặc bất kỳ khối nào. | Biến lớp hay còn gọi là biến static được khai báo bằng từ khóa static trong một lớp, nhưng bên ngoài một phương thức, hàm tạo hoặc một khối. |
| Các biến thể hiện được tạo khi một đối tượng được tạo bằng cách sử dụng từ khóa 'new' và bị hủy khi đối tượng bị hủy      | Các biến static được tạo khi chương trình bắt đầu và bị hủy khi chương trình dừng. |
| Các biến instance có thể được truy cập trực tiếp bằng cách gọi tên biến bên trong lớp. Tuy nhiên, trong các phương thức static (khi các biến thể hiện được cấp khả năng truy cập), chúng phải được gọi bằng tên đủ điều kiện. ObjectReference.VariableName. | Các biến static có thể được truy cập bằng cách gọi tên lớp ClassName.VariableName. |
| Các biến instance giữ các giá trị phải được tham chiếu bởi nhiều phương thức, hàm tạo hoặc khối lệnh hoặc các phần thiết yếu của trạng thái của đối tượng phải có trong toàn bộ lớp. | Sẽ chỉ có một bản sao của mỗi biến lớp cho mỗi lớp, bất kể có bao nhiêu đối tượng được tạo từ nó. |
## 13. Getter và Setter là gì? Khi nào sử dụng chúng?
```
Getter và Setter được sử dụng để truy cập các thành viên private của class và nếu chúng ta sử dụng getter và setter thì chúng ta nắm quyền kiểm soát dữ liệu
```
## 14. Các kiểu khác nhau của access modifier? Cách sử dụng chúng như thế nào?
```
Các kiểu access modifier: private, default, protected, public
```
| Access Modifier     | private | default | protected | public |
| ---- |:-------:|:-------:|:---------:|:------:|
| within class|Yes|Yes|Yes|Yes|
|within package|No|Yes|Yes|Yes|
|outside package by subclass only|No|No|Yes|Yes|
|outside package|No|No|No|Yes|

## 15. Class và Object là gì?
```
Class là một mẫu hoặc thiết kế từ đó các đối tượng được tạo ra.
Đối tượng(Object) là một thể hiện của một lớp(Class).
```
## 16. Chúng ta có thể khai báo class dưới dạng private hoặc static hoặc final được không?
```
Chúng ta không thể khai báo class dưới dạng private. Nếu chúng ta khai báo class dưới dạng private thì class đó sẽ không truy cập được
Chúng ta cũng không thể khai báo class dưới dạng static
Chúng ta có thể khai báo class dưới dạng final. Một final class là dạng đơn giản của class mà không thể được kế thừa.
```
## 17. Chúng ta có thể khai báo một method dưới dạng static final?
```
Chúng ta có thể khai báo một method dưới dạng static final dựa trên tình huống thực tế và chúng ta không thể override method này trong class con.
```
## 18. OOP là gì?
```
OOP (Object-Oriented Programming - Lập trình hướng đối tượng) là một phương pháp để thiết kế một chương trình bằng cách sử dụng các class và object
Các đặc điểm của OOP:
-   Encapsulation (Tính đóng gói)
-   Inheritance (Tính kế thừa)
-   Polymorphism (Tính đa hình)
-   Abstraction (Tính trừu tượng)
```
## 19. Sự khác nhau giữa abstract class và interface?
```
Abstract class sẽ có phương thức abstract và non-abstract
Abstract class không hỗ trợ đa kế thừa
Một abstract class có thể kế thừa một class khác bằng từ khóa extends và kế thừa nhiều interface

Interface chỉ có phương thức abstract
Interface hỗ trợ đã kế thừa
Interface chỉ có các biến static và final
Một interface chỉ có thể kế thừa một interface khác
```
## 20. Tính kế thừa là gì? Java có hỗ trợ đã kế thừa không?
```
Tính kế thừa là một quá trình trong đó có một đối tượng có thể dùng lại các thuộc tính của một đối tượng khác.
Java không hỗ trợ đa kế thừa
Java hỗ trợ kế thừa đã cấp
```
## 21. Sự khác biệt giữa overloading và overriding là gì?

|  Overriding  |  Overloading  |
|---------|----------|
| Xảy ra tại "runtime" | Xảy ra tại "compile time" |
| Gọi phương thức được xác định tại runtime dựa trên loại đối tượng | Gọi phương thức được xác định tại compile time |
| Xảy ra giữa lớp cha và lớp con | Xảy ra giữa các phương thức trong cùng một lớp |
| Có cùng signature (cùng tên phương thức, cùng số lượng và kiểu đối số) |	Cùng tên phương thức, nhưng đối số là khác nhau (kiểu, số lượng) |
| Nếu có lỗi, thì sẽ kết quả sẽ hiển thị tại runtime |	Nếu có lỗi, nó có thể bị bắt tại compile time |

## 22. Chúng ta có thể nạp chồng phương thức main() của một class được không?
```
Chúng ta có thể nạp chồng phương thức main() của một class
```

## 23. Chúng ta có thể ghi đè một phương thức static?
```
Chúng ta không thể ghi đè một phương thức static
```

## 24. Chúng ta có thể ghi đè phương thức main()?
```
Chúng ta không thể ghi đè phương thức main() vì main() là một phương thức static
```

## 25. Chúng ta có thể ghi đè phương thức main()?
```
Chúng ta không thể ghi đè phương thức main() vì main() là một phương thức static
```

# Selenium
## 1. Selenium là gì?
```
Selenium là một bộ công cụ tự động hóa web nguồn mở tận dụng sức mạnh của trình duyệt web và giúp tự động hóa quy trình công việc về cách người dùng tương tác với ứng dụng web trong trình duyệt. Hiện tại Selenium bao gồm các công cụ sau: Selenium IDE, Selenium WebDriver, Selenium Grid
```
## 2. Phiên bản hiện tại của Selenium là gì?
```
Selenium 4
```
## 3. Sự khác nhau giữa Selenium IDE, Selenium WebDriver, Selenium Grid?
```
Selenium IDE là một tiện ích mở rộng có sẵn cho cả Firefox và Chrome, có sẵn chức năng ghi và phát lại.
Selenium WebDriver là thành phần được sử dụng phổ biến nhất của Selenium. WebDriver cho phép người dùng viết mã tùy chỉnh bằng ngôn ngữ họ chọn và tương tác với trình duyệt họ chọn, thông qua trình điều khiển dành riêng cho trình duyệt.
Selenium GRID cho phép người dùng chạy thử nghiệm đồng thời trên các máy khác nhau, với các trình duyệt và hệ điều hành khác nhau, mang lại khả năng chạy thử nghiệm song song, nhờ đó tiết kiệm rất nhiều thời gian và tài nguyên thử nghiệm trên nhiều máy.
```
## 4. Ngôn ngữ lập trình được hỗ trợ bởi selenium là những ngôn ngữ nào? Những trình duyệt nào được Selenium hỗ trợ?
```
Ngôn ngữ lập trình: Java, Javascript, Python, C#, Ruby, ...
Trình duyệt được hỗ trợ: Google Chrome, Firefox, Opera, Safari, Microsoft Edge, ...  
```
## 5. Giới hạn của Selenium là gì?
```
1. Selenium chỉ hỗ trợ cho các ứng dụng dựa trên nền tảng web
2. Selenium không hỗ trợ việc so sánh Bitmap (tức là các file ảnh)
3. Selenium không hỗ trợ xuất testing report, phải sử dụng công cụ (thư viện) của bên thứ 3
4. Selenium không có hỗ trợ customer service giống như các tool trả phí khác như QTP/UFT, Katalon, Ranorex
5. Không có khái niệm object repository trong Selenium, nên việc bảo trì các đối tượng trở nên khó khăn hơn
```
## 6. Bạn có thể giải thích WebDriver driver = new ChromeDriver() hoặc WebDriver driver = new FirefoxDriver()?
```
WebDriver là một interface
driver là một biến tham chiếu
new ChromeDriver() cấp phát bộ nhớ cho đối tượng kiểu ChromeDriver
WebDriver driver = new ChromeDriver() => cấp phát bộ nhớ cho đối tượng driver có kiểu WebDriver => mở trình duyệt Google Chrome
```
## 7. Locator là gì?
```
Locator có thể được gọi là một địa chỉ xác định một web element duy nhất bên trong trang web đó
```
## 8. Các loại locator trong Selenium là gì?
```
Có 8 loại locator: id, name, className, xpath, css, tagName, linkText, partialLinkText
```
## 9. Xpath là gì?
```
Xpath được sử dụng để xác định một web element dựa trên biểu thức đường dẫn XML của nó. Xpath có thể được sử dụng để xác định phần tử HTML.
```
## 10. Sự khác biệt giữa '/' và '//' của xpath trong Selenium là gì?
```
'/': được sử dụng để tạo xpath tuyệt đối (absolute Xpath) của element
'//': được sử dụng để tạo xpath tương đối (relative Xpath) của element
```
## 11. Absolute Xpath (Xpath tuyệt đối) và relative Xpath (Xpath tương đối) là gì?
```
Absolute Xpath (Xpath tuyệt đối): viết đường dẫn hoàn thiện của một phần tử được chỉ định bằng cách chỉ sử dụng single forward slash (/) được gọi là Xpath tuyệt đối. Ví dụ: /html/body/div[7]/div[3]/span
Relative Xpath (Xpath tương đối): viết đường dẫn hoàn thiện của một phần tử được chỉ định bằng cách chỉ sử dụng double forward slash (//) được gọi là Xpath tương đối. Ví dụ: //table[@class='dataTable']/tbody//td[2]
```
## 12. Selenium WebDriver là gì?
```
Selenium WebDriver là một tool dùng cho kiểm thử tự động ứng dụng web. Nó cung cấp các API thân thiện, dễ khám phá và dễ hiểu.
```
## 13. Các loại khác nhau của driver có sẵn trong WebDriver?
```
FirefoxDriver
ChromeDriver
SafariDriver
EdgeDriver
OperaDriver
AndroidDriver
iOSDriver
...
```
## 14. WebElement là gì?
```
WebElement đại diện cho một phần tử HTML. Mọi thao tác trên một trang web sẽ được sử dụng thông qua WebElement 
```
## 15. Khởi chạy trình duyêt sử dụng WebDriver như thế nào?
```
//Firefox
WebDriver driver = new FirefoxDriver();

//Chrome
WebDriver driver = new ChromeDriver();

//Edge
WebDriver driver = new EdgeDriver();
```
## 16. Mở một website trong Chrome như thế nào?
```
WebDriver driver = new ChromeDriver();
driver.get("https://dantri.com.vn");
```

## 17. Làm thế nào để chúng ta lấy text của một webelement?
```
Câu lệnh getText() được sử dụng để lấy inner text của một web element. Câu lệnh không cần truyền bất kỳ tham số nào nhưng trả về một giá trị kiểu String
```
## 18. Làm thế nào để chúng ta phóng to trình duyệt?
```
Chúng ta sử dụng câu lệnh: driver.manage().window().maximize();
```
## 19. Sự khác nhau giữa findElement() và findElements?
```
findElement():
1. Tìm phần tử đầu tiên bên trong trang web hiện tại thông qua locator
2. Trả về một web element duy nhất
3. Nếu không tìm thấy element, sẽ xuất ra NoSuchElementException
4. Tương tác với element không cần phải truyền index
findElements()
1. Tìm tất cả các phần tử bên trong trang web hiện tại thông qua locator
2. Trả về một list Web Element
3. Nếu không tìm thấy web element, sẽ trả về một list web element trống
4. Tương tác với element thì cần phải truyền index
```
## 20. Sự khác nhau giữa driver.close() và driver.quit()?
```
close(): đóng cửa sổ trình duyệt mà hiện tại user đang làm việc.
quit(): đóng tất cả các cửa sổ trình duyệt mà chương trình đang mở
```
## 21. Liệt kê các cách phổ biến để thực hiện upload file?
```
Có 3 cách phổ biến để upload file:
1. Sử dụng hàm sendKeys() với điều kiện đây là thẻ input
2. Sủ dụng Robot class của Java
3. Sử dụng AutoIT (chỉ dành cho hệ điều hành Windows)
```
## 22. Các loại Wait trong Selenium?
```
```
## 22. Hãy miêu tả cách bạn tương tác với Calendar
```
1. Xem UI và cơ chế hoạt động của Calendar (định dạng ngày tháng năm, cách chọn day, month, year, ...) trên website
2. Bắt locator của các element trên calendar như: day, month, year, next button, previous button, ...
3. Viết các hàm tương tác với các element trên
4. Viết một hàm chung thực hiện chọn date theo một format cụ thể trên calendar chứa các hàm ở step 3 theo cơ chế hoạt động hiện tại của Calendar
```

# TestNG

## 1. TestNG là gì?
```
TestNG là một testing framework và kế thừa từ JUnit và Nunit bằng cách thêm một vài tính năng để làm TestNG trở nên mạnh mẽ hơn các framework khác
```

## 2. Các lợi ích của TestNG?
```
1. Tạo HTML report
2. Có thể chạy các test case cùng group
3. Hỗ trợ thực thi parallel các test script
4. Có thể thay đổi thứ tự chạy các test case
5. Có thể tham số số các test, ...
```

