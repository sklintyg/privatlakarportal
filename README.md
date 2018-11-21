# Privatlakarportal

Tjänst för inloggning av privatläkare.

## Komma igång med lokal installation

Den här sektionen beskriver hur man bygger applikationen för att kunna köras helt fristående.

Vi använder Gradle för att bygga applikationerna.

Börja med att skapa en lokal klon av källkodsrepositoryt:

    git clone https://github.com/sklintyg/privatlakarportal.git

Efter att man har klonat repository navigera till den klonade katalogen rehabstod och kör följande kommando:

    ./gradlew build

Det här kommandot kommer att bygga samtliga moduler i systemet. 

När applikationen har byggt klart, kan man gå till `/web` och köra kommandot

    ./gradlew appRun

för att starta applikationen lokalt.

Nu går det att öppna en webbläsare och surfa till 

    http://localhost:8090/welcome.html 

Observera jetty körs i gradleprocessen, så gradle "blir inte klar" förrän du stoppar servern med ^c, och applikationen är bara igång fram till dess.

För att starta applikationen i debugläge används:

    ./gradlew appRunDebug
    
Applikationen kommer då att starta upp med debugPort = **5010**. Det är denna port du ska använda när du sätter upp din 
debug-konfiguration i din utvecklingsmiljö.

### Visa databasen

Man kan även komma åt H2-databasen som startas:

    $ open http://localhost:9090/

Fyll i JDBC URL'n: `jdbc:h2:tcp://localhost:8093/mem:datajpa`
(inera/inera)
