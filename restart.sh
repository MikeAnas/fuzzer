Red='\033[0;31m'          # Red
Green='\033[0;32m'        # Green
NC='\033[0m' # No Color
project='fuzzer-android-server'


echo -e "${Green}Run uiAutomator:${NC} "
adb shell uiautomator runtest /sdcard/${project}.jar -e class driver.SetUpUiAutomator
