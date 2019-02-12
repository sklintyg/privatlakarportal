const cypress = require('cypress');
const marge = require('mochawesome-report-generator');
const { merge } = require('mochawesome-merge');


// https://github.com/cypress-io/cypress/issues/1946

const config = {};

process.argv.forEach(function (val) {

    if (val.indexOf('=') > -1) {
        const parts = val.split('=');

        config[parts[0]] = parts[1];
    }
});

const options = {
    "reportDir": "build/test-results"
};

const cypressOption = {
    "config": config
};

cypress.run(cypressOption).then(
    () => {
    generateReport(options)
},
    error => {
    generateReport(options)
    console.error(error)
    process.exit(1)
}
)

function generateReport(options) {
    return merge(options).then(report => marge.create(report, options))
}
