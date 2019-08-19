angular.module('privatlakareApp').factory('WindowUnload',
    function($window, $log, UserModel) {
      'use strict';

      return {
        enable: function() {
          this.enabled = true;
        },
        disable: function() {
          this.enabled = false;
        },
        bindUnload: function($scope) {

          this.enable();

          var windowUnload = this;

          $window.onbeforeunload = function(event) {

            if (windowUnload.enabled) {

              if (!UserModel.get().loggedIn) {
                $log.debug('WindowUnload.bindUnload - not logged in - skipping dialog.');
                return;
              }

              var message = 'Om du lämnar sidan sparas inte dina ändringar.';
              if (typeof event === 'undefined') {
                event = $window.event;
              }
              if (event) {
                event.returnValue = message;
              }
              return message;
            }
          };

          $scope.$on('$destroy', function() {
            $window.onbeforeunload = null;
          });

        }
      };
    }
);
