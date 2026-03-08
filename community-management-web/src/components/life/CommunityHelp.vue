<script setup>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElEmpty } from 'element-plus';
import * as Icons from '@element-plus/icons-vue';
const IconPlaceholder = { template: '<span></span>' };
const HelpFilled = Icons['HelpFilled'] || IconPlaceholder;
const Send = Icons['Send'] || IconPlaceholder;
const List = Icons['List'] || IconPlaceholder;
const User = Icons['User'] || IconPlaceholder;
const Phone = Icons['Phone'] || IconPlaceholder;
const Clock = Icons['Clock'] || IconPlaceholder;
const CheckCircle = Icons['CheckCircle'] || IconPlaceholder;
const AlertCircle = Icons['AlertCircle'] || IconPlaceholder;
const Edit = Icons['Edit'] || IconPlaceholder;

const helps = ref([]);
const form = reactive({
  type: '',
  content: '',
  contactInfo: '',
});
const loading = ref(false);
const activeTab = ref('list');

const helpTypes = [
  { label: '物资交换', value: '物资交换' },
  { label: '志愿者招募', value: '志愿者招募' },
  { label: '寻求帮助', value: '寻求帮助' },
  { label: '其他', value: '其他' },
];

const publishHelp = async () => {
  if (!form.type || !form.content || !form.contactInfo) {
    ElMessage.warning('请填写完整信息');
    return;
  }

  loading.value = true;
  try {
    // 模拟API调用
    setTimeout(() => {
      const newHelp = {
        id: Date.now(),
        userId: 1,
        type: form.type,
        content: form.content,
        contactInfo: form.contactInfo,
        status: '待解决',
        createdAt: new Date().toLocaleString(),
      };
      helps.value.unshift(newHelp);
      form.type = '';
      form.content = '';
      form.contactInfo = '';
      loading.value = false;
      ElMessage.success('发布成功');
    }, 1000);
  } catch (error) {
    ElMessage.error('发布失败，请重试');
    loading.value = false;
  }
};

const getHelpList = async () => {
  loading.value = true;
  try {
    // 模拟API调用
    setTimeout(() => {
      helps.value = [
        {
          id: 1,
          userId: 2,
          type: '物资交换',
          content: '我有多余的口罩，需要的邻居可以联系我',
          contactInfo: '13800138001',
          status: '待解决',
          createdAt: '2026-03-08 09:00:00',
        },
        {
          id: 2,
          userId: 3,
          type: '志愿者招募',
          content: '社区需要志愿者协助物资配送，有意者请联系',
          contactInfo: '13800138002',
          status: '待解决',
          createdAt: '2026-03-07 18:00:00',
        },
      ];
      loading.value = false;
    }, 1000);
  } catch (error) {
    ElMessage.error('获取互助信息失败，请重试');
    loading.value = false;
  }
};

const updateHelpStatus = async (helpId, status) => {
  loading.value = true;
  try {
    // 模拟API调用
    setTimeout(() => {
      const help = helps.value.find((item) => item.id === helpId);
      if (help) {
        help.status = status;
      }
      loading.value = false;
      ElMessage.success('状态更新成功');
    }, 1000);
  } catch (error) {
    ElMessage.error('状态更新失败，请重试');
    loading.value = false;
  }
};

const getStatusType = (status) => {
  switch (status) {
    case '已解决':
      return 'success';
    case '待解决':
      return 'warning';
    case '已关闭':
      return 'info';
    default:
      return 'info';
  }
};

onMounted(() => {
  getHelpList();
});
</script>

<template>
  <div class="community-help-container">
    <div class="page-header">
      <h2>社区互助大厅</h2>
      <el-button type="primary" icon="HelpFilled" circle size="large" />
    </div>

    <el-tabs v-model="activeTab" class="tabs-container">
      <el-tab-pane label="发布互助" name="publish">
        <el-card class="publish-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon><Send /></el-icon>
              <span>发布互助信息</span>
            </div>
          </template>
          <el-form :model="form" label-width="120px" class="publish-form">
            <el-form-item label="互助类型" required>
              <div class="input-with-icon">
                <el-icon class="input-icon"><HelpFilled /></el-icon>
                <el-select v-model="form.type" placeholder="请选择互助类型" class="type-select">
                  <el-option
                    v-for="type in helpTypes"
                    :key="type.value"
                    :label="type.label"
                    :value="type.value"
                  />
                </el-select>
              </div>
            </el-form-item>
            <el-form-item label="互助内容" required>
              <div class="input-with-icon">
                <el-icon class="input-icon"><AlertCircle /></el-icon>
                <el-input
                  v-model="form.content"
                  type="textarea"
                  placeholder="请描述您的互助需求或提供的帮助"
                  rows="4"
                  class="content-input"
                />
              </div>
            </el-form-item>
            <el-form-item label="联系方式" required>
              <div class="input-with-icon">
                <el-icon class="input-icon"><Phone /></el-icon>
                <el-input
                  v-model="form.contactInfo"
                  placeholder="请留下您的联系方式，如电话、微信等"
                  class="contact-input"
                />
              </div>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                :loading="loading"
                class="publish-button"
                @click="publishHelp"
              >
                <el-icon><Send /></el-icon>
                发布互助
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="互助列表" name="list">
        <div class="help-list" :loading="loading">
          <el-card v-for="help in helps" :key="help.id" class="help-card" shadow="hover">
            <div class="help-header">
              <div
                class="help-type-badge"
                :style="{
                  backgroundColor:
                    help.type === '物资交换'
                      ? '#67C23A'
                      : help.type === '志愿者招募'
                        ? '#1890ff'
                        : help.type === '寻求帮助'
                          ? '#E6A23C'
                          : '#909399',
                }"
              >
                {{ help.type }}
              </div>
              <el-tag :type="getStatusType(help.status)">
                {{ help.status }}
              </el-tag>
            </div>
            <div class="help-content">
              <p class="help-text">{{ help.content }}</p>
              <div class="help-meta">
                <div class="meta-item">
                  <el-icon><Phone /></el-icon>
                  <span>{{ help.contactInfo }}</span>
                </div>
                <div class="meta-item">
                  <el-icon><Clock /></el-icon>
                  <span>{{ help.createdAt }}</span>
                </div>
              </div>
              <div class="help-actions">
                <el-select
                  v-model="help.status"
                  placeholder="更新状态"
                  class="status-select"
                  @change="updateHelpStatus(help.id, help.status)"
                >
                  <el-option label="待解决" value="待解决" />
                  <el-option label="已解决" value="已解决" />
                  <el-option label="已关闭" value="已关闭" />
                </el-select>
              </div>
            </div>
          </el-card>

          <div v-if="helps.length === 0 && !loading" class="empty-help">
            <el-empty description="暂无互助信息" />
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped>
.community-help-container {
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

.tabs-container {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.publish-card {
  border-radius: 12px;
  transition: all 0.3s ease;
}

.publish-card:hover {
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

.publish-form {
  padding: 16px 0;
}

.input-with-icon {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.input-icon {
  font-size: 20px;
  color: #1890ff;
  flex-shrink: 0;
  margin-top: 4px;
}

.type-select,
.contact-input {
  width: 300px;
}

.content-input {
  flex: 1;
  min-width: 300px;
}

.publish-button {
  font-size: 16px;
  padding: 10px 32px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.publish-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.help-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 24px;
}

.help-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  overflow: hidden;
}

.help-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.help-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.help-type-badge {
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 600;
  color: white;
}

.help-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.help-text {
  margin: 0;
  font-size: 16px;
  line-height: 1.6;
  color: #333;
}

.help-meta {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding: 12px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #666;
}

.help-actions {
  display: flex;
  justify-content: flex-end;
  padding-top: 12px;
  border-top: 1px solid #f0f0f0;
}

.status-select {
  width: 120px;
}

.empty-help {
  padding: 80px 0;
  text-align: center;
  background-color: #f9f9f9;
  border-radius: 12px;
  margin-top: 24px;
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

  .type-select,
  .contact-input,
  .content-input {
    width: 100%;
    min-width: unset;
  }

  .publish-button {
    width: 100%;
  }

  .help-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .help-actions {
    justify-content: flex-start;
  }

  .status-select {
    width: 100%;
  }
}
</style>
