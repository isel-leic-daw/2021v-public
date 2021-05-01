// Bundling example

// lib3
function module_lib3(exports, require) {
    exports.add = function(a,b) { return a + b }
}

// lib2
function module_lib2(exports, require) {
    const lib3 = require('./lib3')

    exports.add = lib3.add
    exports.sub = function(a, b) { return a - b }
}

// lib1
function module_lib1(exports, require) {
    const lib2 = require('./lib2')
    const lib3 = require('./lib3')

    console.log(lib3.add(3, lib2.sub(2, 1)))
}

const moduleTable = {
    './lib3': module_lib3,
    './lib2': module_lib2,
    './lib1': module_lib1,
}

function require(moduleName) {
    const moduleEntry = moduleTable[moduleName]
    if(!moduleEntry) {
        throw new Error(`module ${moduleName} does not exist`)
    }
    if(typeof moduleEntry === 'function') {
        const exports = {}
        moduleTable[moduleName] = exports
        moduleEntry(exports, require)
        return exports
    }
    return moduleEntry
}

// require the entry point
require('./lib1')
