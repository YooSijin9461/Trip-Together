
// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    https: true,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        // target: 'https://localhost:8443/'
        target: 'https://i5d201.p.ssafy.io:8443/',
      },
      '/webjars': {
        // target: 'https://localhost:8443/'
        target: 'https://i5d201.p.ssafy.io:8443/',
      },
      '/groupcall': {
        // target: 'https://localhost:8443/'
        target: 'https://i5d201.p.ssafy.io:8443/',
      },
      '/upload': {
        // target: 'https://localhost:8443/'
        target: 'https://i5d201.p.ssafy.io:8443/',
      },
      '/websocket': {
        target: 'https://0.0.0.0/',
      }
    },
    historyApiFallback: true,
    hot: true,
  },
  lintOnSave: false
}