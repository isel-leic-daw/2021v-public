import { sub } from './lib2.js'

console.log(`lib1 running: sub=${sub}`)

const somePrivateString = "some private content"
const theResult = 2 + 3

// named export
export function add(x,y) { return x + y;}
export const result = theResult
