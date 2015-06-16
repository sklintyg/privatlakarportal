'use strict';

angular.module('privatlakareApp')
  .controller('Step3Ctrl', function ($scope, RegisterViewStateService, UserModel, RegisterModel) {
        RegisterViewStateService.updateStep();
        
        $scope.uppgifter = [
            { name: 'Personnummer', value: UserModel.personnummer },
            { name: 'Namn', value: UserModel.name },
            { name: 'Befattning', value: RegisterModel.befattning },
            { name: 'Verksamhetens namn', value: RegisterModel.verksamhetensNamn },
            { name: 'Ägandeform', value: RegisterModel.agandeForm },
            { name: 'Vårdform', value: RegisterModel.vardform },
            { name: 'Verksamhetstyp', value: RegisterModel.verksamhetstyp },
            { name: 'Arbetsplatskod', value: RegisterModel.arbetsplatskod }
        ];

        $scope.kontaktUppgifter = [
            { name: 'Telefonnummer', value: RegisterModel.telefonnummer },
            { name: 'E-post', value: RegisterModel.epost },
            { name: 'Adress', value: RegisterModel.adress },
            { name: 'Postnummer', value: RegisterModel.postnummer },
            { name: 'Postort', value: RegisterModel.postort },
            { name: 'Kommun', value: RegisterModel.kommun }
        ];

        $scope.socialstyrelsenUppgifter = [
            { name: 'Legimiterad yrkesgrupp', value: RegisterModel.legitimeradYrkesgrupp },
            { name: 'Specialitet', value: RegisterModel.specialitet },
            { name: 'Förskrivarkod', value: RegisterModel.forskrivarkod }
        ];
  });
