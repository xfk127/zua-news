import svgLoader from 'vite-svg-loader'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { resolve } from "node:path"

// https://vite.dev/config/
// GitHub Pages 部署时，base 需改为你的仓库名（如 '/demo/'）
// 本地开发时保持 '/'
export default defineConfig({
  base: '/',
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
    svgLoader()
  ],
  resolve: {

    alias: {

      // @ 符号指向 src 目录

      "@": resolve(__dirname, "src"),

      // @@ 符号指向 src/common 通用目录

      "@@": resolve(__dirname, "src/common")

    }

  },
  // 开发环境服务器配置
  server: {
    // 是否监听所有地址
    host: true,
    // 端口号
    port: 5173,
    // 端口被占用时，是否直接退出
    strictPort: false,
    // 是否自动打开浏览器
    open: true,

    // 是否允许跨域
    cors: true,
    // 预热常用文件，提高初始页面加载速度
    warmup: {
      clientFiles: [
        "./src/layouts/**/*.*",
        "./src/pinia/**/*.*",
        "./src/router/**/*.*"
      ]
    }
  }
})


