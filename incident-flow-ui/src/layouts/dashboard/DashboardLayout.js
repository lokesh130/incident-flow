import React, { useState } from 'react';
import { styled } from '@mui/material/styles';
import { Alert, AlertTitle } from '@mui/lab';
import { ErrorOutline, Info, CheckCircle, Warning } from '@mui/icons-material';
import ReportProblemIcon from '@mui/icons-material/ReportProblem';
import { Outlet } from 'react-router-dom';
import Nav from './nav';
import AlertSidebar from './sidebar/AlertSidebar'; // Import the Sidebar component
import FollowupSidebar from './sidebar/FollowupSidebar'; // Import the Sidebar component

const APP_BAR_MOBILE = 64;
const APP_BAR_DESKTOP = 5;

const StyledRoot = styled('div')({
  display: 'flex',
  minHeight: '100%',
  overflow: 'hidden',
  position: 'relative',
});

const Main = styled('div')(({ theme }) => ({
  flexGrow: 1,
  overflow: 'auto',
  minHeight: '100%',
  paddingTop: 0,
  paddingBottom: 0,
  [theme.breakpoints.up('lg')]: {
    paddingTop: 0,
    paddingLeft: 0,
    paddingRight: 0,
  },
}));

const AlertToggleButton = styled('button')(( ) => ({
  position: 'fixed',
  bottom: 16,
  right: 16,
  backgroundColor: 'transparent', // Set background to transparent
  color: 'transparent', // Set text color to transparent
  padding: '10px',
  border: 'none',
  cursor: 'pointer',
}));

const FolloupToggleButton = styled('button')(( ) => ({
  position: 'fixed',
  bottom: 80,
  right: 16,
  backgroundColor: 'transparent', // Set background to transparent
  color: 'transparent', // Set text color to transparent
  padding: '10px',
  border: 'none',
  cursor: 'pointer',
}));

const CenteredAlert = styled(Alert)({
  display: 'flex',
  alignItems: 'center',
  paddingRight: '0.1em',
  paddingLeft: '1em',
  justifyContent: 'center',
  border: '1px solid #ddd', // Add shaded border styling here
  borderRadius: '5px', // Add border radius for rounded corners
});

export default function DashboardLayout() {
  const [navOpen, setNavOpen] = useState(true);
  const [alertSidebarOpen, setAlertSidebarOpen] = useState(false);
  const [followupSidebarOpen, setFollowupSidebarOpen] = useState(false);


  const toggleNav = () => {
    setNavOpen(!navOpen);
  };

  const toggleAlertSidebar = () => {
    setAlertSidebarOpen(!alertSidebarOpen);
  };

  const toggleFollowupSidebar = () => {
    setFollowupSidebarOpen(!followupSidebarOpen);
  };

  return (
    <StyledRoot>
      <Nav openNav={navOpen} onCloseNav={toggleNav} />
      <Main>
        <Outlet />
      </Main>
      <FollowupSidebar open={followupSidebarOpen} onClose={() => setFollowupSidebarOpen(!followupSidebarOpen)} />

      <AlertSidebar open={alertSidebarOpen} onClose={() => setAlertSidebarOpen(!alertSidebarOpen)} />

      <FolloupToggleButton onClick={toggleFollowupSidebar}>
        {followupSidebarOpen ? <CenteredAlert icon={<Info />} severity="info"/> : <CenteredAlert icon={<Info />} severity="info"/>} {/* Add the Alert icon here */}
      </FolloupToggleButton>

      <AlertToggleButton onClick={toggleAlertSidebar}>
        {alertSidebarOpen ? <CenteredAlert icon={<Warning />} severity="error"/> : <CenteredAlert icon={<Warning />} severity="error"/>} {/* Add the Alert icon here */}
      </AlertToggleButton>
    </StyledRoot>
  );
}
