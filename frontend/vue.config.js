
// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    https: true,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'http://localhost:8443/'
      },
      '/webjars': {
        target: 'http://localhost:8443/'
      },
      '/group-call': {
        target: 'http://localhost:8443/'
      },
      '/upload': {
        target: 'http://localhost:8443/'
      },
    },
    historyApiFallback: true,
    hot: true,
  },
  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
}