#!/usr/bin/env bash
rm -rf masi_deploy
rm masi_deploy.tar
mkdir -p masi_deploy/backend
mkdir -p masi_deploy/frontend

# budowanie backendu
cd ./backend/
./gradlew clean build
cd ..
cp ./backend/build/libs/masi-0.0.1-SNAPSHOT.jar masi_deploy/backend/masi.jar
# kopiowanie .properties backendu
cp ./backend/src/main/resources/application-production.properties masi_deploy/backend/


# budowanie frontendu
cd ./frontend/
npm run ng build --prod
cd ..
cp ./frontend/dist/frontend/** ./masi_deploy/frontend/

# kopiowanie konfiguracji nginx
cp ./nginx.conf ./masi_deploy/nginx.conf

# kopiowanie docker-compose.yml
cp ./docker-compose.yml ./masi_deploy/

# kopiowanie klucza do google translate API
cp ./MASI-Black-google-credentials.json ./masi_deploy/backend/

# skrypt startujący kontenery
echo "docker-compose up --build -d">./masi_deploy/run.sh
chmod a+x ./masi_deploy/run.sh

# budowanie paczki
tar -cvpf masi_deploy.tar masi_deploy




