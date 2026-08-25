# Bao cao kiem thu tu dong - Capstone

**Nguoi thuc hien:** (dien ten) · **Ngay nop:** 27/08/2026
**Pham vi:** ba lop Web + API + Mobile, moi lop mot he thong that

---

## 1. Tom tat

| Lop | He thong duoc test | So test case | Ket qua | Thoi gian chay |
|---|---|---|---|---|
| API | Restful Booker (`restful-booker.herokuapp.com`) | 7 | **7/7 dat** | ~63 giay |
| Web | WorkDo Dash SaaS - Sales Invoice (`dash-demo.workdo.io`) | 7 | **7/7 dat** | ~340 giay |
| Mobile | WDIO Demo App v2.2.0 - Android 14 emulator | 6 | **6/6 dat** | ~284 giay |
| | | **20** | **20/20 dat** | ~11 phut |

Ket qua tren la cua mot lan chay day du bang `./run-all-tests.sh` (khong dung retry:
`-Dretry.count=0` cho cac lan doi chieu). Bao cao Allure kem theo: **`allure-report.html`** - mot file HTML tu chua, mo bang trinh duyet
la xem duoc, khong can chay lai test va khong can cai Java/Maven/Appium.

Bay defect duoc ghi o muc 4 KHONG lam do test nao: bo test ghi lai hanh vi thuc te cua he thong
va bao cao rieng cho nguoi phat trien, dung nguyen tac "test do chi khi he thong lech khoi hanh
vi da duoc chot".

---

## 2. Vi sao chon ba he thong khac nhau

Ba lop khong dung chung mot ung dung, va day la lua chon co chu dinh chu khong phai tien dau
lam day:

- **API - Restful Booker:** co xac thuc bang token va CRUD day du, cong khai, khong can dang ky.
  API noi bo cua WorkDo la Laravel dung session + CSRF, phai do nguoc luong xac thuc truoc khi
  viet duoc test dau tien - dat hon nhieu ma khong day them duoc ky thuat kiem thu nao moi.
- **Web - WorkDo Sales Invoice:** ung dung nghiep vu that, co tinh toan tien, co rang buoc
  bat buoc, co trang thai. Dung React + Radix nen bat buoc phai xu ly cac ky thuat kho: dropdown
  ao, DOM render lai lien tuc, element bi che.
- **Mobile - WDIO Demo App:** man hinh login co validate ro rang de ap dung phan lop tuong duong
  va gia tri bien. Da loai hai APK khac vi ly do ky thuat (xem muc 6).

---

## 3. Danh sach test case

### 3.1 API - Restful Booker

| Ma | Muc tieu | Ky thuat | Ket qua |
|---|---|---|---|
| API_AUTH_01 | `POST /auth` voi tai khoan dung tra ve token | Happy path | ✅ Dat |
| API_AUTH_02 | `POST /auth` voi mat khau sai KHONG tra ve token | Negative / bao mat | ✅ Dat |
| API_BOOK_01 | `POST /booking` tra ve dung du lieu da gui | Happy path + so sanh ca doi tuong | ✅ Dat |
| API_BOOK_02 | `GET /booking/{id}` tra ve dung ban ghi vua tao | Kiem tra du lieu that su duoc luu | ✅ Dat |
| API_BOOK_03 | `PUT /booking/{id}` co token cap nhat duoc | Happy path + doc lai de xac nhan | ✅ Dat |
| API_BOOK_04 | `PUT /booking/{id}` khong token bi tu choi 403 | Negative / bao mat | ✅ Dat |
| API_BOOK_05 | `DELETE /booking/{id}` xoa han ban ghi | Happy path + doc lai ra 404 | ✅ Dat |

### 3.2 Web - WorkDo Sales Invoice

| Ma | Muc tieu | Ky thuat | Ket qua |
|---|---|---|---|
| WEB_INV_01 | Trang danh sach tai duoc va co du lieu | Smoke | ✅ Dat |
| WEB_INV_02 | Tinh tien voi qty=2, don gia=100, chiet khau 0% | Kiem tra tinh toan | ✅ Dat |
| WEB_INV_03 | Tinh tien voi qty=3, don gia=100, chiet khau 10% | Kiem tra tinh toan | ✅ Dat |
| WEB_INV_04 | Tinh tien voi qty=1, don gia=99.99, chiet khau 50% | Bien lam tron (49.995) | ✅ Dat (lo ra DEF-07) |
| WEB_INV_05 | Tao hoa don hop le thi xuat hien trong danh sach | Happy path E2E | ✅ Dat |
| WEB_INV_06 | Tim theo so hoa don tra ve dung mot dong | Kiem tra co che co lap du lieu | ✅ Dat |
| WEB_INV_07 | Bam Create khi form trong thi KHONG tao ban ghi nao | Negative | ✅ Dat |

WEB_INV_07 co y **khong** assert noi dung thong bao loi: trong luc khao sat, form khi thieu du
lieu co luc hien thong bao co luc khong (DEF-03, DEF-04). Gan test vao mot hanh vi chua on dinh
se tao ra test chap chon; gan vao hau qua nghiep vu - "co sinh ra ban ghi rac hay khong" - thi
vua on dinh vua dung cai can bao ve.

Ba case tinh tien kiem tra hai thu: Subtotal va Discount phai bang dung so tinh tay, VA bon con
so trong khoi Summary phai nhat quan voi nhau theo `Total = Subtotal - Discount + Tax`. Phep
thu hai khong phu thuoc thue suat cua san pham nao, nen bo test khong vo khi du lieu demo doi.

### 3.3 Mobile - WDIO Demo App

| Ma | Email | Password | Ky vong | Ket qua |
|---|---|---|---|---|
| MOB_LOGIN_01 | hop le | hop le | Hop thoai "Success" / "You are logged in!" | ✅ Dat |
| MOB_LOGIN_02 | sai dinh dang | hop le | "Please enter a valid email address" | ✅ Dat |
| MOB_LOGIN_03 | rong | hop le | "Please enter a valid email address" | ✅ Dat |
| MOB_LOGIN_04 | hop le | 7 ky tu | "Please enter at least 8 characters" | ✅ Dat |
| MOB_LOGIN_05 | hop le | rong | "Please enter at least 8 characters" | ✅ Dat |
| MOB_LOGIN_06 | rong | rong | Hien ca hai thong bao | ✅ Dat |

Bo case duoc thiet ke theo **phan lop tuong duong** (email: hop le / sai dinh dang / rong;
password: du dai / thieu / rong) va **gia tri bien** (MOB_LOGIN_04 dung dung 7 ky tu - bien duoi
cua rang buoc "toi thieu 8").

---

## 4. Loi tim duoc

### DEF-01 - Restful Booker tra ve `418` cho header `Accept` hop le

- **He thong:** Restful Booker - `POST /booking`
- **Muc do:** Trung binh (chan hoan toan client dung thu vien HTTP thong dung)
- **Buoc tai hien:**
  ```bash
  curl -X POST https://restful-booker.herokuapp.com/booking \
    -H 'Content-Type: application/json' \
    -H 'Accept: application/json, application/javascript, text/javascript, text/json' \
    -d '{"firstname":"Jim","lastname":"Brown","totalprice":111,"depositpaid":true,
         "bookingdates":{"checkin":"2026-09-01","checkout":"2026-09-05"}}'
  ```
- **Ky vong:** `200` kem booking vua tao. Header tren hoan toan hop le theo RFC 9110 va
  `application/json` con nam o vi tri dau tien.
- **Thuc te:** `418 I'm a Teapot`, body `text/plain`.
- **Phan tich:** server so khop nguyen ca chuoi `Accept` thay vi parse danh sach media type.
  Day chinh la header mac dinh cua REST Assured khi dung `ContentType.JSON`, nen bat cu ai dung
  thu vien nay theo cach thong thuong deu bi chan. Dang chu y hon: `POST /auth` khong bi anh
  huong, nen trieu chung de bi tuong nham la loi xac thuc.
- **Cach di vong trong bo test:** dat thang `Accept: application/json`. Xem `BaseApi.ACCEPT_JSON`.

### DEF-02 - WorkDo: thong bao loi lo ten cot trong co so du lieu

- **He thong:** WorkDo - `/sales-invoices/create`
- **Muc do:** Thap (trai nghiem nguoi dung + lo cau truc du lieu)
- **Buoc tai hien:** mo form, de trong tat ca, bam **Create**
- **Ky vong:** `Customer is required.` / `Warehouse is required.`
- **Thuc te:** `The customer id field is required.` va
  `The warehouse id field is required when type is product.`
- **Phan tich:** hien nguyen ten cot (`customer id`, `warehouse id`) va ca logic noi bo
  (`when type is product`). Nguoi dung cuoi khong hieu, dong thoi lo cau truc du lieu ra ngoai.

### DEF-03 - WorkDo: dong Item khong duoc validate

- **He thong:** WorkDo - `/sales-invoices/create`
- **Muc do:** Trung binh
- **Buoc tai hien:** de trong tat ca, bam **Create**
- **Ky vong:** `Product`, `Qty`, `Unit Price` deu co dau `*` nen phai bao loi khi de trong
- **Thuc te:** chi ba loi cua Due Date / Customer / Warehouse hien ra, khong co loi nao cho
  dong Item du Product dang trong.

### DEF-04 - WorkDo: bam Create khi thieu Product thi khong co phan hoi nao

- **He thong:** WorkDo - `/sales-invoices/create`
- **Muc do:** **Cao** (nguoi dung bi ket, khong biet phai lam gi)
- **Buoc tai hien:** dien du Due Date + Customer + Warehouse, **khong chon Product**, bam Create
- **Ky vong:** hoac tao thanh cong, hoac bao ro "chua chon san pham"
- **Thuc te:** khong chuyen trang, khong thong bao, khong loi tren giao dien. Form dung im.
- **Phan tich:** phat hien trong luc go loi bo test tu dong - test bam Create ma khong co gi
  xay ra va khong co manh moi nao de lan. Nguoi dung that gap tinh huong nay se bam di bam lai
  ma khong hieu vi sao. Lien quan truc tiep toi DEF-03: validate thieu nen loi bi nuot im lang.

### DEF-06 - WorkDo: widget feedback cua ben thu ba che mat nut Create

- **He thong:** WorkDo - `/sales-invoices/create`
- **Muc do:** **Cao** (che mat hanh dong chinh cua man hinh)
- **Buoc tai hien:** mo form o do phan giai 1920x1200, dien day du, dua chuot toi nut **Create**
  o goc phai duoi
- **Ky vong:** bam duoc nut Create
- **Thuc te:** `document.elementFromPoint()` tai tam nut Create tra ve
  `<div id="rt-feedback-widget-root">` chu khong phai nut. Selenium bao
  `ElementClickInterceptedException: ... is not clickable at point (1846, 1213).
  Other element would receive the click: <div id="rt-feedback-widget-root">`.
- **Phan tich:** widget nay la cua ben thu ba (`response-tracker`) va tren he thong demo no dang
  hong - moi request cua no tra ve `403 Forbidden` (thay trong console). Mot widget hong lai
  trai lop phu len dung hanh dong chinh cua form. Nguoi dung that o cung do phan giai cung se
  bam khong duoc.
- **Cach chung minh khong phai loi cua ung dung ben duoi:** goi thang
  `document.querySelector('form').requestSubmit()` thi form gui `POST /sales-invoices` va nhan
  ve `200` kem thong bao *"The sales invoice has been created successfully."* - tuc la form va
  server hoan toan binh thuong, chi co lop phu chan cu bam.
- **Anh huong toi bo test:** `clickCreate()` go bo lop phu truoc khi bam.

### DEF-05 - WorkDo: chon kho khong con hang thi dropdown San pham rong ma khong bao gi

- **He thong:** WorkDo - `/sales-invoices/create`
- **Muc do:** Trung binh
- **Buoc tai hien:**
  1. Mo form tao Sales Invoice
  2. Chon Warehouse = **Central Distribution Center** (kho dau tien trong danh sach)
  3. Mo dropdown **Product** o dong Item
- **Ky vong:** hoac hien thong bao "kho nay khong con san pham", hoac khong cho chon kho rong
- **Thuc te:** dropdown San pham rong tuyet doi, khong co dong chu nao giai thich. Kho thu hai
  co 25 san pham, kho thu ba co 19, kho thu tu co 13 - nen day khong phai loi tai du lieu demo
  trong ma la thieu phan hoi cho mot trang thai hop le.
- **Phan tich:** ket hop voi DEF-04 thanh mot cai bay kin: chon nham kho -> khong chon duoc san
  pham -> bam Create khong co gi xay ra -> khong co bat cu thong bao nao chi ra nguyen nhan.
  Bo test tu dong da mat mot vong go loi vi dung ly do nay.
- **Anh huong toi bo test:** page object khong ghi cung chi so kho ma **do tim kho con hang**
  (`selectWarehouseWithAvailableProducts`), nho vay test khong vo khi du lieu demo thay doi.

---

## 4b. Hai ket luan sai da bi bac bo trong qua trinh lam

Ghi lai vi ca hai deu suyt tro thanh bao cao loi sai gui cho mentor:

**(1) "Ung dung cat mat phan thap phan cua don gia" - SAI.**
Test nhap `99.99` vao o Don gia, khoi Invoice Summary hien `99.00$`, trong het nhu mot loi lam
tron cua ung dung. Kiem chung lai thi o nhap ket thuc voi gia tri `"099"`: nguyen nhan la o so
lieu do React kiem soat, `clear()` khong dong bo state cua React va go tung phim vao
`<input type=number>` bi React ghi de sau moi ky tu. Dat gia tri bang ham `set` nguyen ban cua
`HTMLInputElement` roi ban su kien `input`/`change` thi ung dung tinh dung `99.99$`.
**Day la loi cua bo test, khong phai cua ung dung.**

**(2) "Dieu khien duoc the `<select>` an cua Radix la du" - SAI.**
Spike ngay 17/08 ket luan lop `Select` cua Selenium dat duoc gia tri cho cac the `<select>` an
ma Radix giu lai, va vi vay moi dropdown chi ton mot dong. Ket luan nay dung o phan "dat duoc
gia tri cho the an" nhung **sai o phan quan trong hon**: React khong doc gia tri tu the do, nen
form van coi truong la rong va bam Create khong gui gi len server. Cai bay nam o cho dat the an
VAN kich hoat cac danh sach phu thuoc tai lai (chon Warehouse xong thi danh sach San pham co du
lieu that), nen moi thu trong nhu da chay dung. Chi khi so sanh chu hien tren the
`<button role=combobox>` moi thay no van la *"Select Warehouse"*.
**Ket luan dung: phai bam combobox roi chon option nhu nguoi dung that.**

---

## 5. Diem ky thuat dang chu y trong bo khung

1. **Module `core` dung chung cho ca ba lop.** Retry, chup man hinh khi fail, doc cau hinh,
   sinh du lieu co marker deu viet mot lan. Ba module test chi phu thuoc `core`, khong phu
   thuoc lan nhau.

2. **Listener tu dong nap qua `ServiceLoader`** - khong file suite XML nao phai khai bao
   `<listeners>`, them suite moi la co san retry va chup anh khi fail.

3. **Object repository tach khoi code.** Locator nam trong file JSON; doi giao dien thi sua
   JSON, khong phai sua code Java va bien dich lai.

4. **Khong dung `static Response` dung chung o lop API.** Cach viet cu (luu response cuoi cung
   vao bien static roi cac ham kiem tra doc lai) hong ngay khi chay song song: hai test ghi de
   ket qua cua nhau. O day moi ham API tra ve `Response`, test tu giu lay.

5. **Moi test tu tao du lieu cua rieng no**, khong dung `dependsOnMethods`. Chay rieng le mot
   test bat ky van xanh, va mot test do khong keo theo ca chuoi bao SKIP che mat van de that.

6. **Don du lieu sau khi chay.** Bo test API xoa moi booking no tao ra. Du lieu web mang marker
   `AT_<timestamp>_<random>` trong o Notes de tim lai va don tay khi can.

7. **Cho theo trang thai, khong cho theo dong ho.** Cac cho doi quan trong (vao workspace, danh
   sach san pham nap xong) deu cho den khi dieu kien dung, khong dung `delay` co dinh - he thong
   demo cong cong co thoi gian phan hoi dao dong tu 2 den hon 10 giay.

8. **Hai co che chan loi mo ho, them vao sau khi bi chinh chung lam mat thoi gian:**
   - `BaseMobileTest.verifyAppStarted()` - dung ngay voi thong bao ro rang neu khong tao duoc
     phien Appium. Truoc do, phien that bai lam ca sau test do voi thong bao ve "thong bao loi
     email khong hien ra", khong he chi ra nguyen nhan that.
   - `LoginScreen` tach `MESSAGE_TIMEOUT` (cho element SE hien ra) khoi `ABSENCE_CHECK_TIMEOUT`
     (kiem tra element KHONG ton tai). Dung chung mot con so ngan cho ca hai lam bo test do het
     khi emulator cham, con dung chung mot con so dai thi bo test cham gap nhieu lan.

9. **Mot loi that trong `core` da duoc sua:** `PropertyHelper.getInstance()` dung mot bien
   `ThreadLocal` chua duoc gan gia tri, nen di nap file `null.properties` va nem exception.
   Loi chi lo ra o lop web (`WebUI` doc cau hinh ngay trong static initializer, truoc khi co ai
   dat ten file), con lop mobile khong dinh vi `MobileUI` tao `Configuration` truoc. Da them
   gia tri mac dinh la `configuration`.

---

## 6. Cac lua chon da loai va ly do

| Da loai | Ly do ky thuat |
|---|---|
| APKPure (`com.apkpure.aegon`) lam app mobile | Chi co `arm64-v8a` va `armeabi-v7a`; emulator la `x86_64` nen khong cai duoc. Ngoai ra id bi lam roi, noi dung doi theo gio, khong co nghiep vu on dinh de ap dung ky thuat thiet ke test |
| FC Mobile (game) lam app mobile | Toan bo giao dien ve trong mot `SurfaceView`, Appium dump ra 0 element - khong viet duoc screen object. Rao can la cach ve giao dien, khong phai ABI, nen may that cung khong cuu duoc |
| Guru99 Bank lam he thong web | Trang da chet (`404` tai thoi diem khao sat) |
| API noi bo cua WorkDo | Laravel dung session + CSRF, phai do nguoc luong xac thuc truoc khi viet duoc test dau tien |
| Mobile trong pipeline Jenkins | Can emulator hoac may that gan vao agent - ha tang khac han agent build. Mobile chay tay, ket qua nop kem trong bao cao Allure |

---

## 7. Gioi han cua bo test hien tai

Ghi ro de nguoi cham biet cai gi CHUA duoc bao ve, thay vi de tuong bo test phu rong hon thuc te:

1. **Web chi phu phan he Sales Invoice**, va trong do chi phu tao moi + tim kiem + tinh tien.
   Chua co: sua, xoa, chuyen trang thai (Draft -> Posted -> Paid), thanh toan mot phan.
2. **Khong test duong dan qua menu.** Cac trang deu mo bang deep-link URL. Neu menu hong thi
   bo test nay khong phat hien duoc.
3. **Mobile chi phu man hinh Login**, va chi tren Android. Chua co iOS du object repository cho
   iOS da co san.
4. **Chua co kiem thu du lieu tu file Excel** cho lop web - du lieu dang nam trong
   `@DataProvider` viet trong code.
5. **Chua co ma tran truy vet (RTM)** noi test case voi yeu cau - he thong khong co tai lieu
   dac ta nen chua co goc de truy vet.
6. **Chua chay song song.** Web va API chay tuan tu; mobile chi co mot emulator.
7. **Chay ca ba lop cung luc doi hoi may du RAM.** Emulator x86_64 + Chrome + Maven chay song
   song da lam Chrome bi giet giua chung trong mot lan chay thu: tu do tro di moi lenh WebDriver
   tra ve tuc thi va cac test con lai do hang loat voi thong bao khong lien quan den nguyen nhan
   that. Cach chay an toan (dong emulator khi chay web) da ghi trong `README.md`.
8. **Cac test doi hoi he thong ben ngoai con song.** Ca ba he thong deu la ban demo cong cong
   do ben thu ba van hanh - khong kiem soat duoc thoi gian phan hoi va du lieu.

---

### DEF-07 (can xac nhan) - WorkDo: chiet khau lam tron XUONG thay vi lam tron nua len

- **He thong:** WorkDo - `/sales-invoices/create`, khoi Invoice Summary
- **Muc do:** Thap tren mot hoa don, nhung can xac nhan vi sai so tich luy
- **Buoc tai hien:** Qty = `1`, Unit Price = `99.99`, Discount = `50%`
- **Ky vong (theo thong le ke toan, lam tron nua len):** Discount = `50.00`
- **Thuc te:** Discount = `49.99` (gia tri that la 49.995, bi lam tron xuong)
- **Phan tich:** chenh lech mot xu tren mot dong. Tren mot hoa don thi khong dang ke, nhung neu
  ap dung cho hang nghin dong thi sai so tich luy va lech so. Chua ket luan day la loi vi chua
  co dac ta - can mentor hoac chu san pham xac nhan huong lam tron mong muon.
- **Trong bo test:** WEB_INV_04 ghi lai hanh vi THUC TE (`49.99`) de test khong do vi mot dieu
  chua duoc chot, dong thoi de lo ra ngay neu hanh vi nay thay doi.

---

## 8. Cau hoi con bo ngo

1. **Huong lam tron cua chiet khau** (xem DEF-07): ung dung lam tron XUONG (49.995 -> 49.99)
   thay vi lam tron nua len (-> 50.00). Day la co y hay la loi? Cong thuc con lai da duoc xac
   dinh bang thuc nghiem va ghi trong `exploratory-test-notes.md` muc 2.3:
   `Subtotal = Qty x Unit Price`, `Discount` tinh tren tung dong, `Tax` tinh SAU khi tru chiet
   khau theo thue suat cua san pham, `Total = Subtotal - Discount + Tax`.
2. Bon loi o muc 4 co can lam thanh bao cao loi rieng theo mau cua khoa hoc khong, hay de trong
   bao cao nay la du?
3. Lop mobile co can bo sung iOS khong - object repository cho iOS da co san, chi thieu may
   chay va thoi gian.
