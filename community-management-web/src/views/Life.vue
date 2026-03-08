<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ShoppingCart, Service, HelpFilled } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const activeIndex = ref('/life/group-purchase')

const handleSelect = (key, keyPath) => {
  activeIndex.value = key
  router.push(key)
}
</script>

<template>
  <div class="life-container">
    <el-card class="sidebar-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <h3>生活服务</h3>
        </div>
      </template>
      <el-menu
        :default-active="activeIndex"
        class="life-menu"
        @select="handleSelect"
        background-color="#ffffff"
        text-color="#333333"
        active-text-color="#1890ff"
      >
        <el-menu-item index="/life/group-purchase">
          <el-icon class="menu-icon"><ShoppingCart /></el-icon>
          <span>团购商城</span>
        </el-menu-item>
        <el-menu-item index="/life/quarantine">
          <el-icon class="menu-icon"><Service /></el-icon>
          <span>隔离服务</span>
        </el-menu-item>
        <el-menu-item index="/life/help">
          <el-icon class="menu-icon"><HelpFilled /></el-icon>
          <span>社区互助</span>
        </el-menu-item>
      </el-menu>
    </el-card>
    <div class="content-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>
.life-container {
  display: flex;
  gap: 24px;
  height: 100%;
  min-height: 600px;
}

.sidebar-card {
  width: 240px;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.sidebar-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.card-header h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.life-menu {
  width: 100%;
  border-right: none;
}

.life-menu .el-menu-item {
  height: 60px;
  line-height: 60px;
  font-size: 16px;
  margin: 8px 0;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.life-menu .el-menu-item:hover {
  background-color: rgba(24, 144, 255, 0.05);
}

.life-menu .el-menu-item.is-active {
  background-color: rgba(24, 144, 255, 0.1);
  font-weight: 600;
}

.menu-icon {
  font-size: 20px;
  margin-right: 12px;
}

.content-container {
  flex: 1;
  background-color: white;
  border-radius: 12px;
  padding: 32px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.content-container:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .life-container {
    flex-direction: column;
  }
  
  .sidebar-card {
    width: 100%;
  }
  
  .life-menu {
    display: flex;
    flex-direction: row;
    overflow-x: auto;
  }
  
  .life-menu .el-menu-item {
    white-space: nowrap;
    margin: 0 4px;
  }
  
  .content-container {
    padding: 24px;
  }
}
</style>