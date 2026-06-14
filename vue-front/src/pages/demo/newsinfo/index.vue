<template>
  <el-card>
    <el-table :data="newsList" style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" width="200" show-overflow-tooltip />
      <el-table-column prop="content" label="内容" width="300" show-overflow-tooltip />
      <el-table-column prop="publishDate" label="发布日期" width="120" />
<!--      <el-table-column prop="created_at" label="创建时间" width="180" /> -->
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="handleEdit(scope.row)">
            修改
          </el-button>
          <el-button link type="danger" size="small" @click="handleDelete(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>


    <div class="pagination-container">
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 30, 50]"
        :background="true"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>


    <el-dialog v-model="editDialogVisible" title="修改新闻" width="50%">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="editForm.title" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="editForm.content" type="textarea" :rows="5" />
        </el-form-item>
        <el-form-item label="发布日期">
          <el-date-picker
            v-model="editForm.publishDate"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmEdit">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@@/http/request'

const newsList = ref([])
const loading = ref(false)

// 分页配置
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

const editDialogVisible = ref(false)
const editForm = ref({
  id: 0,
  title: '',
  content: '',
  publishDate: ''
})

// 获取新闻列表
const fetchNewsList = async () => {
  try {
    loading.value = true
    const res = await request.get('/api/news', {
      params: {
        page: pagination.value.currentPage,
        page_size: pagination.value.pageSize
      }
    })
    // res 已由拦截器解居，res.data 即 { data: [], total: n }
    newsList.value = res.data.data
    pagination.value.total = res.data.total
  } catch {
    ElMessage.error('获取新闻列表失败')
  } finally {
    loading.value = false
  }
}

// 分页
const handleSizeChange = (val: number) => {
  pagination.value.pageSize = val
  fetchNewsList()
}

const handleCurrentChange = (val: number) => {
  pagination.value.currentPage = val
  fetchNewsList()
}

// 修改
const handleEdit = (row: any) => {
  editForm.value = {
    id: row.id,
    title: row.title,
    content: row.content,
    publishDate: row.publishDate
  }
  editDialogVisible.value = true
}

const confirmEdit = async () => {
  try {
    await request.put(`/api/news/${editForm.value.id}`, editForm.value)
    ElMessage.success('修改成功')
    editDialogVisible.value = false
    fetchNewsList()
  } catch {
    // 拦截器已处理
  }
}

// 删除
const handleDelete = (row: any) => {
  ElMessageBox.confirm('确定要删除这条新闻吗?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/api/news/${row.id}`)
      ElMessage.success('删除成功')
      fetchNewsList()
    } catch {
      // 拦截器已处理
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 初始化
onMounted(() => {
  fetchNewsList()
})
</script>

<style scoped>
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>