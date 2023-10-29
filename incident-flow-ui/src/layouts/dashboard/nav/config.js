import React from 'react';
import SettingsIcon from '@mui/icons-material/Settings';
import NotificationsIcon from '@mui/icons-material/Notifications';

const navConfig = [
  {
    title: 'Oncall Tracker',
    path: '/app/dashboard/tracker',
    icon: <NotificationsIcon />,
  },
  {
    title: 'Settings',
    path: '/app/dashboard/settings',
    icon: <SettingsIcon />,
  },
];

export default navConfig;
