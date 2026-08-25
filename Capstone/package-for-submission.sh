#!/usr/bin/env bash
# Dong goi bo bai nop cho mentor.
#
# Goi nop gom: ma nguon day du + bao cao Allure dang HTML tinh + tai lieu.
# KHONG gom: thu muc target/ (sinh lai duoc), file APK 118MB (tai lai duoc tu GitHub release).
#
# Bao cao Allure duoc chep vao trong goi de mentor mo bang trinh duyet la xem duoc ngay,
# khong can cai Java/Maven/Appium va khong can chay lai test.
set -euo pipefail

cd "$(dirname "$0")"
CAPSTONE_DIR="$(pwd)"
PROJECT_ROOT="$(dirname "$CAPSTONE_DIR")"
STAMP="$(date +%Y%m%d)"
PACKAGE_NAME="Capstone_TestAutomation_${STAMP}"
STAGING="$PROJECT_ROOT/build-package/$PACKAGE_NAME"

echo "==> Don thu muc dong goi cu"
rm -rf "$PROJECT_ROOT/build-package"
mkdir -p "$STAGING"

echo "==> Chep ma nguon"
mkdir -p "$STAGING/Capstone"
for module in core api-tests web-tests mobile-tests; do
  mkdir -p "$STAGING/Capstone/$module"
  cp -R "$module/src" "$STAGING/Capstone/$module/" 2>/dev/null || true
  cp "$module/pom.xml" "$STAGING/Capstone/$module/"
done
cp pom.xml Jenkinsfile README.md .gitignore run-all-tests.sh "$STAGING/Capstone/"

echo "==> Chep tai lieu"
mkdir -p "$STAGING/docs"
cp "$PROJECT_ROOT/docs/"*.md "$STAGING/docs/" 2>/dev/null || true

echo "==> Chep bao cao Allure (mot file HTML tu chua)"
if [ -f target/allure-report/index.html ]; then
  cp target/allure-report/index.html "$STAGING/allure-report.html"
  # Kiem tra day dung la ban single-file: ban nhieu file chi ~1KB va se mo ra RONG.
  SIZE=$(wc -c < "$STAGING/allure-report.html")
  if [ "$SIZE" -lt 1000000 ]; then
    echo "    LOI: bao cao chi $SIZE bytes -> day la ban NHIEU FILE, mo bang file:// se ra rong."
    echo "         Chay lai: allure generate target/allure-results -o target/allure-report --clean --single-file"
    exit 1
  fi
else
  echo "    CANH BAO: chua co bao cao. Chay ./run-all-tests.sh truoc."
fi

echo "==> Ghi chu ve APK"
cat > "$STAGING/Capstone/mobile-tests/apps-README.txt" <<'TXT'
File APK khong nam trong goi nop vi nang 118MB.

Tai lai:
  https://github.com/webdriverio/native-demo-app/releases/download/v2.2.0/android.wdio.native.app.v2.2.0.apk

Dat vao: Capstone/mobile-tests/apps/android.wdio.native.app.v2.2.0.apk
roi cap nhat duong dan trong:
  Capstone/mobile-tests/src/main/resources/devices/emulator-5554.properties

PHAI dung dung phien ban v2.2.0: cac ban khac doi content-desc cua element,
va toan bo object repository se khong tim thay gi.
TXT

echo "==> Nen"
cd "$PROJECT_ROOT/build-package"
zip -qr "$PACKAGE_NAME.zip" "$PACKAGE_NAME"

echo
echo "================================================"
echo "Goi nop: $PROJECT_ROOT/build-package/$PACKAGE_NAME.zip"
du -h "$PACKAGE_NAME.zip" | cut -f1 | sed 's/^/Kich thuoc: /'
echo "Noi dung:"
find "$PACKAGE_NAME" -maxdepth 2 -type d | sed 's|^|  |'
