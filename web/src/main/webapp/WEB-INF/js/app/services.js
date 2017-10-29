/**
 * Service
 */

'use strict';

app.factory('EventSrv', function ($resource, contextPath) {
    return $resource(contextPath + '/rest/events/:eventId/:mode/:model/:entityId', {
        eventId: '@eventId',
        entityId: '@entityId'
    }, {
        events: {
            method: 'GET', isArray: true
        },
        searchEvents: {
            method: 'GET', isArray: true, params: {mode: 'search'}
        },
        getInputFields: {
            method: 'GET', isArray: true, params: {mode: 'get', model: 'inputfield'}
        },
        addInputField: {
            method: 'POST', params: {mode: 'save', model: 'inputfield'}
        },
        deleteInputField: {
            method: 'POST', params: {mode: 'delete', model: 'inputfield'}
        }
    });
});