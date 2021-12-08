#Anleitung

#### Voraussetzung
* [Docker-Desktop muss installiert sein] (getestet in Version 4.1.0)
* [Ein Webbrowser] (getestet mit Chrome, Mozilla-Firefox und Edge)

#### Das MonitoringApp ausführen

``` Powershell/cmd
$ docker-compose up -d
```
####  MonitoringApp kann nun bedient werden: http://localhost:4200








#### Hilfreiche Befehle für einen Clean-Start (Für mich)

# alle Docker-Container löschen
$ docker rm -f $(docker ps -a -q)

# alle Docker-Images löschen
$ docker rmi -f $(docker images -a -q)

# alle Docker-Volumes löschen
$ docker volume rm $(docker volume ls -q)