<template>
  <div class="sidebar-container">
	  
    <div class="sidebar-logo">
      <VueSvg class="vue-logo" />
    </div>
    
    <el-menu 
      class="sidebar" 
      :default-active="$route.path" 
      router
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
    >
      <el-menu-item index="/dashboard">
        <el-icon><HomeFilled /></el-icon>
        <span>首页</span>
      </el-menu-item>

      <el-sub-menu index="1">
        <template #title>
          <el-icon><Search /></el-icon>
          <span>新闻查询</span>
        </template>
        <el-menu-item index="/demo/news">新闻列表</el-menu-item>
        <el-menu-item index="/demo/news/add">新闻添加</el-menu-item>
        <el-menu-item index="#">近期热点</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="2">
        <template #title>
          <el-icon><Setting /></el-icon>
          <span>系统管理</span>
        </template>
        <el-menu-item index="#">用户管理</el-menu-item>
        <el-menu-item index="#">角色管理</el-menu-item>
        <el-menu-item index="#">操作日志</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import {
  HomeFilled,
  Search,
  Setting
} from '@element-plus/icons-vue'
import VueSvg from '@@/assets/icons/vue.svg?component'
</script>

<style lang="scss" scoped>
.sidebar-container {
  height: 100%;
  background-color: #304156;
  
  .sidebar-logo {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 60px;
    border-bottom: 1px solid #263445;
    
    .vue-logo {
      width: 32px;
      height: 32px;
      color: #409EFF; 
    }
  }
  
  .sidebar {
    width: 220px;
    height: calc(100vh - 60px); 
    border-right: none;
    
    :deep(.el-menu-item),
    :deep(.el-sub-menu__title) {
      &:hover {
        background-color: #263445 !important;
      }
    }
    
    :deep(.el-menu-item.is-active) {
      background-color: #263445 !important;
    }
  }
}
</style>