import React, { ChangeEvent, useEffect, useState } from 'react'
import ReactDOM from 'react-dom'

type Action = {
  title: string,
  method: 'GET' | 'POST',
  href: string
  fields: Array<ActionField>
}

type ActionField = {
  name: string,
  type: string,
  title: string,
  value?: string
}

type DemoFormProps = {
  action: Action
}

enum FormState {
  Edit,
  Submit,
  Error,
  Done
}

type State =
  | {
    type: FormState.Edit
  }
  | {
    type: FormState.Submit
    formData: FormData
  }
  | {
    type: FormState.Error
    errors: { [key: string]: string; }
  }
  | {
    type: FormState.Done
  }

function renderFormData(formData: FormData) {
  return (
    <ul>
      {Array.from(formData.entries(), ([key, value]) => (
        <li key={key}>{key} - {value}</li>
      ))}
    </ul>
  )
}

type SubmitResponse =
  | {
    type: 'success',
  }
  | {
    type: 'client-error'
    errors: { [key: string]: string; }
  }

function fakeSubmit(form: FormData): Promise<SubmitResponse> {

  return new Promise((resolve, reject) => {
    const res: SubmitResponse = form.get('name') == 'Alice' ? {
      type: 'success'
    } : {
      type: 'client-error',
      errors: {
        name: 'the name must be Alice'
      }
    }

    setTimeout(() => resolve(res), 5000)

  })
}

function DemoForm({ action }: DemoFormProps) {

  const [state, setState] = useState<State>({ type: FormState.Edit })
  const isSubmit = state.type == FormState.Submit

  useEffect(() => {
    if (state.type != FormState.Submit) {
      return
    }
    const form = state.formData
    let cancelled = false
    async function submit() {
      const res = await fakeSubmit(form)
      if (!cancelled) {
        if (res.type === 'success') {
          setState({ type: FormState.Done })
        } else {
          setState({ type: FormState.Error, errors: res.errors })
        }
      }
    }
    submit()
    return () => {
      cancelled = true
    }
  }, [isSubmit])


  function handleSubmit(ev: React.FormEvent<HTMLFormElement>) {
    // capture form data
    const formData = new FormData(ev.currentTarget)
    // change component state
    setState({ type: FormState.Submit, formData: formData })
    // prevent the browser of loading a new document
    ev.preventDefault()
  }

  if (state.type == FormState.Done) {
    return <h1>Done</h1>
  }

  const editable = state.type != FormState.Submit
  const errors = state.type == FormState.Error && state.errors

  return (
    <form onSubmit={handleSubmit}>
      <fieldset>
        {action.fields.map(field => (
          <React.Fragment key={field.name}>
            <input type={field.type} name={field.name} id={field.name} disabled={!editable} />
            <label htmlFor={field.name}>{field.title}</label>
            {errors && errors[field.name]}
          </React.Fragment>
        ))}
        <button type='submit' disabled={!editable}>Submit</button>
        {state.type == FormState.Submit && <div><p>Submiting...</p>{renderFormData(state.formData)}</div>}
      </fieldset>
    </form>
  )
}

function DemoFormUsage() {
  const action: Action = {
    title: 'The form',
    method: 'POST',
    href: 'https://httpbin.org/post',
    fields: [
      { name: 'name', type: 'input', title: 'The name' },
      { name: 'number', type: 'input', title: 'The number' },
      { name: 'course', type: 'input', title: 'The course' }
    ]
  }

  return (
    <DemoForm action={action} />
  )
}

export function demo() {

  ReactDOM.render(
    <div>
      <h1>Form example</h1>
      <DemoFormUsage />
    </div>,
    document.getElementById('container')
  )
}
