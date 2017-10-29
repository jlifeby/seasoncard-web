'use strict';

app.factory('Auth', function ($cookieStore) {

    var user = $cookieStore.get('user');

    return {
        user: user,
        hasRole: function (accessLevel) {
            return new RegExp('\\b' + accessLevel + '\\b','i').test(user.roles);
        }
    };
});

app.factory('CommsRes', function ($resource, $cookies) {
    return $resource('api/comms/:commsId', {token: $cookies.token});
});

app.factory('VersionsRes', function ($resource, $cookies) {
    return $resource('api/comms/:commsId/versions/:version', {
        commsId: '@commsId', version: '@version', token: $cookies.token
    }, {
        live: {
            method: 'GET', params: {version: 'live'}
        },
        draft: {
            method: 'GET', params: {version: 'draft'}
        },
        toLive: {
            method: 'PUT', params: {version: 'live'}
        }
    });
});

app.factory('SectionsRes', function ($resource, $cookies) {
    return $resource('api/documents/:docId/sections/:secId/:suffix', {
        docId: '@docId', secId: '@secId', token: $cookies.token
    }, {
        paragraphs: {
            method: 'GET', isArray: true, params: {suffix: 'paragraphs'}
        }
    });
});

app.factory('ParagraphsRes', function ($resource, $cookies) {
    return $resource('api/documents/:docId/paragraphs/:parId/:suffix', {
        docId: '@docId', parId: '@parId', token: $cookies.token
    }, {
        versions: {
            method: 'GET', isArray: true, params: {suffix: 'versions'}
        },
        newVersion: {
            method: 'POST', params: {suffix: 'versions'}
        }
    });
});

app.factory('OffersRes', function ($resource, $cookies) {
    return $resource('api/offers/:offerId', {
        offerId: '@offerId', token: $cookies.token
    }, {
        update: {
            method: 'PUT'
        }
    });
});

app.factory('BarcodesRes', function ($resource, $cookies) {
    return $resource('api/barcodes/:barId', {
        barId: '@barId', token: $cookies.token
    }, {
        update: {
            method: 'PUT'
        }
    });
});

app.factory('ArchitectRes', function ($resource, $cookies) {
    return $resource('api/architect/:documentId/:suffixL1/:sectionId/:suffixL2/:paragraphId', {
        documentId: '@documentId', sectionId: '@sectionId', token: $cookies.token
    }, {
        getDocumentSection: {
            method: 'GET',
            isArray: true,
            params: {suffixL1: 'section'}
        },
        insertSection: {
            method: 'POST',
            params: {suffixL1: 'section'}
        },
        updateSectionIndex: {
            method: 'PUT',
            params: {suffixL1: 'section'}
        },
        removeSection: {
            headers: {'Content-Type': 'application/json'},
            method: 'DELETE',
            params: {suffixL1: 'section'}
        },
        getSectionParagraphs: {
            method: 'GET',
            isArray: true,
            params: {suffixL1: 'section', suffixL2: 'paragraph'}
        },
        insertParagraph: {
            method: 'POST',
            params: {suffixL1: 'section', suffixL2: 'paragraph'}
        },
        updateParagraph: {
            method: 'PUT',
            params: {suffixL1: 'section', suffixL2: 'paragraph'}
        },
        removeParagraph: {
            headers: {'Content-Type':'application/json'},
            method: 'DELETE',
            params: {suffixL1: 'section', suffixL2: 'paragraph'}
        }
    });
});

app.factory('ResourceHelper', function ($resource, $cookies) {
    return $resource('api/:resource/:paramL1/:paramL2/:paramL3/:paramL4', {
        resource: '@resource',
        paramL1: '@paramL1',
        paramL2: '@paramL1',
        paramL3: '@paramL1',
        paramL4: '@paramL1',
        token: $cookies.token
    }, {
        get: {
            method: 'GET'
        },
        getArray: {
            method: 'GET',
            isArray: true
        }
    });
});