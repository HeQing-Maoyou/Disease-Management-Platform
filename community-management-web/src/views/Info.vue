<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import * as Icons from '@element-plus/icons-vue';
const IconPlaceholder = { template: '<span></span>' };
const Message = Icons['Message'] || IconPlaceholder;
const Position = Icons['Position'] || IconPlaceholder;
const ChatDotRound = Icons['ChatDotRound'] || IconPlaceholder;

const route = useRoute();
const router = useRouter();
const activeIndex = ref('/info/notice');

const handleSelect = (key, keyPath) => {
  activeIndex.value = key;
  router.push(key);
};
</script>

<template>
  <div class="info-container">
    <el-card class="sidebar-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <h3>信息中心</h3>
        </div>
      </template>
      <el-menu
        :default-active="activeIndex"
        class="info-menu"
        background-color="#ffffff"
        text-color="#333333"
        active-text-color="#1890ff"
        @select="handleSelect"
      >
        <el-menu-item index="/info/notice">
          <el-icon class="menu-icon"><Message /></el-icon>
          <span>通知公告</span>
        </el-menu-item>
        <el-menu-item index="/info/map">
          <el-icon class="menu-icon"><Position /></el-icon>
          <span>防疫地图</span>
        </el-menu-item>
        <el-menu-item index="/info/chat">
          <el-icon class="menu-icon"><ChatDotRound /></el-icon>
          <span>在线沟通</span>
        </el-menu-item>
      </el-menu>
    </el-card>
    <div class="content-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>
.info-container {
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

.info-menu {
  width: 100%;
  border-right: none;
}

.info-menu .el-menu-item {
  height: 60px;
  line-height: 60px;
  font-size: 16px;
  margin: 8px 0;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.info-menu .el-menu-item:hover {
  background-color: rgba(24, 144, 255, 0.05);
}

.info-menu .el-menu-item.is-active {
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
  .info-container {
    flex-direction: column;
  }

  .sidebar-card {
    width: 100%;
  }

  .info-menu {
    display: flex;
    flex-direction: row;
    overflow-x: auto;
  }

  .info-menu .el-menu-item {
    white-space: nowrap;
    margin: 0 4px;
  }

  .content-container {
    padding: 24px;
  }
}
</style>
