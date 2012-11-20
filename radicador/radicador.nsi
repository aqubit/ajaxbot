;--------------------------------

; The name of the installer
Name "Radicador CCX"

; The file to write
OutFile "target\radicador.exe"

; The default installation directory
InstallDir $PROGRAMFILES\RadicadorCCX

; Registry key to check for directory (so if you install again, it will 
; overwrite the old one automatically)
InstallDirRegKey HKLM "Software\RadicadorCCX" "Install_Dir"

; Request application privileges for Windows Vista
RequestExecutionLevel admin

;--------------------------------

; Pages

!include nsDialogs.nsh
!include "winmessages.nsh"
!define env_hklm 'HKLM "SYSTEM\CurrentControlSet\Control\Session Manager\Environment"'
!define env_hkcu 'HKCU "Environment"'

Page components
Page directory
Page Custom ccxhostcreate ccxhostleave " - IP Servidor Configuración."
Page instfiles

Function ccxhostcreate
	Var /Global MyTextbox
	nsDialogs::Create /NOUNLOAD 1018
	Pop $0
	${NSD_CreateText} 10% 20u 80% 12u "IP:8080"
	Pop $MyTextbox
	nsDialogs::Show
FunctionEnd

Function ccxhostleave
	${NSD_GetText} $MyTextbox $0
	; set variable
        WriteRegExpandStr ${env_hklm} CCXSERVER $0
   	; make sure windows knows about the change
        SendMessage ${HWND_BROADCAST} ${WM_WININICHANGE} 0 "STR:Environment" /TIMEOUT=5000
FunctionEnd

UninstPage uninstConfirm
UninstPage instfiles

;--------------------------------

; First is default
LoadLanguageFile "${NSISDIR}\Contrib\Language files\Spanish.nlf"

;--------------------------------

;--------------------------------

Function .onInit

	;Language selection dialog

	Push ""
	Push ${LANG_SPANISH}
	Push Spanish
	Push A ; A means auto count languages
	       ; for the auto count to work the first empty push (Push "") must remain
	LangDLL::LangDialog "Installer Language" "Please select the language of the installer"

	Pop $LANGUAGE
	StrCmp $LANGUAGE "cancel" 0 +2
		Abort
FunctionEnd

; The stuff to install
Section "FireFox 13.0.1" 
  SetOutPath "$TEMP"
  File "D:\downloads\tools\Firefox Setup 13.0.1.exe"
  DetailPrint "Starting the Firefox installation"
  ExecWait "$TEMP\Firefox Setup 13.0.1.exe"
SectionEnd

Section "Radicador CCX (required)"

  SectionIn RO
  
  ; Set output path to the installation directory.
  SetOutPath $INSTDIR
  
  SetOverwrite on

  ; Put file there
  File /r "target\robot.radicador-1.0\lib"
  File "target\robot.radicador-1.0.jar"
  File /r "installer\*.*"
  File /r "D:\sfw\Java32\jre7"

  ; Write the installation path into the registry
  WriteRegStr HKLM SOFTWARE\LicMan "Install_Dir" "$INSTDIR"
  
  ; Write the uninstall keys for Windows
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RadicadorCCX" "DisplayName" "Radicador CCX"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RadicadorCCX" "UninstallString" '"$INSTDIR\uninstall.exe"'
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RadicadorCCX" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RadicadorCCX" "NoRepair" 1
  WriteUninstaller "uninstall.exe"
  
SectionEnd

; Optional section (can be disabled by the user)
Section "Start Menu Shortcuts"
  SetShellVarContext all
  SectionIn RO
  CreateDirectory "$SMPROGRAMS\RadicadorCCX"
  CreateShortCut "$SMPROGRAMS\RadicadorCCX\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0
  CreateShortCut "$SMPROGRAMS\RadicadorCCX\RadicadorCCX.lnk" "$INSTDIR\radicador.bat" "" "$INSTDIR\radicador.bat" 0
  
SectionEnd

;--------------------------------

; Uninstaller

Section "Uninstall"
  
  DeleteRegValue ${env_hklm} CCXSERVER
  ; make sure windows knows about the change
  SendMessage ${HWND_BROADCAST} ${WM_WININICHANGE} 0 "STR:Environment" /TIMEOUT=5000

  ; Remove registry keys
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RadicadorCCX"

  ; Remove files and uninstaller
  Push  "$INSTDIR"
  
  ; Remove shortcuts, if any
  Delete "$SMPROGRAMS\RadicadorCCX\*.*"

  ; Remove directories used
  RMDir "$SMPROGRAMS\RadicadorCCX"
  RMDir /r "$INSTDIR"

SectionEnd
