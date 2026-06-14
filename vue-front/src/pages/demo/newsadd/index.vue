<template>
  <el-card>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入标题" />
      </el-form-item>

      <el-form-item label="内容" prop="content">
        <el-input
          v-model="form.content"
          type="textarea"
          :rows="5"
          placeholder="请输入内容"
        />
      </el-form-item>

      <el-form-item label="发布日期" prop="publishDate">
        <el-date-picker
          v-model="form.publishDate"
          type="date"
          placeholder="选择日期"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { ElMessage, FormInstance, FormRules } from 'element-plus'
import request from '@@/http/request'

const form = reactive({
  title: '',
  content: '',
  publishDate: ''
})

const rules: FormRules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
  publishDate: [{ required: true, message: '请选择发布日期', trigger: 'change' }]
}

const formRef = ref<FormInstance>()

const submitForm = async () => {
  await formRef.value?.validate(async valid => {
    if (valid) {
      try {
        await request.post('/api/news', form)
        ElMessage.success('添加成功')
        resetForm()
      } catch {
        // 拦截器已处理
      }
    }
  })
}

const resetForm = () => {
  form.title = ''
  form.content = ''
  form.publishDate = ''
}
</script>

<style scoped>
.el-form {
  max-width: 600px;
  margin: 0 auto;
}
</style>