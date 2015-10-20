angular.module('privatlakareApp').service('MinsidaViewState',
    function() {
        'use strict';

        this.reset = function() {

            this.errorMessage = {
                load: null,
                noPermission: false,
                save: null
            };

            this.loading = {
                save: false
            };

            return this;
        };

        this.reset();
    }
);
