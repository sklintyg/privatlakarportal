angular.module('privatlakareApp')
  .controller('Step3Ctrl', function ($scope, RegisterViewStateService, UserModel, RegisterModel) {
        'use strict';
        RegisterViewStateService.updateStep();

        var data = RegisterModel.init();

        $scope.uppgifter = [
            { id: 'personnummer', name: 'Personnummer', value: UserModel.personnummer, locked: true },
            { id: 'namn', name: 'Namn', value: UserModel.name, locked: true },
            { id: 'befattning', name: 'Befattning', value: (!data.befattning) ? '' : data.befattning.label },
            { id: 'verksamhetensnamn', name: 'Verksamhetens namn', value: data.verksamhetensNamn },
            { id: 'agandeform', name: 'Ägarform', value: data.agandeForm, locked: true },
            { id: 'vardform', name: 'Vårdform', value: data.vardform.label },
            { id: 'verksamhetstyp', name: 'Verksamhetstyp', value: (!data.verksamhetstyp) ? '' : data.verksamhetstyp.label },
            { id: 'arbetsplatskod', name: 'Arbetsplatskod', value: data.arbetsplatskod }
        ];

        $scope.kontaktUppgifter = [
            { id: 'telefonnummer', name: 'Telefonnummer', value: data.telefonnummer },
            { id: 'epost', name: 'E-post till verksamheten', value: data.epost },
            { id: 'adress', name: 'Adress till tillverksamheten', value: data.adress },
            { id: 'postnummer', name: 'Postnummer till tillverksamheten', value: data.postnummer },
            { id: 'postort', name: 'Postort till tillverksamheten', value: data.postort },
            { id: 'kommun', name: 'Kommun', value: data.kommun },
            { id: 'lan', name: 'Län', value: data.lan }
        ];
  });
