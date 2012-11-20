@echo off

set basedir=%~f0
:strip
set removed=%basedir:~-1%
set basedir=%basedir:~0,-1%
if NOT "%removed%"=="\" goto strip
set RADICADOR_HOME=%basedir%
set LOCAL_JAVA=%RADICADOR_HOME%\jre7\bin\javaw.exe
	start "Radicador CCX" /b "%LOCAL_JAVA%" -jar -Xmx128m -splash:"ccx.png" -XX:-UseParallelGC -Dprofile=.\profile robot.radicador-1.0.jar 

