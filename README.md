# Darves-Bornoz_Giovanni_SafetyNetAlerts
<<<<<<< HEAD
Safety Net Alerts an OpenClassrooms project.

developpement sur la branch "dev".
=======
Safety Net Alerts is an application that aims to send information to emergency services systems.

# API
Java 17 - Maven - Spring Boot 3.0.6

URL : http://localhost:8080/

Properties : src/main/resources/application.properties

# Data File JSON
Exemple for persons :

    {
      "firstName": "John",
      "lastName": "Boyd",
      "address": "1509 Culver St",
      "city": "Culver",
      "zip": "97451",
      "phone": "841-874-6512",
      "email": "jaboyd@email.com"
    }

Exemple for medical record :

    { 
      "firstName": "John",
      "lastName": "Boyd",
      "birthdate": "03/06/1984",
      "medications": ["aznol:350mg","hydrapermazol:100mg"],
      "allergies": ["nillacilan"]
    }

Exemple for firestation :

      {
        "address": "1509 Culver St",
        "station": "3"
      }

# Endpoints
## GET

### Return a list persons covered by Firestation :
http://localhost:8080/firestation?stationNumber={station_number}
* Ex : http://localhost:8080/firestation?stationNumber=1

### Return list of child(s) living at the address :
http://localhost:8080/childAlert?address={address}
* Ex : http://localhost:8080/childAlert?address=1509%20Culver%20St

### Return list of phone number for persons covered by the Firestation :
http://localhost:8080/phoneAlert?firestation={firestation_number}
* Ex : http://localhost:8080/phoneAlert?firestation=2

### Return list of persons covered by the firestation :
http://localhost:8080/fire?address={address}
* Ex : http://localhost:8080/fire?address=947%20E.%20Rose%20Dr

### Return list of households covered by firestation(s) :
http://localhost:8080/flood/stations?stations={list_of_station_numbers}
* Ex : http://localhost:8080/flood/stations?stations=1,2

### Return informations about a person or family : (Person if you put first&last name, or Family if you put just last name)
http://localhost:8080/personInfo?firstName={firstName}&lastName={lastName}
* Family ex :http://localhost:8080/personInfo?lastName=Boyd
* Person ex : http://localhost:8080/personInfo?firstName=John&lastName=Boyd

### Return list of emails of all the perons in the city :
http://localhost:8080/communityEmail?city={city}
* Ex : http://localhost:8080/communityEmail?city=Culver

## POST/PUT/DELETE
### JSON Body for /person
    { 
      "firstName":"Gerard", 
      "lastName":"Calvi", 
      "address":"789 Nous irons aux oeufs", 
      "city":"Lunaire", 
      "zip":"123456", 
      "phone":"0123456789", 
      "email":"noiretrouge@email.com" 
    }

### JSON Body for /firestation
    { 
      "address":"789 Nous irons aux oeufs", 
      "station":"1" 
    }

### JSON Body for /medicalRecord
    { 
      "firstName":"Gerard", 
      "lastName":"Calvi", 
      "birthdate":"03/03/1789", 
      "medications":["Petrol:1 verre, je vais en mettre 2", "Ciguë"], 
      "allergies":["Et un peu de poivre en grain"] 
    }

## Actuator
* INFO : http://localhost:8080/actuator/info
* HEALTH : http://localhost:8080/actuator/health
* METRICS : http://localhost:8080/actuator/metrics
* HTTP exchange : http://localhost:8080/actuator/httpexchanges
