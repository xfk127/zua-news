<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>登录</h2>
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" style="width: 100%;" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="text" @click="goToRegister">去注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script lang="ts" setup>
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '@@/http/request'
import { useUserStore } from '@/pinia/stores/user'

const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: '', password: '' })

const handleLogin = async () => {
  try {
    const res = await request.post('/api/login', form)
    // res 已由拦截器解居为 Result 对象，res.data 即 token
    userStore.setUser(res.data, form.username)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } catch {
    // 错误已由拦截器弹出，这里不需要重复提示
  }
}

const goToRegister = () => {
  router.push('/register')
}
</script>

<style scoped>
	.login-container {
	  width: 400px;
	  margin: 100px auto;
	}
	h2 {
	  text-align: center;
	  margin-bottom: 20px;
	}

</style>
