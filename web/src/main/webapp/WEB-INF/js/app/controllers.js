'use strict';

/**
 * Relation Controller
 */

'use strict';

app.controller('AppCtrl', ['$scope', '$rootScope',
    function ($scope, $rootScope) {

        $scope.init = function () {

        };
        $scope.init();


    }]);

app.controller('EventCtrl', ['$scope', '$rootScope',
    function ($scope, $rootScope) {
        $scope.init = function () {

        };
        $scope.init();

        $scope.$watch('lng', function () {
            $scope.geoObject = {
                geometry: {
                    type: "Point",
                    coordinates: [$scope.lat, $scope.lng]
                },
                // Свойства.
                properties: {
                    balloonContent: 'Место проведения события'
                }
            };
        });

    }]);

app.controller('EventsCtrl', ['$scope', 'EventSrv',
    function ($scope, EventSrv) {
        $scope.events = [];
        $scope.page = 1;

        $scope.init = function (param) {
            $scope.category = param.category;
            $scope.city = param.city;
        };

        $scope.addEvents = function () {
            EventSrv.events({
                category: $scope.category,
                city: $scope.city,
                page: $scope.page
            }, function (events) {
                if(!events || events.length == 0){
                    $scope.hideAddBtn = true;
                }
                $scope.events = $scope.events.concat(events);
                $scope.page++;
            });
        };

        $scope.$on('onRepeatLast', function(scope, element, attrs){
            if (typeof updateImageSrc != 'undefined') {
                updateImageSrc();
            }
        });

    }]);

app.controller('SearchEventsCtrl', ['$scope', 'EventSrv',
    function ($scope, EventSrv) {
        $scope.events = [];
        $scope.page = 1;

        $scope.init = function (param) {
            $scope.searchString = param.searchString;
        };

        $scope.addSearchedEvents = function () {
            EventSrv.searchEvents({
                searchString: $scope.searchString,
                page: $scope.page
            }, function (events) {
                $scope.events = $scope.events.concat(events);
                $scope.page++;
            });
        };

    }]);

app.controller('AdminCtrl', ['$scope', '$rootScope',
    function ($scope, $rootScope) {

        $scope.init = function () {

        };
        $scope.init();


    }]);

app.controller('EventDescCtrl', ['$scope', '$rootScope',
    function ($scope, $rootScope) {
        $scope.init = function () {

        };
        $scope.init();

        $scope.$watch('lng', function () {
            $scope.geoObject = {
                geometry: {
                    type: "Point",
                    coordinates: [$scope.lat, $scope.lng]
                },
                // Свойства.
                properties: {
                    balloonContent: 'Место проведения события'
                }
            };
        });

        $scope.mapClick = function (e) {
            var coords = e.get('coords');

            $scope.lat = coords[0].toPrecision(6);
            $scope.lng = coords[1].toPrecision(6);
            $scope.geoObject.geometry.coordinates = [coords[0].toPrecision(6), coords[1].toPrecision(6)];
            //ymaps.geocode(coords, {kind: 'locality'}).then(function (res) {
            //    console.log(res.geoObjects.get(0).properties);
            //    $scope.$apply(function(){
            //        $scope.address = res.geoObjects.get(0).properties.get('name');
            //    });
            //});

        };

    }]);

app.controller('EventParticipantCtrl', ['$scope',
    function ($scope) {

        $scope.participants = [{name: "Moroni", age: 50},
            {name: "Tiancum", age: 43},
            {name: "Jacob", age: 27},
            {name: "Nephi", age: 29},
            {name: "Enos", age: 34}];
        $scope.gridOptions = {data: 'participants'};

    }]);

app.controller('EventQuestCtrl', ['$scope', 'EventSrv',
    function ($scope, EventSrv) {

        $scope.init = function (eventId) {
            $scope.eventId = eventId;

            EventSrv.getInputFields({
                eventId: $scope.eventId
            }, function (inputFields) {
                $scope.inputFields = inputFields;
                angular.forEach($scope.inputFields, function (field) {
                    for (var i = 0; i < field.variants.length; i++) {
                        field.variants[i] = {name: field.variants[i]}
                    }
                });
            });
        };

        var InputField = function () {
            this.name = "";
            this.description = "";
            this.required = false;
            this.type = "text";
            this.variants = [{name: ""}, {name: ""}];
        };

        $scope.inputField = new InputField();

        $scope.saveInputField = function () {
            if ($scope.inputField.name.length > 0) {
                var field = $scope.inputField;
                for (var i = 0; i < field.variants.length; i++) {
                    field.variants[i] = field.variants[i].name;
                }
                EventSrv.addInputField({
                    eventId: $scope.eventId
                }, field, function (inputField) {
                    for (var i = 0; i < inputField.variants.length; i++) {
                        inputField.variants[i] = {name: inputField.variants[i]}
                    }
                    for (var i = 0; i < $scope.inputFields.length; i++) {
                        if (inputField.id == $scope.inputFields[i].id) {
                            $scope.inputFields[i] = inputField;
                            return;
                        }
                    }
                    $scope.inputFields.push(inputField);
                });
                $scope.inputField = new InputField();
            }
        };

        $scope.removeInputField = function (inputField) {
            if (inputField) {
                EventSrv.deleteInputField({
                    eventId: $scope.eventId,
                    entityId: inputField.id
                }, function (request) {
                    if (request) {
                        var index = $scope.inputFields.indexOf(inputField);
                        if (index > -1) {
                            $scope.inputFields.splice(index, 1);
                        }
                    }
                });
            }
        };

        $scope.editInputField = function (inputField) {
            $scope.inputField = inputField;
        };

        $scope.removeVariant = function (index) {
            if($scope.inputField.type == 'radio' && $scope.inputField.variants.length > 2){
                $scope.inputField.variants.splice(index, 1);
            } else if($scope.inputField.type == 'checkbox' && $scope.inputField.variants.length > 1){
                $scope.inputField.variants.splice(index, 1);
            }
        };

        $scope.showInputField = function () {
            $scope.showInputFieldItem = true;
            $scope.inputField = new InputField();
        };
        $scope.addVariant = function () {
            $scope.inputField.variants.push({name: ""});
        };

        $scope.changeType = function () {
            if($scope.inputField.type == 'radio' && $scope.inputField.variants.length < 2){
                $scope.inputField.variants.push({name: ""});
            }
        };

    }]);

app.controller('SystemMailCtrl', ['$scope',
    function ($scope) {

        $scope.removeEvent = function (item){
            var index = $scope.selectEvents.indexOf(item)
            $scope.selectEvents.splice(index, 1);
        }

    }]);