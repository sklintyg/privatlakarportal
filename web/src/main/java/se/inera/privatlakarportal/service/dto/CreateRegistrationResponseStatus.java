package se.inera.privatlakarportal.service.dto;

/**
 * Created by pebe on 2015-08-05.
 */
public enum CreateRegistrationResponseStatus {

    // Om användaren inte fanns i HSA:s HOSP visar systemet information om att verifiering har påbörats. HSA lagrar användarens personnummer så att information om denne inkluderas nästa gång information hämtas från HOSP. Användningsfallet avslutas.
    AUTHENTICATION_INPROGRESS,

    // Om användaren fanns i HSA:s HOSP och har en giltig läkarlegitimation godkänner systemet användaren (HoS-personal.godkänd sätts till ”True”) och systemet visar info om att denne kan börja använda systemet.
    AUTHORIZED,

    // Om läkaren fanns i HSA:s HOSP men inte har en giltig läkarlegitimation visar systemet information om att denne inte är behörig.
    NOT_AUTHORIZED
}
