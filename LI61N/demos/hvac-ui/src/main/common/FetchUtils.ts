
/**
 * Type used for representing fetch request's state. Note that SUCCESS is used to represent those
 * cases where a response was actually obtained, regardless of its status code. ERROR is used
 * to represent those cases where a response could not be obtained or a 5XX was received.
 */
export enum FetchState { NOT_READY = "NOT_READY", ERROR = "ERROR", SUCCESS = "SUCCESS" }

/**
 * Type used to caracterize the state of a fetch request.
 * @property status - The status of the fetch request.
 * @property result - The request result, if a response was actually obtained.
 */
export type FetchInfo<T> = {
  status: FetchState,
  result?: Result<T>
}

type ResponseHeader = Pick<Response, 'headers' | 'status' | 'statusText' | 'type' | 'url' | 'ok'>

/**
 * Caracterizes results for API requests.
 * @param header  - the response's header information.
 * @param body    - the result body, if it exists.
 */
export type Result<T> = {
    header: ResponseHeader,
    body?: T
}

/**
 * Caracterizes API requests. T is the expected response's payload type.
 * @param isCanceled  - indicates whether the request has been canceled our not. 
 * @param cancel      - cancels the request.
 * @param send        - asynchronoulsy sends the request and produces the corresponding result
 */
export type Request<T> = {
    isCanceled: boolean
    cancel: () => void
    send: () => Promise<Result<T>>
}

/**
 * Utility function used to get a cancelable API request. Note that our API responses are expected to be 
 * JSON based.
 * @param url   - The resource's URL.
 * @param init  - request parametrization as specified by the Fetch API (see init param in
 * https://developer.mozilla.org/en-US/docs/Web/API/WindowOrWorkerGlobalScope/fetch).
 * @returns the API request to be sent.
 */
export function cancelableRequest<T>(url: URL, init?: RequestInit): Request<T> {

  const isJson = (mimeType: string | null) => !(!mimeType || !mimeType.toLowerCase().includes('json'))
  const controller = new AbortController()

  return {
    isCanceled: controller.signal.aborted,
    cancel: () => { if(!controller.signal.aborted) controller.abort() },
    send: async (): Promise<Result<T>>  => {
      const response = await fetch(url.toString(), { ...init, signal: controller.signal })
      return response.ok && isJson(response.headers.get('Content-Type')) ?
        { header: response, body: await response.json() } : 
        { header: response }
    }
  }
}

/**
 * Checks whether the given API result is a server error.
 * @param result The API result.
 * @returns a boolean value indicating whether its a server error or not.
 */
export function isServerError<T>(result: Result<T>) {
  return result.header.status >= 500 && result.header.status <= 599
}

/**
 * Checks whether the given API result is an authorization error. Note that in our case 
 * we do not need to distinguish 401 from 403 errors.
 * @param result The API result.
 * @returns a boolean value indicating whether its an authorization error.
 */
 export function isAuthorizationError<T>(result: Result<T>) {
  return result.header.status === 401 || result.header.status === 403
}