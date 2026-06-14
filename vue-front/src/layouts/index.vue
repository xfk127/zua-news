<template>
  <div class="layout-wrapper">
    <SideBar />
    <div class="main-content">
      <NavigationBar />
<!--      <TagsView /> -->
      <AppMain />
    </div>
  </div>
</template>

<script setup lang="ts">
import SideBar from "./components/Sidebar/index.vue"
import NavigationBar from "./components/NavifationBar/index.vue"
// import TagsView from "./components/TagsView/index.vue"
import AppMain from "./components/AppMain/index.vue"
</script>

<style lang="scss" scoped>
.layout-wrapper {
  display: flex;
  height: 100vh;
  overflow: hidden;
}
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>