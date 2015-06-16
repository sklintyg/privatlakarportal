angular.module('privatlakareApp')
    .controller('Step1Ctrl', function($scope, RegisterViewStateService) {
        'use strict';
        RegisterViewStateService.updateStep();
    });
