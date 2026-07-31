@echo off
setlocal
set MVN_EXE=%~dp0..\..\..\..\..\SOFTWARE\idle ultimate\plugins\maven\lib\maven3\bin\mvn.cmd
if exist "%MVN_EXE%" (
  "%MVN_EXE%" %*
) else (
  echo Maven was not found. Please install Maven or update backend\mvnw.cmd.
  exit /b 1
)
