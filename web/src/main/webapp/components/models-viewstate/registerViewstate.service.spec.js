describe('Service: RegisterViewstateService', function() {
    'use strict';

    // Load the module and mock away everything that is not necessary.
    beforeEach(angular.mock.module('privatlakareApp', function($provide) {
        $provide.value('HospProxy', { 'getHospInformation' : function() {}});
    }));

    var RegisterViewStateService, model, HospProxy, mockResponse, $rootScope, $httpBackend;

    var testModelNoHOSP = {'befattning':{'id':'201013','label':'Företagsläkare'},'verksamhetensNamn':'arar','agarForm':'Privat',
        'vardform':{'id':'03','label':'Hemsjukvård'},'verksamhetstyp':{'id':'12','label':'Laboratorieverksamhet'},'arbetsplatskod':'aetaet',
        'telefonnummer':'325325','epost':'a@a.a','epost2':'a@a.a','adress':'gesgs','postnummer':'35463',
        'postort':'Linköping','kommun':'Linköping','lan':'Östergötland',
        'legitimeradYrkesgrupp':null,'specialitet':null,'forskrivarkod':null};
    
    // Initialize the controller and a mock scope
    beforeEach(inject(function(_$rootScope_, _$httpBackend_, _RegisterViewStateService_, _HospProxy_, _mockResponse_) {
        $rootScope = _$rootScope_;
        $httpBackend = _$httpBackend_;
        RegisterViewStateService = _RegisterViewStateService_;
        mockResponse = _mockResponse_;

        model = testModelNoHOSP;

        RegisterViewStateService.reset();

        HospProxy = _HospProxy_;
    }));

    xdescribe('decorateModelWithHospInfo', function() {

        // Success
        it('should decorate the model with hospinfo', function() {

            spyOn(HospProxy, 'getHospInformation').and.callFake(function() {
                return {
                    then: function(onSuccess) {
                        onSuccess(mockResponse.hospModel);
                    }
                };
            });

            RegisterViewStateService.decorateModelWithHospInfo(model);

            expect(model.legitimeradYrkesgrupp).toBe('hsaTitle1, hsaTitle2');
            expect(model.specialitet).toBe('specialityName1, specialityName2');
            expect(model.forskrivarkod).toBe('1234567');
        });

        // Fail

        it('should gracefully fail to decorate the model with hospinfo if it is not available', function() {

            spyOn(HospProxy, 'getHospInformation').and.callFake(function() {
                return {
                    then: function(onSuccess, onError) {
                        onError(mockResponse.hospFailModel);
                    }
                };
            });

            RegisterViewStateService.decorateModelWithHospInfo(model);

            expect(model.legitimeradYrkesgrupp).toBe(null);
            expect(model.specialitet).toBe(null);
            expect(model.forskrivarkod).toBe(null);
        });
    });
});
