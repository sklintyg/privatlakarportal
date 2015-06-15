angular.module('privatlakareApp').service('RegisterViewStateService',
    function() {
        'use strict';

        this.reset = function() {
            this.befattningList = [];
            this.vardformList = [];
            this.verksamhetstypList = [];
        };

        this.reset();
    }
);
