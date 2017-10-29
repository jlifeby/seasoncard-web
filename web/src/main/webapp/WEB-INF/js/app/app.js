/**
 * App
 */

var app = angular.module('app', ['ngResource', 'yaMap', 'ngGrid', 'ui'])
    .controller('AdminCtrl', 'EventCtrl', 'EventDescCtrl', 'EventParticipantCtrl');