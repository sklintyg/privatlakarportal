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

    $ open http://localhost:8091/

### Testa Privatläkarportalen med lokal Webcert

För att använda Privatläkarportalen (PP) tillsammans med Webcert (WC) i utvecklingsmiljön krävs det att både WebCert och PP är igång. Då både PP och WC använder sig av stubbat data i utvecklingsmiljön så krävs det att samma data finns upplagt i både PP och WC. Följande person kan användas då denna finns tillgägnlig i båda tjänster:
	Frida Kranstege (Privatläkare, Godkänd)

Gör så här för att testa att WC kan anropa och hämta information från PP i utvecklingsmiljön.

1. Starta PP enligt tidigare instruktion
2. Sätt följande properties i filen `webcert/webcert-dev.properties` i WC, vilket gör att WC anropar PP istället för en lokal stubbe. Notera att lokal stubbe för PP fortfarande startar, men att trafiken istället går till PP.
	```
    privatepractitioner.base.url=http://localhost:8090/services
	privatepractitioner.portal.registration.url=http://localhost:8090
    ```
3. Starta WC
4. Starta en browser och gå till WC på http://localhost:9088/welcome.html
5. Välj "Frida Kranstege (Privatläkare, Godkänd)" och klicka på "Logga in"
6. Nu valideras användaren gentemot lokal PP på localhost:8090
