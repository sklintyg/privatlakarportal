/**
 * Adds validation to confirm values are identical between two fields.
 */
angular.module('privatlakareApp').directive('validateConfirmEmail',
    [
        function() {
            'use strict';

            return {
                restrict: 'A',
                require: 'ngModel',
                scope: {
                    validateConfirmEmail: '='
                },
                link: function(scope, elem, attrs, ctrl){
                    var formName = elem.parents('form').attr('name');
                    scope.controller = ctrl;
                    scope.equality = scope.$parent[formName][attrs.validateConfirmEmail];
                    scope.$watch('[controller.$viewValue, equality.$viewValue]', function(newVal){
                        if(newVal[0] && newVal[1]){
                            ctrl.$setValidity('confirmEmail', newVal[0] === newVal[1]);
                        }
                    });
                }
            };
        }]);