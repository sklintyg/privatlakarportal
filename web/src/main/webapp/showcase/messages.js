/**
 * message directive for externalizing text resources.
 *
 * All resourcekeys are expected to be defined in lowercase and available in a
 * global js object named "messages"
 * Also supports dynamic key values such as key="status.{{scopedvalue}}"
 *
 * Usage: <message key="some.resource.key" [fallback="defaulttextifnokeyfound"]/>
 */
angular.module('privatlakareApp').constant('messages',{
    'sv': {
        'label.text': 'Text',
        'label.loading': 'Laddar...',
        'label.help': 'Halp! Halp!',

        'label.form.postnummer': 'Postnummer',
        'label.form.postnummer.help': 'Ange postadressens postnummer i fem siffor 0-9, med eller utan mellanslag. Postort, kommun och län fylls i automatiskt.',
        'label.form.postnummer.popover': 'Ange postadressens postnummer i fem siffor 0-9, med eller utan mellanslag. Postort, kommun och län fylls i automatiskt.',
        'label.form.postnummer.error.required': 'Postnummer måste anges innan du kan fortsätta.',
        'label.form.postnummer.error.format': 'Felaktigt postnummer. Postnummer måste anges på formaten XXXXX eller XXX XX.',
        'label.form.postnummer.error.region': 'Ett giltigt postnummer som ger en postort, kommun och län måste anges.',

        'label.form.postort': 'Postort',

        'label.form.kommun': 'Kommun',
        'label.form.kommun.help': 'Uppgift om kommun går inte att redigera. Om systemet får fler träffar för kommun vid hämtning av uppgiften ska du ange vilken kommun som är rätt.',
        'label.form.kommun.popover': 'Kommun där verksamheten finns.',
        'label.form.kommun.option': 'Välj kommun',
        'label.form.kommun.morehits': 'Uppgift om kommun har ${hits} träffar. Ange den kommun som är rätt.',
        'label.form.kommun.nohits': 'Inga träffar för postnummer ${postnummer}. Var vänlig kontrollera postnumret och försök igen.',
        'label.form.kommun.error.required': 'Kommun måste väljas innan du kan fortsätta.',
        'label.form.kommun.error.general': 'Ett tekniskt fel har uppstått. Postnumret kunde inte hämtas. Prova igen senare.',
        'label.form.kommun.error.toomanydigits': 'Felaktigt format på postnumret. Ange postnummer med 5 siffror på formaten XXXXX eller XXX XX.',

        'label.form.lan': 'Län',
        'label.form.lan.help': 'Län där verksamheten finns. Uppgift om län går inte att ändra.',

    },
    'en': {
        'label.text': 'Text'
    }
});

