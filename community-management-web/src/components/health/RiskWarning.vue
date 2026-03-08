<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Warning, Search, HomeFilled, Building, Apartment, ShieldCheck, AlertCircle, Clock, Location } from '@element-plus/icons-vue'

const riskInfo = ref({
  riskLevel: '低风险',
  quarantineCountdown: '0天',
  riskArea: '',
  riskMessage: '该区域暂无风险，请注意日常防护'
})
const form = reactive({
  communityId: 1,
  building: '1栋',
  unit: '1单元'
})
const loading = ref(false)

const getRiskLevel = async () => {
  loading.value = true
  try {
    // 模拟API调用
    setTimeout(() => {
      if (form.communityId === 1 && form.building === '1栋' && form.unit === '1单元') {
        riskInfo.value = {
          riskLevel: '高风险',
          quarantineCountdown: '3天',
          riskArea: '1栋1单元',
          riskMessage: '该区域存在确诊病例，请严格执行隔离措施'
        }
      } else if (form.communityId === 1 && form.building === '2栋') {
        riskInfo.value = {
          riskLevel: '中风险',
          quarantineCountdown: '0天',
          riskArea: '2栋',
          riskMessage: '该区域存在密切接触者，请注意防护'
        }
      } else {
        riskInfo.value = {
          riskLevel: '低风险',
          quarantineCountdown: '0天',
          riskArea: '',
          riskMessage: '该区域暂无风险，请注意日常防护'
        }
      }
      loading.value = false
    }, 1000)
  } catch (error) {
    ElMessage.error('获取风险等级失败，请重试')
    loading.value = false
  }
}

const getRiskColor = () => {
  switch (riskInfo.value.riskLevel) {
    case '高风险': return '#F56C6C'
    case '中风险': return '#E6A23C'
    case '低风险': return '#67C23A'
    default: return '#67C23A'
  }
}

const getRiskIcon = () => {
  switch (riskInfo.value.riskLevel) {
    case '高风险': return 'AlertCircle'
    case '中风险': return 'Warning'
    case '低风险': return 'ShieldCheck'
    default: return 'ShieldCheck'
  }
}

onMounted(() => {
  getRiskLevel()
})
</script>

<template>
  <div class="risk-warning-container">
    <div class="page-header">
      <h2>风险预警</h2>
      <el-button 
        type="info" 
        icon="Warning"
        circle
        size="large"
      />
    </div>
    
    <el-card class="search-card" shadow="hover">
      <el-form :model="form" label-width="100px" class="search-form">
        <el-form-item label="社区ID" required>
          <div class="input-with-icon">
            <el-icon class="input-icon"><HomeFilled /></el-icon>
            <el-input 
              v-model="form.communityId" 
              type="number"
              placeholder="请输入社区ID"
            />
          </div>
        </el-form-item>
        <el-form-item label="楼栋" required>
          <div class="input-with-icon">
            <el-icon class="input-icon"><Building /></el-icon>
            <el-input 
              v-model="form.building" 
              placeholder="请输入楼栋号"
            />
          </div>
        </el-form-item>
        <el-form-item label="单元">
          <div class="input-with-icon">
            <el-icon class="input-icon"><Apartment /></el-icon>
            <el-input 
              v-model="form.unit" 
              placeholder="请输入单元号"
            />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="getRiskLevel" 
            :loading="loading"
            class="search-button"
          >
            <el-icon><Search /></el-icon>
            查询风险等级
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="risk-info-card" shadow="hover" :loading="loading">
      <template #header>
        <div class="card-header">
          <el-icon><AlertCircle /></el-icon>
          <span>风险评估结果</span>
        </div>
      </template>
      <div class="risk-info-content">
        <div class="risk-header" :style="{ borderColor: getRiskColor() }">
          <component :is="getRiskIcon()" class="risk-icon" :style="{ color: getRiskColor() }" />
          <div class="risk-level-info">
            <h3 :style="{ color: getRiskColor() }">{{ riskInfo.riskLevel }}</h3>
            <p>{{ riskInfo.riskMessage }}</p>
          </div>
        </div>
        
        <div class="risk-details">
          <div class="risk-item">
            <el-icon class="detail-icon"><Clock /></el-icon>
            <span class="detail-label">隔离倒计时：</span>
            <span class="detail-value">{{ riskInfo.quarantineCountdown }}</span>
          </div>
          <div class="risk-item">
            <el-icon class="detail-icon"><Location /></el-icon>
            <span class="detail-label">风险区域：</span>
            <span class="detail-value">{{ riskInfo.riskArea || '无' }}</span>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.risk-warning-container {
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

.search-card {
  border-radius: 12px;
  transition: all 0.3s ease;
}

.search-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.search-form {
  padding: 16px 0;
}

.input-with-icon {
  display: flex;
  align-items: center;
  gap: 12px;
}

.input-icon {
  font-size: 20px;
  color: #1890ff;
  flex-shrink: 0;
}

.search-button {
  font-size: 16px;
  padding: 10px 32px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.search-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.risk-info-card {
  border-radius: 12px;
  transition: all 0.3s ease;
}

.risk-info-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.risk-info-content {
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.risk-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 24px;
  background-color: #f9f9f9;
  border-left: 4px solid #67C23A;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.risk-header:hover {
  background-color: #f0f9ff;
  transform: translateX(4px);
}

.risk-icon {
  font-size: 48px;
  flex-shrink: 0;
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

.risk-level-info h3 {
  margin: 0 0 8px 0;
  font-size: 24px;
  font-weight: bold;
}

.risk-level-info p {
  margin: 0;
  font-size: 16px;
  color: #666;
  line-height: 1.5;
}

.risk-details {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.risk-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background-color: #f9f9f9;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.risk-item:hover {
  background-color: #f0f9ff;
  transform: translateX(4px);
}

.detail-icon {
  font-size: 20px;
  color: #1890ff;
  flex-shrink: 0;
}

.detail-label {
  font-weight: 600;
  color: #333;
  width: 100px;
}

.detail-value {
  flex: 1;
  font-size: 16px;
  color: #666;
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .input-with-icon {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
  
  .risk-header {
    flex-direction: column;
    text-align: center;
    gap: 16px;
  }
  
  .risk-icon {
    font-size: 32px;
  }
  
  .risk-level-info h3 {
    font-size: 20px;
  }
}
</style>