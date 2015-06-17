angular.module('privatlakareApp')
    .controller('RegisterCtrl', function($scope, $state, UserModel, RegisterModel, RegisterViewStateService) {
        'use strict';
        $scope.user = UserModel;
        $scope.registerModel = RegisterModel.reset();
        $scope.viewState = RegisterViewStateService;

        $scope.$on('$stateChangeStart',
            function(event, toState/*, toParams, fromState, fromParams*/) {

                // Prevent user from navigating forwards if clicking the fishbone nav
                var toStep = RegisterViewStateService.getStepFromState(toState);
                if (toStep > RegisterViewStateService.step + 1) {
                    event.preventDefault();
                    // transitionTo() promise will be rejected with
                    // a 'transition prevented' error
                }
            });

        $scope.$on('$stateChangeSuccess',
            function(/*event, toState, toParams, fromState, fromParams*/) {
                RegisterViewStateService.updateStep();
            });

    });
