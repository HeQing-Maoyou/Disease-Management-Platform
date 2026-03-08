import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
  },
  {
    path: '/health',
    name: 'Health',
    component: () => import('../views/Health.vue'),
    children: [
      {
        path: 'checkin',
        name: 'HealthCheckin',
        component: () => import('../components/health/HealthCheckin.vue'),
      },
      {
        path: 'code',
        name: 'HealthCode',
        component: () => import('../components/health/HealthCode.vue'),
      },
      {
        path: 'risk',
        name: 'RiskWarning',
        component: () => import('../components/health/RiskWarning.vue'),
      },
    ],
  },
  {
    path: '/life',
    name: 'Life',
    component: () => import('../views/Life.vue'),
    children: [
      {
        path: 'group-purchase',
        name: 'GroupPurchase',
        component: () => import('../components/life/GroupPurchase.vue'),
      },
      {
        path: 'quarantine',
        name: 'QuarantineService',
        component: () => import('../components/life/QuarantineService.vue'),
      },
      {
        path: 'help',
        name: 'CommunityHelp',
        component: () => import('../components/life/CommunityHelp.vue'),
      },
    ],
  },
  {
    path: '/info',
    name: 'Info',
    component: () => import('../views/Info.vue'),
    children: [
      {
        path: 'notice',
        name: 'Notice',
        component: () => import('../components/info/Notice.vue'),
      },
      {
        path: 'map',
        name: 'EpidemicMap',
        component: () => import('../components/info/EpidemicMap.vue'),
      },
      {
        path: 'chat',
        name: 'Chat',
        component: () => import('../components/info/Chat.vue'),
      },
    ],
  },
  {
    path: '/user',
    name: 'User',
    component: () => import('../views/User.vue'),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
