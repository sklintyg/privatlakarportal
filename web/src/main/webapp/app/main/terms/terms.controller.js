angular.module('privatlakareApp')
    .controller('MainTermsCtrl', function($scope, $http, $window) {
        'use strict';

        $scope.terms =
            'Dessa användarvillkor (\"Användarvillkoren\") ska gälla mellan Inera AB (\"Inera\") och användare av tjänsten Webcert (\"Kunden\").\n' +
            '\n' +
            'Version 1.0\n' +
            'Uppdaterad 2015-05-06\n' +
            '\n' +
            '1. Beskrivning av Webcert\n' +
            'Webcert är en tjänst som möjliggör för Kunden att skapa nya läkarintyg, komplettera befintliga läkarintyg, ' +
                'kopiera tidigare utfärdade läkarintyg samt skicka läkarintyg till externa aktörer, ' +
                't.ex. Försäkringskassan. Oavsett om läkarintyg lagras lokalt hos Kunden eller inte, sker lagring alltid ' +
                'på lagringsyta som förvaltas av Inera (”Intygstjänsten”). Vid kopiering och komplettering av läkarintyg ' +
                'i Webcert, hämtas befintliga läkarintyg från Intygstjänsten. Webcert möjliggör även för Kunden att skicka ' +
                'till och ta emot meddelanden från externa aktörer, t.ex. Försäkringskassan.\n' +
            'Lagring av läkarintyg i Intygstjänsten sker tillsvidare, övriga meddelanden som skickas och tas emot via ' +
                'Webcert lagras inte i Intygstjänsten.\n' +
            'Lagrade läkarintyg raderas endast efter skriftliga instruktioner från Kunden. Vid radering av läkarintyg i ' +
                'Intygstjänsten, ansvarar Kunden för att nödvändiga myndighetsbeslut erhållits från behörig myndighet.\n' +
            'Webcert tillhandahålls som en webbtjänst och kräver inloggning med SITHS eller e-legitimation. SITHS är en ' +
                'identifieringstjänst som tillhandahålls av Inera och innebär en tjänstelegitimation för både fysisk och ' +
                'elektronisk identifiering.\n' +
            'I syfte att säkerställa att läkarintyg endast innehåller senast gällande folkbokföringsuppgifter samt att ' +
                'minimera mängden information användaren av Webcert behöver fylla i manuellt, inhämtar Webcert ' +
                'kontaktuppgifter avseende invånare från Skatteverkets centrala aviseringssystem Navet.\n' +
            'Innan intyg kan utfärdas i Webcert måste Inera säkerställa att Kunden är behörig att utfärda intyg. ' +
                'Information om behörighet att utfärda intyg finns i Socialstyrelsens register över legitimerad ' +
                'hälso- och sjukvårdspersonal (”HOSP”) och om Kunden är registrerad sedan tidigare i Hälso- och ' +
                'sjukvårdens katalogtjänst HSA (”HSA”) finns informationen även där.\n' +
            '\n' +
            '2. Intyg i invånartjänster\n' +
            'Inera tillhandahåller även en tjänst för invånaren i form av en webbapplikation som möjliggör för invånare ' +
                'att själva hantera sina läkarintyg och skicka dem vidare till externa aktörer såsom Försäkringskassan. ' +
                'Om/när invånaren som ett läkarintyg avser samtyckt till den hantering av läkarintyg som sker i ' +
                'invånarens webbapplikation, lagras även kopia av läkarintyget tillhörande invånaren i Intygstjänsten. ' +
                'Invånarens kopia lagras logiskt åtskilt från Kundens kopia i Intygstjänsten.\n';
        $scope.terms = $scope.terms.replace(/\n/g, '<br />');

        $scope.dismiss = function() {
            $scope.$dismiss();
        };

        $scope.print = function() {
            $window.print();
        };
    });
