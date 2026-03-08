<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { View, Refresh, UserFilled, Calendar, Clock, InfoFilled } from '@element-plus/icons-vue'

const healthCode = ref({
  codeType: '绿码',
  validUntil: '2026-03-15',
  lastUpdate: '2026-03-08 08:00:00'
})
const proxyUser = ref('')
const loading = ref(false)

const getHealthCode = async () => {
  loading.value = true
  try {
    // 模拟API调用
    setTimeout(() => {
      healthCode.value = {
        codeType: '绿码',
        validUntil: '2026-03-15',
        lastUpdate: '2026-03-08 08:00:00'
      }
      loading.value = false
    }, 1000)
  } catch (error) {
    ElMessage.error('获取健康码失败，请重试')
    loading.value = false
  }
}

const proxyHealthCode = async () => {
  if (!proxyUser.value) {
    ElMessage.warning('请输入被代办人ID')
    return
  }
  
  loading.value = true
  try {
    // 模拟API调用
    setTimeout(() => {
      healthCode.value = {
        codeType: '绿码',
        validUntil: '2026-03-15',
        lastUpdate: '2026-03-08 08:00:00'
      }
      loading.value = false
      ElMessage.success('亲属代办成功')
    }, 1000)
  } catch (error) {
    ElMessage.error('代办失败，请重试')
    loading.value = false
  }
}

const getHealthCodeColor = () => {
  switch (healthCode.value.codeType) {
    case '绿码': return '#67C23A'
    case '黄码': return '#E6A23C'
    case '红码': return '#F56C6C'
    default: return '#67C23A'
  }
}

onMounted(() => {
  getHealthCode()
})
</script>

<template>
  <div class="health-code-container">
    <div class="page-header">
      <h2>健康码管理</h2>
      <el-button 
        type="primary" 
        @click="getHealthCode" 
        :loading="loading"
        icon="Refresh"
      >
        刷新
      </el-button>
    </div>
    
    <el-card class="health-code-card" shadow="hover" :loading="loading">
      <div class="health-code-content">
        <div 
          class="health-code-status" 
          :style="{ backgroundColor: getHealthCodeColor() }"
        >
          <el-icon class="health-code-icon"><View /></el-icon>
          <span>{{ healthCode.codeType }}</span>
          <div class="code-pattern"></div>
        </div>
        <div class="health-code-info">
          <div class="info-item">
            <el-icon class="info-icon"><Calendar /></el-icon>
            <span>有效期至：{{ healthCode.validUntil }}</span>
          </div>
          <div class="info-item">
            <el-icon class="info-icon"><Clock /></el-icon>
            <span>最后更新：{{ healthCode.lastUpdate }}</span>
          </div>
          <div class="info-item">
            <el-icon class="info-icon"><InfoFilled /></el-icon>
            <span>核酸检测：阴性（48小时内）</span>
          </div>
        </div>
      </div>
    </el-card>
    
    <el-card class="proxy-section" shadow="hover" style="margin-top: 24px;">
      <template #header>
        <div class="proxy-header">
          <el-icon><UserFilled /></el-icon>
          <span>亲属代办</span>
        </div>
      </template>
      <el-form :model="{ proxyUser }" label-width="120px" class="proxy-form">
        <el-form-item label="被代办人ID" required>
          <el-input 
            v-model="proxyUser" 
            placeholder="请输入被代办人ID"
            class="proxy-input"
          />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="proxyHealthCode" 
            :loading="loading"
            class="proxy-button"
          >
            代办健康码
          </el-button>
        </el-form-item>
      </el-form>
      <div class="proxy-tips">
        <div class="tip-item">
          <el-icon class="tip-icon"><InfoFilled /></el-icon>
          <span>亲属代办功能适用于老人、小孩等没有智能手机的人群</span>
        </div>
        <div class="tip-item">
          <el-icon class="tip-icon"><InfoFilled /></el-icon>
          <span>代办人需与被代办人在同一户口本或有亲属关系证明</span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.health-code-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.page-header h2 {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  color: #333;
}

.health-code-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  max-width: 800px;
}

.health-code-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.health-code-content {
  display: flex;
  align-items: center;
  padding: 32px;
  gap: 32px;
}

.health-code-status {
  width: 180px;
  height: 180px;
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.health-code-status:hover {
  transform: scale(1.05);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.3);
}

.health-code-icon {
  font-size: 64px;
  margin-bottom: 16px;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}

.health-code-status span {
  font-size: 32px;
  font-weight: bold;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.code-pattern {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 40px;
  background: linear-gradient(45deg, rgba(255,255,255,0.1) 25%, transparent 25%, transparent 50%, rgba(255,255,255,0.1) 50%, rgba(255,255,255,0.1) 75%, transparent 75%, transparent);
  background-size: 20px 20px;
  animation: pattern-move 10s linear infinite;
}

@keyframes pattern-move {
  0% {
    background-position: 0 0;
  }
  100% {
    background-position: 20px 20px;
  }
}

.health-code-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 16px;
  color: #333;
  padding: 12px 16px;
  background-color: #f9f9f9;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.info-item:hover {
  background-color: #e6f7ff;
  transform: translateX(4px);
}

.info-icon {
  font-size: 20px;
  color: #1890ff;
  flex-shrink: 0;
}

.proxy-section {
  border-radius: 12px;
  transition: all 0.3s ease;
  max-width: 800px;
}

.proxy-section:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.proxy-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.proxy-form {
  padding: 16px 0;
}

.proxy-input {
  width: 300px;
}

.proxy-button {
  font-size: 16px;
  padding: 10px 32px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.proxy-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.proxy-tips {
  margin-top: 16px;
  padding: 16px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.tip-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 8px 0;
  font-size: 14px;
  color: #666;
  line-height: 1.5;
}

.tip-icon {
  font-size: 16px;
  color: #1890ff;
  flex-shrink: 0;
  margin-top: 2px;
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .health-code-content {
    flex-direction: column;
    text-align: center;
    gap: 24px;
  }
  
  .health-code-status {
    width: 160px;
    height: 160px;
  }
  
  .health-code-icon {
    font-size: 48px;
  }
  
  .health-code-status span {
    font-size: 28px;
  }
  
  .proxy-input {
    width: 100%;
  }
  
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
}
</style>