/**
 * Type used to represent base64 encoded strings.
 * Implementation note: For simplicity, non-ascii characters are not supported.
 * If you really whish to support Unicode strings, check this out:
 * https://developer.mozilla.org/en-US/docs/Web/API/WindowOrWorkerGlobalScope/btoa#unicode_strings
 */
export class Base64Encoded {
  readonly value: string
  constructor(value: string) { this.value = btoa(value) }
}

/**
 * Type used to represent strings that DO NOT include white space characters.
 * The reasoning behind the definition of types like this one is expressed here:
 * https://fsharpforfunandprofit.com/posts/designing-with-types-making-illegal-states-unrepresentable/
 */
export class NonWhiteSpaceString {
  readonly value: string
  constructor(value: string) {
    if (hasWhiteSpace(value))
      throw new Error('Whitespace characters not allowed')
    this.value = value
  }
}

export function hasWhiteSpace(value: string): boolean {
  return value.match(/\s/) !== null
}

export function isEmpty(value: string): boolean {
  return value.length === 0
}