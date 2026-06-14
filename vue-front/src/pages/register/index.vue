<template>
  <div class="register-container">
    <el-card class="register-card">
      <h2>注册</h2>
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" style="width: 100%;" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister">注册</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="text" @click="goToLogin">去登录</el-button>
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

const router = useRouter()
const form = reactive({ username: '', password: '' })

const handleRegister = async () => {
  try {
    await request.post('/api/register', form)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch {
    // 错误已由拦截器弹出
  }
}

const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
	.register-container {
	  width: 400px;
	  margin: 100px auto;
	}
	h2 {
	  text-align: center;
	  margin-bottom: 20px;
	}
</style>
