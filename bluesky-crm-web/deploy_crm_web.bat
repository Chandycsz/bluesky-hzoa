title bluesky-crm-web
git pull
echo delete autocreate less file...
del .\src\assets\theme\color.less
echo build and package...
Call cnpm install
Call npm run build
echo backup old img...
xcopy /e /y /i D:\deploy_web\crm\img D:\deploy_web\img
echo deploy...
rd /s /q D:\deploy_web\crm
xcopy /e /y /i E:\web\bluesky-crm-web\dist D:\deploy_web\crm
xcopy /e /y /i D:\deploy_web\img D:\deploy_web\crm\img
pause