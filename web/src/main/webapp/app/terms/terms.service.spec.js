describe('Service: TermsService', function() {
    'use strict';

    // Load the module and mock away everything that is not necessary.
    beforeEach(angular.mock.module('privatlakareApp', function($provide) {
        $provide.value('$state', { params: { terms: null, termsData: null }});
    }));

    var TermsService, mockResponse, $rootScope, $httpBackend, $state;
    
    // Initialize the controller and a mock scope

    beforeEach(inject(function(_$rootScope_, _$httpBackend_, _TermsService_, _mockResponse_, _$state_) {
        $httpBackend = _$httpBackend_;
        $rootScope = _$rootScope_;
        mockResponse = _mockResponse_;
        TermsService = _TermsService_;
        $state = _$state_;
    }));

    describe('openTerms', function() {
        it('should call the wcModalService to open a dialog', function() {

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
});
