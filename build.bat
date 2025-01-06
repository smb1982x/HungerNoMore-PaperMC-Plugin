@echo off
setlocal enabledelayedexpansion

:: Maven build
echo Building with Maven...
mvn clean package

:: Check if build was successful
if errorlevel 1 (
    echo Build failed
    exit /b 1
)

echo.
echo Build complete! Your plugin jar can be found in the target directory.
echo.

:: Optional: Copy to plugins folder if it exists
if exist "%APPDATA%\.minecraft\plugins" (
    echo Minecraft plugins directory found. Would you like to copy the jar there? (Y/N)
    set /p COPY_CHOICE="> "
    if /i "!COPY_CHOICE!"=="Y" (
        copy /y "target\HungerNoMore-1.0-SNAPSHOT.jar" "%APPDATA%\.minecraft\plugins" >nul
        echo Plugin copied to Minecraft plugins directory.
    )
)

pause