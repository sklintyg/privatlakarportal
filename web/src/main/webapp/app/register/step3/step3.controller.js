angular.module('privatlakareApp')
  .controller('Step3Ctrl', function ($scope, RegisterViewStateService, UserModel, RegisterModel) {
        'use strict';
        RegisterViewStateService.updateStep();

        var data = RegisterModel.init();

        $scope.uppgifter = [
            { id: 'personnummer', name: 'Personnummer', value: UserModel.personnummer },
            { id: 'namn', name: 'Namn', value: UserModel.name },
            { id: 'befattning', name: 'Befattning', value: (!data.befattning) ? '' : data.befattning.label },
            { id: 'verksamhetensnamn', name: 'Verksamhetens namn', value: data.verksamhetensNamn },
            { id: 'agandeform', name: 'Ägandeform', value: data.agandeForm },
            { id: 'vardform', name: 'Vårdform', value: data.vardform.label },
            { id: 'verksamhetstyp', name: 'Verksamhetstyp', value: (!data.verksamhetstyp) ? '' : data.verksamhetstyp.label },
            { id: 'arbetsplatskod', name: 'Arbetsplatskod', value: data.arbetsplatskod }
        ];

        $scope.kontaktUppgifter = [
            { id: 'telefonnummer', name: 'Telefonnummer', value: data.telefonnummer },
            { id: 'epost', name: 'E-post', value: data.epost },
            { id: 'adress', name: 'Adress', value: data.adress },
            { id: 'postnummer', name: 'Postnummer', value: data.postnummer },
            { id: 'postort', name: 'Postort', value: data.postort },
            { id: 'kommun', name: 'Kommun', value: data.kommun }
        ];

        $scope.socialstyrelsenUppgifter = [
            { id: 'legitimeradYrkesgrupp', name: 'Legimiterad yrkesgrupp', value: data.legitimeradYrkesgrupp },
            { id: 'specialitet', name: 'Specialitet', value: data.specialitet },
            { id: 'forskrivarkod', name: 'Förskrivarkod', value: data.forskrivarkod }
        ];
  });
