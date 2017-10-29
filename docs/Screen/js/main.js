jQuery(document).ready(function(){
  'use strict';
  var $ = jQuery;

    //Country
    if ($.fn.county){
        $('#count-down').county({
            endDateTime: new Date('2015/12/29 10:00:00'),
            reflection: false
        }).addClass('count-loaded');
    }

}());