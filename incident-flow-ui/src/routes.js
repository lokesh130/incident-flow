import { Navigate, useRoutes } from 'react-router-dom';
import DashboardLayout from './layouts/dashboard';
import OncallTrackerPage from './pages/OncallTrackerPage';
import SettingsPage from './pages/SettingsPage';

export default function Router() {
  const routes = useRoutes([
    {
      path: '/app/dashboard',
      element: <DashboardLayout />,
      children: [
        { element: <Navigate to="/app/dashboard/tracker" />, index: true },
        { path: 'tracker', element: <OncallTrackerPage /> },
      ],
    },
    {
      path: '/app/dashboard',
      element: <DashboardLayout />,
      children: [
        { element: <Navigate to="/app/dashboard/settings" />, index: true },
        { path: 'settings', element: <SettingsPage /> },
      ],
    },
    {
      path: '/app',
      element: <DashboardLayout />,
      children: [
        { element: <Navigate to="/app/dashboard/tracker" />, index: true },
      ],
    },
  ]);

  return routes;
}
