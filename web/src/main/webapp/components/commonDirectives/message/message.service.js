/**
 * message directive for externalizing text resources.
 *
 * All resourcekeys are expected to be defined in lowercase and available in a
 * global js object named "messages"
 * Also supports dynamic key values such as key="status.{{scopedvalue}}"
 *
 * Usage: <message key="some.resource.key" [fallback="defaulttextifnokeyfound"]/>
 */
angular.module('privatlakareApp').factory('messageService',
    function($rootScope) {
        'use strict';

        var _links = null;
        var _messageResources = null;

        function _propertyExists(key, language, fallbackToDefaultLanguage) {
            var value;

            if (!language) {
                language = $rootScope.lang;
                if (!language && fallbackToDefaultLanguage) {
                    language = $rootScope.DEFAULT_LANG;
                }
            }

            if (language) {
                value = _getPropertyInLanguage(language, key, null);
                if (value === null || value === undefined) {
                    value = false;
                }
            } else {
                value = false;
            }

            return value;
        }

        function _getProperty(key, variables, defaultValue, language, fallbackToDefaultLanguage) {
            var value;

            if (!language) {
                language = $rootScope.lang;
                if (!language && fallbackToDefaultLanguage) {
                    language = $rootScope.DEFAULT_LANG;
                }
            }

            if (language) {
                value = _getPropertyInLanguage(language, key, variables);
                if (value === null || value === undefined) {
                    value = defaultValue === null || defaultValue === undefined ?
                        '[Missing "' + key + '"]' : defaultValue;
                }
            } else {
                value = '[Missing language]';
            }

            return value;
        }

        function _getPropertyInLanguage(lang, key, variables) {
            _checkResources();
            var message = _messageResources[lang][key];

            angular.forEach(variables, function(value, key) {
                var regexp = new RegExp('\\$\\{' + key + '\\}', 'g');
                message = message.replace(regexp, value);
            });

            // Find <LINK: dynamic links and replace
            var regex2 = /<LINK:(.*?)>/gi, result;

            while ( (result = regex2.exec(message)) ) {
                var replace = result[0];
                var linkKey = result[1];

                var dynamicLink = _buildDynamicLink(linkKey);

                var regexp = new RegExp(replace, 'g');
                message = message.replace(regexp, dynamicLink);
            }

            return message;
        }

        function _buildDynamicLink(linkKey) {
            var dynamicLink = '<a href="' + _links[linkKey].url + '"';
            dynamicLink += _links[linkKey].tooltip ? ' title="' + _links[linkKey].tooltip + '"' : '';
            dynamicLink += _links[linkKey].target ? ' target="' + _links[linkKey].target + '">' : '>';
            dynamicLink += _links[linkKey].text + '</a>';
            return dynamicLink;
        }

        function _addResources(resources) {
            _checkResources();
            angular.extend(_messageResources.sv, resources.sv);
            angular.extend(_messageResources.en, resources.en);
        }

        function _addLinks(links) {
            _links = links;
        }

        function _checkResources() {
            if (_messageResources === null) {
                _messageResources = {
                    'sv': {
                        'initial.key': 'Initial nyckel'
                    },
                    'en': {
                        'initial.key': 'Initial key'
                    }
                };
            }
        }

        return {
            propertyExists: _propertyExists,
            getProperty: _getProperty,
            addResources: _addResources,
            addLinks: _addLinks
        };
    }
);
