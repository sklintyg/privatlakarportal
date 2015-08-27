angular.module('privatlakareApp')
    .controller('BootCtrl', function($scope, $timeout, $state, $window,
        UserProxy, UserModel) {
        'use strict';
        $scope.loading = true;
        $scope.errorMessage = null;

        //$timeout(function() {
            UserProxy.getUser().then(function(successData) {
                $scope.loading = false;
                $scope.errorMessage = null;
                UserModel.set(successData);

                switch (UserModel.get().status) {
                case 'NOT_STARTED':
                    $state.go('app.start');
                    break;
                case 'NOT_AUTHORIZED':
                case 'WAITING_FOR_HOSP':
                case 'AUTHORIZED':
                    $state.go('app.minsida');
                    break;
                default:
                    $scope.errorMessage = 'Okänd användare utan status.';
                }
            }, function() {
                $scope.loading = false;
                $scope.errorMessage = 'Kunde inte hämta användare. Har du loggat in?';
                UserModel.fakeLogin();
            });
        //}, 3000);
    });
