;--------------------------------

; The name of the installer
Name "Servidor de Radicación CCX"

; The file to write
OutFile "servidor_radicacion.exe"

; The default installation directory
InstallDir $PROGRAMFILES\ServidorRadicacionCCX

; Registry key to check for directory (so if you install again, it will 
; overwrite the old one automatically)
InstallDirRegKey HKLM "Software\ServidorRadicacionCCX" "Install_Dir"

; Request application privileges for Windows Vista
RequestExecutionLevel admin

;--------------------------------

; Pages

Page components
Page directory
Page instfiles

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

Section "Servidor de Radicación CCX (required)"

  SectionIn RO
  
  ; Set output path to the installation directory.
  SetOutPath $INSTDIR
  
  SetOverwrite on

  ; Put file there
  File /r "installer\*.*"
  File /r "D:\sfw\glassfish3\*.*"

  ; Write the installation path into the registry
  WriteRegStr HKLM SOFTWARE\ServidorRadicacionCCX "Install_Dir" "$INSTDIR"
  
  ; Write the uninstall keys for Windows
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ServidorRadicacionCCX" "DisplayName" "Servidor de Radicación CCX"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ServidorRadicacionCCX" "UninstallString" '"$INSTDIR\uninstall.exe"'
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ServidorRadicacionCCX" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ServidorRadicacionCCX" "NoRepair" 1
  WriteUninstaller "uninstall.exe"
  
SectionEnd

; Optional section (can be disabled by the user)
Section "Start Menu Shortcuts"
  SetShellVarContext all
  SectionIn RO
  CreateDirectory "$SMPROGRAMS\ServidorRadicacionCCX"
  CreateShortCut "$SMPROGRAMS\ServidorRadicacionCCX\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0
  CreateShortCut "$SMPROGRAMS\ServidorRadicacionCCX\ServidorRadicacionCCX.lnk" "$INSTDIR\servidor.bat" "" "$INSTDIR\servidor.bat" 0
  
SectionEnd

;--------------------------------

; Uninstaller

Section "Uninstall"
  
  ; Remove registry keys
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ServidorRadicacionCCX"

  ; Remove files and uninstaller
  Push  "$INSTDIR"
  
  ; Remove shortcuts, if any
  Delete "$SMPROGRAMS\ServidorRadicacionCCX\*.*"

  ; Remove directories used
  RMDir "$SMPROGRAMS\ServidorRadicacionCCX"
  RMDir /r "$INSTDIR"

SectionEnd
