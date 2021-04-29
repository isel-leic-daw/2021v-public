import React from 'react'
import { render, screen } from '@testing-library/react'
import App from '../main/App'

test('renders HVAC text', () => {
  render(<App />)
  const linkElement = screen.getByText(/loading/i)
  expect(linkElement).toBeInTheDocument()
})
