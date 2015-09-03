/**
 * Form directive to show socialstyrelsenuppgifter
 */
angular.module('privatlakareApp').directive('formSocialUppgifter',
    function(HospViewState, HospModel) {
        'use strict';

        return {
            restrict: 'A',
            scope: {
            },
            controller: function($scope) {
                var model = HospModel.init();
                HospViewState.bindScope($scope, HospViewState, model);
                HospViewState.loadHosp(HospViewState, model);
            },
            templateUrl: 'components/appDirectives/formSocialUppgifter/formSocialUppgifter.directive.html'
        };
    });
