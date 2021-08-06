
// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    https: true,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'https://localhost:8443/'
        // target: 'http://i5d201.p.ssafy.io/'
      },
      '/webjars': {
        target: 'https://localhost:8443/'
        // target: 'http://i5d201.p.ssafy.io/'
      },
      '/group-call': {
        target: 'https://localhost:8443/'
        // target: 'http://i5d201.p.ssafy.io/'
      },
      '/upload': {
        target: 'https://localhost:8443/'
        // target: 'http://i5d201.p.ssafy.io/'
      },
    },
    historyApiFallback: true,
    hot: true,
  },
  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
}