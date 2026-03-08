<script setup>
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import * as Icons from '@element-plus/icons-vue';
const IconPlaceholder = { template: '<span></span>' };
const Thermometer = Icons['Thermometer'] || IconPlaceholder;
const AlertCircle = Icons['AlertCircle'] || IconPlaceholder;
const CheckCircle = Icons['CheckCircle'] || IconPlaceholder;
const Clock = Icons['Clock'] || IconPlaceholder;

const loading = ref(false);
const successMessage = ref('');
const currentDate = ref(new Date().toLocaleDateString());

const form = reactive({
  temperature: '',
  symptoms: '',
});

const submitCheckin = async () => {
  if (!form.temperature) {
    ElMessage.warning('请输入体温');
    return;
  }

  const temp = parseFloat(form.temperature);
  if (temp < 35 || temp > 42) {
    ElMessage.warning('请输入有效的体温值');
    return;
  }

  loading.value = true;
  try {
    // 模拟API调用
    setTimeout(() => {
      successMessage.value = '打卡成功，状态：正常';
      form.temperature = '';
      form.symptoms = '';
      loading.value = false;
    }, 1000);
  } catch (error) {
    ElMessage.error('打卡失败，请重试');
    loading.value = false;
  }
};
</script>

<template>
  <div class="health-checkin-container">
    <div class="page-header">
      <h2>每日健康打卡</h2>
      <div class="date-info">
        <el-icon><Clock /></el-icon>
        <span>{{ currentDate }}</span>
      </div>
    </div>

    <el-card class="form-card" shadow="hover">
      <el-form :model="form" label-width="100px" class="checkin-form">
        <el-form-item label="体温" required>
          <div class="temperature-input">
            <el-icon class="input-icon"><Thermometer /></el-icon>
            <el-input
              v-model="form.temperature"
              placeholder="请输入体温（℃）"
              type="number"
              step="0.1"
              class="temp-input"
            />
            <span class="unit">℃</span>
          </div>
        </el-form-item>
        <el-form-item label="症状">
          <div class="symptoms-input">
            <el-icon class="input-icon"><AlertCircle /></el-icon>
            <el-input
              v-model="form.symptoms"
              placeholder="如有症状请描述，如：咳嗽、发热等"
              type="textarea"
              rows="3"
            />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" class="submit-button" @click="submitCheckin">
            <el-icon v-if="!loading"><CheckCircle /></el-icon>
            {{ loading ? '提交中...' : '提交打卡' }}
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-alert
      v-if="successMessage"
      :title="successMessage"
      type="success"
      show-icon
      class="success-alert"
    />

    <el-card class="tips-card" shadow="hover">
      <template #header>
        <div class="tips-header">
          <el-icon><AlertCircle /></el-icon>
          <span>打卡提醒</span>
        </div>
      </template>
      <ul class="tips-list">
        <li class="tip-item">
          <el-icon class="tip-icon"><Clock /></el-icon>
          <span>请每天早晨 9:00 前完成打卡</span>
        </li>
        <li class="tip-item">
          <el-icon class="tip-icon"><Thermometer /></el-icon>
          <span>体温超过 37.3℃ 请及时联系社区网格员</span>
        </li>
        <li class="tip-item">
          <el-icon class="tip-icon"><AlertCircle /></el-icon>
          <span>如有发热、咳嗽等症状，请如实填写</span>
        </li>
      </ul>
    </el-card>
  </div>
</template>

<style scoped>
.health-checkin-container {
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

.date-info {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #666;
}

.form-card {
  border-radius: 12px;
  transition: all 0.3s ease;
}

.form-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.checkin-form {
  padding: 16px 0;
}

.temperature-input {
  display: flex;
  align-items: center;
  gap: 12px;
}

.symptoms-input {
  display: flex;
  gap: 12px;
}

.input-icon {
  font-size: 20px;
  color: #1890ff;
  flex-shrink: 0;
  margin-top: 4px;
}

.temp-input {
  flex: 1;
  max-width: 200px;
}

.unit {
  font-size: 16px;
  color: #666;
  font-weight: 500;
}

.submit-button {
  font-size: 16px;
  padding: 10px 32px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.submit-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.success-alert {
  border-radius: 8px;
  margin: 16px 0;
}

.tips-card {
  border-radius: 12px;
  transition: all 0.3s ease;
}

.tips-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.tips-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.tips-list {
  margin: 16px 0 0 0;
  padding: 0;
  list-style: none;
}

.tip-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
  transition: all 0.3s ease;
}

.tip-item:hover {
  background-color: #f9f9f9;
  padding-left: 8px;
  border-radius: 4px;
}

.tip-item:last-child {
  border-bottom: none;
}

.tip-icon {
  font-size: 16px;
  color: #1890ff;
  flex-shrink: 0;
  margin-top: 2px;
}

.tip-item span {
  flex: 1;
  font-size: 14px;
  color: #666;
  line-height: 1.5;
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .temperature-input {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .temp-input {
    max-width: 100%;
    width: 100%;
  }

  .symptoms-input {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .input-icon {
    margin-top: 0;
  }
}
</style>
