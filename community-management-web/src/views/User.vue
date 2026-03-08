<script setup>
import { ref, onMounted } from 'vue';
import * as Icons from '@element-plus/icons-vue';
const IconPlaceholder = { template: '<span></span>' };
const User = Icons['User'] || IconPlaceholder;
const UserFilled = Icons['UserFilled'] || IconPlaceholder;
const Phone = Icons['Phone'] || IconPlaceholder;
const Idcard = Icons['Idcard'] || IconPlaceholder;
const Home = Icons['Home'] || IconPlaceholder;
const OfficeBuilding = Icons['OfficeBuilding'] || IconPlaceholder;
const Apartment = Icons['Apartment'] || IconPlaceholder;
const Star = Icons['Star'] || IconPlaceholder;
import { ElMessage } from 'element-plus';

const userInfo = ref({
  id: 1,
  username: 'user123',
  name: '张三',
  phone: '13800138000',
  idCard: '110101199001011234',
  communityId: 1,
  building: '1栋',
  unit: '1单元',
  room: '101',
  role: '居民',
});

const familyMembers = ref([
  {
    id: 2,
    name: '李四',
    relationship: '配偶',
    idCard: '110101199002022345',
    phone: '13800138001',
  },
  {
    id: 3,
    name: '张小明',
    relationship: '子女',
    idCard: '110101201503033456',
    phone: '13800138002',
  },
]);

const activeTab = ref('info');

const saveChanges = () => {
  // 模拟保存操作
  ElMessage.success('信息保存成功');
};

const addFamilyMember = () => {
  // 模拟添加成员操作
  ElMessage.success('添加成员功能开发中');
};

const editFamilyMember = (member) => {
  // 模拟编辑成员操作
  ElMessage.success(`编辑成员: ${member.name}`);
};

const deleteFamilyMember = (member) => {
  // 模拟删除成员操作
  ElMessage.success(`删除成员: ${member.name}`);
};
</script>

<template>
  <div class="user-container">
    <div class="user-header">
      <div class="header-bg"></div>
      <div class="user-profile">
        <div class="avatar-container">
          <img
            src="https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=professional%20user%20avatar&image_size=square"
            alt="用户头像"
            class="user-avatar"
          />
        </div>
        <div class="user-info">
          <h2 class="user-name">{{ userInfo.name }}</h2>
          <p class="user-role">{{ userInfo.role }}</p>
          <p class="user-address">
            {{ userInfo.building }} {{ userInfo.unit }} {{ userInfo.room }}
          </p>
        </div>
      </div>
    </div>

    <el-tabs v-model="activeTab" class="user-tabs">
      <el-tab-pane label="个人信息" name="info">
        <el-card class="user-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <h3>基本信息</h3>
            </div>
          </template>
          <el-form :model="userInfo" label-width="120px" class="user-form">
            <el-form-item label="用户名">
              <el-input v-model="userInfo.username" disabled />
            </el-form-item>
            <el-form-item label="真实姓名">
              <el-input v-model="userInfo.name" />
            </el-form-item>
            <el-form-item label="手机号码">
              <el-input v-model="userInfo.phone" />
            </el-form-item>
            <el-form-item label="身份证号">
              <el-input v-model="userInfo.idCard" disabled />
            </el-form-item>
            <el-form-item label="所属社区">
              <el-input v-model="userInfo.communityId" disabled />
            </el-form-item>
            <el-form-item label="楼栋">
              <el-input v-model="userInfo.building" />
            </el-form-item>
            <el-form-item label="单元">
              <el-input v-model="userInfo.unit" />
            </el-form-item>
            <el-form-item label="房间号">
              <el-input v-model="userInfo.room" />
            </el-form-item>
            <el-form-item label="角色">
              <el-input v-model="userInfo.role" disabled />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" class="save-button" round @click="saveChanges">
                保存修改
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="家庭成员" name="family">
        <el-card class="user-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <h3>家庭成员管理</h3>
              <el-button type="primary" size="small" round @click="addFamilyMember">
                添加成员
              </el-button>
            </div>
          </template>
          <el-table :data="familyMembers" style="width: 100%" class="family-table">
            <el-table-column prop="name" label="姓名" width="120" />
            <el-table-column prop="relationship" label="关系" width="120" />
            <el-table-column prop="idCard" label="身份证号" width="200" />
            <el-table-column prop="phone" label="手机号码" width="150" />
            <el-table-column label="操作" width="180">
              <template #default="scope">
                <el-button
                  type="primary"
                  size="small"
                  class="table-button"
                  round
                  @click="editFamilyMember(scope.row)"
                >
                  编辑
                </el-button>
                <el-button
                  type="danger"
                  size="small"
                  class="table-button"
                  round
                  @click="deleteFamilyMember(scope.row)"
                >
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="我的订单" name="orders">
        <el-card class="user-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <h3>团购订单</h3>
            </div>
          </template>
          <div class="empty-state">
            <div class="empty-icon">
              <i class="el-icon-shopping-cart-2"></i>
            </div>
            <p class="empty-text">暂无订单</p>
            <el-button type="primary" round class="empty-button"> 去购物 </el-button>
          </div>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="我的服务" name="services">
        <el-card class="user-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <h3>隔离服务</h3>
            </div>
          </template>
          <div class="empty-state">
            <div class="empty-icon">
              <i class="el-icon-service"></i>
            </div>
            <p class="empty-text">暂无服务</p>
            <el-button type="primary" round class="empty-button"> 预约服务 </el-button>
          </div>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped>
.user-container {
  width: 100%;
  min-height: 600px;
}

.user-header {
  position: relative;
  margin-bottom: 32px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.header-bg {
  height: 160px;
  background: linear-gradient(135deg, #1890ff, #36cfc9);
}

.user-profile {
  position: absolute;
  bottom: -60px;
  left: 32px;
  display: flex;
  align-items: center;
  background-color: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  transition: all 0.3s ease;
}

.user-profile:hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
}

.avatar-container {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
  border: 4px solid white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-right: 20px;
}

.user-avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-info {
  flex: 1;
}

.user-name {
  margin: 0 0 8px 0;
  font-size: 24px;
  font-weight: 600;
  color: #333;
}

.user-role {
  margin: 0 0 4px 0;
  font-size: 14px;
  color: #1890ff;
  font-weight: 500;
}

.user-address {
  margin: 0;
  font-size: 14px;
  color: #666;
}

.user-tabs {
  background-color: transparent;
}

.user-tabs .el-tabs__header {
  margin-bottom: 24px;
}

.user-tabs .el-tabs__item {
  font-size: 16px;
  font-weight: 500;
  padding: 0 24px;
  height: 48px;
  line-height: 48px;
}

.user-tabs .el-tabs__active-bar {
  height: 3px;
  background-color: #1890ff;
}

.user-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  margin-bottom: 24px;
}

.user-card:hover {
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

.user-form {
  padding: 0 8px;
}

.user-form .el-form-item {
  margin-bottom: 20px;
}

.user-form .el-input {
  border-radius: 8px;
  transition: all 0.3s ease;
}

.user-form .el-input:hover {
  border-color: #1890ff;
}

.save-button {
  height: 40px;
  padding: 0 32px;
  font-size: 16px;
  transition: all 0.3s ease;
}

.save-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(24, 144, 255, 0.3);
}

.family-table {
  border-radius: 8px;
  overflow: hidden;
}

.family-table .el-table__header-wrapper th {
  background-color: #fafafa;
  font-weight: 600;
}

.family-table .el-table__row {
  transition: all 0.3s ease;
}

.family-table .el-table__row:hover {
  background-color: rgba(24, 144, 255, 0.05);
}

.table-button {
  margin-right: 8px;
  transition: all 0.3s ease;
}

.table-button:hover {
  transform: translateY(-1px);
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 0;
  text-align: center;
}

.empty-icon {
  font-size: 64px;
  color: #d9d9d9;
  margin-bottom: 16px;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}

.empty-text {
  font-size: 16px;
  color: #999;
  margin-bottom: 24px;
}

.empty-button {
  transition: all 0.3s ease;
}

.empty-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(24, 144, 255, 0.3);
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .user-profile {
    left: 16px;
    right: 16px;
    flex-direction: column;
    text-align: center;
    bottom: -100px;
  }

  .avatar-container {
    margin-right: 0;
    margin-bottom: 12px;
  }

  .user-name {
    font-size: 20px;
  }

  .user-form .el-form-item {
    margin-bottom: 16px;
  }

  .user-form .el-form-item__label {
    width: 100px;
  }

  .family-table {
    font-size: 14px;
  }

  .family-table .el-table__column {
    width: auto !important;
  }

  .table-button {
    margin-bottom: 8px;
  }

  .empty-state {
    padding: 40px 0;
  }

  .empty-icon {
    font-size: 48px;
  }
}
</style>
