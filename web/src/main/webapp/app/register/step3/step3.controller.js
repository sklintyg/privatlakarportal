'use strict';

angular.module('privatlakareApp')
  .controller('Step3Ctrl', function ($scope, RegisterViewStateService) {
        RegisterViewStateService.updateStep();
  });
