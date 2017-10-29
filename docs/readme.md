Создать дамп:
mongodump -d <name> -o <out path>
(example, mongodump -d events_test -o D:\logs\)

Залить дамп в базу:
mongorestore --drop <path to dump>

Подробное описание параметров:
http://funix.ru/bazy-dannyx/rezervnoe-kopirovanie-dannyx-mongodb.html

Запуск сервиса:
net stop MongoDB

Поиск:

 mongod --setParameter textSearchEnabled=true
        or
 db.adminCommand({ setParameter: 1, textSearchEnabled: true })

Замена в базе

db.tops.update( {"type":"main-banner"}, { $set : { "finishDate" : new ISODate("2014-03-25 07:00:000.000Z") } }, true, true);


mongodump --host 127.0.0.1 -d development --port 27017 --username user --password pass --out /opt/backup/mongodump-2013-10-07-1

mongorestore --host 178.124.132.205 --port 27017 --drop /mongodump

mongodump --host 178.124.132.205 -d events_test --port 27017 --out logs\

gradlew.bat clean build


location / {
 		proxy_set_header X-Forwarded-Host $host;
        	proxy_set_header X-Forwarded-Server $host;
        	proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_pass http://localhost:8080/;
	expires 0;
    }

    location /uploaded/ {
        root   D:/Environment/events;
        index  index.html index.htm;
    }

    location /js/ {
        root   D:\Projects\jlife\events\web\src\main\webapp\WEB-INF;
    }

    location /css/ {
        root   D:\Projects\jlife\events\web\src\main\webapp\WEB-INF;
    }

    location /images/ {
        root   D:\Projects\jlife\events\web\src\main\webapp\WEB-INF;
    }

    location /fonts/ {
        root   D:\Projects\jlife\events\web\src\main\webapp\WEB-INF;
    }

    proxy_connect_timeout 1s;

    или
    
    location ^~ /(images|css|js|fonts)/ {
               root /Users/minsler/projects/events/web/src/main/webapp/WEB-INF/;
            }


