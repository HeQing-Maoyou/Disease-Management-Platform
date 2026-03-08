<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue';
import { ElMessage, ElEmpty } from 'element-plus';
import * as Icons from '@element-plus/icons-vue';
const IconPlaceholder = { template: '<span></span>' };
const Position = Icons['Position'] || IconPlaceholder;
const Search = Icons['Search'] || IconPlaceholder;
const Location = Icons['Location'] || IconPlaceholder;
const Map = Icons['Map'] || IconPlaceholder;
const Phone = Icons['Phone'] || IconPlaceholder;
const Clock = Icons['Clock'] || IconPlaceholder;
const Navigation = Icons['Navigation'] || IconPlaceholder;
const Refresh = Icons['Refresh'] || IconPlaceholder;
const AlertCircle = Icons['AlertCircle'] || IconPlaceholder;

const locations = ref([]);
const form = reactive({
  type: 'vaccine', // vaccine: 疫苗接种点, nucleic: 核酸采样点
  latitude: 39.9042,
  longitude: 116.4074,
});
const loading = ref(false);
const map = ref(null);
const markers = ref([]);
const geolocation = ref(null);

const locationTypes = [
  { label: '疫苗接种点', value: 'vaccine' },
  { label: '核酸采样点', value: 'nucleic' },
];

// 初始化地图
const initMap = () => {
  // 确保高德地图API已加载
  if (typeof AMap === 'undefined') {
    ElMessage.error('地图加载失败，请刷新页面重试');
    return;
  }

  // 创建地图实例
  map.value = new AMap.Map('map-container', {
    zoom: 15,
    center: [form.longitude, form.latitude],
    resizeEnable: true,
  });

  // 添加地图控件
  map.value.addControl(new AMap.Scale());
  map.value.addControl(new AMap.ToolBar());
  map.value.addControl(new AMap.MapType());

  // 调用定位方法
  getCurrentLocation();
};

// 获取当前位置
const getCurrentLocation = () => {
  // 加载定位插件
  AMap.plugin('AMap.Geolocation', function () {
    geolocation.value = new AMap.Geolocation({
      enableHighAccuracy: true, // 是否使用高精度定位
      timeout: 10000, // 超时时间
      maximumAge: 0, // 缓存时间
      convert: true, // 是否自动偏移
      showButton: true, // 显示定位按钮
      buttonPosition: 'RB', // 定位按钮位置
      buttonOffset: new AMap.Pixel(10, 20), // 定位按钮偏移量
      showMarker: true, // 显示定位点
      showCircle: true, // 显示定位精度圈
      panToLocation: true, // 定位后自动移动到定位点
      zoomToAccuracy: true, // 定位后自动调整缩放级别
    });

    // 添加定位控件
    map.value.addControl(geolocation.value);

    // 定位成功回调
    geolocation.value.on('complete', (data) => {
      // 更新表单中的经纬度
      form.longitude = data.position.lng;
      form.latitude = data.position.lat;
      // 重新获取地点信息
      getLocations();
    });

    // 定位失败回调
    geolocation.value.on('error', (data) => {
      ElMessage.warning('定位失败，使用默认位置');
    });

    // 开始定位
    geolocation.value.getCurrentPosition();
  });
};

// 标记地点
const markLocations = () => {
  // 清除之前的标记
  markers.value.forEach((marker) => {
    map.value.remove(marker);
  });
  markers.value = [];

  // 标记新的地点
  locations.value.forEach((location) => {
    // 创建标记
    const marker = new AMap.Marker({
      position: [location.longitude, location.latitude],
      title: location.name,
      map: map.value,
    });

    // 创建信息窗口
    const infoWindow = new AMap.InfoWindow({
      content: `
        <div style="padding: 16px; border-radius: 8px; background-color: white; box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);">
          <h3 style="margin: 0 0 12px 0; font-size: 18px; font-weight: 600; color: #333;">${location.name}</h3>
          <p style="margin: 8px 0; font-size: 14px; color: #666;">地址：${location.address}</p>
          <p style="margin: 8px 0; font-size: 14px; color: #666;">联系电话：${location.phone}</p>
          <p style="margin: 8px 0; font-size: 14px; color: #666;">营业时间：${location.hours}</p>
          <p style="margin: 8px 0; font-size: 14px; color: #666;">拥挤度：<span style="color: ${location.crowdLevel === '高' ? '#F56C6C' : location.crowdLevel === '中' ? '#E6A23C' : '#67C23A'}; font-weight: 600;">${location.crowdLevel}</span></p>
        </div>
      `,
      offset: new AMap.Pixel(0, -30),
    });

    // 点击标记显示信息窗口
    marker.on('click', () => {
      infoWindow.open(map.value, marker.getPosition());
    });

    // 保存标记
    markers.value.push(marker);
  });
};

// 导航到地点
const navigateToLocation = (location) => {
  // 打开高德地图导航
  const url = `https://uri.amap.com/marker?position=${location.longitude},${location.latitude}&name=${encodeURIComponent(location.name)}&src=webapp&coordinate=gaode`;
  window.open(url, '_blank');
};

const getLocations = async () => {
  loading.value = true;
  try {
    // 模拟API调用
    setTimeout(() => {
      if (form.type === 'vaccine') {
        locations.value = [
          {
            id: 1,
            name: '社区卫生服务中心接种点',
            address: '社区中心路123号',
            latitude: form.latitude + 0.01,
            longitude: form.longitude + 0.01,
            crowdLevel: '低',
            phone: '12345678901',
            hours: '09:00-17:00',
          },
          {
            id: 2,
            name: '区人民医院接种点',
            address: '健康路456号',
            latitude: form.latitude - 0.02,
            longitude: form.longitude + 0.03,
            crowdLevel: '中',
            phone: '12345678902',
            hours: '08:30-18:00',
          },
        ];
      } else {
        locations.value = [
          {
            id: 3,
            name: '社区广场采样点',
            address: '社区广场',
            latitude: form.latitude + 0.015,
            longitude: form.longitude - 0.01,
            crowdLevel: '低',
            phone: '12345678903',
            hours: '08:00-20:00',
          },
          {
            id: 4,
            name: '超市门口采样点',
            address: '便民超市门口',
            latitude: form.latitude - 0.01,
            longitude: form.longitude - 0.02,
            crowdLevel: '高',
            phone: '12345678904',
            hours: '09:00-19:00',
          },
        ];
      }
      loading.value = false;
      // 标记地点
      markLocations();
    }, 1000);
  } catch (error) {
    ElMessage.error('获取地点信息失败，请重试');
    loading.value = false;
  }
};

const getCrowdLevelColor = (level) => {
  switch (level) {
    case '高':
      return '#F56C6C';
    case '中':
      return '#E6A23C';
    case '低':
      return '#67C23A';
    default:
      return '#67C23A';
  }
};

onMounted(() => {
  // 初始化地图
  initMap();
  // 获取地点信息
  getLocations();
});

onUnmounted(() => {
  // 清理地图资源
  if (map.value) {
    map.value.destroy();
  }
  if (geolocation.value) {
    geolocation.value.destroy();
  }
});
</script>

<template>
  <div class="epidemic-map-container">
    <div class="page-header">
      <h2>便民防疫地图</h2>
      <el-button type="primary" icon="Map" circle size="large" />
    </div>

    <el-card class="search-card" shadow="hover">
      <el-form :model="form" label-width="120px" class="search-form">
        <el-form-item label="地点类型" required>
          <div class="input-with-icon">
            <el-icon class="input-icon"><Position /></el-icon>
            <el-select v-model="form.type" placeholder="请选择地点类型" class="type-select">
              <el-option
                v-for="type in locationTypes"
                :key="type.value"
                :label="type.label"
                :value="type.value"
              />
            </el-select>
          </div>
        </el-form-item>
        <el-form-item label="纬度">
          <div class="input-with-icon">
            <el-icon class="input-icon"><Location /></el-icon>
            <el-input v-model="form.latitude" type="number" step="0.0001" class="lat-input" />
          </div>
        </el-form-item>
        <el-form-item label="经度">
          <div class="input-with-icon">
            <el-icon class="input-icon"><Location /></el-icon>
            <el-input v-model="form.longitude" type="number" step="0.0001" class="lng-input" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" class="search-button" @click="getLocations">
            <el-icon><Search /></el-icon>
            查询地点
          </el-button>
          <el-button type="info" class="location-button" @click="getCurrentLocation">
            <el-icon><Refresh /></el-icon>
            获取当前位置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <div id="map-container" class="map-container"></div>

    <div class="locations-list" :loading="loading">
      <el-card
        v-for="location in locations"
        :key="location.id"
        class="location-card"
        shadow="hover"
      >
        <div class="location-header">
          <h3 class="location-name">{{ location.name }}</h3>
          <el-tag
            :type="
              location.crowdLevel === '高'
                ? 'danger'
                : location.crowdLevel === '中'
                  ? 'warning'
                  : 'success'
            "
            class="crowd-tag"
          >
            <el-icon><AlertCircle /></el-icon>
            {{ location.crowdLevel }}拥挤
          </el-tag>
        </div>
        <div class="location-content">
          <div class="location-info">
            <div class="info-item">
              <el-icon><Position /></el-icon>
              <span>{{ location.address }}</span>
            </div>
            <div class="info-item">
              <el-icon><Phone /></el-icon>
              <span>{{ location.phone }}</span>
            </div>
            <div class="info-item">
              <el-icon><Clock /></el-icon>
              <span>{{ location.hours }}</span>
            </div>
          </div>
          <el-button type="primary" class="navigate-button" @click="navigateToLocation(location)">
            <el-icon><Navigation /></el-icon>
            导航
          </el-button>
        </div>
      </el-card>

      <div v-if="locations.length === 0 && !loading" class="empty-location">
        <el-empty description="暂无地点信息" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.epidemic-map-container {
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

.type-select,
.lat-input,
.lng-input {
  width: 200px;
}

.search-button,
.location-button {
  font-size: 16px;
  padding: 10px 24px;
  border-radius: 8px;
  transition: all 0.3s ease;
  margin-right: 12px;
}

.search-button:hover,
.location-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.map-container {
  height: 500px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.map-container:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.locations-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 24px;
}

.location-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  overflow: hidden;
}

.location-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.location-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.location-name {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.crowd-tag {
  flex-shrink: 0;
}

.location-content {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 24px;
}

.location-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 14px;
  color: #666;
}

.navigate-button {
  border-radius: 8px;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.navigate-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.empty-location {
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
  .lat-input,
  .lng-input {
    width: 100%;
  }

  .search-button,
  .location-button {
    width: 100%;
    margin-right: 0;
    margin-bottom: 8px;
  }

  .map-container {
    height: 400px;
  }

  .location-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .navigate-button {
    width: 100%;
  }
}
</style>
