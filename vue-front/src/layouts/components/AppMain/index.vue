<template>
  <div class="app-main">
	<router-view v-slot="{ Component }">
		<transition name="fade-transform" mode="out-in">
			<component :is="Component" />
		</transition>
	</router-view>
	<Footer/>
  </div>
</template>

<script setup lang="ts">
	import { Footer } from "../index"
	
</script>

<style scoped lang="scss">
.app-main {
  flex: 1;
  overflow: auto;
  padding: 16px;
}
</style>
