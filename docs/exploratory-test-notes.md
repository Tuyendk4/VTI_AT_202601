# Exploratory Test Notes — WorkDo Dash SaaS / Sales Invoice

**AUT:** https://dash-demo.workdo.io — vào bằng nút "Explore All Add-ons" trên trang login
**Phân hệ:** Account → Sales Invoice (`/sales-invoices`)
**Người thực hiện:** (tên bạn)
**Bối cảnh:** brownfield — sản phẩm đã chạy, không có SRS. Test basis = hành vi thực tế của ứng dụng.

> Cách dùng: mở file này song song với trình duyệt, điền trực tiếp trong lúc khám phá.
> Chụp màn hình MỌI thứ khả nghi ngay lúc gặp — quay lại tái hiện sau tốn gấp ba.
> Mở sẵn DevTools cả 3 phiên, gặp field nào thì copy locator xuống mục cuối file.

---

## CHARTER 1 — Bản đồ chức năng ✅ ĐÃ LÀM (17/08/2026, ~14:00)

**Mục tiêu:** liệt kê cái gì TỒN TẠI. Không đánh giá đúng/sai ở phiên này.

### 1.1 Các màn hình

| Màn hình | URL | Ghi chú |
|---|---|---|
| Danh sách invoice | `/sales-invoices` | |
| Tạo mới | `/sales-invoices/create` | |
| Sales Invoice Returns | menu con riêng | ngoài scope |
| Chi tiết / Sửa | (mở qua icon `eye` / `square-pen` ở từng dòng) | **CẦN XÁC NHẬN URL** |

**Deep-link qua URL — chạy được, dùng để cô lập dữ liệu test:**
```
?status=draft | posted | partial | paid | overdue
?search=<invoice_number>          → trả đúng 1 dòng, tự điền vào ô search
?per_page=100
```

### 1.2 Trang danh sách

**Cột:** Invoice Number · Customer · Invoice Date · Due Date · Total Amount · Balance · Status · Actions

**Sắp xếp được:** tất cả trừ `Customer` và `Actions`

**4 thẻ thống kê:**
| Thẻ | Giá trị (17/08 14:00) |
|---|---|
| Outstanding | 4,049,071.15$ — 28 invoices |
| Overdue | 3,994,911.23$ — 25 invoices |
| Collected | 428,843.14$ — 6 paid in full |
| Drafted | 1,039,939.36$ — 6 not yet sent |

Thêm khối "Outstanding by Customer" (12 khách, mỗi khách có nút `Chase payment`).

**Search:** ô `placeholder="Search by invoice number..."` + nút `Search`. Chỉ tìm theo số hoá đơn.

**Filters (panel mở ra):** Customer · Warehouse · Status · Date Range

**Phân trang:** `10 per page` + `Previous / 1 / 2 / 3 / 4 / Next`

**5 trạng thái tồn tại:** `Draft` · `Posted` · `Partial` · `Paid` · `Overdue`

### 1.3 Form tạo mới — bảng field

| Field | Có `*`? | Validate thật? | Kiểu | Mặc định | Ghi chú |
|---|---|---|---|---|---|
| Product Wise / Service Wise | — | — | radio | Product Wise | **đổi field bắt buộc** |
| Invoice Date | ✅ | (không hiện lỗi) | datepicker | **hôm nay** | `id=invoice_date`, `type=hidden` |
| Due Date | ✅ | ✅ | datepicker | trống | `id=due_date`, `type=hidden` |
| Customer | ✅ | ✅ | dropdown Radix | trống | 25 lựa chọn |
| Warehouse | ✅ | ✅ | dropdown Radix | trống | 13 lựa chọn |
| Payment Terms | ❌ | — | text | trống | `id=payment_terms`, ph `e.g., Net 30` |
| Notes | ❌ | — | textarea | trống | ← **nhét marker `AT_*` vào đây** |
| Recurring Frequency | ❌ | — | dropdown | `No` | 12 lựa chọn |
| Commission Plan | ❌ | — | dropdown | trống | |
| Select Agent | ❌ | — | dropdown | trống | disabled lúc đầu |
| **Item: Product** | ✅ | ❌ **KHÔNG** | dropdown | trống | ⚠️ xem BUG-03 |
| **Item: Qty** | ✅ | ❌ | number | `1` | không có id/name |
| **Item: Unit Price** | ✅ | ❌ | number | `0` | không có id/name |
| Item: Discount % | ❌ | — | number | `0` | |
| Item: Tax | — | — | chỉ đọc | `No tax` | |
| Item: Total | — | — | chỉ đọc | `0.00$` | |

**Nút:** `+ Add Item` · `Cancel` · `Create`

**Khối Invoice Summary (chỉ đọc):** `Subtotal` / `Discount` / `Tax` / `Total` ← target assert của nhóm `INV_CALC`

**Kết quả submit form rỗng** (URL không đổi, KHÔNG tạo dữ liệu — validation chặn):
```
The due date field is required.
The customer id field is required.
The warehouse id field is required when type is product.
```

### 1.4 Ma trận Trạng thái × Hành động ⭐

| Status | Số lượng | `square-pen` Edit | `trash2` Delete | `pen-tool` | `file-text` | `download` | `file-down` | `eye` | **Tổng icon** |
|---|---|---|---|---|---|---|---|---|---|
| **Draft** | 6 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **7** |
| **Posted** | — | ❌ | ❌ | ✅ | ❌ | ✅ | ✅ | ✅ | **4** |
| **Paid** | 6 | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | **3** |
| Partial | ? | | | | | | | | **CẦN LÀM** |
| Overdue | ? | | | | | | | | **CẦN LÀM** |

**Kết luận cho chiến lược test:**
- Chỉ **Draft** mới sửa/xoá được → invoice test **giữ ở Draft**, xoá ở `@AfterMethod`
- Chỉ 2 case `INV_STATE` mới đẩy lên Posted → chấp nhận 1–2 record mồ côi mỗi lần chạy
- `pen-tool` có ở Draft + Posted, **mất khi Paid** → nhiều khả năng là **Add Payment**. ⚠️ Cần hover xác nhận tên thật

### 1.5 Việc còn thiếu của Charter 1 (bạn tự làm, ~15')

1. Hover từng icon để lấy **tên hành động thật** — `pen-tool` và `file-text` là gì?
2. Chạy `?status=partial` và `?status=overdue`, điền 2 dòng còn trống ở bảng 1.4
3. Bấm icon `eye` → ghi lại **URL trang chi tiết**; bấm `square-pen` → ghi **URL trang sửa**
4. Bấm radio **Service Wise** → field bắt buộc đổi thế nào? (thông báo lỗi đã lộ ra là có đổi)

---

## CHARTER 2 — Luồng nghiệp vụ E2E

**Mục tiêu:** tự tay đi hết Customer → Product → Invoice → Payment → Paid.
**Thời gian:** 90' — bắt đầu ____ kết thúc ____

### 2.1 Tạo Customer

Đường dẫn menu: 
Các field bắt buộc: 
Kết quả: 

### 2.2 Tạo Product

Đường dẫn menu: 
Các field bắt buộc: 
Có field Tax / đơn vị / giá không: 

### 2.3 ⭐ BẢNG SỐ LIỆU — phần quan trọng nhất cả buổi

Nhập từng bộ số vào form, ghi lại **đúng** con số ứng dụng hiện ra trong khối Invoice Summary.

| # | Qty | Unit Price | Discount % | Subtotal | Discount | Tax | **Total** |
|---|---|---|---|---|---|---|---|
| 1 | 1 | 100 | 0 | | | | |
| 2 | 3 | 100 | 0 | | | | |
| 3 | 3 | 100 | 10 | | | | |
| 4 | 1 | 99.99 | 33 | | | | |
| 5 | 2 dòng item, discount khác nhau | | | | | | |

**Từ bảng trên, trả lời 3 câu — đây chính là Expected Result của nhóm `INV_CALC`:**

> ✅ **ĐÃ TRẢ LỜI (25/08/2026)** — xác định bằng cách chạy test tự động với nhiều bộ số rồi
> đọc lại đúng con số ứng dụng hiện ra. Số liệu thực đo:
>
> | Qty | Unit Price | Disc % | Subtotal | Discount | Tax | Total |
> |---|---|---|---|---|---|---|
> | 2 | 100 | 0 | 200.00 | -0.00 | 60.00 | 260.00 |
> | 3 | 100 | 10 | 300.00 | -30.00 | 81.00 | 351.00 |
> | 1 | 99.99 | 50 | 99.99 | -49.99 | 15.00 | 64.99 |

1. Discount tính trên **từng dòng** hay trên **tổng**?
   → **Từng dòng.** `Discount = Subtotal_dòng × %`. Với 3×100 và 10% ra đúng 30.00.

2. Tax tính **trước** hay **sau** khi trừ discount?
   → **SAU.** Thuế suất của sản phẩm đã chọn (Coffee = 30%) áp lên phần *đã trừ chiết khấu*:
   `81.00 = (300 − 30) × 30%`, `60.00 = 200 × 30%`, `15.00 ≈ (99.99 − 49.995) × 30%`.
   ⚠️ Lưu ý: khi **chưa chọn sản phẩm** thì ô Tax hiện `No tax` — dễ tưởng nhầm hệ thống
   không tính thuế. Thuế suất đi theo sản phẩm, không phải theo hoá đơn.

3. Làm tròn thế nào? Mấy chữ số thập phân? Lên hay xuống?
   → **2 chữ số, làm tròn XUỐNG (không phải half-up).** Bộ số 99.99 với 50%: chiết khấu thật
   là **49.995**, ứng dụng hiện **`-49.99`**. Theo thông lệ kế toán (half-up) phải là `50.00`.
   Chênh 1 xu. Tổng thật 64.9935 → hiện `64.99` (đúng theo mọi cách làm tròn nên không phân
   biệt được ở con số này).

   ⚠️ **Cần mentor xác nhận:** hướng làm tròn này là cố ý hay là lỗi? Trên một hoá đơn thì 1 xu
   không đáng kể, nhưng nếu áp lên hàng nghìn dòng thì sai số tích luỹ và lệch sổ. Đã ghi thành
   mục hỏi trong `capstone-test-report.md`.

**Công thức chốt (dùng làm Expected Result cho nhóm `INV_CALC`):**
```
Subtotal = Qty × Unit Price
Discount = Subtotal × (Disc% / 100)          — trên từng dòng
Tax      = (Subtotal − Discount) × thuế suất của sản phẩm
Total    = Subtotal − Discount + Tax
```

> Không có spec nên đây là cách DUY NHẤT biết được công thức.
> Nếu công thức sai so với chuẩn kế toán → đó là bug ngon nhất cho buổi demo.

### 2.4 Chuyển trạng thái

| Từ | Hành động gì | Sang | Ghi chú |
|---|---|---|---|
| (mới tạo) | bấm Create | | Invoice mới sinh ra ở status nào? |
| Draft | | Posted | Nút tên gì, ở đâu? |
| Posted | thanh toán MỘT PHẦN | | |
| Posted | thanh toán ĐỦ | | |
| ? | quá hạn Due Date | Overdue | Tự động hay phải làm gì? |

Số invoice hệ thống sinh ra có dạng: 
(Đã biết 2 dạng: `SI-2026-02-020` và `INV-20260630-6a439058d3189` — kiểm chứng lại)

**Sơ đồ trạng thái** (vẽ tay rồi chụp, hoặc mô tả bằng chữ — sẽ chèn vào Test Plan):

```
Draft --(?)--> Posted --(?)--> ...
```

---

## CHARTER 3 — Săn bug

**Mục tiêu:** tìm chỗ ứng dụng cư xử sai so với nghiệp vụ hợp lý.
**Thời gian:** 90' — bắt đầu ____ kết thúc ____

Tick từng dòng sau khi thử. Cột "Kết quả" ghi ngắn: OK / LẠ / **BUG**.

### 3.1 Giá trị biên

| # | Thử | Kỳ vọng hợp lý | Thực tế | Kết quả |
|---|---|---|---|---|
| 1 | Qty = `0` | chặn | | |
| 2 | Qty = `-1` | chặn | | |
| 3 | Qty = `0.5` | tuỳ nghiệp vụ | | |
| 4 | Qty = `999999999` | chặn hoặc tính đúng | | |
| 5 | Unit Price = `0` | cho phép? | | |
| 6 | Unit Price = `-1` | chặn | | |
| 7 | Unit Price = `abc` | chặn | | |
| 8 | Discount % = `-1` | chặn | | |
| 9 | Discount % = `100` | Total = 0 | | |
| 10 | Discount % = `101` | chặn | | |
| 11 | Due Date **trước** Invoice Date | chặn | | |

### 3.2 Ràng buộc bắt buộc

| # | Thử | Thực tế | Kết quả |
|---|---|---|---|
| 12 | Để trống hết, bấm Create — field nào báo đỏ? | | |
| 13 | Thông báo lỗi có rõ nghĩa không? | | |
| 14 | Tạo invoice **không có dòng item nào** | | |

### 3.3 Thao tác bất thường

| # | Thử | Kỳ vọng hợp lý | Thực tế | Kết quả |
|---|---|---|---|---|
| 15 | Bấm **Create 2 lần thật nhanh** | tạo 1 invoice | | |
| 16 | **F5** giữa lúc đang điền form | | | |
| 17 | **Back** sau khi Save thành công | | | |
| 18 | Sửa invoice Draft rồi bấm **Cancel** | KHÔNG lưu | | |
| 19 | **Đổi Customer sau khi đã thêm item** — item có bị reset? | không reset | | |
| 20 | Xoá hết item rồi Save | chặn | | |

> #19 liên quan trực tiếp tới lỗi `StaleElementReferenceException` gặp lúc spike:
> React render lại form sau mỗi lần chọn dropdown. Đáng nghi.

### 3.4 Dữ liệu lạ

| # | Thử | Kỳ vọng hợp lý | Thực tế | Kết quả |
|---|---|---|---|---|
| 21 | Notes: 5000 ký tự | chặn hoặc cắt | | |
| 22 | Notes: `<b>test</b>` | hiện nguyên chữ, KHÔNG in đậm | | |
| 23 | Payment Terms: `'; DROP TABLE--` | hiện nguyên chữ | | |
| 24 | Customer name có dấu tiếng Việt | hiển thị đúng | | |

---

## DANH SÁCH BUG

> Điền dần trong lúc làm cả 3 charter. Đây là nguyên liệu thô cho Defect Report ngày 11.

### BUG-01 — Thông báo lỗi lộ tên field kỹ thuật của backend
- **Màn hình / URL:** `/sales-invoices/create`
- **Các bước tái hiện:**
  1. Mở form Create Sales Invoice
  2. Để trống tất cả, bấm **Create**
- **Kỳ vọng:** `Customer is required.` / `Warehouse is required.`
- **Thực tế:** `The customer id field is required.` — `The warehouse id field is required when type is product.`
- **Phân tích:** thông báo hiển thị nguyên tên cột database (`customer id`, `warehouse id`) và cả logic nội bộ (`when type is product`). Người dùng cuối không hiểu; đồng thời lộ cấu trúc dữ liệu.
- **Mức độ:** Thấp (UX / information disclosure)
- **Ảnh chụp:** (cần bổ sung)

### BUG-02 — Thiếu validate cho dòng Item
- **Màn hình / URL:** `/sales-invoices/create`
- **Các bước tái hiện:**
  1. Để trống tất cả, bấm **Create**
- **Kỳ vọng:** `Product`, `Qty`, `Unit Price` đều gắn dấu `*` → phải báo lỗi khi để trống
- **Thực tế:** chỉ 3 lỗi hiện ra (Due Date, Customer, Warehouse). **Không có lỗi nào cho dòng Item** dù Product đang trống
- **Cần xác nhận thêm:** điền đủ Due Date + Customer + Warehouse, **để trống Product**, rồi Create → có tạo được invoice không có sản phẩm không? Nếu CÓ thì nâng mức độ lên **Cao**
- **Mức độ:** Trung bình (chờ xác nhận)
- **Ảnh chụp:** (cần bổ sung)

### BUG-03
- **Tiêu đề:** 

---

## LOCATOR THU THẬP ĐƯỢC

> Gom trong lúc khám phá, ngày 4 đổ thẳng vào `object_repository/*.json`.
> Ưu tiên `id:` > `name:` > xpath theo label. Thử bằng `$$("...")` trong Console — phải ra ĐÚNG 1 phần tử.

### LoginPage
```
TXT_EMAIL     = id:email
TXT_PASSWORD  = id:password
BTN_LOGIN     = 
BTN_EXPLORE_ALL_ADDONS = //button[contains(normalize-space(.),'Explore All Add-ons')]
```

### InvoiceListPage
```
TXT_SEARCH    = 
BTN_CREATE    = 
TBL_ROWS      = 
BTN_DELETE_ROW = //button[.//svg[contains(@class,'lucide-trash2')]]
BTN_EDIT_ROW   = //button[.//svg[contains(@class,'lucide-square-pen')]]
BTN_VIEW_ROW   = //button[.//svg[contains(@class,'lucide-eye')]]
```

### InvoiceCreatePage
```
TXT_INVOICE_DATE = id:invoice_date      (type=hidden - sendKeys KHONG chay)
TXT_DUE_DATE     = id:due_date          (type=hidden)
CBB_CUSTOMER     = 
CBB_WAREHOUSE    = 
TXT_PAYMENT_TERMS = id:payment_terms
TXA_NOTES        = 
TXT_QTY          = 
TXT_UNIT_PRICE   = 
TXT_DISCOUNT     = 
LBL_SUBTOTAL     = 
LBL_TOTAL        = 
BTN_CREATE       = 
```

---

## CÂU HỎI MỞ

> Ghi lại mọi thứ chưa rõ, giải đáp sau hoặc đưa vào mục Rủi ro của Test Plan.

1. 
2. 

---

## GHI CHÚ KỸ THUẬT ĐÃ BIẾT TRƯỚC (từ spike 17/08)

- Ứng dụng là **React SPA** (shadcn/ui + Radix + Tailwind). **Không có `data-testid` nào.**
- Dropdown là Radix: thẻ nhìn thấy là `<div role="combobox">`, `aria-controls` random mỗi lần render → **cấm dùng làm locator**.
- Nhưng DOM còn **6 `<select>` native ẩn** (1x1px, clip 0) và **`Select` class của Selenium CHẠY ĐƯỢC** trên chúng.
- ⚠️ **React render lại form sau mỗi lần chọn** → `StaleElementReferenceException`. Phải tìm lại element ngay trước mỗi lần tương tác, không cache `WebElement`.
- Overlay *"Please wait while we prepare your webapp..."* xuất hiện mỗi lần load → cần explicit wait.
- Bong bóng chat AI góc phải dưới **che nút Create** → `ElementClickInterceptedException`, cần JS click hoặc scroll.
- URL deep-link filter chạy được: `?status=draft`, `?search=<invoice_number>` → dùng để cô lập record của mình.
- Invoice number **auto-generated**, không có ô nhập → marker `AT_*` phải nhét vào **Notes**.
