# E2E tester med Cypress.io

## Utveckling 

För att installera beroenden kör 

    npm install

Vid utveckling av tester rekommenderas att man kör igång Cypress Test Runner. Med denna kan man köra tester individuellt och felsöka tester med den inbyggda browsern.

För att starta runnern kan man köra ett skript från `package.json`

    npm run-script cypress:open

Vilket är en förenkling av detta (som går lika bra om man föredrar det)

    ./node_modules/.bin/cypress open

Det går också att köra alla sviter i headless läge med följande kommando:

    npm run-script cypress:run

Som man ser av utskriften av kommandot ovan så skapas även en videofil där man kan se hela testkörningen.

### Nya testsviter

Läggs till under `cypress/integration`

## Gradle

Det finns en gradle task som kör alla testerna och skapar en testrapport under `build\test-results`. Denna installerar även alla beroenden med hjälp av yarn.

    gradlew cypressTest
