/**
 * Form directive to show socialstyrelsenuppgifter
 */
angular.module('privatlakareApp').directive('formSocialUppgifter',
    function(HospService, HospViewState, HospModel) {
        'use strict';

        return {
            restrict: 'A',
            scope: {
            },
            controller: function($scope) {
                var model = HospModel.init();
                HospService.bindScope($scope, HospViewState, model);
                HospService.loadHosp(HospViewState, model);
            },
            templateUrl: 'components/appDirectives/formSocialUppgifter/formSocialUppgifter.directive.html'
        };
    });
