import About from '../views/components/About';
import Account from '../views/components/Account';
import Dashboard from '../views/components/Dashboard';
import Login from '../views/components/Login';
import Option from '../views/components/Url';
import Releases from '../views/components/Releases';
import Workspace from '../views/components/Workspace';

export default [
  {
    icon: 'el-icon-odometer',
    name: '대시보드',
    path: '/',
    component: Dashboard,
  },
  {
    icon: 'el-icon-user',
    name: '계정',
    path: '/account',
    component: Account,
  },
  {
    icon: 'el-icon-setting',
    name: '로그인',
    path: '/login',
    component: Login,
  },
  {
    icon: 'el-icon-s-promotion',
    name: 'URL 입력',
    path: '/inject',
    component: Option,
  },
  {
    icon: 'el-icon-s-platform',
    name: '내 공간',
    path: '/workspace',
    component: Workspace,
  },
  {
    icon: 'el-icon-bell',
    name: '공지사항',
    path: '/releases',
    needBadge: true,
    component: Releases,
  },
  {
    icon: 'el-icon-message',
    name: '정보',
    path: '/about',
    component: About,
  },
];
