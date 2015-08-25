package se.inera.privatlakarportal.integration.terms.stub;

import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionerterms.v1.rivtabp21.GetPrivatePractitionerTermsResponderInterface;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionertermsresponder.v1.GetPrivatePractitionerTermsResponseType;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionertermsresponder.v1.GetPrivatePractitionerTermsType;
import se.riv.infrastructure.directory.privatepractitioner.terms.v1.AvtalType;

/**
 * Created by pebe on 2015-08-25.
 */
public class TermsWebServiceStub implements GetPrivatePractitionerTermsResponderInterface {

    @Override
    public GetPrivatePractitionerTermsResponseType getPrivatePractitionerTerms(String s, GetPrivatePractitionerTermsType getPrivatePractitionerTermsType) {

        AvtalType avtalType = new AvtalType();
        avtalType.setAvtalText("Användaravtal placeholder");
        avtalType.setAvtalVersion(0);

        GetPrivatePractitionerTermsResponseType response = new GetPrivatePractitionerTermsResponseType();
        response.setAvtal(avtalType);

        return response;
    }

}
