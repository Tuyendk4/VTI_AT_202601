# Brainstorm — Đồ án cuối kỳ: Test Mock Project (Web + Mobile)

Ngày: 2026-08-17 | Deadline: **< 2 tuần** (~12 ngày, mốc ~29/08/2026)

## 1. Problem statement

Làm đồ án nộp + demo mentor cuối kỳ, gồm 2 phần:
- **Web**: phát triển automation script cho 1 phân hệ của https://workdo.io/
- **Mobile**: app chưa chốt (đang hỏi mentor)

Deliverable mentor yêu cầu: script automation + Test Plan & Test Case Excel + CI/CD.

## 2. Hiện trạng repo (đã khảo sát)

| Module | Nội dung | Dùng được gì |
|---|---|---|
| `UiFramework/` | Sandbox lớp học: Selenium + TestNG + Allure + Cucumber + POM + PageFactory + Excel/DataFaker + **Appium** + RestAssured | `common/keywords/WebUI.java`, `MobileUI.java`, `helper/*`, `screens/*` (WDIO Demo App), `devices/*.properties` |
| `GuruBank/` | Mock project mẫu (Guru99 Bank) — mentor đã chấp nhận pattern | Template `BaseTest` + `pages/` + `object_repository/*.json` + `testsuites/*.xml` |
| `Performance/` | JMeter (Restful-booker) | — |

**Phát hiện quan trọng:**
- `WebUI.java` đã có sẵn `CHROME_HEADLESS`, `FIREFOX_HEADLESS`, `RemoteWebDriver` → **CI gần như free**.
- Đã có `takeScreenshot()`, `attachmentScreenshot()`, `takeScreenshotAndMarkElement()` → Allure attachment sẵn sàng.
- **Thiếu**: `IRetryAnalyzer` và `ITestListener` (screenshot-on-failure). ~50 dòng, làm ngày 1.
- Mobile app lớp học = **WDIO Demo App** (`com.wdiodemoapp`), đã có `LoginScreen.json` (Android + iOS), `NavigationBar.json`, 3 device profiles.
- `UiFramework/` **không nên** dùng làm nơi build đồ án: chứa ~200 file screenshot, `jenkins.war`, `selenium-server-4.45.0.jar` commit vào git.

## 3. Rủi ro chính

### R1 — `dash-demo.workdo.io` là demo công cộng, dữ liệu chung, ai cũng sửa/xoá được ⚠️ CAO
Hệ quả: test pass hôm nay, fail hôm demo. Demo có thể reset định kỳ.

**Mitigation (đưa vào Test Plan mục Risks):**
- Mọi test tự tạo data với prefix duy nhất: `AT_<yyyyMMddHHmmss>_<rand4>`
- Tìm lại record **bằng search theo prefix**, không click row đầu bảng
- `@AfterMethod` cleanup record đã tạo
- **Cấm** assert vào tổng số bản ghi / dữ liệu có sẵn / thứ tự
- Test độc lập hoàn toàn, không phụ thuộc thứ tự chạy

Đây là **test independence & isolation** — điểm cộng khi demo, không phải workaround.

### R2 — Scope creep ⚠️ CAO (với deadline 12 ngày)
Mitigation: giới hạn cứng 15 case web + 8 case mobile. Chất lượng thiết kế > số lượng.

### R3 — Demo live flaky (emulator/mạng/demo site sập) ⚠️ TRUNG BÌNH
Mitigation: quay video backup toàn bộ run + export sẵn Allure HTML.

### R4 — CI chạy vào demo công cộng sẽ đỏ ngẫu nhiên ⚠️ TRUNG BÌNH
Mitigation: CI chỉ chạy **smoke 5 case ổn định nhất** + `RetryAnalyzer(1)`, headless. Full suite chạy local. Ghi rõ lý do trong Test Plan.

Ghi chú: **Jenkins local giảm rủi ro này** so với GitHub Actions — GHA chạy từ IP datacenter, Cloudflare của WorkDo dễ chặn/challenge. Jenkins local dùng cùng IP/mạng với lúc chạy tay.

### R5 — Setup Jenkins lần đầu tốn thời gian ⚠️ TRUNG BÌNH (mới)
Jenkins ≈ 5h cho người mới (GHA ≈ 1h). Phát hiện trục trặc JDK/port/plugin vào ngày 9 = thảm hoạ.
Mitigation: **smoke Jenkins 30 phút ngay ngày 1** (chạy `jenkins.war`, tạo job hello-world, cài 4 plugin).

## 4. Các phương án đã đánh giá

### 4.1 Chọn phân hệ Web

| Phân hệ (Dash SaaS) | Logic để test | Rủi ro | Điểm demo |
|---|---|---|---|
| **Account/Finance – Invoice** ✅ | Tính tiền/thuế/discount + state machine Draft→Sent→Partially Paid→Paid/Overdue | Setup nhiều bước (Customer + Product trước) | ⭐⭐⭐⭐⭐ |
| HRM – Employee + Leave | Validate form, date range, luồng approve | Ít logic tính toán → bài nhạt | ⭐⭐⭐⭐ |
| CRM – Lead/Deal | Convert Lead→Deal, kanban | Drag-drop → flaky nặng | ⭐⭐⭐ |
| POS | Luồng bán hàng | Phụ thuộc data có sẵn | ⭐⭐⭐ |

**Chọn: Invoice.** Lý do quyết định: là phân hệ duy nhất cho phép áp dụng **đủ 4 kỹ thuật thiết kế test** (EP, BVA, Decision Table, State Transition) — đúng thứ mentor chấm điểm.

**Tránh**: kanban drag-drop, upload file, biểu đồ.

### 4.2 Chọn app Mobile

**Cập nhật 17/8:** mentor cho chọn app **bất kỳ** (thường dò `appPackage`/`bundleId`).

| App | Ưu | Nhược | Kết luận |
|---|---|---|---|
| **Sauce Labs My Demo App (RN)** ✅ | Flow E2E thật Login→Catalog→Cart→Checkout, accessibility id sạch, APK free (`saucelabs/my-demo-app-rn`), **chạy offline** → ổn định hơn cả phần web | Phải viết 3 screen mới (~1.5 ngày) | **Chọn** |
| WDIO Demo App | Đã wire sẵn trong `UiFramework` → chỉ ~4h | Màn hình đồ chơi, không nghiệp vụ | **Fallback** (gate EOD ngày 8) |
| App thật (Shopee/Tiki/bank) | "Trông thật" | ID obfuscate, OTP/captcha, chống automation, A/B test layout | **Loại** |
| App WorkDo | Cùng domain với web | Addon trả phí, không có APK public | Loại |

**Lý do chọn Sauce Labs:** ăn khớp câu chuyện với web —
Web = order-to-cash (Customer → Invoice → Payment) · Mobile = order (Login → Catalog → Cart → Checkout).

**Giá trị cần cấu hình** (`devices/*.properties`) — xác nhận lại bằng lệnh dò:
```
appPackage  = com.saucelabs.mydemoapp.rn
appActivity = com.saucelabs.mydemoapp.rn.MainActivity
```

**Cách dò appPackage / appActivity / bundleId:**
```bash
adb shell pm list packages -3                       # app do user cài
adb shell dumpsys window | grep -E 'mCurrentFocus|mFocusedApp'   # app đang focus
adb shell cmd package resolve-activity --brief <package>          # launcher activity
xcrun simctl listapps booted | grep -B5 CFBundleIdentifier        # iOS sim
```

**Scope mobile: Android only.** iOS/WDA setup tốn cả ngày Xcode + signing. Ghi rõ giới hạn trong Test Plan.

**Gate:** timebox hết ngày 8. Checkout chưa chạy → về WDIO Demo App, 6 case login (~4h). Quyết định trước ngày 9.

### 4.3 Kiến trúc repo

**Chọn:** module `Capstone/` mới trong repo hiện tại, có `core` dùng chung.

```
VTI_AT_202601/
└── Capstone/                    ← parent aggregator pom
    ├── core/                    ← WebUI, MobileUI, helpers, Configuration,
    │                              RetryAnalyzer, TestListener, DataPrefix  (DRY)
    ├── web-tests/               ← pages/, models/, tests/, object_repository/, data/
    └── mobile-tests/            ← screens/, tests/, devices/
```

**Ràng buộc:** extract `core` **1 lần ngày 1 rồi đóng băng**. Không refactor core sau ngày 3.

Fallback nếu ngày 1 vượt 3h: copy `GuruBank` thành 2 module độc lập, chấp nhận trùng lặp, **nói ra trade-off khi demo**.

### 4.4 CI — Jenkins vs GitHub Actions

**Chốt: Jenkins** (người dùng chọn; và có lý do kỹ thuật thật, xem R4).

| Hạng mục | Chốt |
|---|---|
| Loại job | **Pipeline + `Jenkinsfile` commit trong repo**. KHÔNG dùng Freestyle — job click trong UI không để lại dấu vết git, mentor không chấm được |
| Plugin | Git, Maven Integration, **Allure Jenkins Plugin**, HTML Publisher |
| Global Tool Config | JDK, Maven, **Allure Commandline** (thiếu → Allure plugin không chạy) |
| Job chạy | **Chỉ web smoke, `CHROME_HEADLESS`** (`WebUI.java` đã hỗ trợ sẵn) |
| Mobile trên Jenkins | **Không** — cần Appium server + emulator sống → flaky. Chạy tay, nộp kèm Allure report |

Khởi động: `java -jar UiFramework/src/main/resources/jenkins/jenkins.war --httpPort=8080`

## 5. Giải pháp chốt

**Web**: WorkDo Dash SaaS — phân hệ Account/Finance, sub-module **Invoice** — 15 case (sàn cứng 12)
**Mobile**: Sauce Labs My Demo App (RN), Android only — 8 case (fallback WDIO 6 case)
**Kiến trúc**: `Capstone/{core, web-tests, mobile-tests}`
**CI**: Jenkins local, Pipeline + Jenkinsfile, headless smoke 5 case
**Nhân lực**: 1 người (solo) → slack = 0, sequencing là tất cả

### Cắt thẳng vì làm solo (không thương lượng)

- ❌ iOS / WDA
- ❌ **Cucumber/BDD** — có sẵn trong `UiFramework` nên sẽ bị cám dỗ thêm vào. +1.5 ngày, 0 điểm thêm khi đã có POM + data-driven
- ❌ Selenium Grid, parallel execution, cross-browser
- ⚠️ `core` = **copy, không refactor**: tạo `Capstone/core`, copy nguyên package `common/` từ `UiFramework`, thêm 3 class mới. Không đổi tên, không dọn dẹp. 2 tiếng.

### Danh sách cắt case — quyết định TRƯỚC (kỷ luật cứu đồ án solo)

Nếu EOD ngày 6 chưa xong 15 case web, cắt đúng thứ tự này, không nghĩ lại:

| # | Case cắt | Còn |
|---|---|---|
| 1 | `INV_CRUD` — tạo invoice nhiều item | 14 |
| 2 | `INV_VALID` — discount > 100 | 13 |
| 3 | `INV_VALID` — price âm | 12 |

**Sàn cứng: 12 case.** Case bị cắt vẫn giữ trong Excel với `Automated = No / Reason: out of time-box`.

### Cổng go/no-go — làm TRƯỚC khi viết code (ngày 1, ~60 phút)

1. Login `dash-demo.workdo.io` → xác nhận addon Account/Finance có bật, role nào thấy Invoice
2. Tạo tay 1 Customer + 1 Product + 1 Invoice → xoá được không?
3. Ghi giờ tạo → **hôm sau kiểm tra record còn không** (biết demo có reset)
4. DevTools: form Invoice có iframe / modal / select2 / datepicker JS không (quyết định độ khó script)

→ Nếu Invoice không truy cập được: **chuyển HRM Employee+Leave ngay trong ngày**, không đốt 3 ngày.

### Thiết kế 15 case web

| Nhóm | Case | Số | Kỹ thuật |
|---|---|---|---|
| `INV_CRUD` | tạo 1 item / tạo nhiều item / sửa / xoá Draft / xem chi tiết | 5 | — |
| `INV_VALID` | thiếu field bắt buộc / qty=0 / price âm / discount>100 / DueDate<IssueDate | 5 | **EP + BVA** |
| `INV_CALC` | subtotal/tax/discount/total — data-driven từ Excel qua `@DataProvider` | 3 | **Decision Table** |
| `INV_STATE` | Draft→Sent, thanh toán đủ→Paid | 2 | **State Transition** |

Thêm 5–8 case **manual-only** trong Excel (cột `Automated = No` + lý do).

### Thiết kế 8 case mobile (Sauce Labs My Demo App)

Screens cần viết: `LoginScreen`, `CatalogScreen`, `CartCheckoutScreen` (tái dụng `BaseScreen` + `MobileUI` đã có).

| Nhóm | Case | Số |
|---|---|---|
| Login | valid / sai password / email sai format / field rỗng | 4 |
| Catalog | xem danh sách sản phẩm, mở product detail, sort | 2 |
| Cart/Checkout | thêm vào giỏ + verify badge số lượng, hoàn tất checkout | 2 |

*Fallback (WDIO Demo App)*: 6 case login — valid / sai password / email rỗng / email sai format / password rỗng / password < 8 ký tự. Tận dụng `LBL_EMAIL_ERROR_MESSAGE` + `LBL_PASSWORD_ERROR_MESSAGE` đã có sẵn.

## 6. Kế hoạch 12 ngày (solo + Jenkins)

| Ngày | Việc | Gate |
|---|---|---|
| **1** (17/8) | Spike WorkDo 60' + **smoke Jenkins 30'** + skeleton `Capstone/` + copy core + `RetryAnalyzer`/`TestListener`/`DataPrefix` | 🚦 Invoice truy cập được? Jenkins lên được? |
| 2–3 | **Test Plan + Test Case Excel + RTM** | Docs xong = an toàn |
| 4–6 | Web: `CustomerPage`, `ProductPage`, `InvoicePage`, `InvoiceDetailPage` + 15 script | 🚦 EOD ngày 6 chưa xong → cắt theo bảng §5 |
| 7–8 | Mobile: 3 screen + 8 script Android (Sauce Labs) | 🚦 EOD ngày 8 checkout chưa chạy → về WDIO |
| **9** | `Jenkinsfile` + Allure plugin + build xanh | |
| 10 | Chạy lại **3 lần liên tiếp**, diệt flaky | |
| 11 | Test Summary + **quay video backup** cả 2 suite | Phao demo |
| 12 | Buffer + tập demo | |

Ngày 1 có **3 cổng go/no-go** (xem §6.1). Cả ba đều rẻ, mỗi cái cứu vài ngày.

---

## 6.1 RUNBOOK TUẦN TỰ (Web trước → Mobile sau)

### Nguyên tắc xuyên suốt: walking skeleton

> Mỗi phase, việc **đầu tiên** là đưa **một** test chạy xanh từ đầu đến cuối. Sau đó mới nhân bản case còn lại.

Sai lầm kinh điển của solo: viết 4 page class + 15 test rồi mới Run lần đầu → vỡ 30 chỗ cùng lúc, mất nguyên ngày debug. Có 1 test xanh trước thì mọi lỗi sau đều là lỗi cục bộ.

### Điều chỉnh: "tuần tự" áp dụng cho VIẾT SCRIPT, không áp dụng cho KIỂM TRA MÔI TRƯỜNG

Môi trường mobile (Android SDK + emulator + Appium + driver) là thứ dễ vỡ nhất. Để đến ngày 7 mới đụng mà fail thì chỉ còn 1 ngày buffer. Web thì `GuruBank` đã chạy được → rủi ro ~0.

**3 smoke gate ngày 1, mỗi cái 30', không viết code:**

| Gate | Làm gì | Pass = |
|---|---|---|
| A. WorkDo | Login demo, tạo tay 1 Invoice, xoá được | Invoice truy cập & sửa được |
| B. Jenkins | `java -jar jenkins.war --httpPort=8080` + job hello-world | Jenkins lên, 4 plugin cài xong |
| C. Appium | Bật emulator + Appium, chạy `LoginTest` có sẵn trong `UiFramework` | 1 test mobile cũ chạy xanh |

Gate C **không phải** làm mobile sớm — là kiểm tra máy chạy được Appium không. Fail thì còn 6 ngày xử lý thay vì 1.

### PHASE 0 — Ngày 1: Nền móng

| # | Việc | Xong khi |
|---|---|---|
| 0.1 | Gate A / B / C | 3 gate pass, hoặc biết cái nào fail |
| 0.2 | Tạo `Capstone/` + parent pom + 3 module con | `mvn clean compile` xanh |
| 0.3 | Copy package `common/` từ `UiFramework` → `core`. Copy nguyên, không đổi tên, không dọn | Build xanh |
| 0.4 | Thêm `RetryAnalyzer`, `TestListener` (screenshot-on-fail → Allure), `DataPrefix` (`AT_<ts>_<rand4>`) | Build xanh |
| 0.5 | Commit + push branch `capstone-skeleton` | Có commit đầu tiên |

🚦 Gate A fail → chuyển HRM ngay trong ngày 1.

### PHASE 1 — Ngày 2–3: Tài liệu (TRƯỚC khi code)

| # | Việc |
|---|---|
| 1.1 | Test Plan: scope, approach, env, entry/exit, **Risks & Mitigation**, giới hạn scope (Android only) |
| 1.2 | Test Case Excel theo format `TestCaseSuite_v2.xlsx` — 15 web + 8 mobile + 5–8 manual-only |
| 1.3 | Cột `Technique`: EP / BVA / Decision Table / State Transition |
| 1.4 | Sơ đồ state Invoice chèn vào Test Plan |
| 1.5 | RTM: Requirement ↔ Test Case ID ↔ Script method |

### PHASE 2 — Ngày 4–6: WEB (WorkDo Invoice)

**Ngày 4 — Walking skeleton + data setup**

| # | Việc |
|---|---|
| 2.1 | `LoginPage` + `object_repository/LoginPage.json` |
| 2.2 | ⭐ **Chạy được 1 test: login thành công.** Chưa xanh thì không đi tiếp |
| 2.3 | `LeftMenu` component → điều hướng Invoice list (copy pattern `GuruBank/pages/components/LeftMenu.java`) |
| 2.4 | `CustomerPage` + `ProductPage` — **chỉ hàm create** |

⚠️ **Customer & Product là test data setup, KHÔNG phải đối tượng test.** Không viết full POM CRUD (phí nửa ngày). Tạo **một lần** ở `@BeforeClass`, dùng chung cả suite, xoá ở `@AfterClass` — không tạo lại mỗi test.

**Ngày 5 — Nhân bản case**

| # | Việc |
|---|---|
| 2.5 | `InvoicePage` (list + create form) + `InvoiceDetailPage` |
| 2.6 | 5 case `INV_CRUD` |
| 2.7 | 5 case `INV_VALID` (rẻ — cùng form 2.6, chỉ đổi input + assert error) |

**Ngày 6 — Case ăn điểm + chốt**

| # | Việc |
|---|---|
| 2.8 | ⭐ 3 case `INV_CALC` — data-driven Excel qua `@DataProvider` + `ExcelHelper` |
| 2.9 | 2 case `INV_STATE` |
| 2.10 | Chạy full suite 1 lần, ghi lại case flaky (chưa sửa) |
| 2.11 | Commit + push |

🚦 EOD ngày 6 chưa xong → cắt theo §5, rồi **sang mobile ngay**, không nấn ná.

### PHASE 3 — Ngày 7–8: MOBILE (Sauce Labs My Demo App)

**Ngày 7 — Setup + skeleton**

| # | Việc |
|---|---|
| 3.1 | Tải APK `saucelabs/my-demo-app-rn` (GitHub Releases) + `adb install` |
| 3.2 | Dò `appPackage`/`appActivity` → tạo `devices/emulator-XXXX.properties` |
| 3.3 | Appium Inspector dump 3 màn hình → `object_repository/mobile/android/*.json` |
| 3.4 | ⭐ `LoginScreen` (extends `BaseScreen`) → 1 case login xanh |
| 3.5 | 3 case login còn lại |

**Ngày 8 — Nghiệp vụ**

| # | Việc |
|---|---|
| 3.6 | `CatalogScreen` + 2 case |
| 3.7 | `CartCheckoutScreen` + 2 case |
| 3.8 | Chạy full suite mobile, commit |

🚦 EOD ngày 8 checkout chưa chạy → chuyển WDIO Demo App, 6 case login (~4h). Quyết định **trong ngày 8**.

### PHASE 4 — Ngày 9: Jenkins

| # | Việc |
|---|---|
| 4.1 | `Jenkinsfile` Declarative Pipeline commit vào repo (không Freestyle) |
| 4.2 | Stage: `checkout → mvn clean test -DsuiteXmlFile=smoke.xml → allure report` |
| 4.3 | `smoke.xml` = 5 case web ổn định nhất, `CHROME_HEADLESS` |
| 4.4 | Global Tool Config: JDK + Maven + Allure Commandline |
| 4.5 | Build 3 lần liên tiếp, xanh cả 3 |

### PHASE 5 — Ngày 10–12: Ổn định + đóng gói

| Ngày | Việc |
|---|---|
| 10 | Chạy full suite web **3 lần liên tiếp**. Case fail ≥1 lần → sửa wait/locator, hoặc `RetryAnalyzer`, hoặc hạ xuống manual-only. Không để case "lúc xanh lúc đỏ" |
| 11 | Test Summary + **video backup** 2 suite + export Allure HTML + (khuyến nghị) Defect Report 3–5 bug (~2h) |
| 12 | Buffer + tập demo + cập nhật `README.md` hướng dẫn chạy |

### Kỷ luật git

`README.md` khoá dạy git flow rõ → mentor sẽ mở lịch sử commit. Solo hay commit 1 phát ngày cuối, nhìn rất tệ.
**Commit cuối mỗi ngày**, 1 branch/phase, message theo việc đã làm. 2 phút/ngày.

**Nguyên tắc sequencing:** tài liệu ngày 2–3, **trước** khi code. Nếu ngày 10 code còn lỗi vẫn có Test Plan + Excel để nộp. Mentor cũng chấm theo luồng test design → automation.

## 7. Success metrics

- [ ] ≥12 case web (mục tiêu 15) pass ổn định **3 lần chạy liên tiếp** local
- [ ] 8 case mobile pass trên Android emulator (fallback: 6 case WDIO)
- [ ] Jenkins Pipeline build xanh, smoke 5 case headless, `Jenkinsfile` trong git
- [ ] Test Plan + Test Case Excel (format `Documents/TestCaseSuite_v2.xlsx`) + RTM
- [ ] Allure report export HTML + **video backup** 2 suite
- [ ] Zero test phụ thuộc data có sẵn của demo site

## 8. Đề xuất bổ sung (chi phí thấp, giá trị cao)

**Defect Report** — không nằm trong yêu cầu bạn chọn, nhưng gần như miễn phí. Trong 3 ngày viết script chắc chắn vấp bug thật của WorkDo (sản phẩm CodeCanyon). 1 file, mỗi bug: ID / Steps / Expected / Actual / Screenshot. Tốn ~2h. Là thứ duy nhất trong bộ hồ sơ chứng minh bạn là **tester** chứ không phải người viết code Selenium.

## 8.5 KẾT QUẢ PHASE 0.1 — 3 SMOKE GATE (chạy 17/08/2026)

### GATE A — WorkDo ✅ PASS (giữ nguyên lựa chọn Invoice)

| Mục | Kết quả |
|---|---|
| Login | `dash-demo.workdo.io/login`, creds điền sẵn `company@example.com` / `1234`, nút **"Explore All Add-ons"** bật hết addon |
| URL sau login | `/account/dashboard` |
| Invoice list | **`/sales-invoices`** — Search by invoice number, Filters, 4 stat card (Outstanding/Overdue/Collected/Drafted) |
| Create form | **`/sales-invoices/create`** — đủ field theo thiết kế test |
| Invoice Summary | ✅ Subtotal / Discount / Tax / Total → target assert cho `INV_CALC` |
| Items table | Product / Qty / Unit Price / Discount % / Tax / Total + `+ Add Item` |

**5 phát hiện làm ĐỔI kế hoạch:**

1. **React SPA (shadcn/ui + Radix + Tailwind)**, KHÔNG phải Laravel Blade. `data-testid` = **0**. Khác hẳn GuruBank (PHP, ID sạch) → ngày 4 khó hơn dự kiến.

2. **Dropdown = Radix**, trigger là `[role=combobox]` với `aria-controls="radix-:r2ok:"` — **ID random mỗi render, CẤM dùng làm locator**.
   Nhưng có **6 `<select>` native ẩn** (`display:block`, `visibility:visible`, `aria-hidden=true`, options thật 25/13/12/16) → **`Select` class Selenium CÓ THỂ chạy**. ⚠️ Việc đầu tiên ngày 4: thử. Chạy được = tiết kiệm ~1 ngày.

3. **Datepicker: `#invoice_date` / `#due_date` là `type="hidden"`** (value ISO `2026-08-17`). `sendKeys` vô dụng → click qua calendar popup, hoặc JS set value + dispatch React event.

4. 🔴 **Invoice "Posted" KHÔNG có Edit, KHÔNG có Delete.** Chỉ 4 action: `pen-tool`, `download`, `file-down`, `eye`. Không có trash icon nào trên trang.
   → **Chiến lược cleanup R1 bị vỡ.** Chưa xác nhận Draft có delete (trang 1 toàn Posted). **Việc cần làm: Filters → Draft → kiểm tra (2 phút).**

5. **Invoice number auto-generated** (`INV-20260630-6a439058d3189`), không có ô nhập → **prefix `AT_<ts>` KHÔNG áp dụng cho invoice**.
   **Thay thế:** nhét prefix vào field **Notes** + **bắt invoice number sinh ra sau Create** → lưu biến → dùng để search/verify. Sửa `DataPrefix` theo hướng này ở ngày 1.

**Chướng ngại vặt (ghi vào object repository):**
- Overlay `"Please wait while we prepare your webapp"` mỗi lần load → explicit wait chờ biến mất
- **Bong bóng chat AI góc phải dưới che nút Create** → `ElementClickInterceptedException`, cần JS click / scroll
- Banner "Bundle Sale" trên cùng đẩy layout
- Action button **không có `title`/`aria-label`** → hook ổn định nhất: `//button[.//svg[contains(@class,'lucide-eye')]]`
- Login form **điền sẵn value** → `clear()` trước `sendKeys`

### GATE B — Jenkins ⚠️ PASS sau khi sửa

```
Running with Java 26 ... which is not yet fully supported.
Supported Java versions are: [21, 25]
```
Jenkins **từ chối khởi động** với JDK mặc định (Java 26). Máy **đã có sẵn JDK 21.0.11** → trỏ `JAVA_HOME` sang nó.

Xung đột 2: `pom.xml` đặt `maven.compiler.source/target = 25`.
**Khuyến nghị (KISS): hạ pom `Capstone` xuống 21, chạy mọi thứ trên JDK 21.** Một JDK duy nhất. Code không dùng tính năng Java 25 nào.

Đã có sẵn: Maven 3.9.16, Allure CLI 2.43.0, Node 26.

### GATE C — Appium ❌ FAIL

```
appium  → command not found
adb     → command not found
ANDROID_HOME → (rỗng)
~/Library/Android/sdk → không tồn tại
```
**Máy chưa có gì cho mobile.** `configuration.properties` trỏ `/usr/local/lib/node_modules/appium` = đường dẫn máy giảng viên.

Cần cài (~1–2h + vài GB tải):
1. `npm i -g appium`
2. `appium driver install uiautomator2`
3. Android Studio (hoặc cmdline-tools) → platform-tools (`adb`) + emulator + 1 system image
4. Set `ANDROID_HOME` + PATH
5. Chạy lại `LoginTest` trong `UiFramework` xác nhận

⚠️ **Tải Android Studio ngay hôm nay, chạy nền.** Đây là việc chặn duy nhất có thời gian chờ không rút ngắn được.

### 8.6 KẾT QUẢ VÒNG 2 (17/08 13:31) — 5 việc tiếp theo

#### 🎯 #2 — Draft CÓ delete. Cleanup được cứu.

`?status=draft` → 5 dòng. Action icons: `download`, `file-down`, `eye`, `file-text`, **`square-pen` (EDIT)**, **`trash2` (DELETE)**.

| Status | Edit | Delete |
|---|---|---|
| **Draft** | ✅ | ✅ |
| **Posted** | ❌ | ❌ |

**Chiến lược chốt:** invoice test **giữ ở Draft**, xoá ở `@AfterMethod`. Chỉ 2 case `INV_STATE` post lên Posted → chấp nhận 1–2 record mồ côi/lần chạy, ghi vào Test Plan là **giới hạn của ứng dụng**.

#### 🎯 PHÁT HIỆN LỚN: URL deep-link filter CHẠY ĐƯỢC

```
?status=draft            → lọc đúng 5 Draft
?search=SI-2026-02-020   → trả 1 dòng, tự điền vào ô search
```

**Thay đổi cuộc chơi cho ngày 4–6.** Test điều hướng thẳng tới view chỉ chứa record của chính nó:
- Không phải đánh vật Radix dropdown để lọc
- Không phải xử lý phân trang
- Không phụ thuộc dữ liệu người khác

→ **R1 (demo bẩn) gần như xử lý triệt để. Độ khó ngày 4–6 giảm đáng kể** so với đánh giá §8.5.

**Invoice number có 2 format** — đừng hardcode 1 regex:
- Draft/mới: `SI-2026-02-020` (tuần tự `SI-YYYY-MM-NNN`)
- Posted cũ: `INV-20260630-6a439058d3189`

#### #3 — Fingerprint dataset (chụp 17/08/2026 13:31)

```
5 invoice đầu: INV-20260630-6a439058d3189 / d143e / cfd03 / ce2f9 / cc7fa
Outstanding: 4,049,071.15$ — 28 invoices
Overdue:     3,994,911.23$ — 25 invoices
Collected:   428,843.14$   — 6 paid in full
Draft:       5
```
Ngày 18/08 mở `/sales-invoices`, so 4 con số. Khác → demo có reset.

#### #4 — Jenkins ✅ ĐANG CHẠY

```
Java: openjdk 21.0.11  →  HTTP 200, "Jenkins is fully up and running"
URL:  http://localhost:8080
Unlock: 300b674287144d98827287384261cc75      (PID 22031, kill 22031 để dừng)
```
Còn lại (làm tay qua UI): cài 4 plugin **Git, Maven Integration, Allure, HTML Publisher**.

Lệnh khởi động chuẩn:
```bash
export JAVA_HOME=/usr/local/Cellar/openjdk@21/21.0.11/libexec/openjdk.jdk/Contents/Home
java -jar UiFramework/src/main/resources/jenkins/jenkins.war --httpPort=8080
```

#### #5 — Appium ✅

```
appium 3.6.0 | uiautomator2@8.4.0 [installed]
```

⚠️ **Rủi ro mới R6:** pom dùng `appium-java-client 10.1.1` (viết cho **Appium 2.x**), vừa cài **Appium 3.x**.
Mitigation: ngày 7 nếu lỗi protocol lạ → `npm i -g appium@2` (bản khớp khoá học).

#### ✅ GATE C ĐÃ XANH (hoàn tất 17/08 ~14:00)

`appium driver doctor uiautomator2` → **0 required fixes needed**

| Thành phần | Trạng thái |
|---|---|
| ANDROID_HOME | ✅ `~/Library/Android/sdk` |
| adb + emulator | ✅ 1.0.41 (37.0.1) |
| JAVA_HOME (JDK 21) | ✅ |
| Appium | ✅ 3.6.0 |
| uiautomator2 | ✅ 8.4.0 |
| System image | ✅ `android-34;google_apis;x86_64` (4.2 GB) |
| AVD `Pixel7_API34` | ✅ |
| Emulator chạy | ✅ `emulator-5554 device`, Android 14, x86_64 |

Cảnh báo còn lại (`bundletool`, `gstreamer`) = **optional, không liên quan** (bundletool cho `.aab`, ta dùng `.apk`; gstreamer để stream màn hình).

**Vấn đề đã xử lý trên đường đi:**
1. **Emulator tự tắt khi chạy có cửa sổ từ shell** (~20s) — vấn đề window server. Dùng `-no-window` thì ổn. Chạy từ Device Manager của Studio thì bình thường.
2. **`avdmanager` của Homebrew không thấy SDK** — nó resolve SDK theo đường dẫn tương đối với chính nó. Fix: copy `cmdline-tools` vào `$ANDROID_HOME/cmdline-tools/latest` (layout chuẩn).
3. **Chọn API 34 thay vì 37** — SDK có sẵn `android-37.0` nhưng `uiautomator2 8.4.0` chưa kiểm chứng với API 37; ngày 7 không có thời gian debug driver.

**Đã thêm vào `~/.zshrc`:**
```bash
export ANDROID_HOME=$HOME/Library/Android/sdk
export ANDROID_SDK_ROOT=$ANDROID_HOME
export PATH=$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$ANDROID_HOME/cmdline-tools/latest/bin:$PATH
export JAVA_HOME_21=/usr/local/Cellar/openjdk@21/21.0.11/libexec/openjdk.jdk/Contents/Home
alias jenkins-start=...   # Jenkins voi JDK 21
alias emu-start=...       # bat Pixel7_API34
```

⚠️ **Ngày 7:** emulator lên đúng serial `emulator-5554`, trùng file `UiFramework/.../devices/emulator-5554.properties` có sẵn. Nhưng file đó trỏ `appPath=/Users/tuyenluu/...` (máy giảng viên) → phải tự tải APK và sửa `appPath`.

---

#### (Lịch sử) Android SDK lúc 13:31 — đã giải quyết

```
~/Library/Android/sdk → không tồn tại
adb → not found
```
Mới **tải** Android Studio, chưa **chạy** Setup Wizard (wizard mới tải SDK + platform-tools + emulator, vài GB).

Sau khi chạy wizard, thêm vào `~/.zshrc`:
```bash
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH
```
Rồi tạo 1 AVD trong Device Manager.

## 9. Đã chốt (cập nhật 17/8)

| # | Câu hỏi | Trả lời |
|---|---|---|
| 1 | App mobile | Mentor cho tự do → **Sauce Labs My Demo App (RN), Android** |
| 2 | CI | **Jenkins** (Pipeline + Jenkinsfile, local) |
| 5 | Nhân lực | **Solo** → cắt Cucumber/Grid/iOS, `core` = copy không refactor, pre-decide cut list |

## 10. Unresolved questions (cập nhật 17/08 13:31)

**Đã giải — PHASE 0.1 ĐÓNG, cả 3 gate XANH:**
- ~~Gate A: Addon Account/Finance có bật?~~ ✅ PASS + bonus URL deep-link filter
- ~~Draft có nút Delete?~~ ✅ CÓ — cleanup khả thi
- ~~Gate B: Jenkins chạy được?~~ ✅ JDK 21, HTTP 200
- ~~Gate C: Appium + Android SDK + AVD?~~ ✅ doctor 0 required fixes, `emulator-5554` booted

**Còn lại:**
1. **Cài 4 plugin Jenkins** (Git, Maven Integration, Allure, HTML Publisher) — làm tay qua UI `localhost:8080`, unlock `300b674287144d98827287384261cc75`.
2. **Demo site có reset không?** — 18/08 so fingerprint §8.6.
3. **`Select` class Selenium có chạy với native select ẩn của Radix không?** — thử đầu ngày 4. (Ưu tiên giảm — URL deep-link đã giải quyết phần lọc.)
4. **Appium 3.x có tương thích `java-client 10.1.1` không?** — R6, ngày 7. Fallback `npm i -g appium@2`.
5. **Tải APK + sửa `appPath`** — ngày 7, sau khi chốt app (Sauce Labs vs WDIO).
6. **Mentor có template Test Plan riêng của khoá không?**
7. **Máy đủ RAM chạy emulator + Chrome + Jenkins đồng thời?** — chưa test đồng thời. Nếu yếu, dùng máy Android thật qua `adb`.
8. **Defect Report** — chưa trong scope. ~2h, giá trị demo cao nhất. Cân nhắc ngày 11.
