<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import * as Icons from '@element-plus/icons-vue';
const IconPlaceholder = { template: '<span></span>' };
const Promotion = Icons['Promotion'] || IconPlaceholder;
const View = Icons['View'] || IconPlaceholder;
const Warning = Icons['Warning'] || IconPlaceholder;

const route = useRoute();
const router = useRouter();
const activeIndex = ref('/health/checkin');

const handleSelect = (key, keyPath) => {
  activeIndex.value = key;
  router.push(key);
};
</script>

<template>
  <div class="health-container">
    <el-card class="sidebar-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <h3>健康管理</h3>
        </div>
      </template>
      <el-menu
        :default-active="activeIndex"
        class="health-menu"
        background-color="#ffffff"
        text-color="#333333"
        active-text-color="#1890ff"
        @select="handleSelect"
      >
        <el-menu-item index="/health/checkin">
          <el-icon class="menu-icon"><Promotion /></el-icon>
          <span>健康打卡</span>
        </el-menu-item>
        <el-menu-item index="/health/code">
          <el-icon class="menu-icon"><View /></el-icon>
          <span>健康码管理</span>
        </el-menu-item>
        <el-menu-item index="/health/risk">
          <el-icon class="menu-icon"><Warning /></el-icon>
          <span>风险预警</span>
        </el-menu-item>
      </el-menu>
    </el-card>
    <div class="content-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>
.health-container {
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

.health-menu {
  width: 100%;
  border-right: none;
}

.health-menu .el-menu-item {
  height: 60px;
  line-height: 60px;
  font-size: 16px;
  margin: 8px 0;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.health-menu .el-menu-item:hover {
  background-color: rgba(24, 144, 255, 0.05);
}

.health-menu .el-menu-item.is-active {
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
  .health-container {
    flex-direction: column;
  }

  .sidebar-card {
    width: 100%;
  }

  .health-menu {
    display: flex;
    flex-direction: row;
    overflow-x: auto;
  }

  .health-menu .el-menu-item {
    white-space: nowrap;
    margin: 0 4px;
  }

  .content-container {
    padding: 24px;
  }
}
</style>
