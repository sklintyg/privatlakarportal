'use strict';

angular.module('privatlakareApp')
    .controller('RegisterCtrl', function ($scope, $state, UserModel, RegisterModel, RegisterViewStateService) {
        $scope.user = UserModel;
        $scope.registerModel = RegisterModel;
        $scope.viewState = RegisterViewStateService;
/*
        $scope.$on('$stateChangeStart',
            function(event, toState, toParams, fromState, fromParams){

                // Prevent user from navigating forwards if clicking the fishbone nav
                var toStep = RegisterViewStateService.getStepFromUrl(toState.name)
                if(toStep > RegisterViewStateService.step) {
                    event.preventDefault();
                    // transitionTo() promise will be rejected with
                    // a 'transition prevented' error
                }
            });

        $scope.$on('$stateChangeSuccess',
            function(event, toState, toParams, fromState, fromParams){
                RegisterViewStateService.updateStep();
            });
            */
    });
