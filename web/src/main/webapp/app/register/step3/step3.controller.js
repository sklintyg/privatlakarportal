angular.module('privatlakareApp')
  .controller('Step3Ctrl', function ($scope, $log, $state,
        RegisterViewStateService, UserModel, RegisterModel, RegisterProxy) {
        'use strict';
        RegisterViewStateService.updateStep();

        $scope.viewState = RegisterViewStateService;

        var model = RegisterModel.init();

        $scope.uppgifter = [
            { id: 'personnummer', name: 'Personnummer', value: UserModel.personnummer, locked: true },
            { id: 'namn', name: 'Namn', value: UserModel.name, locked: true },
            { id: 'befattning', name: 'Befattning', value: (!model.befattning) ? '' : model.befattning.label },
            { id: 'verksamhetensnamn', name: 'Verksamhetens namn', value: model.verksamhetensNamn },
            { id: 'agarform', name: 'Ägarform', value: model.agarForm, locked: true },
            { id: 'vardform', name: 'Vårdform', value: model.vardform.label },
            { id: 'verksamhetstyp', name: 'Verksamhetstyp', value: (!model.verksamhetstyp) ? '' : model.verksamhetstyp.label },
            { id: 'arbetsplatskod', name: 'Arbetsplatskod', value: model.arbetsplatskod }
        ];


        /// TEMP FIX UNTIL kommun/län lookup is implemented
        model.postort = 'Linköping';
        model.kommun = 'Linköping';
        model.lan = 'Östergötland';

        $scope.kontaktUppgifter = [
            { id: 'telefonnummer', name: 'Telefonnummer', value: model.telefonnummer },
            { id: 'epost', name: 'E-post till verksamheten', value: model.epost },
            { id: 'adress', name: 'Adress till verksamheten', value: model.adress },
            { id: 'postnummer', name: 'Postnummer till verksamheten', value: model.postnummer },
            { id: 'postort', name: 'Postort till verksamheten', value: model.postort },
            { id: 'kommun', name: 'Kommun', value: model.kommun },
            { id: 'lan', name: 'Län', value: model.lan }
        ];

        $scope.createAccount = function(){
            RegisterProxy.registerPrivatlakare(model).then(function(successData) {
                $state.go('app.register.complete');
                RegisterViewStateService.registerErrorMessage = null;
                RegisterModel.reset();
            }, function(errorData) {
                RegisterViewStateService.registerErrorMessage = 'Kunde inte registrera privatläkare. (' + errorData.message + ')';
                $log.debug('Failed to register errorCode:' + errorData.errorCode + ' reason:' + errorData.message);
            });
        };

  });
