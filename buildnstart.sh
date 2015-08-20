Red='\033[0;31m'		# Red
Green='\033[0;32m'		# Green
NC='\033[0m' 			# No Color
project='fuzzer-android-server'

echo -e "${Green}START:${NC} "
cd ${project}/
#ant clean
android create uitest-project -n ${project} -t 3 -p ${project}/
ant build
adb push bin/${project}.jar /sdcard/
adb forward tcp:11235 tcp:11235
echo -e "${Green}Run uiAutomator:${NC} "
adb shell uiautomator runtest /sdcard/${project}.jar -e class driver.SetUpUiAutomator
