angular.module('privatlakareApp').service('RegisterViewStateService',
    function($state) {
        'use strict';

        this.reset = function() {
            this.step = 1;

            this.befattningList = [];
            this.vardformList = [];
            this.verksamhetstypList = [];
        };

        this.getStepFromUrl = function(url) {
            return Number(url[url.length - 1]);
        };

        this.updateStep = function() {
            this.step = this.getStepFromUrl($state.current.name);
        };

        this.reset();
    }
);
