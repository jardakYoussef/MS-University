@echo off

for /d %%i in (*) do (
    if exist "%%i\pom.xml" (
        echo Building project in %%i ...
        pushd "%%i"
        mvn clean package -DskipTests
        popd
    )
)
docker-compose up
