import React, { ChangeEvent, useEffect, useState } from 'react'
import ReactDOM from 'react-dom'

function Fetch({ uri }: { uri: string }) {
  const [status, setStatus] = useState('?')
  const [text, setText] = useState('fetching...')
  console.log(`Fetched called with '${uri}'`)

  useEffect(() => {
    let isCancelled = false
    async function doFetch() {
      try {
        console.log(`starting fetch of - ${uri}`)
        const resp = await fetch(uri)
        if (isCancelled) {
          return
        }
        setStatus(resp.status.toString())
        const body = await resp.text()
        if (isCancelled) {
          return
        }
        setText(body)
        console.log(`fetch of '${uri}' completed`)
      } catch (error) {
        if (isCancelled) {
          return
        }
        setStatus('error')
        setText(error)
      }
    }
    doFetch()
    return () => {
      console.log(`cleanup function called for URI = '${uri}'`)
      // isCancelled = true
    }
  }, [uri])

  return (
    <div>
      <p>URI: {uri}</p>
      <p>{status}</p>
      <textarea value={text} rows={25} cols={120} readOnly={true} />
    </div>
  )
}

export function demo() {
  ReactDOM.render(
    <Fetch uri='https://httpbin.org/delay/5' />,
    document.getElementById('container')
  )
  setTimeout(() => {
    ReactDOM.render(
      <Fetch uri='https://httpbin.org/delay/1' />,
      document.getElementById('container')
    )
  }, 2000)
}

