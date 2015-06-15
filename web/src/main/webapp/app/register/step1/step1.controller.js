'use strict';

angular.module('privatlakareApp')
  .controller('Step1Ctrl', function ($scope, RegisterViewStateService) {
        RegisterViewStateService.updateStep();
  });
