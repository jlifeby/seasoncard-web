'use strict';

// Define angular module
var app = angular.module('cms', ['ngResource', 'ngCookies', 'ui.scrollfix', 'ui.bootstrap']);

// Setup routing
app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            redirectTo: '/comms'
        })
        .when('/comms', {
            templateUrl: './assets/templates/comms.html',
            controller: 'CommsCtrl'
        })
        .when('/comms/:commsId/versions', {
            templateUrl: './assets/templates/comms-versions.html',
            controller: 'CommsCtrl'
        })
        .when('/comms/:commsId/versions/:documentId', {
            templateUrl: './assets/templates/document-edit.html',
            controller: 'DocumentsCtrl'
        })
        .when('/comms/:commsId/versions/:documentId/preview', {
            templateUrl: './assets/templates/document-preview.html',
            controller: 'PreviewCtrl'
        })
        .when('/offers', {
            templateUrl: './assets/templates/offers.html',
            controller: 'OffersCtrl'
        })
        .when('/offers/:offerId', {
            templateUrl: './assets/templates/offers.html',
            controller: 'OffersCtrl'
        })
        .when('/barcodes', {
            templateUrl: './assets/templates/barcodes.html',
            controller: 'BarcodesCtrl'
        })
        .when('/architect/:documentId', {
            templateUrl: './assets/templates/architect.html',
            controller: 'ArchitectCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
});

app.run(function ($rootScope, $location) {
    $rootScope.location = $location;
    $rootScope.locationIs = function (s) {
        return _.str.startsWith($location.path(), s);
    };
});