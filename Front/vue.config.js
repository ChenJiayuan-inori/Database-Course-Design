const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  //关闭语法检测
  lintOnSave: false,

  devServer: {
    client: {
      overlay: false,
    },
    port: 7000,
  }
})
