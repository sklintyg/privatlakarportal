/**
 * Adds validation to postnummer format to field.
 */
angular.module('privatlakareApp').directive('validatePostnummer',
    function(PostnummerHelper) {
        'use strict';

        return {
            restrict: 'A',
            require: 'ngModel',
            scope: {
                validatePostnummer: '='
            },
            link: function(scope, elem, attrs, ctrl){

                scope.controller = ctrl;
                scope.$watch('controller.$viewValue', function(newVal){
                    ctrl.$setValidity('format', PostnummerHelper.isValidPostnummer(newVal));
                });

            }
        };
    });