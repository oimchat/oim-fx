@echo off

title oim-server-boot

setlocal


set minimal_version=1.7.0

for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    rem @echo Debug Output: %%g
    set java_bin=%%g
)
if not defined java_bin (
    @echo 没有发现可运行Java环境，请检查是否安装Java可运行环境！
    set error_level=2
    goto pause
)
set java_bin=%java_bin:"=%
for /f "delims=. tokens=1-3" %%v in ("%java_bin%") do (
    set current_minor=%%w
)

for /f "delims=. tokens=1-3" %%v in ("%minimal_version%") do (
    set minimal_minor=%%w
)

if not defined current_minor (
    @echo 没有发现可运行Java环境，请检查是否安装Java可运行环境！
    set error_level=2
    goto pause
)
rem @echo Debug: CURRENT=%current_minor% - MINIMAL=%minimal_minor%
if %current_minor% LSS %minimal_minor% (
    @echo Error: Java 版本 -- %java_bin% -- 低于需要最低运行版本： %minimal_version%
    set error_level=3
    goto pause
)

if .%oim_launch% == . set oim_launch=java.exe

if exist startup.bat goto winNT1
if .%oim_bin% == . set oim_bin=%~dp0

:winNT1
rem On NT/2K grab all arguments at once
set oim_cmd_line_args=%*

rem The following link describes the -XX options:
rem http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html
rem http://java.sun.com/developer/TechTips/2000/tt1222.html has some more descriptions
rem Unfortunately TechTips no longer seem to be available

rem See the unix startup file for the rationale of the following parameters,
rem including some tuning recommendations
set heap=-Xms512m -Xmx512m
set new=-XX:NewSize=128m -XX:MaxNewSize=128m
set survivor=-XX:SurvivorRatio=8 -XX:TargetSurvivorRatio=50%
set tenuring=-XX:MaxTenuringThreshold=2
rem Java 8 remove Permanent generation, don't settings the PermSize
if %current_minor% LEQ "8" (
    rem Increase MaxPermSize if you use a lot of Javascript in your Test Plan :
    set perm=-XX:PermSize=64m -XX:MaxPermSize=128m
)

set class_unload=-XX:+CMSClassUnloadingEnabled
rem set DEBUG=-verbose:gc -XX:+PrintTenuringDistribution

rem Always dump on OOM (does not cost anything unless triggered)
set dump=-XX:+HeapDumpOnOutOfMemoryError

rem Additional settings that might help improve GUI performance on some platforms
rem See: http://java.sun.com/products/java-media/2D/perf_graphics.html

set ddraw=
rem  Setting this flag to true turns off DirectDraw usage, which sometimes helps to get rid of a lot of rendering problems on Win32.
rem set ddraw=%ddraw% -Dsun.java2d.noddraw=true

rem  Setting this flag to false turns off DirectDraw offscreen surfaces acceleration by forcing all createVolatileImage calls to become createImage calls, and disables hidden acceleration performed on surfaces created with createImage .
rem set ddraw=%ddraw% -Dsun.java2d.ddoffscreen=false

rem Setting this flag to true enables hardware-accelerated scaling.
rem set ddraw=%ddraw% -Dsun.java2d.ddscale=true

rem Server mode
rem Collect the settings defined above
set args=%dump% %heap% %new% %survivor% %tenuring% %perm% %class_unload% %ddraw%

%oim_launch% %args% -jar "%oim_bin%oim-server-boot.jar" %oim_cmd_line_args%

rem If the error_level is not zero, then display it and pause

if NOT errorlevel 0 goto pause
if errorlevel 1 goto pause

goto end

:pause
echo errorlevel=%error_level%
pause

:end
