import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  // token 优先从 localStorage 恢复（刷新页面不丢失）
  const token = ref<string>(localStorage.getItem('token') ?? '')
  const username = ref<string>(localStorage.getItem('username') ?? '')

  /** 登录：保存 token 和用户名 */
  const setUser = (newToken: string, newUsername: string) => {
    token.value = newToken
    username.value = newUsername
    localStorage.setItem('token', newToken)
    localStorage.setItem('username', newUsername)
  }

  /** 登出：清空状态 */
  const logout = () => {
    token.value = ''
    username.value = ''
    localStorage.removeItem('token')
    localStorage.removeItem('username')
  }

  /** 是否已登录 */
  const isLoggedIn = () => !!token.value

  return { token, username, setUser, logout, isLoggedIn }
})
