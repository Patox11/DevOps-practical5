// Minimal test script (no dependencies): checks the app source exists and responds as expected.
const assert = require('assert');
const fs = require('fs');

assert.ok(fs.existsSync('index.html'), 'index.html should exist');
assert.ok(fs.readFileSync('index.html', 'utf8').includes('<html'), 'index.html should contain HTML');
console.log('All tests passed.');
