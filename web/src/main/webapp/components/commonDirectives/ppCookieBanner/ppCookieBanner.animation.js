angular.module('privatlakareApp').animation('.cookie-banner-directive-slide-animation', function() {
  'use strict';
  return {
    enter: function(element, done) {

      element.css({
        opacity: 0,
        top: '-80px'
      }).animate({
        opacity: 1,
        top: '0px'
      }, 500, done);
    },
    leave: function(element, done) {
      element.css({
        opacity: 1,
        top: '0px'
      })
      .animate({
        opacity: 0,
        top: '-80px'
      }, 500, done);
    }
  };
});