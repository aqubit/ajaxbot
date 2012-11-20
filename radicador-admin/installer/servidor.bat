@echo off
set basedir=%~f0
:strip
set removed=%basedir:~-1%
set basedir=%basedir:~0,-1%
if NOT "%removed%"=="\" goto strip

cd %basedir%\bin
start cmd /c asadmin.bat start-database --dbport 1527 --dbhome %basedir%\db
start cmd /c asadmin.bat start-domain
exit
