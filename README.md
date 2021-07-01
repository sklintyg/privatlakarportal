# Privatlakarportal

Tjänst för inloggning av privatläkare.

## Komma igång med lokal installation

Den här sektionen beskriver hur man bygger applikationen för att kunna köras helt fristående.

Vi använder Gradle för att bygga applikationerna.

Börja med att skapa en lokal klon av källkodsrepositoryt:

    git clone https://github.com/sklintyg/privatlakarportal.git

Läs vidare i gemensam dokumentation [devops/develop README-filen](https://github.com/sklintyg/devops/tree/release/2021-1/develop/README.md)

### Testa Privatläkarportalen med lokal Webcert

För att använda Privatläkarportalen (PP) tillsammans med Webcert (WC) i utvecklingsmiljön krävs det att både WebCert och PP är igång. Då både PP och WC använder sig av stubbat data i utvecklingsmiljön så krävs det att samma data finns upplagt i både PP och WC. Följande person kan användas då denna finns tillgägnlig i båda tjänster:
	Frida Kranstege (Privatläkare, Godkänd)

Gör så här för att testa att WC kan anropa och hämta information från PP i utvecklingsmiljön.

1. Starta PP enligt tidigare instruktion
2. Sätt följande properties i filen `webcert/webcert-dev.properties` i WC, vilket gör att WC anropar PP istället för en lokal stubbe. Notera att lokal stubbe för PP fortfarande startar, men att trafiken istället går till PP.
   
		privatepractitioner.base.url=http://localhost:8060/services
		privatepractitioner.portal.registration.url=http://localhost:8060
		privatepractitioner.internalapi.base.url=http://localhost:8160/internalapi
    
3. Starta WC
4. Starta en browser och gå till WC på https://wc.localtest.me/welcome.html
5. Välj "Frida Kranstege (Privatläkare, Godkänd)" och klicka på "Logga in"
6. Nu valideras användaren gentemot lokal PP på localhost:8060

## Licens
Copyright (C) 2021 Inera AB (http://www.inera.se)

Privatläkarportal is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

Privatläkarportal is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.

Se även [LICENSE](LICENSE). 