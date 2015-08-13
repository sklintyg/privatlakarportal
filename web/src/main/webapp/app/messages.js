/* jshint maxlen: false, unused: false */
var ppMessages = {
    'sv': {

        'common.continue': 'Fortsätt',
        'common.yes': 'Ja',
        'common.no': 'Nej',
        'common.yes.caps': 'JA',
        'common.no.caps': 'NEJ',
        'common.nodontask': 'Nej, fråga inte igen',
        'common.ok': 'OK',
        'common.cancel': 'Avbryt',
        'common.goback': 'Tillbaka',
        'common.revoke': 'Intyget ska återtas',
        'common.sign': 'Signera',
        'common.send': 'Skicka',
        'common.copy': 'Kopiera',
        'common.delete': 'Radera',
        'common.sign.intyg': 'Signera intyget',
        'common.date': 'Datum',
        'common.when': 'När?',
        'common.notset': 'Ej angivet',
        'common.about.cookies': '<p>Så kallade kakor (cookies) används för att underlätta för besökaren på webbplatsen. En kaka är en textfil som lagras på din dator och som innehåller information. Denna webbplats använder så kallade sessionskakor. Sessionskakor lagras temporärt i din dators minne under tiden du är inne på en webbsida. Sessionskakor försvinner när du stänger din webbläsare. Ingen personlig information om dig sparas vid användning av sessionskakor.</p><p>Om du inte accepterar användandet av kakor kan du stänga av det via din webbläsares säkerhetsinställningar. Du kan även ställa in webbläsaren så att du får en varning varje gång webbplatsen försöker sätta en kaka på din dator.</p><p><strong>Observera!</strong> Om du stänger av kakor i din webbläsare kan du inte logga in i Webcert.</p><p>Allmän information om kakor (cookies) och lagen om elektronisk kommunikation finns på Post- och telestyrelsens webbplats.</p><p><a href="http://www.pts.se/sv/Bransch/Regler/Lagar/Lag-om-elektronisk-kommunikation/Cookies-kakor/" target="_blank">Mer om kakor (cookies) på Post- och telestyrelsens webbplats</a></p>',

        'common.label.saving': 'Sparar',
        'common.label.loading': 'Laddar',

        // wc-common-directives-resources
        'nav.label.loggedinas': 'Inloggad som:',

        // Common errors
        'common.error.unknown': '<strong>Tekniskt fel.</strong>',
        'common.error.authorization_problem' : '<strong>Behörighet saknas.</strong><br>Du saknar behörighet att använda denna resurs.',
        'common.error.cantconnect': '<strong>Kunde inte kontakta servern.</strong>',
        'common.error.certificatenotfound': '<strong>Intyget finns inte.</strong>',
        'common.error.certificateinvalid': '<strong>Intyget är inte korrekt ifyllt.</strong>',
        'common.error.certificateinvalidstate': '<strong>Intyget är inte ett utkast.</strong>Inga operationer kan utföras på det längre.',
        'common.error.invalid_state': '<strong>Operation är inte möjlig.</strong><br>Förmodligen har en annan användare ändrat informationen medan du arbetat på samma utkast. Ladda om sidan och försök igen',
        'common.error.sign.general': '<strong>Intyget kunde inte signeras.</strong><br>Försök igen senare.',
        'common.error.sign.netid': '<strong>Intyget kunde inte signeras.</strong><br>Kunde inte kontakta Net iD-klienten. Försök igen senare eller kontakta din support.',
        'common.error.sign.not_ready_yet': '<strong>Intyget är nu signerat.</strong><br>Tyvärr kan inte intyget visas än då det behandlas. Prova att ladda om sidan lite senare. Om problemet kvarstår, kontakta i första hand din lokala IT-avdelning och i andra hand Nationell kundservice på 0771-251010.',
        'common.error.sign.concurrent_modification': '<strong>Det går inte att signera utkastet.</strong><br/>Utkastet har ändrats av en annan användare sedan du började arbeta på det. Ladda om sidan, kontrollera att uppgifterna stämmer och försök signera igen.<br/>Utkastet ändrades av ${name}.',
        'common.error.unknown_internal_problem': '<strong>Tekniskt fel i Webcert.</strong><br>Försök igen senare.',
        'common.error.data_not_found': '<strong>Intyget kunde inte hittas.</strong><br>Intyget är borttaget eller så saknas behörighet.',
        'common.error.could_not_load_cert': '<strong>Intyget gick inte att läsa in.</strong><br>Prova att ladda om sidan. Om problemet kvarstår, kontakta i första hand din lokala IT-avdelning och i andra hand Nationell kundservice på 0771-251010.',
        'common.error.could_not_load_cert_not_auth': '<strong>Kunde inte hämta intyget eftersom du saknar behörighet.</strong>',
        'common.error.module_problem': '<strong>Tekniskt fel i Webcert.</strong><br>Problem att kontakta intygsmodulen.',
        'common.error.discard.concurrent_modification': '<strong>Kan inte ta bort utkastet. Utkastet har ändrats av en annan användare medan du arbetat på samma utkast.</strong><br>Ladda om sidan och försök igen. Utkastet ändrades av: ${name}',
        'common.error.save.unknown': '<strong>Okänt fel.</strong> Det går för tillfället inte att spara ändringar.',
        'common.error.save.data_not_found': '<strong>Okänt fel.</strong> Det går för tillfället inte att spara ändringar.',
        'common.error.save.concurrent_modification': '<strong>Kan inte spara utkastet. Utkastet har ändrats av en annan användare medan du arbetat på samma utkast.</strong><br>Ladda om sidan och försök igen. Utkastet ändrades av: ${name}',
        'common.error.save.unknown_internal_problem': '<strong>Tappade anslutningen till servern.</strong><br>Det går för tillfället inte att spara ändringar.',
        'common.error.save.invalid_state': '<strong>Tekniskt fel.</strong><br>Intyget kunde inte laddas korrekt. (invalid_state).',

        // Register
        'register.label.grundinformation': 'Grundinformation',
        'register.label.kontaktuppgifter': 'Kontaktuppgifter i verksamheten',
        'register.label.bekraftelse': 'Bekräftelse',

        // Register form step 1
        'label.form.personnummer' : 'Personnummer',
        'label.form.personnummer.help' : 'Personnummer hämtas från den e-legitimation som används vid inloggning. Personnummer går inte att ändra.',

        'label.form.name' : 'Namn',
        'label.form.name.help' : 'Namn hämtas från folkbokföringen. Namn går inte att ändra.',

        'label.form.befattning' : 'Befattning',
        'label.form.befattning.help' : 'Välj din huvudsakliga befattning enligt AID-etikett (Arbetsidentifikation kommuner och landsting).',
        'label.form.befattning.error.required' : 'Befattning måste anges innan du kan fortsätta.',

        'label.form.verksamhetensnamn' : 'Verksamhetens namn',
        'label.form.verksamhetensnamn.help' : 'Ange verksamhetens fullständiga namn.',
        'label.form.verksamhetensnamn.error.required' : 'Verksamhetens namn måste anges innan du kan fortsätta.',

        'label.form.agarform' : 'Ägarform',
        'label.form.agarform.help' : 'Ange verksamhetens ägarform enligt Hälso- och sjukvårdens adressregister, HSA. ”Privat” är förvalt och informationen går inte att ändra.',

        'label.form.vardform' : 'Vårdform',
        'label.form.vardform.help' : 'Ange verksamhetens huvudsakliga vårdform enligt definition i Socialstyrelsens termbank.',

        'label.form.verksamhetstyp' : 'Verksamhetstyp',
        'label.form.verksamhetstyp.help' : 'Välj den typ av verksamhet som huvudsakligen bedrivs. Med \'övrig medicinsk verksamhet\' avses paramedicinsk verksamhet som bedrivs av exempelvis sjukgymnaster, arbetsterapeuter, kiropraktorer och logopeder. Med \'övrig medicinsk serviceverksamhet\' avses all medicinsk serviceverksamhet undantaget laboratorieverksamhet och radiologisk verksamhet. Välj \'medicinsk verksamhet\' om den verksamhet du bedriver inte stämmer med några andra verksamhetstyper i denna lista.',
        'label.form.verksamhetstyp.error.required' : 'Verksamhetstyp måste anges innan du kan fortsätta.',

        'label.form.arbetsplatskod' : 'Arbetsplatskod <i>(valfritt)</i>',
        'label.form.arbetsplatskod.help' : 'Ange verksamhetens arbetsplatskod. Arbetsplatskod används för att identifiera vid vilken arbetsplats receptutfärdaren tjänstgör i samband med läkemedelsförskrivning. Vid intygsutfärdande används arbetsplatskod av Försäkringskassan för att samla in information om vid vilken arbetsplats den intygsutfärdande läkaren tjänstgör. Insamlingen sker på Socialstyrelsens uppdrag. Arbetsplatskod är inte en obligatorisk uppgift.',

        // Step 2
        'label.form.telefonnummer' : 'Telefonnummer',
        'label.form.telefonnummer.help' : 'Ange det telefonnummer där du vill bli kontaktad om mottagaren av intyget behöver nå dig för kompletterade frågor.',
        'label.form.telefonnummer.error.required' : 'Telefonnummer måste anges innan du kan fortsätta.',

        'label.form.epost' : 'E-postadress',
        'label.form.epost.help' : 'E-postadressen används för att kontakta dig då en mottagare av intyg behöver nå dig för kompletterande frågor samt då Inera behöver nå dig i ärenden som gäller användningen av Webcert. Till exempel för att meddela när du är godkänd för att använda Webcert',
        'label.form.epost.error.required' : 'E-postadress måste anges innan du kan fortsätta.',
        'label.form.epost.error.email' : 'En korrekt e-postadress måste anges innan du kan fortsätta.',

        'label.form.epost2' : 'Upprepa e-postadress',
        'label.form.epost2.error.required' : 'E-postadress måste anges innan du kan fortsätta.',
        'label.form.epost2.error.email' : 'En korrekt e-postadress måste anges innan du kan fortsätta.',
        'label.form.epost2.error.confirmemail' : 'E-postadressen är inte identisk med ovanstående angiven e-post.',

        'label.form.adress' : 'Postadress',
        'label.form.adress.help' : 'Ange den postadress som du vill bli kontaktad på om mottagaren av intyget behöver nå dig för kompletterande frågor. Postadressen är även nödvändig för Ineras eventuella fakturering för användning av Webcert.',
        'label.form.adress.error.required' : 'Postadress måste anges innan du kan fortsätta.',

        'label.form.postnummer' : 'Postnummer',
        'label.form.postnummer.help' : 'Ange postadressens postnummer i fem siffor 0-9, med eller utan mellanslag.',
        'label.form.postnummer.error.required' : 'Postnummer måste anges innan du kan fortsätta.',

        'label.form.postort' : 'Postort',

        'label.form.kommun' : 'Kommun',
        'label.form.kommun.help' : 'Uppgift om kommun går inte att redigera. Om systemet får fler träffar för kommun vid hämtning av uppgiften ska du ange vilken kommun som är rätt.',

        'label.form.lan' : 'Län',
        'label.form.lan.help' : 'Län där verksamheten finns. Uppgift om län går inte att ändra.',

        // General errors
        'label.form.error.pastenotallowed' : 'Du måste ange e-postadresserna genom att skriva in dem.'
    },
    'en': {
        'common.ok': 'OK',
        'common.cancel': 'Cancel'
    }
};
