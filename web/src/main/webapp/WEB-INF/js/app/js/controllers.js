'use strict';

// Objects with suffix 'Res' are Resources defined in file services.js

Messenger.options = {
    extraClasses: 'messenger-fixed messenger-on-top messenger-on-right'
};

var logger = {
    info: function (msg) {
        Messenger().post({
            message: msg,
            hideAfter: 1.5,
            showCloseButton: true
        });
    },
    success: function (msg) {
        Messenger().post({
            message: msg,
            type: 'success',
            hideAfter: 1,
            showCloseButton: true
        });
    },
    error: function (response) {
        var msg = typeof response.data.message != 'undefined'
                ? response.data.message
                : response.data;
        Messenger().post({
            message: '<strong>Error!</strong> ' + msg,
            type: 'error',
            hideAfter: 5,
            showCloseButton: true
        });
        console.log(['Error ', response.status, ': ', JSON.stringify(response.data), ' (', response.config.method, ' ', response.config.url, ')'].join(''));
    }
};

function api(url, token, params) {
    var query = '';
    if (typeof params === 'object') {
        query = _.reduce(params, function (res, val, key) {
            return res + '&' + key + '=' + val;
        }, query);
    }
    return 'api' + url + '?token=' + token + '&burst=' + new Date().getTime() + query;
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

app.controller('CommsCtrl', function ($scope, $http, $routeParams, $cookies, $modal, $log, CommsRes, VersionsRes) {

    $scope.token =  $cookies.token;

    $scope.refreshCommsList = function () {

        CommsRes.query(function (commsList) {

            _.each(commsList, function (comm) {

                comm.versions = [];

                VersionsRes.live({
                    commsId: comm.id
                }, function (doc) {
                    comm.versions.push(doc);
                    comm.versions.live = doc;
                }, function (response) {
                    if (response.status != 404) {
                        logger.error(response);
                    }
                });

                VersionsRes.draft({
                    commsId: comm.id
                }, function (doc) {
                    comm.versions.push(doc);
                    comm.versions.draft = doc;
                }, function (response) {
                    if (response.status != 404) {
                        logger.error(response);
                    }
                });

            });

            $scope.commsList = commsList;

        }, function (response) {
            logger.error(response);
        });

    };

    $scope.refreshCommsVersionsPage = function () {

        CommsRes.get({
            commsId: $routeParams.commsId
        }, function (comms) {
            $scope.comms = comms;
            $scope.refreshCommsVersionsList();
        }, function (response) {
            logger.error(response);
        });

    };

    $scope.refreshCommsVersionsList = function () {

        VersionsRes.query({
            commsId: $routeParams.commsId
        }, function (versions) {

            _.each(versions, function (version, i) {
                if (version.id == $scope.comms.liveDocumentId) {
                    _.extend(version, {status: "live"});
                } else if (version.published) {
                    _.extend(version, {status: "approved"});
                } else {
                    _.extend(version, {status: "draft"});
                }
            });

            $scope.versions = versions;

        }, function (response) {
            logger.error(response);
        });

    };

    $scope.pushVersionToLive = function (version) {

        version.$toLive({
            commsId: version.commsId
        }, function (response) {
            $scope.refreshCommsVersionsPage();
        }, function (response) {
            logger.error(response);
        });

    };

    $scope.downloadDocument = function (documentId) {
        VersionsRes
        $routeParams.commsId
    };

    $scope.uploadDocument = function () {
        VersionsRes
        $routeParams.commsId
    };

    $scope.revertDraftChanges = function () {
        VersionsRes
        $routeParams.commsId
    }

    // Controller for modal window
    var ModalInstanceCtrl = function ($scope, $modalInstance, object) {

        $scope.object = object;

        $scope.ok = function () {
            $modalInstance.close($scope.object);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };

    // open dialog
    $scope.open = function () {

        $scope.actualObject = {};
        $scope.actualObject.token = $scope.token;
        $scope.actualModalTemplate = 'importDocument.html';

        var modalInstance = $modal.open({
            templateUrl: $scope.actualModalTemplate,
            controller: ModalInstanceCtrl,
            resolve: {
                object: function () {
                    return $scope.actualObject;
                }
            }
        });

        modalInstance.result.then(function (object) {
            $log.info('Return object: ' + object);
            $scope.returnedObject = object;
        }, function () {
            $log.info('Modal dialog for' + dialog + ' dismissed at: ' + new Date());
        });
    };
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// TODO: Refactor this madness!!!
app.controller('DocumentsCtrl', function ($scope, $http, $routeParams, CommsRes, VersionsRes, SectionsRes, ParagraphsRes, $timeout) {

    $scope.initPage = function () {

        // Get comms name
        CommsRes.get({
            commsId: $routeParams.commsId
        }, function (comms) {

            $scope.comms = comms;

            // Get version number and status
            VersionsRes.get({
                commsId: $routeParams.commsId,
                version: $routeParams.documentId
            }, function (version) {
                if (version.id == $scope.comms.liveDocumentId) {
                    _.extend(version, {status: "live"});
                } else if (version.published) {
                    _.extend(version, {status: "approved"});
                } else {
                    _.extend(version, {status: "draft"});
                }
                $scope.version = version;
            }, function (response) {
                logger.error(response);
            });

        }, function (response) {
            logger.error(response);
        });

        // Get sections list
        SectionsRes.query({
            docId: $routeParams.documentId
        }, function (sections) {
            $scope.sections = sections;
        }, function (response) {
            logger.error(response);
        });

    };

    $scope.activateSection = function (section) {

        $scope.currentSection = section;

        // load paragraphs list if necessary
        if (!_.has(section, 'paragraphs')) {
            SectionsRes.paragraphs({
                docId: $routeParams.documentId,
                secId: section.id
            }, function (paragraphs) {
                section.paragraphs = paragraphs;
                // then show first paragraph in list
                $scope.showParagraph(paragraphs[0]);
            }, function (response) {
                logger.error(response);
            });
        } else {
            // or just show first paragraph of section
            $scope.showParagraph(section.paragraphs[0]);
        }

    };

    $scope.showParagraph = function (paragraph) {

        $scope.currentParagraph = paragraph;

        ParagraphsRes.versions({
            docId: $routeParams.documentId,
            parId: paragraph.id
        }, function (versions) {

            var currentParagraphVersionText;
            if (versions.length > 0) {
                $scope.currentParagraphVersion = versions[0];
                currentParagraphVersionText = versions[0].text;
            } else {
                delete $scope.currentParagraphVersion;
                currentParagraphVersionText = '';
            }

            var editor = CKEDITOR.instances['paragraphText'];
            if (editor) {
                editor.editable().setHtml(currentParagraphVersionText);
            } else {
                CKEDITOR.replace('paragraphText');
            }

            // take all except first and last element
            var previousVersions = _.initial(versions);

            _.each(previousVersions, function (item, i) {
                // patch 'created' field to be able to use it with ng.filter:date
                item.created = new Date(item.created);
                // compare text of previous version with text of 'item'
                item.changes = highlightDiffText(versions[i+1].text, item.text);
            });

            $scope.previousParagraphVersions = previousVersions;

        }, function (response) {
            logger.error(response);
        });

    };

    $scope.saveParagraphChanges = function () {

        $scope.savingChanges = true;

        var newParagraphVersion = new ParagraphsRes({
            paragraphId: $scope.currentParagraph.id,
            text: CKEDITOR.instances['paragraphText'].getData()
        });

        newParagraphVersion.$newVersion({
            docId: $routeParams.documentId,
            parId: newParagraphVersion.paragraphId
        }, function (response) {
            $scope.savingChanges = false;
            logger.success("Saved");
            $scope.showParagraph($scope.currentParagraph);
        }, function (response) {
            $scope.savingChanges = false;
            logger.error(response);
        });

    };

    $scope.discardParagraphChanges = function () {

        CKEDITOR.instances['paragraphText'].editable().setHtml($scope.currentParagraphVersion.text);

    };

    // destroy ckeditor instance when route change starts
    $scope.$on('$routeChangeStart', function (next, current) {
        var editor = CKEDITOR.instances['paragraphText'];
        if (editor) { editor.destroy(true); }
    });

    $scope.pushVersionToLive = function (version) {

        version.$toLive({
            commsId: version.commsId
        }, function (response) {
            $scope.initPage();
        }, function (response) {
            logger.error(response);
        });

    };

});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

app.controller('PreviewCtrl', function ($scope, $http, $routeParams, $timeout, $cookies, CommsRes, VersionsRes, OffersRes) {

    var EMPTY_VALUE = '';
    var EMPTY_OPTION = {name: '--', value: EMPTY_VALUE};

    var commsType = '',
        tariffNameField,
        offerIdField;

    $scope.offers = [];

    function filterOffers(offers) {
        var tariffName = tariffNameField.value;
        return _.filter(offers, function (offer) {
            return tariffName != ''
                && (offer.offerData['plans'][tariffName] == 'checked' || offer.offerData['plans']['all'] == 'checked');
        });
    }

    function optionsFromOffers(offers) {
        var options = [];
        if (offers.length > 0) {
            options = _.map(offers, function (offer) {
                return {
                    name: offer.name,
                    value: offer.offerId
                }
            });
            options.unshift(EMPTY_OPTION);
        }
        return options;
    }

    var plans = [
        // consumer plans
        {value: 'freebc', name: 'Free Broadband & Weekend Calls with 10GB usage'},
        {value: 'freebaci', name: 'Free Broadband & Anytime Mobile Calls (1000) with 10GB usage'},
        {value: 'bc', name: 'Broadband & Weekend Calls with unlimited usage'},
        {value: 'baci', name: 'Broadband & Anytime Mobile Calls (1000) with unlimited/20GB usage'},
        {value: 'fibre40', name: 'Fibre & Weekend Calls with unlimited usage'},
        {value: 'fibre40ac', name: 'Fibre & Anytime Mobile Calls (1000) with unlimited usage'},
        {value: 'fibre80', name: 'Fibre Plus & Weekend Calls with unlimited usage'},
        {value: 'fibre80ac', name: 'Fibre Plus & Anytime Mobile Calls (1000) with unlimited usage'},
        // business plans
        {value: '40bus', name: 'Business Broadband with Unlimited usage'},
        {value: 'fibre40bus', name: 'Business 40Meg Fibre Broadband with Unlimited usage'},
        {value: 'fibre80bus', name: 'Business 80Meg Fibre Broadband with Unlimited usage'}
    ];

    var LineTypes = {
        ORANGE: {
            SOWL: 'TakeoverWorkingLine',
            SOSL: 'StartStoppedLine',
            MPF: 'Import',
            NLP: 'Provide'
        },
        APOLLO: {
            SOWL: 'SOWL',
            SOSL: 'SOSL',
            MPF: 'IMPORT',
            NLP: 'PROVIDE'
        }
    };

    var lineTypeRuleBase = {
        // Orange params
        regrade: false,
        consumerFibre: false,
        referral: false,
        elp: false,
        macNeededButNotProvided: false,
        lineTypeActive: false,
        // Apollo params
        nlp: false,
        // Common params
        lineType: ''
    };

    function buildLineTypeRule(rule) { return _.defaults(rule, lineTypeRuleBase); }

    $scope.previewRequestParams = _.defaults({
        tariffName: EMPTY_VALUE,
        market: '1',
        rentalSaver : false,
        callFeatureBundle: false,
        ppCharges: false,
        offerId: EMPTY_VALUE,
        selfInstall: false
    }, lineTypeRuleBase);

    var Field = function (extension) {
        this.name = '';
        _.extend(this, extension);

        if (!_.has(this, 'value'))
            this.value = $scope.previewRequestParams[this.name];

        if (!_.has(this, 'onChange'))
            this.onChange = function () {
                $scope.previewRequestParams[this.name] = this.value;
            };
    };

    $scope.fields = [
        new Field({
            name: 'tariffName', label: 'Product', type: 'select',
            options: [],
            filter: function (item) {
                if (item.value == EMPTY_VALUE) {
                    return true;
                } else if (commsType.indexOf('B2B') != -1) {
                    return item.value.indexOf('bus') != -1;
                } else if (commsType.indexOf('B2C') != -1) {
                    return item.value.indexOf('bus') == -1;
                } else {
                    return true;
                }
            },
            onChange: function () {
                $scope.previewRequestParams.tariffName = this.value;
                $scope.previewRequestParams.fibre = this.value.indexOf('fibre') != -1;

                // wrapping in $timeout is a hack for IE
                offerIdField.options = [];
                $timeout(function () {
                    offerIdField.options = optionsFromOffers(filterOffers($scope.offers));
                    if (!_.any(offerIdField.options, {value: offerIdField.value}))
                        offerIdField.value = EMPTY_VALUE;
                }, 50);
            }
        }),

        new Field({
            name: 'market', type: 'radio',
            options: [
                {name: 'Market 1', value: '1'},
                {name: 'Market 2', value: '2'},
                {name: 'Market 3', value: '3'}
            ]
        }),

        new Field({name: 'rentalSaver', label: 'Line Rental saver', type: 'checkbox'}),
        new Field({name: 'callFeatureBundle', label: 'Call Feature Bundle', type: 'checkbox'}),
        new Field({name: 'ppCharges', label: 'Postage & Packaging', type: 'checkbox'}),

        // OC & SP line types
        new Field({
            name: 'lineType', label: 'Line Type', type: 'list', value: EMPTY_VALUE,
            items: [
                {name: 'ELP (MAC provided)', preset: {elp: true}},
                {name: 'ELP (MAC needed)', preset: {elp: true, macNeededButNotProvided: true}},
                {name: 'Engineer', preset: {lineType: LineTypes.ORANGE.NLP}},
                {name: 'SOWL', preset: {lineType: LineTypes.ORANGE.SOWL}},
                {name: 'SOSL', preset: {lineType: LineTypes.ORANGE.SOSL}},
                {name: 'MPF', preset: {lineType: LineTypes.ORANGE.MPF}},
                {name: 'NLP Sim Provide', preset: {lineType: LineTypes.ORANGE.NLP, lineTypeActive: true}},
                {name: 'SOWL Sim Provide', preset: {lineType: LineTypes.ORANGE.SOWL, lineTypeActive: true}},
                {name: 'SOSL Sim Provide', preset: {lineType: LineTypes.ORANGE.SOSL, lineTypeActive: true}},
                {name: 'MPF Sim Provide', preset: {lineType: LineTypes.ORANGE.MPF, lineTypeActive: true}},
                {name: 'CC Referral', preset: {referral: true}},
//                {name: 'Employee', preset: {}},
                {name: 'Regrade ADSL to ASDL', preset: {regrade: true, elp: true}, shown: {fibre: false}},
                {name: 'Regrade Fibre to ASDL', preset: {regrade: true, consumerFibre: true, elp: true}, shown: {fibre: false}},
                {name: 'Regrade ADSL to Fibre', preset: {regrade: true, fibre: true, elp: true}, shown: {fibre: true}},
                {name: 'Regrade Fibre to Fibre', preset: {regrade: true, consumerFibre: true, elp: true}, shown: {fibre: true}}
            ],
            filter: function (item) {
                return _.has(item, 'shown')
                    ? _.every([$scope.previewRequestParams], item.shown)
                    : true;
            },
            show: function () {
                return /^(SP|OC)_B2.+/.test(commsType)
                    && tariffNameField.value != EMPTY_VALUE;
            },
            onClick: function (item) {
                this.value = item.name;
                _.extend($scope.previewRequestParams, buildLineTypeRule(item.preset));
            }
        }),

        // Apollo line types
        new Field({
            name: 'lineType', label: 'Line Type', type: 'list', value: EMPTY_VALUE,
            items: [
                {name: 'ELP', preset: {}},
                {name: 'Engineer', preset: {nlp: true, lineType: LineTypes.APOLLO.NLP}},
                {name: 'SOWL', preset: {nlp: true, lineType: LineTypes.APOLLO.SOWL}},
                {name: 'SOSL', preset: {nlp: true, lineType: LineTypes.APOLLO.SOSL}},
                {name: 'MPF', preset: {nlp: true, lineType: LineTypes.APOLLO.MPF}},
                {name: 'NLP Sim Provide', preset: {nlp: true, lineType: LineTypes.APOLLO.NLP}},
                {name: 'SOWL Sim Provide', preset: {nlp: true, lineType: LineTypes.APOLLO.SOWL}},
                {name: 'SOSL Sim Provide', preset: {nlp: true, lineType: LineTypes.APOLLO.SOSL}},
                {name: 'MPF Sim Provide', preset: {nlp: true, lineType: LineTypes.APOLLO.MPF}}
            ],
            filter: function (item) {
                return _.has(item, 'shown')
                    ? _.every([$scope.previewRequestParams], item.shown)
                    : true;
            },
            show: function () {
                return /^APOLLO*/.test(commsType)
                    && tariffNameField.value != EMPTY_VALUE;
            },
            onClick: function (item) {
                this.value = item.name;
                _.extend($scope.previewRequestParams, buildLineTypeRule(item.preset));
            }
        }),

        new Field({name: 'selfInstall', label: 'Self Install', type: 'checkbox'}),

        new Field({name: 'offerId', label: 'Offer', type: 'select', options: []})
    ];

    tariffNameField = _.find($scope.fields, {name: 'tariffName'});
    offerIdField    = _.find($scope.fields, {name: 'offerId'});

    $scope.initPage = function () {

        // Get comms name
        CommsRes.get({
            commsId: $routeParams.commsId
        }, function (comms) {

            $scope.comms = comms;
            commsType = comms.type.toUpperCase();

            // Get version number and status
            VersionsRes.get({
                commsId: $routeParams.commsId,
                version: $routeParams.documentId
            }, function (version) {
                if (version.id == $scope.comms.liveDocumentId) {
                    _.extend(version, {status: "live"});
                } else if (version.published) {
                    _.extend(version, {status: "approved"});
                } else {
                    _.extend(version, {status: "draft"});
                }
                $scope.version = version;
            }, function (response) {
                logger.error(response);
            });

            // populate 'Product' field with options
            var options = _.map(plans, function (plan) {
                return _.defaults({group: plan.value.indexOf('fibre') != -1 ? 'Fibre' : 'ADSL'}, plan);
            });
            options.unshift(EMPTY_OPTION);
            tariffNameField.options = options;

        }, function (response) {
            logger.error(response);
        });

        OffersRes.query(function (offers) {
            $scope.offers = _.filter(offers, function (offer) { return !_.str.isBlank(offer.offerData); });
            $scope.offers = _.each($scope.offers, function (offer) { offer.offerData = JSON.parse(offer.offerData); });
            offerIdField.options = optionsFromOffers(filterOffers($scope.offers));
        }, function (response) {
            logger.error(response);
        });

    };

    $scope.$watch('previewRequestParams', function (newVal, oldVal) { if (newVal != oldVal) $scope.refreshCommsText(); }, true);

    $scope.refreshCommsText = function () {

        $http.get(api('/documents/' + $routeParams.documentId, $cookies.token, {request: JSON.stringify($scope.previewRequestParams)}))
            .success(function (data) {
                $scope.commsText = data;
            })
            .error(function (response) {
                logger.error(response);
            });

    };

    $scope.pushVersionToLive = function (version) {

        version.$toLive({
            commsId: version.commsId
        }, function (response) {
            $scope.initPage();
        }, function (response) {
            logger.error(response);
        });

    };

});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

app.controller('OffersCtrl', function ($scope, $location, $routeParams, OffersRes) {

    $scope.updateOffersList = function () {

        OffersRes.query({
            lite: true // do not load offers' settings
        }, function (offers) {

            $scope.offers = offers;

            if (_.has($routeParams, 'offerId')) {
                var offer = _.find($scope.offers, {'offerId': parseInt($routeParams.offerId)});
                if (offer) {
                    $scope.showOffer(offer);
                } else {
                    $location.path('/offers');
                }
            }

        }, function (response) {
            logger.error(response);
        });
    };

    $scope.showOffer = function(offer) {

        $scope.currentOffer = offer;

        var editor = CKEDITOR.instances['offerText'];
        if (editor) {
            editor.editable().setHtml(offer.text);
        } else {
            CKEDITOR.replace('offerText');
        }

    };

    $scope.saveChanges = function () {

        $scope.savingChanges = true;

        $scope.currentOffer.text = CKEDITOR.instances['offerText'].getData();

        $scope.currentOffer.$update(function (response) {
            $scope.savingChanges = false;
            logger.success("Saved");
            $scope.updateOffersList();
        }, function (response) {
            $scope.savingChanges = false;
            logger.error(response);
        });
    };

    $scope.discardChanges = function () {

        CKEDITOR.instances['offerText'].editable().setHtml($scope.currentOffer.text);
    };

    // destroy ckeditor instance when route change starts
    $scope.$on('$routeChangeStart', function (next, current) {
        var editor = CKEDITOR.instances['offerText'];
        if (editor) {
            editor.destroy(true);
        }
    });

});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

app.controller('BarcodesCtrl', function ($scope, $http, $routeParams, BarcodesRes) {

    $scope.updateBarcodeList = function () {
        BarcodesRes.query(function (barcodes) {
            $scope.barcodeList = barcodes;
        }, function (response) {
            logger.error(response);
        });
    };

    $scope.createNewBarcode = function () {
        $scope.editType = "add";
        $scope.currentBarcode = {text: '', rules: ''};
        $scope.currentBarcodeText = '';
        $scope.currentBarcodeRules = '';
    };

    $scope.editBarcode = function (barcode) {
        $scope.editType = "update";
        $scope.currentBarcode = barcode;
        $scope.currentBarcodeText = barcode.text;
        $scope.currentBarcodeRules = barcode.rules;
    };

    $scope.saveChanges = function () {

        $scope.savingChanges = true;

        var barcode;

        if ($scope.editType == "add") {

            barcode = new BarcodesRes({
                text: $scope.currentBarcodeText,
                rules: $scope.currentBarcodeRules
            });

            barcode.$save(function (response) {
                $scope.savingChanges = false;
                logger.success("Saved");
                deleteTrash();
                $scope.updateBarcodeList();
            }, function (response) {
                $scope.savingChanges = false;
                logger.error(response);
            });

        } else if ($scope.editType == "update") {

            barcode = $scope.currentBarcode;
            barcode.text = $scope.currentBarcodeText;
            barcode.rules = $scope.currentBarcodeRules;

            barcode.$update({
                barId: barcode.id
            }, function (response) {
                $scope.savingChanges = false;
                logger.success("Updated");
                deleteTrash();
                $scope.updateBarcodeList();
            }, function (response) {
                $scope.savingChanges = false;
                logger.error(response);
            });

        }
    };

    $scope.discardChanges = function () {
        delete $scope.currentBarcode;
        delete $scope.editType;
    };

    $scope.deleteBarcode = function (barcode) {
        barcode.$delete({
            barId: barcode.id
        }, function (response) {
            logger.success("Deleted");
            deleteTrash();
            $scope.updateBarcodeList();
        }, function (response) {
            logger.error(response);
        });
    };

    function deleteTrash() {
        delete $scope.currentBarcode;
        delete $scope.currentBarcodeText;
        delete $scope.currentBarcodeRules;
        delete $scope.editType;
    }

});
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

app.controller('ArchitectCtrl', function ($scope, $http, $routeParams, $modal, $log, ArchitectRes, ResourceHelper) {

    $scope.currentDocumentId = $routeParams.documentId;
    $scope.currentSectionId = 0;
    $scope.currentSection = {};
    $scope.sectionAll = {};
    $scope.currentParagraphId = 0;
    $scope.currentParagraph = {};
    $scope.sections = {};
    $scope.paragraphs = {};
    $scope.isNewRecord = false;

    $scope.isCollapsed = false;


    $scope.initParagraphArchitecture = function() {
        $scope.refreshSectionList();
        ResourceHelper.getArray({resource: "architect", paramL1: "allSection"},
        function(data){
            $scope.sectionAll = data;
        },
        function(error){alert(error);})
    }

    $scope.refreshSectionList = function() {
        ArchitectRes.getDocumentSection({
            documentId: $scope.currentDocumentId
        }, function (sections) {
            $scope.sections = sections;
        }, function (response) {
            logger.error(response);
        });
    }

    $scope.refreshParagraphList = function() {
        ArchitectRes.getSectionParagraphs({
            documentId: $scope.currentDocumentId,
            sectionId: $scope.currentSectionId
        }, function (paragraphs) {
            $scope.paragraphs = paragraphs;
        }, function (response) {
            logger.error(response);
        });
    }

    $scope.activateSection = function(section) {
        $scope.currentSectionId = section.id;
        $scope.currentSection = section;

        ArchitectRes.getSectionParagraphs({
            documentId: $scope.currentDocumentId,
            sectionId: $scope.currentSectionId
        }, function (paragraphs) {
            $scope.paragraphs = paragraphs;
        }, function (response) {
            logger.error(response);
        });
    }

/*    $scope.activateSectionP = function(section, $e) {

        $e.stopPropagation();

        $scope.currentSectionId = section.id;

        ArchitectRes.getSectionParagraphs({
            documentId: $scope.currentDocumentId,
            sectionId: $scope.currentSectionId
        }, function (paragraphs) {
            $scope.paragraphs = paragraphs;
        }, function (response) {
            logger.error(response);
        });
    }*/

//  DIALOG

    $scope.addSection = function() {
        $scope.isNewRecord = true;
        $scope.currentSection = {};
        $scope.open('section', attacheSection);
    };

    $scope.editSection = function(section) {
        $scope.currentSection = section;
        $scope.currentSectionId = section.id;
        $scope.open('section', updateSectionIndex);
    };

    $scope.deleteSection = function(id) {
        ArchitectRes.removeSection(
            {documentId: $scope.currentDocumentId,
                sectionId: id},
            function(data){$scope.refreshSectionList()},
            function(error){alert('error')})
    };

    $scope.addParagraph = function() {
        $scope.isNewRecord = true;
        $scope.currentParagraph = {};
        $scope.currentParagraphId = {};
        $scope.open('paragraph', saveParagraph);
        $scope.isNewRecord = false;
    }

    $scope.editParagraph = function(paragraph) {
        $scope.currentParagraph = paragraph;
        $scope.currentParagraphId = paragraph.id;
        $scope.open('paragraph', updateParagraph);
    }

    $scope.deleteParagraph = function(id) {
        ArchitectRes.removeParagraph(
            {documentId: $scope.currentDocumentId,
                sectionId: $scope.currentSectionId,
                paragraphId: id},
            function(data){$scope.refreshParagraphList()},
            function(error){alert('error')})
    };

    // Controller for modal window
    var ModalInstanceCtrl = function ($scope, $modalInstance, object) {

        $scope.object = object;

        $scope.ok = function () {
            $modalInstance.close($scope.object);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.setSectionName = function(section) {
            object.uniqueName = section.uniqueName;
        }
    };

    // open dialog
    $scope.open = function (dialog, callback) {

        $scope.actualObject = {};
        $scope.actualModalTemplate = '';

        if (dialog == 'section') {
            $scope.actualObject = $scope.currentSection;
            $scope.actualModalTemplate = 'sectionModalTemplate.html';
        } else if (dialog == 'paragraph') {
            $scope.actualObject = $scope.currentParagraph;
            $scope.actualModalTemplate = 'paragraphModalTemplate.html';
        };

        $scope.actualObject.sectionAll = $scope.sectionAll;
        $scope.actualObject.isNewRecord = $scope.isNewRecord;

        var modalInstance = $modal.open({
            templateUrl: $scope.actualModalTemplate,
            controller: ModalInstanceCtrl,
            resolve: {
                object: function () {
                    return $scope.actualObject;
                }
            }
        });

        modalInstance.result.then(function (object) {
            $log.info('Return object: ' + object);
            callback(object);
//            $scope.returnedObject = object;
        }, function () {
            $log.info('Modal dialog for' + dialog + ' dismissed at: ' + new Date());
        });
    };

    // callback function
    function saveParagraph(dataFromDialog){
        ArchitectRes.insertParagraph({documentId: $scope.currentDocumentId, sectionId : $scope.currentSectionId},
            {"name": dataFromDialog.name, "rule": dataFromDialog.rule},
            function(){
                $scope.refreshParagraphList();
            },
            function(error) {
                alert('bad');
            });
    }

    function updateParagraph(dataFromDialog){
        ArchitectRes.updateParagraph(
            {documentId: $scope.currentDocumentId, sectionId : $scope.currentSectionId, paragraphId: dataFromDialog.id},
            {   "name": dataFromDialog.name,
                "rule": dataFromDialog.rule,
                "sectionId": $scope.currentSectionId,
                "id": dataFromDialog.id},
            function(){
                $scope.refreshParagraphList();
            },
            function(error) {
                alert('bad');
            });
    }

    function attacheSection(dataFromDialog){
        ArchitectRes.insertSection({documentId: $scope.currentDocumentId},
            {   "id": dataFromDialog.id,
                "index": dataFromDialog.index,
                "documentId": $scope.currentDocumentId,
                "uniqueName": dataFromDialog.uniqueName},
            function(){
                $scope.refreshSectionList();
            },
            function(error) {
                alert('bad');
            });
    }

    function updateSectionIndex(dataFromDialog){
        ArchitectRes.updateSectionIndex(
            {documentId: $scope.currentDocumentId, sectionId : $scope.currentSectionId},
            {   "id": dataFromDialog.id,
                "index": dataFromDialog.index,
                "documentId": $scope.currentDocumentId,
                "uniqueName": dataFromDialog.uniqueName},
            function(){
                $scope.refreshSectionList();
            },
            function(error) {
                alert('bad');
            });
    }


});