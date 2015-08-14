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
    },
    'omradeOK': {
        'omradeList':[
            {'postnummer':'13155','postort':'NACKA','kommun':'STOCKHOLM','lan':'STOCKHOLM'},
            {'postnummer':'13155','postort':'NACKA','kommun':'NACKA','lan':'STOCKHOLM'}
        ]
    },
    'omradeModel': [
        {'postnummer':'13155','postort':'NACKA','kommun':'STOCKHOLM','lan':'STOCKHOLM'},
        {'postnummer':'13155','postort':'NACKA','kommun':'NACKA','lan':'STOCKHOLM'}
    ]
});