angular.module('privatlakareApp').factory('ObjectHelper',
    function() {
      'use strict';

      return {
        isDefined: function(value) {
          return value !== null && typeof value !== 'undefined';
        },
        isEmpty: function(value) {
          return value === null || typeof value === 'undefined' || value === '';
        },
        isFalsy: function(value) {
          return this.isEmpty(value) || value === 'false' || value === false;
        },
        returnJoinedArrayOrNull: function(value) {
          return value !== null && value !== undefined ? value.join(', ') : null;
        },
        valueOrNull: function(value) {
          return value !== null && value !== undefined ? value : null;
        },
        stringBoolToBool: function(value) {
          return value === true || value === 'true';
        },
        stringBoolToBoolUndefinedTrue: function(value) {
          if (!this.isDefined(value)) {
            return true;
          } else {
            return value === true || value === 'true';
          }
        }
      };
    }
);
