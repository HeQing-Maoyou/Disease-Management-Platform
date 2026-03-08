<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElEmpty } from 'element-plus'
import { ShoppingCart, Search, Plus, Minus, Trash, Checkout, Order, GoodsFilled } from '@element-plus/icons-vue'

const products = ref([])
const cart = ref([])
const orders = ref([])
const activeTab = ref('products')
const loading = ref(false)
const form = reactive({
  category: '',
  page: 1,
  size: 10
})

const getProducts = async () => {
  loading.value = true
  try {
    // 模拟API调用
    setTimeout(() => {
      products.value = [
        {
          id: 1,
          name: '新鲜蔬菜包',
          description: '包含土豆、白菜、萝卜等新鲜蔬菜',
          price: 39.9,
          stock: 100,
          category: '蔬菜'
        },
        {
          id: 2,
          name: '水果套餐',
          description: '包含苹果、梨、橙子等新鲜水果',
          price: 59.9,
          stock: 50,
          category: '水果'
        },
        {
          id: 3,
          name: '肉类套餐',
          description: '包含猪肉、鸡肉等肉类',
          price: 89.9,
          stock: 30,
          category: '肉类'
        },
        {
          id: 4,
          name: '生活用品包',
          description: '包含纸巾、牙膏、洗发水等生活用品',
          price: 69.9,
          stock: 80,
          category: '生活用品'
        }
      ]
      loading.value = false
    }, 1000)
  } catch (error) {
    ElMessage.error('获取商品列表失败，请重试')
    loading.value = false
  }
}

const addToCart = (product) => {
  const existingItem = cart.value.find(item => item.id === product.id)
  if (existingItem) {
    existingItem.quantity += 1
  } else {
    cart.value.push({
      id: product.id,
      name: product.name,
      price: product.price,
      quantity: 1
    })
  }
  ElMessage.success('已添加到购物车')
}

const removeFromCart = (index) => {
  cart.value.splice(index, 1)
  ElMessage.success('已从购物车移除')
}

const updateQuantity = (item, change) => {
  if (item.quantity + change < 1) return
  item.quantity += change
}

const createOrder = async () => {
  if (cart.value.length === 0) {
    ElMessage.warning('购物车为空，请先添加商品')
    return
  }
  
  loading.value = true
  try {
    // 模拟API调用
    setTimeout(() => {
      const newOrder = {
        id: Date.now(),
        orderNo: 'ORD' + Date.now(),
        totalAmount: cart.value.reduce((sum, item) => sum + item.price * item.quantity, 0),
        status: '待支付',
        createdAt: new Date().toLocaleString()
      }
      orders.value.push(newOrder)
      cart.value = []
      loading.value = false
      ElMessage.success('订单创建成功')
    }, 1000)
  } catch (error) {
    ElMessage.error('创建订单失败，请重试')
    loading.value = false
  }
}

const getOrders = async () => {
  loading.value = true
  try {
    // 模拟API调用
    setTimeout(() => {
      orders.value = [
        {
          id: 1,
          orderNo: 'ORD1234567890',
          totalAmount: 99.8,
          status: '已完成',
          createdAt: '2026-03-07 10:00:00'
        },
        {
          id: 2,
          orderNo: 'ORD1234567891',
          totalAmount: 59.9,
          status: '待配送',
          createdAt: '2026-03-08 09:00:00'
        }
      ]
      loading.value = false
    }, 1000)
  } catch (error) {
    ElMessage.error('获取订单列表失败，请重试')
    loading.value = false
  }
}

onMounted(() => {
  getProducts()
  getOrders()
})
</script>

<template>
  <div class="group-purchase-container">
    <div class="page-header">
      <h2>团购商城</h2>
      <el-button 
        type="primary" 
        icon="ShoppingCart"
        circle
        size="large"
      />
    </div>
    
    <el-tabs v-model="activeTab" class="tabs-container">
      <el-tab-pane label="商品列表" name="products">
        <el-card class="search-card" shadow="hover">
          <el-form :model="form" label-width="100px" class="search-form">
            <el-form-item label="分类">
              <el-select v-model="form.category" placeholder="请选择分类" class="category-select">
                <el-option label="蔬菜" value="蔬菜" />
                <el-option label="水果" value="水果" />
                <el-option label="肉类" value="肉类" />
                <el-option label="生活用品" value="生活用品" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button 
                type="primary" 
                @click="getProducts" 
                :loading="loading"
                class="search-button"
              >
                <el-icon><Search /></el-icon>
                查询
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
        
        <div class="products-grid" :loading="loading">
          <el-card 
            v-for="product in products" 
            :key="product.id" 
            class="product-card" 
            shadow="hover"
          >
            <div class="product-image">
              <el-icon class="product-icon"><GoodsFilled /></el-icon>
            </div>
            <div class="product-info">
              <h3 class="product-name">{{ product.name }}</h3>
              <p class="product-description">{{ product.description }}</p>
              <div class="product-meta">
                <span class="product-price">¥{{ product.price }}</span>
                <span class="product-stock">库存：{{ product.stock }}</span>
              </div>
              <el-button 
                type="primary" 
                @click="addToCart(product)"
                class="add-to-cart-button"
              >
                <el-icon><ShoppingCart /></el-icon>
                加入购物车
              </el-button>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      
      <el-tab-pane label="购物车" name="cart">
        <div v-if="cart.length === 0" class="empty-cart">
          <el-empty description="购物车为空" />
        </div>
        <div v-else class="cart-container">
          <el-card 
            v-for="(item, index) in cart" 
            :key="item.id" 
            class="cart-item" 
            shadow="hover"
          >
            <div class="cart-item-content">
              <div class="cart-item-info">
                <h4 class="cart-item-name">{{ item.name }}</h4>
                <span class="cart-item-price">¥{{ item.price }}</span>
              </div>
              <div class="cart-item-actions">
                <div class="quantity-control">
                  <el-button 
                    type="default" 
                    size="small" 
                    @click="updateQuantity(item, -1)"
                    circle
                  >
                    <el-icon><Minus /></el-icon>
                  </el-button>
                  <span class="quantity">{{ item.quantity }}</span>
                  <el-button 
                    type="default" 
                    size="small" 
                    @click="updateQuantity(item, 1)"
                    circle
                  >
                    <el-icon><Plus /></el-icon>
                  </el-button>
                </div>
                <el-button 
                  type="danger" 
                  size="small" 
                  @click="removeFromCart(index)"
                  class="remove-button"
                >
                  <el-icon><Trash /></el-icon>
                  移除
                </el-button>
              </div>
            </div>
          </el-card>
          
          <el-card class="cart-total-card" shadow="hover">
            <div class="cart-total">
              <div class="total-info">
                <span class="total-label">总计：</span>
                <span class="total-amount">¥{{ cart.reduce((sum, item) => sum + item.price * item.quantity, 0) }}</span>
              </div>
              <el-button 
                type="primary" 
                @click="createOrder" 
                :loading="loading"
                class="checkout-button"
              >
                <el-icon><Checkout /></el-icon>
                提交订单
              </el-button>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      
      <el-tab-pane label="我的订单" name="orders">
        <el-card class="orders-card" shadow="hover">
          <template #header>
            <div class="orders-header">
              <el-icon><Order /></el-icon>
              <span>订单列表</span>
            </div>
          </template>
          <el-table :data="orders" style="width: 100%" :loading="loading" class="orders-table">
            <el-table-column prop="orderNo" label="订单号" width="180" />
            <el-table-column prop="totalAmount" label="总金额" width="100">
              <template #default="scope">
                <span class="order-amount">¥{{ scope.row.totalAmount }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag 
                  :type="scope.row.status === '已完成' ? 'success' : scope.row.status === '待配送' ? 'warning' : 'info'"
                >
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createdAt" label="创建时间" width="180" />
            <el-table-column label="操作" width="120">
              <template #default="scope">
                <el-button type="primary" size="small" class="view-button">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped>
.group-purchase-container {
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

.search-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  margin-bottom: 24px;
}

.search-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.search-form {
  padding: 16px 0;
}

.category-select {
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

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
  margin-top: 24px;
}

.product-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.product-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.product-image {
  height: 160px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  margin: -20px -20px 20px;
}

.product-icon {
  font-size: 64px;
  color: #1890ff;
  opacity: 0.7;
}

.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.product-name {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.product-description {
  margin: 0;
  font-size: 14px;
  color: #666;
  line-height: 1.5;
  flex: 1;
}

.product-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 8px;
}

.product-price {
  font-size: 20px;
  font-weight: bold;
  color: #F56C6C;
}

.product-stock {
  font-size: 14px;
  color: #999;
}

.add-to-cart-button {
  width: 100%;
  margin-top: 12px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.add-to-cart-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.empty-cart {
  padding: 80px 0;
  text-align: center;
  background-color: #f9f9f9;
  border-radius: 12px;
  margin-top: 24px;
}

.cart-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-top: 24px;
}

.cart-item {
  border-radius: 12px;
  transition: all 0.3s ease;
}

.cart-item:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.cart-item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.cart-item-info {
  flex: 1;
}

.cart-item-name {
  margin: 0 0 8px 0;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.cart-item-price {
  font-size: 18px;
  font-weight: bold;
  color: #F56C6C;
}

.cart-item-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 8px;
  background-color: #f9f9f9;
  padding: 4px;
  border-radius: 6px;
}

.quantity {
  min-width: 40px;
  text-align: center;
  font-size: 14px;
  font-weight: 500;
}

.remove-button {
  border-radius: 6px;
  transition: all 0.3s ease;
}

.remove-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(245, 108, 108, 0.3);
}

.cart-total-card {
  margin-top: 16px;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.cart-total-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.cart-total {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
}

.total-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.total-label {
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.total-amount {
  font-size: 24px;
  font-weight: bold;
  color: #F56C6C;
}

.checkout-button {
  font-size: 16px;
  padding: 10px 32px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.checkout-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.orders-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  margin-top: 24px;
}

.orders-card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.orders-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.orders-table {
  margin-top: 16px;
}

.order-amount {
  font-weight: bold;
  color: #F56C6C;
}

.view-button {
  border-radius: 6px;
  transition: all 0.3s ease;
}

.view-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .products-grid {
    grid-template-columns: 1fr;
  }
  
  .cart-item-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
  
  .cart-item-actions {
    width: 100%;
    justify-content: space-between;
  }
  
  .cart-total {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
  
  .checkout-button {
    width: 100%;
  }
}
</style>