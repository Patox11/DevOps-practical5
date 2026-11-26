// Minimal build: copies index.html into dist/
const fs = require('fs');
fs.mkdirSync('dist', { recursive: true });
fs.copyFileSync('index.html', 'dist/index.html');
console.log('Build complete: dist/index.html');
