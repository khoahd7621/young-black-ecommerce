import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import 'tw-elements';

import './Admin.scss';
import { Navbar } from './components';

import SideBar from './components/SideBar/SideBar';

const Admin = () => {
  const [collapsed, setCollapsed] = useState(false);

  return (
    <div className="admin-container">
      <div className="w-full h-screen flex ">
        <div className="side-bar">
          <SideBar collapsed={collapsed} />
        </div>
        <div className="content flex-1">
          <div className="header">
            <Navbar collapsed={collapsed} setCollapsed={setCollapsed} />
          </div>
          <div className="content">
            <Outlet />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Admin;