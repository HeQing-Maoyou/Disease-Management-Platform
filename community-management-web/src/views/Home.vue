<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { 
  Promotion, 
  View, 
  ShoppingCart, 
  Service, 
  HelpFilled, 
  Message, 
  Position, 
  ChatDotRound,
  Calendar,
  Clock,
  Check
} from '@element-plus/icons-vue'

const router = useRouter()
const healthCodeStatus = ref('绿码')
const healthCodeValidity = ref('2026-03-15')
const notices = ref([
  {
    id: 1,
    title: '关于开展新一轮核酸检测的通知',
    time: '2026-03-08'
  },
  {
    id: 2,
    title: '社区疫情防控措施调整通知',
    time: '2026-03-07'
  },
  {
    id: 3,
    title: '生活物资配送安排',
    time: '2026-03-06'
  }
])

const quickServices = [
  {
    name: '健康打卡',
    icon: Promotion,
    path: '/health/checkin',
    color: '#409EFF'
  },
  {
    name: '健康码',
    icon: View,
    path: '/health/code',
    color: '#67C23A'
  },
  {
    name: '团购商城',
    icon: ShoppingCart,
    path: '/life/group-purchase',
    color: '#E6A23C'
  },
  {
    name: '隔离服务',
    icon: Service,
    path: '/life/quarantine',
    color: '#F56C6C'
  },
  {
    name: '社区互助',
    icon: HelpFilled,
    path: '/life/help',
    color: '#909399'
  },
  {
    name: '通知公告',
    icon: Message,
    path: '/info/notice',
    color: '#409EFF'
  },
  {
    name: '防疫地图',
    icon: Position,
    path: '/info/map',
    color: '#67C23A'
  },
  {
    name: '在线沟通',
    icon: ChatDotRound,
    path: '/info/chat',
    color: '#E6A23C'
  }
]

const navigateTo = (path) => {
  router.push(path)
}

const getHealthCodeColor = () => {
  switch (healthCodeStatus.value) {
    case '绿码': return '#67C23A'
    case '黄码': return '#E6A23C'
    case '红码': return '#F56C6C'
    default: return '#67C23A'
  }
}
</script>

<template>
  <div class="home-container">
    <!-- 欢迎横幅 -->
    <el-card class="welcome-card" shadow="hover">
      <div class="welcome-content">
        <h1>欢迎回来！</h1>
        <p>智慧社区疫情与生活综合管理系统</p>
        <el-button type="primary" size="large" @click="navigateTo('/health/checkin')">
          <Check class="button-icon" />
          立即健康打卡
        </el-button>
      </div>
    </el-card>

    <div class="content-grid">
      <!-- 健康码展示 -->
      <el-card class="health-code-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <h3>我的健康码</h3>
            <el-button type="primary" size="small" @click="navigateTo('/health/code')">
              查看详情
            </el-button>
          </div>
        </template>
        <div class="health-code-content">
          <div 
            class="health-code-status" 
            :style="{ backgroundColor: getHealthCodeColor() }"
          >
            <el-icon class="health-code-icon"><View /></el-icon>
            <span>{{ healthCodeStatus }}</span>
          </div>
          <div class="health-code-info">
            <div class="info-item">
              <el-icon class="info-icon"><Calendar /></el-icon>
              <span>有效期至：{{ healthCodeValidity }}</span>
            </div>
            <div class="info-item">
              <el-icon class="info-icon"><Clock /></el-icon>
              <span>核酸检测：阴性（48小时内）</span>
            </div>
          </div>
        </div>
      </el-card>

      <!-- 快捷服务 -->
      <el-card class="quick-services-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <h3>快捷服务</h3>
          </div>
        </template>
        <div class="quick-services-grid">
          <div 
            v-for="(service, index) in quickServices" 
            :key="index" 
            class="service-item" 
            @click="navigateTo(service.path)"
          >
            <div class="service-icon" :style="{ backgroundColor: service.color + '20', color: service.color }">
              <component :is="service.icon" />
            </div>
            <span>{{ service.name }}</span>
          </div>
        </div>
      </el-card>

      <!-- 通知公告 -->
      <el-card class="notices-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <h3>通知公告</h3>
            <el-button type="primary" size="small" @click="navigateTo('/info/notice')">
              查看全部
            </el-button>
          </div>
        </template>
        <div class="notices-list">
          <div v-for="notice in notices" :key="notice.id" class="notice-item">
            <el-icon class="notice-icon"><Message /></el-icon>
            <span class="notice-title">{{ notice.title }}</span>
            <span class="notice-time">{{ notice.time }}</span>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.home-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.welcome-card {
  background: linear-gradient(135deg, #1890ff 0%, #36cfc9 100%);
  color: white;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.welcome-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(24, 144, 255, 0.3);
}

.welcome-content {
  padding: 40px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.welcome-content h1 {
  margin: 0;
  font-size: 32px;
  font-weight: 600;
}

.welcome-content p {
  margin: 0;
  font-size: 18px;
  opacity: 0.9;
}

.button-icon {
  margin-right: 8px;
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 2fr;
  grid-template-rows: auto auto;
  gap: 24px;
  grid-template-areas:
    "health quick"
    "notices quick";
}

.health-code-card {
  grid-area: health;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.quick-services-card {
  grid-area: quick;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.notices-card {
  grid-area: notices;
  border-radius: 12px;
  transition: all 0.3s ease;
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

.health-code-content {
  display: flex;
  align-items: center;
  padding: 24px;
  gap: 24px;
}

.health-code-status {
  width: 140px;
  height: 140px;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.health-code-status:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.health-code-icon {
  font-size: 48px;
  margin-bottom: 8px;
}

.health-code-status span {
  font-size: 24px;
  font-weight: bold;
}

.health-code-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  color: #333;
}

.info-icon {
  color: #1890ff;
  font-size: 18px;
}

.quick-services-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
  padding: 16px 0;
}

.service-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px 16px;
  background-color: #ffffff;
  border: 1px solid #e8e8e8;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  gap: 12px;
}

.service-item:hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
  border-color: #1890ff;
}

.service-icon {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32px;
  transition: all 0.3s ease;
}

.service-item:hover .service-icon {
  transform: scale(1.1);
}

.service-item span {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  text-align: center;
}

.notices-list {
  padding: 16px 0;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.notice-item {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  background-color: #f9f9f9;
  border-radius: 8px;
  transition: all 0.3s ease;
  gap: 12px;
}

.notice-item:hover {
  background-color: #e6f7ff;
  transform: translateX(4px);
}

.notice-icon {
  color: #1890ff;
  font-size: 16px;
  flex-shrink: 0;
}

.notice-title {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.notice-time {
  color: #909399;
  font-size: 13px;
  flex-shrink: 0;
}

/* 响应式设计 */
@media screen and (max-width: 1200px) {
  .content-grid {
    grid-template-columns: 1fr;
    grid-template-areas:
      "health"
      "quick"
      "notices";
  }
  
  .quick-services-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media screen and (max-width: 768px) {
  .welcome-content {
    padding: 30px;
  }
  
  .welcome-content h1 {
    font-size: 24px;
  }
  
  .quick-services-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .health-code-content {
    flex-direction: column;
    text-align: center;
    gap: 16px;
  }
  
  .health-code-status {
    width: 120px;
    height: 120px;
  }
  
  .health-code-icon {
    font-size: 40px;
  }
  
  .health-code-status span {
    font-size: 20px;
  }
}
</style>