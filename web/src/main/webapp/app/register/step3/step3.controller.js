angular.module('privatlakareApp')
  .controller('Step3Ctrl', function ($scope, RegisterViewStateService, UserModel, RegisterModel) {
        'use strict';
        RegisterViewStateService.updateStep();

        var data = RegisterModel.init();

        $scope.uppgifter = [
            { name: 'Personnummer', value: UserModel.personnummer },
            { name: 'Namn', value: UserModel.name },
            { name: 'Befattning', value: (!data.befattning) ? '' : data.befattning.label },
            { name: 'Verksamhetens namn', value: data.verksamhetensNamn },
            { name: 'Ägandeform', value: data.agandeForm },
            { name: 'Vårdform', value: data.vardform.label },
            { name: 'Verksamhetstyp', value: (!data.verksamhetstyp) ? '' : data.verksamhetstyp.label },
            { name: 'Arbetsplatskod', value: data.arbetsplatskod }
        ];

        $scope.kontaktUppgifter = [
            { name: 'Telefonnummer', value: data.telefonnummer },
            { name: 'E-post', value: data.epost },
            { name: 'Adress', value: data.adress },
            { name: 'Postnummer', value: data.postnummer },
            { name: 'Postort', value: data.postort },
            { name: 'Kommun', value: data.kommun }
        ];

        $scope.socialstyrelsenUppgifter = [
            { name: 'Legimiterad yrkesgrupp', value: data.legitimeradYrkesgrupp },
            { name: 'Specialitet', value: data.specialitet },
            { name: 'Förskrivarkod', value: data.forskrivarkod }
        ];
  });
