@echo off

title oim-server-boot

setlocal

set MINIMAL_VERSION=1.6.0

set JAVAVER=jre\bin

if not defined JAVAVER (
    @echo Not able to find Java executable or version. Please check your Java installation.
    set ERRORLEVEL=2
    goto pause
)

if .%JM_LAUNCH% == . set JM_LAUNCH=jre\bin\java.exe

if exist startup.bat goto winNT1
if .%OIM_BIN% == . set OIM_BIN=%~dp0

:winNT1
rem On NT/2K grab all arguments at once
set JMETER_CMD_LINE_ARGS=%*


set HEAP=-Xms512m -Xmx512m
set NEW=-XX:NewSize=128m -XX:MaxNewSize=128m
set SURVIVOR=-XX:SurvivorRatio=8 -XX:TargetSurvivorRatio=50%
set TENURING=-XX:MaxTenuringThreshold=2


set CLASS_UNLOAD=-XX:+CMSClassUnloadingEnabled
rem set DEBUG=-verbose:gc -XX:+PrintTenuringDistribution

rem Always dump on OOM (does not cost anything unless triggered)
set DUMP=-XX:+HeapDumpOnOutOfMemoryError



set DDRAW=

set ARGS=%DUMP% %HEAP% %NEW% %SURVIVOR% %TENURING%  %CLASS_UNLOAD% %DDRAW%

%JM_LAUNCH% %ARGS% -jar "%OIM_BIN%oim-server-boot.jar" %JMETER_CMD_LINE_ARGS%

rem If the errorlevel is not zero, then display it and pause

if NOT errorlevel 0 goto pause
if errorlevel 1 goto pause

goto end

:pause
echo errorlevel=%ERRORLEVEL%
pause

:end
