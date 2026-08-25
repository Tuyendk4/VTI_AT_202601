#!/usr/bin/env bash
# Chay ca ba lop test roi dung mot bao cao Allure duy nhat.
#
# Mobile duoc bo qua neu khong tim thay thiet bi nao: nguoi cham co the khong dung emulator,
# va bat ca script do vi mot lop khong chay duoc la vo ich. Ket qua mobile da chay san nam
# trong allure-report/ kem theo goi nop.
set -uo pipefail

cd "$(dirname "$0")"
export JAVA_HOME="${JAVA_HOME:-$(/usr/libexec/java_home -v21 2>/dev/null)}"
echo "JAVA_HOME=$JAVA_HOME"

echo "==> Bien dich va nap core"
mvn -q clean install -DskipTests || { echo "Build that bai"; exit 1; }

echo "==> API tests"
mvn -pl api-tests test; API_EXIT=$?

echo "==> Web tests"
mvn -pl web-tests test; WEB_EXIT=$?

MOBILE_EXIT=0
if adb devices 2>/dev/null | grep -qw "device"; then
  echo "==> Mobile tests"
  mvn -pl mobile-tests test; MOBILE_EXIT=$?
else
  echo "==> BO QUA mobile tests: khong co thiet bi Android nao dang ket noi"
  MOBILE_EXIT="skipped"
fi

echo "==> Gop ket qua va dung bao cao Allure"
rm -rf target/allure-results target/allure-report
mkdir -p target/allure-results
cp -r ./*/target/allure-results/* target/allure-results/ 2>/dev/null
allure generate target/allure-results -o target/allure-report --clean

echo
echo "================ KET QUA ================"
echo "API    : exit=$API_EXIT"
echo "Web    : exit=$WEB_EXIT"
echo "Mobile : exit=$MOBILE_EXIT"
echo "Bao cao: $(pwd)/target/allure-report/index.html"
