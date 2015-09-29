describe('Service: TermsService', function() {
    'use strict';

    // Load the module and mock away everything that is not necessary.
    beforeEach(angular.mock.module('privatlakareApp', function($provide) {
        $provide.value('$state', { params: { terms: null, termsData: null }});
    }));

    var $rootScope, $httpBackend, $state, wcModalService, $window, AppTermsModalModel, TermsModel, TermsService, mockResponse;
    
    // Initialize the controller and a mock scope
    beforeEach(inject(function(_$rootScope_, _$httpBackend_, _TermsService_, _mockResponse_, _$state_, _AppTermsModalModel_, _wcModalService_, _$window_, _TermsModel_) {
        $httpBackend = _$httpBackend_;
        $rootScope = _$rootScope_;
        mockResponse = _mockResponse_;
        TermsService = _TermsService_;
        $state = _$state_;
        AppTermsModalModel = _AppTermsModalModel_;
        TermsModel = _TermsModel_;
        wcModalService = _wcModalService_;
        $window = _$window_;
    }));

    describe('openTerms', function() {
        it('should call the wcModalService to open a dialog', function() {
            spyOn(wcModalService, 'open').and.stub();
            var modalModel = AppTermsModalModel.init();
            TermsService.openTerms(modalModel);
            expect(wcModalService.open).toHaveBeenCalled();
        });
    });
    describe('loadTerms', function() {
        it('should fetch terms from webcert if stateparams.terms are set to "webcert"', function() {
            $httpBackend.expectGET('/api/terms/webcert').respond(200, { terms: {}});
            $state.params.terms = 'webcert';
            TermsService.loadTerms().then(function(terms) {
                expect(terms).not.toBeNull();
                expect(terms.loadedOK).toBeTruthy();
            });
            $httpBackend.flush();
            $rootScope.$apply();
        });
        it('should fetch terms from portal if stateparams.termsData are set', function() {
            $state.params.termsData = {};
            TermsService.loadTerms().then(function(terms) {
                expect(terms).not.toBeNull();
                expect(terms.loadedOK).toBeTruthy();
            });
            $rootScope.$apply();
        });
        it('should report error if neither stateparams.termsData or stateparams.terms are set', function() {
            TermsService.loadTerms().then(function(terms) {
                expect(terms).not.toBeNull();
                expect(terms.loadedOK).toBeFalsy();
            });
            $rootScope.$apply();
        });
    });
   describe('printTerms', function() {
        it('should call the wcModalService to open a dialog', function() {
            spyOn($window, 'open').and.returnValue({
                window: { focus: function() {}},
                document: { write: function() {}, open: function() {}, close: function() {}},
                close: function() {},
                open: function() {}
            });
            var content = {
                terms: { termsModel: TermsModel.init() },
                absUrl: 'url',
                titleId: 'label.modal.content.title.portalvillkor',
                logoImage: null
            };

            TermsService.printTerms(content);
            expect($window.open).toHaveBeenCalled();
        });
    });

});
