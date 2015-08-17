/**
 * Form directive to show socialstyrelsenuppgifter
 */
angular.module('privatlakareApp').directive('formSocialUppgifter',
    function() {
        'use strict';

        return {
            restrict: 'A',
            transclude: true,
            scope: true,
            controller: function($scope) {
                $scope.$watch('registerModel' , function(newVal) {
                    $scope.socialstyrelsenUppgifter = [
                        { id: 'legitimeradYrkesgrupp', name: 'Legimiterad yrkesgrupp', value: newVal.legitimeradYrkesgrupp, locked: true },
                        { id: 'specialitet', name: 'Specialitet', value: newVal.specialitet, locked: true },
                        { id: 'forskrivarkod', name: 'Förskrivarkod', value: newVal.forskrivarkod, locked: true }
                    ];
                });
            },
            templateUrl: 'components/formSocialUppgifter/formSocialUppgifter.directive.html'
        };
    });
