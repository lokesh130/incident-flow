import PropTypes from 'prop-types';
import { forwardRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { Box, Link } from '@mui/material';

const Logo = forwardRef(({ disabledLink = false, sx, ...other }, ref) => {

  const logo = (
    <Box
      component="img"
      src={`${process.env.PUBLIC_URL}/assets/logo/incidentflow_logo1.png`}
      sx={{ width: 200, height: 69, cursor: 'pointer', ...sx }}
    />
  );


  if (disabledLink) {
    return <>{logo}</>;
  }

  return (
    <Link to="/app" component={RouterLink} sx={{ display: 'contents' }}>
      {logo}
    </Link>
  );
});

Logo.propTypes = {
  sx: PropTypes.object,
  disabledLink: PropTypes.bool,
};

export default Logo;
