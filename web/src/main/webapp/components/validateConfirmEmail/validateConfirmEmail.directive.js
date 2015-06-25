/**
 * Enable tooltips for other components than wcFields
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
                    scope.$watch('[controller.$viewValue, equality.$viewValue]', function(newVal, oldVal){
                        if(newVal[0] && newVal[1]){// && newVal[0].length >= attrs.minlength){
                            ctrl.$setValidity('confirmEmail', newVal[0] === newVal[1]);
                        }
                    });
                }
/*                link: function(scope, elem, attrs, ngModelCtrl) {

                    ngModelCtrl.$validators.confirmEmail = function(modelValue) {
                        return scope.validateConfirmEmail === modelValue;
                    };

                }*/
            };
        }]);