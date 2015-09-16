angular.module('privatlakareApp').service('Step2ViewState',
    function() {
        'use strict';

        this.reset = function() {
            this.step = 2;

            return this;
        };

        this.reset();
    }
);
