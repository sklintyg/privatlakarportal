angular.module('privatlakareApp').value('mockResponse', {
    'hospOK': {
        'hospInformation': {
            'personalPrescriptionCode' : '1234567',
            'specialityNames': ['specialityName1', 'specialityName2'],
            'hsaTitles': ['hsaTitle1','hsaTitle2']
        }
    },
    'hospModel': {
        'personalPrescriptionCode' : '1234567',
        'specialityNames': ['specialityName1', 'specialityName2'],
        'hsaTitles': ['hsaTitle1','hsaTitle2']
    },
    'hospFail' : {
        'hospInformation': null
    },
    'hospFailModel' : {
    }

});