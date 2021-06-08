const path = require('path');
const fs = require('fs')

// This assumes the existance of a github.secret file outside the repo
const token = fs.readFileSync('../../../../../github.secret', { encoding: 'ascii' })
const authorizationHeaderValue = `Bearer ${token.trim()}`

module.exports = {
  mode: 'development',
  entry: './src/index.ts',
  output: {
    filename: 'main.js',
    path: path.resolve(__dirname, 'dist'),
  },
  devServer: {
    contentBase: './dist',
    historyApiFallback: true,
    proxy: {
      '/github': {
        target: 'https://api.github.com',
        pathRewrite: { '^/github': '' },
        changeOrigin: true,
        onProxyReq(proxyReq, req) {
          proxyReq.setHeader('authorization', authorizationHeaderValue)
        }
      },
      '/httpbin': {
        target: 'https://httpbin.org',
        pathRewrite: { '^/httpbin': '' },
        changeOrigin: true
      }
    }
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
    ],
  },
};
