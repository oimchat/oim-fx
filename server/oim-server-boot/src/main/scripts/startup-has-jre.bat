@echo off

title oim-server-boot

setlocal

set minimal_version=1.7.0

set java_bin=jre\bin

if not defined java_bin (
    @echo 没有发现可运行Java虚拟机，请检查此跟目录是否有jre可运行虚拟机！
    set error_level=2
    goto pause
)

if .%oim_launch% == . set oim_launch=jre\bin\java.exe

if exist startup.bat goto winNT1
if .%oim_bin% == . set oim_bin=%~dp0

:winNT1
rem On NT/2K grab all arguments at once
set oim_cmd_line_args=%*


set heap=-Xms512m -Xmx512m
set new=-XX:NewSize=128m -XX:MaxNewSize=128m
set survivor=-XX:SurvivorRatio=8 -XX:TargetSurvivorRatio=50%
set tenuring=-XX:MaxTenuringThreshold=2


set class_unload=-XX:+CMSClassUnloadingEnabled
rem set DEBUG=-verbose:gc -XX:+PrintTenuringDistribution

rem Always dump on OOM (does not cost anything unless triggered)
set dump=-XX:+HeapDumpOnOutOfMemoryError



set ddraw=

set args=%dump% %heap% %new% %survivor% %tenuring%  %class_unload% %ddraw%

%oim_launch% %args% -jar "%oim_bin%oim-server-boot.jar" %oim_cmd_line_args%

rem If the error_level is not zero, then display it and pause

if NOT errorlevel 0 goto pause
if errorlevel 1 goto pause

goto end

:pause
echo errorlevel=%error_level%
pause

:end
