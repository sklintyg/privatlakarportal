angular.module('common').controller('common.wcHeaderController',
    ['$anchorScroll', '$cookieStore', '$location', '$log', '$modal', '$scope', '$state', '$window', 'common.dialogService',
        'common.featureService', 'common.messageService', 'common.statService', 'common.User', 'common.UserModel',
        function($anchorScroll, $cookieStore, $location, $log, $modal, $scope, $state, $window, dialogService,
            featureService, messageService, statService, User, UserModel) {
            'use strict';

            //Expose 'now' as a model property for the template to render as todays date
            $scope.today = new Date();
            $scope.user = UserModel.userContext;

            /**
             * Private functions
             */

            /**
             * Exposed scope interaction functions
             */

            $scope.logout = function() {
                if (UserModel.userContext.authenticationScheme === 'urn:inera:webcert:fake') {
                    $window.location = '/logout';
                } else {
                    iid_Invoke('Logout');
                    $window.location = '/saml/logout/';
                }
            };
        }
    ]);
