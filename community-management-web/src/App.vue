<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { House, HomeFilled, Sunny, GoodsFilled, MessageFilled, UserFilled } from '@element-plus/icons-vue'

const router = useRouter()
const activeIndex = ref('/')

const handleSelect = (key, keyPath) => {
  activeIndex.value = key
  router.push(key)
}
</script>

<template>
  <div class="app-container">
    <el-container>
      <el-header height="70px" class="header-container">
        <div class="logo">
          <el-icon class="logo-icon"><House /></el-icon>
          <span>智慧社区疫情与生活综合管理系统</span>
        </div>
        <el-menu
          :default-active="activeIndex"
          class="nav-menu"
          mode="horizontal"
          @select="handleSelect"
          background-color="#ffffff"
          text-color="#333333"
          active-text-color="#1890ff"
        >
          <el-menu-item index="/">
            <el-icon><HomeFilled /></el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/health">
            <el-icon><Sunny /></el-icon>
            <span>健康管理</span>
          </el-menu-item>
          <el-menu-item index="/life">
            <el-icon><GoodsFilled /></el-icon>
            <span>生活服务</span>
          </el-menu-item>
          <el-menu-item index="/info">
            <el-icon><MessageFilled /></el-icon>
            <span>信息中心</span>
          </el-menu-item>
          <el-menu-item index="/user">
            <el-icon><UserFilled /></el-icon>
            <span>个人中心</span>
          </el-menu-item>
        </el-menu>
      </el-header>
      <el-main class="main-content">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
.app-container {
  height: 100vh;
  width: 100%;
  background-color: #f0f2f5;
}

.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 40px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  background-color: #ffffff;
  position: sticky;
  top: 0;
  z-index: 100;
  transition: all 0.3s ease;
}

.header-container:hover {
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

.logo {
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
  color: #1890ff;
  gap: 10px;
}

.logo-icon {
  font-size: 28px;
}

.nav-menu {
  flex: 1;
  justify-content: flex-end;
  border-bottom: none;
}

.nav-menu .el-menu-item {
  font-size: 16px;
  padding: 0 24px;
  transition: all 0.3s ease;
  border-radius: 6px;
  margin: 0 4px;
}

.nav-menu .el-menu-item:hover {
  background-color: rgba(24, 144, 255, 0.05);
}

.nav-menu .el-menu-item.is-active {
  background-color: rgba(24, 144, 255, 0.1);
  font-weight: 600;
}

.main-content {
  padding: 30px;
  min-height: calc(100vh - 70px);
  background-color: #f0f2f5;
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .header-container {
    padding: 0 20px;
  }
  
  .logo span {
    display: none;
  }
  
  .nav-menu .el-menu-item {
    font-size: 14px;
    padding: 0 16px;
  }
  
  .main-content {
    padding: 20px;
  }
}
</style>
