<script setup>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElEmpty } from 'element-plus';
import * as Icons from '@element-plus/icons-vue';
const IconPlaceholder = { template: '<span></span>' };
const Message = Icons['Message'] || IconPlaceholder;
const Search = Icons['Search'] || IconPlaceholder;
const Clock = Icons['Clock'] || IconPlaceholder;
const HomeFilled = Icons['HomeFilled'] || IconPlaceholder;
const Building = Icons['Building'] || IconPlaceholder;
const Apartment = Icons['Apartment'] || IconPlaceholder;
const CheckCircle = Icons['CheckCircle'] || IconPlaceholder;
const AlertFilled = Icons['AlertFilled'] || IconPlaceholder;

const notices = ref([]);
const form = reactive({
  communityId: 1,
  building: '1栋',
  unit: '1单元',
});
const loading = ref(false);

const getNotices = async () => {
  loading.value = true;
  try {
    // 模拟API调用
    setTimeout(() => {
      notices.value = [
        {
          id: 1,
          title: '关于开展新一轮核酸检测的通知',
          content: '根据社区疫情防控要求，将于3月9日开展新一轮核酸检测，请各位居民按时参加。',
          communityId: 1,
          building: '',
          unit: '',
          isUrgent: true,
          createdAt: '2026-03-08 10:00:00',
        },
        {
          id: 2,
          title: '社区疫情防控措施调整通知',
          content: '根据最新疫情形势，社区防控措施进行调整，具体内容请查看附件。',
          communityId: 1,
          building: '',
          unit: '',
          isUrgent: false,
          createdAt: '2026-03-07 15:00:00',
        },
        {
          id: 3,
          title: '1栋居民生活物资配送安排',
          content: '1栋居民的生活物资将于3月8日下午2点开始配送，请各位居民注意查收。',
          communityId: 1,
          building: '1栋',
          unit: '',
          isUrgent: false,
          createdAt: '2026-03-06 09:00:00',
        },
      ];
      loading.value = false;
    }, 1000);
  } catch (error) {
    ElMessage.error('获取通知失败，请重试');
    loading.value = false;
  }
};

const markAsRead = async (noticeId) => {
  try {
    // 模拟API调用
    ElMessage.success('标记已读成功');
  } catch (error) {
    ElMessage.error('标记失败，请重试');
  }
};

onMounted(() => {
  getNotices();
});
</script>

<template>
  <div class="notice-container">
    <div class="page-header">
      <h2>通知公告</h2>
      <el-button type="primary" icon="Message" circle size="large" />
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
              class="community-input"
            />
          </div>
        </el-form-item>
        <el-form-item label="楼栋">
          <div class="input-with-icon">
            <el-icon class="input-icon"><Building /></el-icon>
            <el-input v-model="form.building" placeholder="请输入楼栋号" class="building-input" />
          </div>
        </el-form-item>
        <el-form-item label="单元">
          <div class="input-with-icon">
            <el-icon class="input-icon"><Apartment /></el-icon>
            <el-input v-model="form.unit" placeholder="请输入单元号" class="unit-input" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" class="search-button" @click="getNotices">
            <el-icon><Search /></el-icon>
            查询通知
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <div class="notices-list" :loading="loading">
      <el-card v-for="notice in notices" :key="notice.id" class="notice-card" shadow="hover">
        <div class="notice-header">
          <div class="notice-title-container">
            <el-icon v-if="notice.isUrgent" class="urgent-icon"><AlertFilled /></el-icon>
            <h3 class="notice-title">{{ notice.title }}</h3>
            <el-tag v-if="notice.isUrgent" type="danger" class="urgent-tag">紧急</el-tag>
          </div>
          <div class="notice-time">
            <el-icon><Clock /></el-icon>
            <span>{{ notice.createdAt }}</span>
          </div>
        </div>
        <div class="notice-content">
          <p class="notice-text">{{ notice.content }}</p>
          <div class="notice-footer">
            <div class="notice-range">
              <el-icon><HomeFilled /></el-icon>
              <span
                >{{ notice.building ? notice.building : '全社区'
                }}{{ notice.unit ? notice.unit : '' }}</span
              >
            </div>
            <el-button
              type="primary"
              size="small"
              class="read-button"
              @click="markAsRead(notice.id)"
            >
              <el-icon><CheckCircle /></el-icon>
              标记已读
            </el-button>
          </div>
        </div>
      </el-card>

      <div v-if="notices.length === 0 && !loading" class="empty-notice">
        <el-empty description="暂无通知" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.notice-container {
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

.community-input,
.building-input,
.unit-input {
  width: 200px;
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

.notices-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 24px;
}

.notice-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  overflow: hidden;
}

.notice-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.notice-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.notice-title-container {
  display: flex;
  align-items: center;
  gap: 12px;
  flex: 1;
}

.urgent-icon {
  font-size: 20px;
  color: #f56c6c;
  flex-shrink: 0;
}

.notice-title {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
  flex: 1;
}

.urgent-tag {
  flex-shrink: 0;
}

.notice-time {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #999;
  flex-shrink: 0;
}

.notice-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.notice-text {
  margin: 0;
  font-size: 16px;
  line-height: 1.6;
  color: #333;
}

.notice-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 12px;
  border-top: 1px solid #f0f0f0;
}

.notice-range {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #666;
}

.read-button {
  border-radius: 6px;
  transition: all 0.3s ease;
}

.read-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.empty-notice {
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

  .community-input,
  .building-input,
  .unit-input {
    width: 100%;
  }

  .search-button {
    width: 100%;
  }

  .notice-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .notice-footer {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .read-button {
    width: 100%;
  }
}
</style>
