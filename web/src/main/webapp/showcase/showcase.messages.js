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
    },
    'en': {
        'label.text': 'Text'
    }
});

