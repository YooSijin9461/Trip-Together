
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
        ssl: 'keystore/ssafy.p12',
        wss: true,
      },
      '/webjars': {
        // target: 'https://localhost:8443/'
        target: 'https://i5d201.p.ssafy.io:8443/',
        ssl: 'keystore/ssafy.p12',
        wss: true,
      },
      '/group-call': {
        // target: 'https://localhost:8443/'
        target: 'https://i5d201.p.ssafy.io:8443/',
        ssl: 'keystore/ssafy.p12',
        wss: true,
      },
      '/upload': {
        // target: 'https://localhost:8443/'
        target: 'https://i5d201.p.ssafy.io:8443/',
        ssl: 'keystore/ssafy.p12',
        wss: true,
      },
    },
    historyApiFallback: true,
    hot: true,
  },
  lintOnSave: false
}