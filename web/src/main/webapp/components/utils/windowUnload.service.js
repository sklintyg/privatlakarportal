angular.module('privatlakareApp').factory('WindowUnload',
    function($window, $log,
        UserModel, ObjectHelper) {
        'use strict';

        return {
            bindUnload: function($scope, conditionObject) {

                if(!ObjectHelper.isDefined(conditionObject)) {
                    conditionObject = { condition: true };
                }

                $window.onbeforeunload = function(event) {

                    if(!ObjectHelper.isDefined(conditionObject.condition)) {
                        $log.debug('WindowUnload.bindUnload - form not available - skipping dialog.');
                        return null;
                    }

                    if (conditionObject.condition) {

                        if(!UserModel.get().loggedIn) {
                            $log.debug('WindowUnload.bindUnload - not logged in - skipping dialog.');
                            return null;
                        }

                        var message = 'Om du väljer "Lämna sidan" sparas inte dina inmatade uppgifter. Om du väljer "Stanna kvar på sidan" kan du spara ändringarna och sedan stänga webbläsaren.';
                        if (typeof event === 'undefined') {
                            event = $window.event;
                        }
                        if (event) {
                            event.returnValue = message;
                        }
                        return message;
                    }
                    return null;
                };

                $scope.$on('$destroy', function() {
                    $window.onbeforeunload = null;
                });

            }
        };
    }
);
