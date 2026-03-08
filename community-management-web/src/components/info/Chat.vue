<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';

const messages = ref([]);
const form = reactive({
  receiverId: 2, // 默认为网格员
  content: '',
});
const loading = ref(false);
const messagesContainer = ref(null);

const contacts = [
  {
    id: 2,
    name: '社区网格员',
    avatar:
      'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=professional%20community%20worker%20avatar&image_size=square',
  },
  {
    id: 3,
    name: '邻居小王',
    avatar:
      'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=friendly%20neighbor%20avatar&image_size=square',
  },
  {
    id: 4,
    name: '邻居小李',
    avatar:
      'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=young%20person%20avatar&image_size=square',
  },
];

const sendMessage = async () => {
  if (!form.content) {
    ElMessage.warning('请输入消息内容');
    return;
  }

  loading.value = true;
  try {
    // 模拟API调用
    // const response = await axios.post('/api/chat/messages', {
    //   userId: 1,
    //   receiverId: form.receiverId,
    //   content: form.content
    // })
    // messages.value.push(response.data.data)

    // 模拟成功响应
    setTimeout(() => {
      const newMessage = {
        id: Date.now(),
        senderId: 1,
        receiverId: form.receiverId,
        content: form.content,
        sendTime: new Date().toLocaleString(),
        readStatus: false,
      };
      messages.value.push(newMessage);
      form.content = '';
      loading.value = false;
      scrollToBottom();
    }, 500);
  } catch (error) {
    ElMessage.error('发送消息失败，请重试');
    loading.value = false;
  }
};

const getMessages = async () => {
  loading.value = true;
  try {
    // 模拟API调用
    // const response = await axios.get('/api/chat/messages', {
    //   params: {
    //     userId: 1,
    //     otherUserId: form.receiverId,
    //     page: 1,
    //     size: 50
    //   }
    // })
    // messages.value = response.data.data.records

    // 模拟成功响应
    setTimeout(() => {
      messages.value = [
        {
          id: 1,
          senderId: 2,
          receiverId: 1,
          content: '您好，请问有什么可以帮助您的？',
          sendTime: '2026-03-08 09:00:00',
          readStatus: true,
        },
        {
          id: 2,
          senderId: 1,
          receiverId: 2,
          content: '我想咨询一下核酸检测的时间安排',
          sendTime: '2026-03-08 09:05:00',
          readStatus: true,
        },
        {
          id: 3,
          senderId: 2,
          receiverId: 1,
          content: '核酸检测时间是明天上午8点到12点，在社区广场进行',
          sendTime: '2026-03-08 09:10:00',
          readStatus: false,
        },
      ];
      loading.value = false;
      scrollToBottom();
    }, 1000);
  } catch (error) {
    ElMessage.error('获取消息失败，请重试');
    loading.value = false;
  }
};

const handleContactChange = () => {
  getMessages();
};

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
    }
  });
};

onMounted(() => {
  getMessages();
});
</script>

<template>
  <div class="chat-container">
    <h2 class="page-title">在线沟通</h2>
    <el-row :gutter="24">
      <el-col :span="6">
        <el-card class="contacts-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <h3>联系人</h3>
            </div>
          </template>
          <div class="contacts-list">
            <div
              v-for="contact in contacts"
              :key="contact.id"
              class="contact-item"
              :class="form.receiverId === contact.id ? 'active' : ''"
              @click="
                form.receiverId = contact.id;
                handleContactChange();
              "
            >
              <div class="contact-avatar">
                <img :src="contact.avatar" :alt="contact.name" />
              </div>
              <div class="contact-info">
                <div class="contact-name">{{ contact.name }}</div>
                <div v-if="form.receiverId === contact.id" class="contact-status">
                  <span class="status-dot online"></span>
                  <span class="status-text">在线</span>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card class="chat-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="chat-header-info">
                <h3>{{ contacts.find((c) => c.id === form.receiverId)?.name }}</h3>
                <div class="status-indicator">
                  <span class="status-dot online"></span>
                  <span class="status-text">在线</span>
                </div>
              </div>
            </div>
          </template>
          <div ref="messagesContainer" class="chat-messages" :class="{ loading: loading }">
            <div
              v-for="message in messages"
              :key="message.id"
              class="message-item"
              :class="message.senderId === 1 ? 'sent' : 'received'"
            >
              <div v-if="message.senderId !== 1" class="message-avatar">
                <img
                  :src="contacts.find((c) => c.id === message.senderId)?.avatar"
                  :alt="contacts.find((c) => c.id === message.senderId)?.name"
                />
              </div>
              <div class="message-content-wrapper">
                <div class="message-content">
                  <p>{{ message.content }}</p>
                  <div class="message-meta">
                    <span class="message-time">{{ message.sendTime }}</span>
                    <span v-if="message.senderId === 1" class="message-status">
                      {{ message.readStatus ? '已读' : '未读' }}
                    </span>
                  </div>
                </div>
              </div>
              <div v-if="message.senderId === 1" class="message-avatar">
                <img
                  src="https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=user%20avatar&image_size=square"
                  alt="我"
                />
              </div>
            </div>
            <div v-if="messages.length === 0" class="empty-messages">
              <p>暂无消息，开始聊天吧！</p>
            </div>
          </div>
          <div class="chat-input">
            <el-input
              v-model="form.content"
              type="textarea"
              placeholder="输入消息..."
              rows="3"
              resize="none"
              class="message-textarea"
            />
            <el-button
              type="primary"
              :loading="loading"
              class="send-button"
              round
              @click="sendMessage"
            >
              发送
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.chat-container {
  width: 100%;
  height: 100%;
  min-height: 600px;
}

.page-title {
  margin-bottom: 24px;
  color: #333;
  font-size: 24px;
  font-weight: 600;
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

.contacts-card {
  height: 100%;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.contacts-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.contacts-list {
  margin-top: 16px;
}

.contact-item {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  border-radius: 8px;
  cursor: pointer;
  margin-bottom: 8px;
  transition: all 0.3s ease;
}

.contact-item:hover {
  background-color: rgba(24, 144, 255, 0.05);
  transform: translateX(4px);
}

.contact-item.active {
  background-color: rgba(24, 144, 255, 0.1);
  border-left: 4px solid #1890ff;
}

.contact-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 12px;
  flex-shrink: 0;
}

.contact-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.contact-info {
  flex: 1;
  min-width: 0;
}

.contact-name {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.contact-status {
  display: flex;
  align-items: center;
  font-size: 12px;
  color: #999;
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 4px;
}

.status-dot.online {
  background-color: #67c23a;
}

.status-text {
  font-size: 12px;
  color: #999;
}

.chat-card {
  height: 100%;
  display: flex;
  flex-direction: column;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.chat-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.chat-header-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.chat-header-info h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.status-indicator {
  display: flex;
  align-items: center;
  gap: 4px;
}

.chat-messages {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 20px;
  transition: all 0.3s ease;
}

.chat-messages.loading {
  opacity: 0.7;
}

.empty-messages {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  color: #999;
  font-size: 16px;
}

.message-item {
  display: flex;
  margin-bottom: 20px;
  animation: messageSlideIn 0.3s ease forwards;
}

@keyframes messageSlideIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.message-item.sent {
  flex-direction: row-reverse;
}

.message-item.received {
  flex-direction: row;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  margin: 0 12px;
  flex-shrink: 0;
}

.message-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.message-content-wrapper {
  max-width: 70%;
  display: flex;
  flex-direction: column;
}

.message-item.sent .message-content-wrapper {
  align-items: flex-end;
}

.message-item.received .message-content-wrapper {
  align-items: flex-start;
}

.message-content {
  padding: 12px 16px;
  border-radius: 12px;
  position: relative;
  max-width: 100%;
  word-wrap: break-word;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.message-item.sent .message-content {
  background: linear-gradient(135deg, #1890ff, #36cfc9);
  color: white;
  border-bottom-right-radius: 4px;
}

.message-item.received .message-content {
  background-color: #ffffff;
  color: #333;
  border: 1px solid #f0f0f0;
  border-bottom-left-radius: 4px;
}

.message-content p {
  margin: 0 0 8px 0;
  line-height: 1.4;
}

.message-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  opacity: 0.8;
}

.message-time {
  font-size: 12px;
}

.message-status {
  font-size: 12px;
}

.chat-input {
  display: flex;
  gap: 12px;
  padding: 0 24px 24px;
}

.message-textarea {
  flex: 1;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.message-textarea:hover {
  border-color: #1890ff;
}

.send-button {
  width: 80px;
  height: 40px;
  align-self: flex-end;
  transition: all 0.3s ease;
}

.send-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(24, 144, 255, 0.3);
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .chat-container {
    min-height: 500px;
  }

  .el-row {
    flex-direction: column;
  }

  .el-col {
    width: 100% !important;
    margin-bottom: 20px;
  }

  .contacts-card {
    height: auto;
  }

  .contacts-list {
    display: flex;
    overflow-x: auto;
    gap: 12px;
  }

  .contact-item {
    flex-direction: column;
    align-items: center;
    padding: 12px;
    min-width: 100px;
    text-align: center;
  }

  .contact-avatar {
    margin-right: 0;
    margin-bottom: 8px;
  }

  .contact-info {
    text-align: center;
  }

  .message-content-wrapper {
    max-width: 85%;
  }

  .chat-messages {
    padding: 16px;
  }

  .chat-input {
    padding: 0 16px 16px;
  }
}
</style>
