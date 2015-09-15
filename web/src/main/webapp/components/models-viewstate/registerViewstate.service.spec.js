describe('Service: RegisterViewState', function() {
    'use strict';

    // Load the module and mock away everything that is not necessary.
    beforeEach(angular.mock.module('privatlakareApp', function($provide) {
        $provide.value('APP_CONFIG', {});
    }));

    var RegisterViewState, HospProxy, mockResponse, $rootScope, $httpBackend;

    // Initialize the controller and a mock scope
    beforeEach(inject(function(_$rootScope_, _$httpBackend_, _RegisterViewState_, _HospProxy_, _mockResponse_) {
        $rootScope = _$rootScope_;
        $httpBackend = _$httpBackend_;
        RegisterViewState = _RegisterViewState_;
        mockResponse = _mockResponse_;

        RegisterViewState.reset();

        HospProxy = _HospProxy_;
    }));

    describe('registerViewState', function() {
        it('should ...', function() {
            expect(1).toBe(1);
        });
    });
});
