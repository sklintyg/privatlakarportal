'use strict';

angular.module('privatlakareApp')
  .controller('Step2Ctrl', function ($scope, RegisterViewStateService) {
        RegisterViewStateService.updateStep();
  });
