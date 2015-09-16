angular.module('privatlakareApp').service('MinsidaViewState',
    function() {
        'use strict';

        this.reset = function() {

            this.errorMessage = {
                noPermission: false,
                save: false
            };

            this.loading = {
                save: false
            };

            return this;
        };

        this.reset();
    }
);
