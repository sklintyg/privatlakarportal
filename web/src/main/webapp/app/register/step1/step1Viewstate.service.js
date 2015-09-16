angular.module('privatlakareApp').service('Step1ViewState',
    function() {
        'use strict';

        this.reset = function() {
            this.step = 1;

            return this;
        };

        this.reset();
    }
);
