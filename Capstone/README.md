# Capstone - Test Automation Framework (Web + API + Mobile)

Bo khung kiem thu tu dong cho ba lop, dung chung mot module loi.

| Lop | He thong duoc test | So test | Trang thai |
|---|---|---|---|
| API | `restful-booker.herokuapp.com` | 7 | ✅ 7/7 |
| Web | `dash-demo.workdo.io` - phan he Sales Invoice | 7 | ✅ 7/7 |
| Mobile | WDIO Demo App v2.2.0 tren Android emulator | 6 | ✅ 6/6 |

Ket qua chi tiet, danh sach defect va gioi han: `docs/capstone-test-report.md`.

---

## 1. Kien truc

```
Capstone/
├── pom.xml                 # parent: quan ly phien ban thu vien + cau hinh Surefire dung chung
├── core/                   # THU VIEN DUNG CHUNG - khong chua test nao
│   └── vn/edu/vtiacademy/common/
│       ├── keywords/       # WebUI (Selenium), MobileUI (Appium)
│       ├── helper/         # Configuration, Device, PropertyHelper, Excel/File/DateTime
│       ├── listeners/      # TestListener (chup anh khi fail), RetryAnalyzer, RetryTransformer
│       └── data/           # TestDataPrefix - sinh marker AT_ cho du lieu test
├── api-tests/              # REST Assured
├── web-tests/              # Selenium + Page Object
└── mobile-tests/           # Appium + Screen Object
    └── apps/               # APK duoc test
```

**Vi sao tach `core` rieng:** ba lop test dung chung retry, chup man hinh khi fail, doc file
cau hinh va sinh du lieu co marker. Neu de moi module tu viet thi mot thay doi ve cach chup
anh phai sua ba noi. Module test chi phu thuoc `core`, khong phu thuoc lan nhau.

**Listener tu dong nap:** `TestListener` va `RetryTransformer` duoc TestNG nap qua
`ServiceLoader` (`core/src/main/resources/META-INF/services/org.testng.ITestNGListener`).
Vi vay khong file suite XML nao phai khai bao `<listeners>` - them suite moi la co san.

---

## 2. Yeu cau moi truong

| Thanh phan | Phien ban da kiem chung | Ghi chu |
|---|---|---|
| JDK | **21** | May co the dang dat mac dinh JDK khac - xem lenh o duoi |
| Maven | 3.9.16 | |
| Chrome | 151 | Chi can cho `web-tests` |
| Node.js | 26 | Chi can cho `mobile-tests` |
| Appium | 3.6.0 + driver `uiautomator2` | Chi can cho `mobile-tests` |
| Android SDK | AVD Android 14, ABI **x86_64** | Chi can cho `mobile-tests` |

```bash
# Bat buoc chay truoc moi lenh mvn neu JDK mac dinh cua may khong phai 21
export JAVA_HOME=$(/usr/libexec/java_home -v21)
```

---

## 3. Cach chay

```bash
cd Capstone
mvn clean install -DskipTests     # bien dich va nap module core vao local repo
```

### API - nhanh nhat, khong can cai gi them

```bash
mvn -pl api-tests test
```

Doi sang moi truong khac ma khong sua code:

```bash
mvn -pl api-tests test -Dapi.base.uri=https://moi-truong-khac.example.com
```

### Web

```bash
mvn -pl web-tests test                      # Chrome headless (mac dinh)
mvn -pl web-tests test -Dbrowser=CHROME     # mo trinh duyet de nhin tan mat
```

### Mobile - can chuan bi truoc

```bash
# 1. Khoi dong emulator va CHO BOOT XONG
$ANDROID_HOME/emulator/emulator -avd <ten_AVD> -gpu swiftshader_indirect &
adb wait-for-device
adb shell getprop sys.boot_completed        # phai tra ve 1

# 2. Cap nhat thong tin thiet bi cho khop may cua ban
#    mobile-tests/src/main/resources/devices/emulator-5554.properties
#    Lay gia tri bang:
adb devices                                  # -> udid
adb shell getprop ro.build.version.release   # -> platformVersion
adb shell getprop ro.product.model           # -> deviceName

# 3. Chay
mvn -pl mobile-tests test
```

> **Luu ý:** APK phai co ABI khop emulator. Kiem tra truoc khi doi app:
> `aapt2 dump badging <file.apk> | grep native-code` - emulator x86_64 ma APK chi co
> `arm64-v8a` thi Appium se cai that bai voi thong bao rat kho hieu.

### Chay mot suite khac

Moi module co bien `testsuite` tro toi file trong `src/test/resources/testsuites/`:

```bash
mvn -pl web-tests test -Dtestsuite=InvoiceTestSuite.xml
```

### Tat retry khi go loi

Mac dinh mot test fail se duoc chay lai 1 lan (he thong demo hay chap chon). Khi dang go loi
thi tat di de thay loi that ngay:

```bash
mvn -pl web-tests test -Dretry.count=0
```

### Luu y ve tai nguyen may - doc truoc khi chay ca ba lop

**Dung chay emulator Android va Chrome cung luc tren may cau hinh vua.** Da gap dung tinh
huong nay: emulator x86_64 + Chrome + Maven chay song song lam Chrome bi he dieu hanh giet
giua chung. Trieu chung rat de hieu nham thanh loi cua bo test: tu mot thoi diem tro di, MOI
lenh WebDriver tra ve tuc thi trong cung mot giay, cac test con lai do hang loat voi thong bao
kieu "khong thay bang danh sach" - trong nhu locator sai, thuc ra la trinh duyet da chet.

Cach chay an toan: dong emulator khi chay web, mo lai khi chay mobile.

```bash
adb emu kill                      # tra RAM cho Chrome
mvn -pl api-tests test
mvn -pl web-tests test
# mo lai emulator roi moi chay mobile
mvn -pl mobile-tests test
```

`./run-all-tests.sh` chay tuan tu ca ba lop nhung KHONG tu tat emulator - neu may yeu thi chay
tay theo ba buoc tren.

---

## 4. Bao cao Allure

```bash
# Gop ket qua ca ba module roi dung mot bao cao chung
mkdir -p target/allure-results
cp -r */target/allure-results/* target/allure-results/ 2>/dev/null

# --single-file la BAT BUOC - xem canh bao ben duoi
allure generate target/allure-results -o target/allure-report --clean --single-file
```

Ban dung san nam o `docs/allure-report.html` - **mo bang trinh duyet la xem duoc, khong can
cai Allure va khong can chay lai test.**

> ⚠️ **PHAI dung `--single-file`.** Ban nhieu file (mac dinh) nap du lieu test bang `fetch()`
> luc chay, nen mo bang `file://` se bi Chrome chan CORS va bao cao hien ra **RONG**: co day du
> giao dien nhung khong co test nao, khong co Epic nao. Da kiem chung bang Chrome headless -
> ban nhieu file khong render duoc mot ten test nao, ban single-file render du 3 Epic va 20 test.
> Suyt gui cho nguoi cham mot bao cao rong vi loi nay. `package-for-submission.sh` co chot kiem
> tra kich thuoc de chan lai.

Trong bao cao co: cac buoc (`@Step`) cua tung test, anh chup man hinh tai thoi diem fail,
va voi lop API la nguyen van request/response cua moi loi goi.

---

## 5. Nhung cho de vap - doc truoc khi sua code

Cac diem duoi day deu tra gia bang mot lan chay do moi tim ra, ghi lai de nguoi sau khong
mat cong lai:

1. **API tra ve `418 I'm a Teapot`.** Restful Booker khong parse header `Accept` dung chuan.
   `RequestSpecBuilder.setAccept(ContentType.JSON)` bung header thanh bon gia tri va bi tu
   choi. Phai dat thang `addHeader("Accept", "application/json")`. Xem `BaseApi`.

2. **Dropdown cua WorkDo la Radix Select - PHAI bam qua giao dien that.** Trong DOM co the
   `<select>` an va `Select` cua Selenium dat duoc gia tri cho no, nhung **React khong doc gia
   tri tu do**: form van coi truong la rong va bam Create khong gui gi len server. Cai bay o cho
   dat the an VAN lam cac danh sach phu thuoc tai lai, nen moi thu trong nhu dang chay dung.
   Phai bam `<button role=combobox>` roi chon `<div role=option>`.

3. **React ve lai form sau moi lan chon dropdown.** Tuyet doi khong cache `WebElement`; tim
   lai element ngay truoc moi thao tac.

4. **O ngay la `<input type=hidden>`.** Set gia tri bang JavaScript KHONG an - React giu ngay
   trong state rieng. Phai mo lich (react-datepicker) va bam vao o ngay nhu nguoi dung that.

5. **Mot lop phu de len nut Create.** Widget feedback ben thu ba
   (`#rt-feedback-widget-root`, dang loi 403) nam dung tren nut. `element.click()` nem
   `ElementClickInterceptedException`, con bam bang toa do thi **im lang roi vao lop phu** -
   khong request, khong thong bao, form dung im. Phai go lop phu roi bam. Rieng lich chon ngay
   thi nguoc lai: Radix Popover chi mo khi nhan pointer event that.

6. **Danh sach san pham nap bat dong bo** sau khi chon Warehouse. Cho bang delay co dinh se
   thinh thoang gap `<select>` rong -> chon truot -> bam Create khong co gi xay ra **va cung
   khong bao loi gi**. Phai cho co dieu kien.

7. **San pham duoc loc theo ton kho cua kho da chon.** Kho dau tien trong danh sach
   ("Central Distribution Center") co dung **0 san pham**; cac kho sau co 25 / 19 / 13. Ghi cung
   `warehouse[0]` thi khong bao gio chon duoc san pham va test do ma khong co manh moi nao.
   Page object vi vay **do tim kho con hang** thay vi ghi cung chi so.

8. **Hop thoai cua app mobile dung resource-id lan lon:** tieu de la
   `com.wdiodemoapp:id/alert_title` (cua app) nhung noi dung va nut lai la `android:id/*`
   (cua he dieu hanh). Doan la `android:id/alertTitle` thi khong bao gio tim thay.

9. **O so lieu la input co kiem soat cua React.** `clear()` + `sendKeys("99.99")` cho ra gia
   tri `"099"` va Summary hien `99.00$` - trong het nhu ung dung cat mat phan thap phan.
   Phai dat gia tri bang ham `set` nguyen ban cua `HTMLInputElement` roi ban `input`/`change`.

10. **Hai loai timeout, dung lan nhau la hong ca hai chieu.**
   - *Cho mot element SE xuat hien* -> can timeout DAI. Bo test mobile tung do ca sau case tren
     mot emulator dang cham chi vi cho 3 giay: React Native chua ve xong thong bao loi thi test
     da ket luan "khong co thong bao nao". Trieu chung trong het nhu app hong.
   - *Kiem tra mot element KHONG ton tai* -> can timeout NGAN. Voi mac dinh 30 giay cua `core`,
     moi cau tra loi "khong" ton dung 30 giay: bo test tung mat hon 15 phut chi de doi cac
     element vang mat.

   Vi vay `LoginScreen` co hai hang so rieng (`MESSAGE_TIMEOUT` va `ABSENCE_CHECK_TIMEOUT`)
   chu khong dung chung mot con so.

11. **Emulator vua bao `sys.boot_completed=1` VAN chua san sang.** Appium se bao
   *"Appium Settings app is not running after 30000ms"*, phien khong tao duoc, va vi
   `MobileUI.startApplication()` nuot exception nen MOI test sau do do voi thong bao khong lien
   quan gi den nguyen nhan that. `BaseMobileTest.verifyAppStarted()` chan viec nay lai va bao
   loi ro rang. Cach chay dung: doi them `init.svc.bootanim=stopped` va khoang 45 giay nua.

---

## 6. Tai lieu

| File | Noi dung |
|---|---|
| `docs/capstone-test-report.md` | Bao cao ket qua, danh sach test case, defect tim duoc, gioi han |
| `docs/exploratory-test-notes.md` | Ghi chep phien kham pha he thong WorkDo - co so de viet ky vong |
