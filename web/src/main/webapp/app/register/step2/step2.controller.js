angular.module('privatlakareApp')
  .controller('Step2Ctrl', function ($scope, RegisterViewStateService) {
        'use strict';
        RegisterViewStateService.updateStep();
  });
