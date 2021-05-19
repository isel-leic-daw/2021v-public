import { useEffect, useReducer } from 'react'

type State =
  {
    type: 'started'
  }
  | {
    type: 'fetching'
  }
  | {
    type: 'error',
    error: string
  }
  | {
    type: 'response',
    status: number,
    body: string
  }

type Action =
  {
    type: 'fetching'
  }
  | {
    type: 'error',
    error: string
  }
  | {
    type: 'response',
    status: number,
    body: string
  }

function actionError(error: string): Action {
  return { type: 'error', error: error }
}

function actionResponse(status: number, body: string): Action {
  return { type: 'response', status: status, body: body }
}

function reducer(state: State, action: Action): State {
  console.log('reducer handling action')
  console.log(action)
  switch (action.type) {
    case 'fetching': return { type: 'fetching' }
    case 'error': return { type: 'error', error: action.error }
    case 'response': return { type: 'response', status: action.status, body: action.body }
  }
}

export function useFetch(uri: string): State {
  const [state, dispatcher] = useReducer(reducer, { type: 'started' })

  useEffect(() => {
    if (!uri) {
      return
    }
    let isCancelled = false
    const abortController = new AbortController()
    const signal = abortController.signal
    async function doFetch() {
      try {
        dispatcher({ type: 'fetching' })
        const resp = await fetch(uri, { signal })
        if (isCancelled) {
          return
        }
        const body = await resp.text()
        if (isCancelled) {
          return
        }
        dispatcher(actionResponse(resp.status, body))
      } catch (error) {
        if (isCancelled) {
          return
        }
        dispatcher(actionError(error.message))
      }
    }
    doFetch()
    return () => {
      isCancelled = true
      abortController.abort()
    }
  }, [uri])

  return state
}