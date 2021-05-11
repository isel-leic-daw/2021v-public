// lib1
function lib1_module(exports, require) {
const lib2 = require('./lib2')
const abc = 1

console.log("Hi, I'm lib1")
console.log(lib2.add(3, lib2.sub(2, 1)))
}

// lib2
function lib2_module(exports, require) {
const lib3 = require('./lib3')
const abc = "xyz"

exports.add = lib3.add
exports.sub = function(a, b) { return a - b }
}
// lib3
function lib3_module(exports, require) {
exports.add = function(a,b) { return a + b }
}

const moduleTable = {
    './lib1':lib1_module,
    './lib2':lib2_module,
    './lib3':lib3_module,
}

const moduleCache = {}

function require(name) {
    const cached = moduleCache[name]
    if(cached) {
        return cached
    }
    const moduleFunction = moduleTable[name]
    if(!moduleFunction) {
        throw new Error(`module ${name} does not exist`)
    }
    moduleCache[name] = {}
    moduleFunction(moduleCache[name], require)
    return moduleCache[name]
}

require('./lib1')
