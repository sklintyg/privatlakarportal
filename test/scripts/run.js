/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
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

const cypress = require('cypress');
const marge = require('mochawesome-report-generator');
const {merge} = require('mochawesome-merge');
const fs = require('fs');

const dir = 'build/test-results';

if (!fs.existsSync(dir)) {
  fs.mkdirSync(dir, {recursive: true});
}

// https://github.com/cypress-io/cypress/issues/1946

const config = {};

process.argv.forEach(function(val) {
  if (val.indexOf('=') > -1) {
    const parts = val.split('=');

    config[parts[0]] = parts[1];
  }
});

const options = {
  "reportDir": dir
};

const cypressOption = {
  "config": config
};

cypress.run(cypressOption).then(
    (results) => {
      //console.log(results);
      generateReport(options).finally(
          () => {
            if (results.totalFailed > 0) {
              process.exit(1)
            }
          }
      );
    }
).catch(error => {
  generateReport(options);
  console.error(error);
  process.exit(1);
});

function generateReport(options) {
  return merge(options).then(report => marge.create(report, options));
}
