Set oShell = CreateObject ("Wscript.Shell") 
Dim strArgs
strArgs = "cmd /c desktop_launch.bat"
oShell.Run strArgs, 0, false