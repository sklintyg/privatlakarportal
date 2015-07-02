/**
 * Form directive to show socialstyrelsenuppgifter
 */
angular.module('privatlakareApp').directive('formSocialUppgifter',
    function(RegisterModel) {
        'use strict';

        return {
            restrict: 'A',
            transclude: true,
            scope: true,
            controller: function($scope) {

                var data = RegisterModel.init();

                $scope.socialstyrelsenUppgifter = [
                    { id: 'legitimeradYrkesgrupp', name: 'Legimiterad yrkesgrupp', value: data.legitimeradYrkesgrupp, locked: true },
                    { id: 'specialitet', name: 'Specialitet', value: data.specialitet, locked: true },
                    { id: 'forskrivarkod', name: 'Förskrivarkod', value: data.forskrivarkod, locked: true }
                ];

            },
            templateUrl: 'components/formSocialUppgifter/formSocialUppgifter.directive.html'
        };
    });
