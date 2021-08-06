
// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    https: true,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'http://i5d201.p.ssafy.io/'
      },
      '/webjars': {
        target: 'http://i5d201.p.ssafy.io/'
      },
      '/group-call': {
        target: 'http://i5d201.p.ssafy.io/'
      },
      '/upload': {
        target: 'http://i5d201.p.ssafy.io/'
      },
    },
    historyApiFallback: true,
    hot: true,
  },
  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
}