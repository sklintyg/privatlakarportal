/*
 * Copyright (C) 2018 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
    ],
    'userOK': { user: {
            'namn':'Oskar Johansson',
            'status':'NOT_STARTED',
            'personalIdentityNumber': '191212121212',
            'authenticationScheme': 'urn:inera:privatlakarportal:eleg:fake',
            'fakeSchemeId': 'urn:inera:privatlakarportal:eleg:fake',
            'loggedIn': false,
            'nameFromPuService': false,
            'nameUpdated': false
        }
    },
    'userModel': {
        'namn':'Oskar Johansson',
        'status':'NOT_STARTED',
        'personalIdentityNumber': '191212121212',
        'authenticationScheme': 'urn:inera:privatlakarportal:eleg:fake',
        'fakeSchemeId': 'urn:inera:privatlakarportal:eleg:fake',
        'loggedIn': false,
        'nameFromPuService': false,
        'nameUpdated': false
    },
    'termsOK': {'terms':{'text':'Användaravtal placeholder','version':0}},
    'termsModel': {'text':'Användaravtal placeholder','version':0}
});