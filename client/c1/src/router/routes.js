import About from '../views/components/About';
import Login from '../views/components/Login';
import MyFolder from '../views/components/MyFolder';
import RealFolder from '../views/components/RealFolder';
import Releases from '../views/components/Releases';
import Option from '../views/components/Url';
import Workspace from '../views/components/Workspace';

export default [
  {
    icon: 'el-icon-setting',
    name: '로그인',
    path: '/',
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
    icon: 'el-icon-s-platform',
    name: '나의 폴더',
    path: '/folders',
    component: RealFolder,
  },
  {
    icon: 'el-icon-s-platform',
    name: '테스트 폴더',
    path: '/tests',
    component: MyFolder,
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
