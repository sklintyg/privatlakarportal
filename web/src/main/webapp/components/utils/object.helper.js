angular.module('privatlakareApp').factory('ObjectHelper',
    function() {
        'use strict';

        return {
            isDefined: function(value) {
                return value !== null && typeof value !== 'undefined';
            },
            returnJoinedArrayOrNull: function(value) {
                return value !== null && value !== undefined ? value.join(', ') : null;
            },
            valueOrNull: function(value) {
                return value !== null && value !== undefined ? value : null;
            }
        };
    }
);
