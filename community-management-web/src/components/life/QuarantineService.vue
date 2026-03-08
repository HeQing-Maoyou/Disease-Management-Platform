<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Service, Calendar, Clock, CheckCircle, AlertCircle, Edit, Refresh } from '@element-plus/icons-vue'

const services = ref([])
const form = reactive({
  serviceType: '',
  appointmentTime: ''
})
const loading = ref(false)

const serviceTypes = [
  { label: '上门收取生活垃圾', value: '垃圾收取' },
  { label: '上门核酸检测', value: '核酸检测' },
  { label: '物资配送', value: '物资配送' },
  { label: '其他服务', value: '其他' }
]

const bookService = async () => {
  if (!form.serviceType || !form.appointmentTime) {
    ElMessage.warning('请选择服务类型和预约时间')
    return
  }
  
  loading.value = true
  try {
    // 模拟API调用
    setTimeout(() => {
      const newService = {
        id: Date.now(),
        serviceType: form.serviceType,
        appointmentTime: form.appointmentTime,
        status: '待处理',
        createdAt: new Date().toLocaleString()
      }
      services.value.push(newService)
      form.serviceType = ''
      form.appointmentTime = ''
      loading.value = false
      ElMessage.success('服务预约成功')
    }, 1000)
  } catch (error) {
    ElMessage.error('预约失败，请重试')
    loading.value = false
  }
}

const getServices = async () => {
  loading.value = true
  try {
    // 模拟API调用
    setTimeout(() => {
      services.value = [
        {
          id: 1,
          serviceType: '垃圾收取',
          appointmentTime: '2026-03-08 10:00',
          status: '已完成',
          createdAt: '2026-03-07 18:00:00'
        },
        {
          id: 2,
          serviceType: '核酸检测',
          appointmentTime: '2026-03-09 09:00',
          status: '待处理',
          createdAt: '2026-03-08 08:00:00'
        }
      ]
      loading.value = false
    }, 1000)
  } catch (error) {
    ElMessage.error('获取服务列表失败，请重试')
    loading.value = false
  }
}

const updateServiceStatus = async (serviceId, status) => {
  loading.value = true
  try {
    // 模拟API调用
    setTimeout(() => {
      const service = services.value.find(item => item.id === serviceId)
      if (service) {
        service.status = status
      }
      loading.value = false
      ElMessage.success('状态更新成功')
    }, 1000)
  } catch (error) {
    ElMessage.error('状态更新失败，请重试')
    loading.value = false
  }
}

const getStatusType = (status) => {
  switch (status) {
    case '已完成': return 'success'
    case '处理中': return 'warning'
    case '待处理': return 'info'
    case '已取消': return 'danger'
    default: return 'info'
  }
}

onMounted(() => {
  getServices()
})
</script>

<template>
  <div class="quarantine-service-container">
    <div class="page-header">
      <h2>隔离服务预约</h2>
      <el-button 
        type="primary" 
        icon="Service"
        circle
        size="large"
      />
    </div>
    
    <el-card class="book-service-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <el-icon><Service /></el-icon>
          <span>预约服务</span>
        </div>
      </template>
      <el-form :model="form" label-width="120px" class="book-form">
        <el-form-item label="服务类型" required>
          <div class="input-with-icon">
            <el-icon class="input-icon"><AlertCircle /></el-icon>
            <el-select 
              v-model="form.serviceType" 
              placeholder="请选择服务类型"
              class="service-select"
            >
              <el-option 
                v-for="type in serviceTypes" 
                :key="type.value" 
                :label="type.label" 
                :value="type.value" 
              />
            </el-select>
          </div>
        </el-form-item>
        <el-form-item label="预约时间" required>
          <div class="input-with-icon">
            <el-icon class="input-icon"><Calendar /></el-icon>
            <el-date-picker
              v-model="form.appointmentTime"
              type="datetime"
              placeholder="选择预约时间"
              format="YYYY-MM-DD HH:mm"
              value-format="YYYY-MM-DD HH:mm"
              class="time-picker"
            />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="bookService" 
            :loading="loading"
            class="book-button"
          >
            <el-icon><CheckCircle /></el-icon>
            提交预约
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="service-list-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <el-icon><Clock /></el-icon>
          <span>我的服务</span>
          <el-button 
            type="info" 
            size="small" 
            @click="getServices" 
            :loading="loading"
            icon="Refresh"
          >
            刷新
          </el-button>
        </div>
      </template>
      <el-table :data="services" style="width: 100%" :loading="loading" class="services-table">
        <el-table-column prop="serviceType" label="服务类型" width="180">
          <template #default="scope">
            <span class="service-type">{{ scope.row.serviceType }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="appointmentTime" label="预约时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-select 
              v-model="scope.row.status" 
              @change="updateServiceStatus(scope.row.id, scope.row.status)" 
              placeholder="更新状态"
              class="status-select"
            >
              <el-option label="待处理" value="待处理" />
              <el-option label="处理中" value="处理中" />
              <el-option label="已完成" value="已完成" />
              <el-option label="已取消" value="已取消" />
            </el-select>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<style scoped>
.quarantine-service-container {
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

.book-service-card {
  border-radius: 12px;
  transition: all 0.3s ease;
}

.book-service-card:hover {
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

.card-header .el-button {
  margin-left: auto;
}

.book-form {
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

.service-select {
  width: 300px;
}

.time-picker {
  width: 300px;
}

.book-button {
  font-size: 16px;
  padding: 10px 32px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.book-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.service-list-card {
  border-radius: 12px;
  transition: all 0.3s ease;
}

.service-list-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.services-table {
  margin-top: 16px;
}

.service-type {
  font-weight: 500;
  color: #333;
}

.status-select {
  width: 120px;
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
  
  .service-select,
  .time-picker {
    width: 100%;
  }
  
  .book-button {
    width: 100%;
  }
  
  .status-select {
    width: 100%;
  }
}
</style>