import React, { useState, useEffect } from 'react';
import {
  Box,
  Typography,
  Button,
  TextField,
  Divider,
  styled,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  Paper,
} from '@mui/material';
import axios from '../utils/CustomAxios';
import { getWatchEmailsApiEndpoint, getConfigurationsApiEndpoint, getFollowupConfigApiEndpoint, updateFollowupConfigApiEndpoint } from '../utils/ApiUtils';

const HeadingTypography = styled(Typography)(({ theme }) => ({
  letterSpacing: '0.1em',
  fontWeight: 'bold',
  fontFamily: 'Cursive, sans-serif',
  color: '#8b008b',
  textTransform: 'uppercase',
  textAlign: 'center',
  padding: '0.2em 0.5em',
  borderRadius: '5px',
}));

const SectionHeading = styled(Typography)(({ theme }) => ({
  fontSize: '1.2rem',
  color: '#2196F3',
  fontWeight: 'bold',
  marginTop: '1em',
}));

const SettingsPage = () => {
  const [watchEmails, setWatchEmails] = useState([]);
  const [newEmail, setNewEmail] = useState('');
  const [configurations, setConfigurations] = useState([]);
  const [newConfiguration, setNewConfiguration] = useState({
    subjectRegex: '',
    frequency: 0,
  });
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [followupConfig, setFollowupConfig] = useState({ id: null, expectedDuration: 0 });
  const [customFollowup, setCustomFollowup] = useState({
    months: 0,
    days: 0,
    hours: 0,
    minutes: 0,
    seconds: 0,
  });

  const handleClickOpen = () => {
    setIsDialogOpen(true);
  };

  const handleClose = () => {
    setIsDialogOpen(false);
  };

  useEffect(() => {
    axios.get(getWatchEmailsApiEndpoint())
      .then(response => setWatchEmails(response.data))
      .catch(error => console.error('Error fetching watch emails:', error));

    axios.get(getConfigurationsApiEndpoint())
      .then(response => setConfigurations(response.data))
      .catch(error => console.error('Error fetching configurations:', error));

    axios.get(getFollowupConfigApiEndpoint())
      .then(response => setFollowupConfig(response.data))
      .catch(error => console.error('Error fetching followup configuration:', error));
  }, []);

  const addWatchEmail = () => {
    axios.post(getWatchEmailsApiEndpoint(), { email: newEmail })
      .then(() => {
        setNewEmail('');
        axios.get(getWatchEmailsApiEndpoint())
          .then(response => setWatchEmails(response.data))
          .catch(error => console.error('Error refreshing watch emails:', error));
      })
      .catch(error => console.error('Error adding watch email:', error));
  };

  const deleteWatchEmail = (email) => {
    axios.delete(`${getWatchEmailsApiEndpoint()}/${email}`)
      .then(() => {
        axios.get(getWatchEmailsApiEndpoint())
          .then(response => setWatchEmails(response.data))
          .catch(error => console.error('Error refreshing watch emails:', error));
      })
      .catch(error => console.error('Error deleting watch email:', error));
  };

  const addConfiguration = () => {
    axios.post(getConfigurationsApiEndpoint(), newConfiguration)
      .then(() => {
        setNewConfiguration({
          subjectRegex: '',
          frequency: 0,
        });
        axios.get(getConfigurationsApiEndpoint())
          .then(response => setConfigurations(response.data))
          .catch(error => console.error('Error refreshing configurations:', error));
      })
      .catch(error => console.error('Error adding configuration:', error));
  };

  const openUpdateDialog = (id) => {
    setNewConfiguration(configurations.find(config => config.id === id));
    handleClickOpen();
  };

  const updateConfiguration = () => {
    axios.put(`${getConfigurationsApiEndpoint()}/${newConfiguration.id}`, newConfiguration)
      .then(() => {
        setNewConfiguration({
          subjectRegex: '',
          frequency: 0,
        });
        setIsDialogOpen(false);
        axios.get(getConfigurationsApiEndpoint())
          .then(response => setConfigurations(response.data))
          .catch(error => console.error('Error refreshing configurations:', error));
      })
      .catch(error => console.error('Error updating configuration:', error));
  };

  const deleteConfiguration = (id) => {
    axios.delete(`${getConfigurationsApiEndpoint()}/${id}`)
      .then(() => {
        axios.get(getConfigurationsApiEndpoint())
          .then(response => setConfigurations(response.data))
          .catch(error => console.error('Error refreshing configurations:', error));
      })
      .catch(error => console.error('Error deleting configuration:', error));
  };

  const updateFollowupConfig = () => {
    const { months, days, hours, minutes, seconds } = customFollowup;
    const totalSeconds = months * 30 * 24 * 60 * 60 + days * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60 + seconds;

    axios.put(updateFollowupConfigApiEndpoint(totalSeconds))
      .then(() => {
        axios.get(getFollowupConfigApiEndpoint())
          .then(response => setFollowupConfig({ expectedDuration: response.data.expectedDuration }))
          .catch(error => console.error('Error refreshing followup configuration:', error));
      })
      .catch(error => console.error('Error updating followup configuration:', error));
  };

  const convertSecondsToDuration = (seconds) => {
    const months = Math.floor(seconds / (30 * 24 * 60 * 60));
    seconds -= months * 30 * 24 * 60 * 60;
    const days = Math.floor(seconds / (24 * 60 * 60));
    seconds -= days * 24 * 60 * 60;
    const hours = Math.floor(seconds / (60 * 60));
    seconds -= hours * 60 * 60;
    const minutes = Math.floor(seconds / 60);
    seconds -= minutes * 60;
  
    return { months, days, hours, minutes, seconds };
  };

  return (
    <Box style={{ margin: '22px' }}>
      <SectionHeading style={{ color: '#8b008b', borderColor: '#8b008b' }}>Watch Emails</SectionHeading>
      <Box>
        <TextField
          label="Email"
          variant="outlined"
          value={newEmail}
          sx={{ marginTop: 2, marginRight: 1 }}
          onChange={(e) => setNewEmail(e.target.value)}
        />
        <Button variant="contained" color="primary" onClick={addWatchEmail} sx={{ marginTop: 3.1 }} style={{ backgroundColor: '#8b008b' }}>
          Add Email
        </Button>
        <TableContainer component={Paper} sx={{ marginTop: 3 }}>
          <Table>
            <TableBody>
              {watchEmails.map((email, index) => (
                <TableRow
                  key={email.email}
                  sx={{ backgroundColor: index % 2 === 0 ? 'rgba(0, 0, 0, 0.05)' : 'white' }}
                >
                  <TableCell>{email.email}</TableCell>
                  <TableCell>
                    <Button variant="outlined" color="secondary" onClick={() => deleteWatchEmail(email.email)} style={{ color: '#8b008b', borderColor: '#8b008b' }}>
                      Delete
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>

      <Divider sx={{ my: 4 }} />

      <SectionHeading sx={{ marginBottom: 1 }} style={{ color: '#8b008b', borderColor: '#8b008b' }}>Configurations</SectionHeading>
      <Box>
        <TextField
          label="Subject Regex"
          variant="outlined"
          value={newConfiguration.subjectRegex}
          sx={{ marginTop: 2, marginRight: 1 }}
          onChange={(e) => setNewConfiguration({ ...newConfiguration, subjectRegex: e.target.value })}
        />
        <TextField
          label="Frequency"
          variant="outlined"
          type="number"
          value={newConfiguration.frequency}
          sx={{ marginTop: 2, marginRight: 1 }}
          onChange={(e) => setNewConfiguration({ ...newConfiguration, frequency: e.target.value })}
        />
        <Button variant="contained" color="primary" onClick={addConfiguration} sx={{ marginTop: 3.1 }} style={{ backgroundColor: '#8b008b' }}>
          Add Configuration
        </Button>
        <br />
        <br />
        <TableContainer component={Paper}>
          <Table>
            <TableBody>
              {configurations.map((config, index) => (
                <TableRow
                  key={config.id}
                  sx={{ backgroundColor: index % 2 === 0 ? 'rgba(0, 0, 0, 0.05)' : 'white' }}
                >
                  <TableCell>{config.subjectRegex}</TableCell>
                  <TableCell>{config.frequency}</TableCell>
                  <TableCell>
                    <Button variant="outlined" color="primary" onClick={() => openUpdateDialog(config.id)} sx={{ marginRight: 2 }} style={{ color: '#8b008b', borderColor: '#8b008b' }}>
                      Update
                    </Button>
                    <Button variant="outlined" color="secondary" onClick={() => deleteConfiguration(config.id)} style={{ color: '#8b008b', borderColor: '#8b008b' }}>
                      Delete
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
        <Dialog open={isDialogOpen} onClose={handleClose}>
          <DialogTitle>Update Configuration</DialogTitle>
          <DialogContent>
            <TextField
              label="Subject Regex"
              variant="outlined"
              value={newConfiguration.subjectRegex}
              sx={{ marginTop: 2, marginRight: 1 }}
              onChange={(e) => setNewConfiguration({ ...newConfiguration, subjectRegex: e.target.value })}
            />
            <TextField
              label="Frequency"
              variant="outlined"
              type="number"
              value={newConfiguration.frequency}
              sx={{ marginTop: 2 }}
              onChange={(e) => setNewConfiguration({ ...newConfiguration, frequency: e.target.value })}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose} color="primary" style={{ color: '#8b008b', borderColor: '#8b008b' }}>
              Cancel
            </Button>
            <Button onClick={updateConfiguration} color="primary" style={{ color: '#8b008b', borderColor: '#8b008b' }}>
              Update
            </Button>
          </DialogActions>
        </Dialog>
      </Box>

      <Divider sx={{ my: 4 }} />

      <SectionHeading sx={{ marginBottom: 1 }} style={{ color: '#8b008b', borderColor: '#8b008b' }}>Followup Settings</SectionHeading>
      <Box>
        <Typography sx={{ marginTop: 2, marginBottom: 1 }}>
          <b>Current Expected Duration :</b> {' '}
          {Object.entries(convertSecondsToDuration(followupConfig.expectedDuration))
            .map(([unit, value]) => (value > 0 ? `${value} ${unit}` : ''))
            .filter(Boolean)
            .join(', ')}
            <br/>
            <br/>
        </Typography>
        <TextField
          label="Months"
          variant="outlined"
          type="number"
          value={customFollowup.months}
          onChange={(e) => setCustomFollowup({ ...customFollowup, months: e.target.value })}
          sx={{ marginRight: 1, width: '60px' }}
        />
        <TextField
          label="Days"
          variant="outlined"
          type="number"
          value={customFollowup.days}
          onChange={(e) => setCustomFollowup({ ...customFollowup, days: e.target.value })}
          sx={{ marginRight: 1, width: '60px' }}
        />
        <TextField
          label="Hours"
          variant="outlined"
          type="number"
          value={customFollowup.hours}
          onChange={(e) => setCustomFollowup({ ...customFollowup, hours: e.target.value })}
          sx={{ marginRight: 1, width: '60px' }}
        />
        <TextField
          label="Minutes"
          variant="outlined"
          type="number"
          value={customFollowup.minutes}
          onChange={(e) => setCustomFollowup({ ...customFollowup, minutes: e.target.value })}
          sx={{ marginRight: 1, width: '60px' }}
        />
        <TextField
          label="Seconds"
          variant="outlined"
          type="number"
          value={customFollowup.seconds}
          onChange={(e) => setCustomFollowup({ ...customFollowup, seconds: e.target.value })}
          sx={{ width: '60px' }}
        />

        <Button variant="contained" color="primary" onClick={updateFollowupConfig} sx={{ marginLeft:2, marginTop: 1.1 }} style={{ backgroundColor: '#8b008b' }}>
          Update Followup Duration
        </Button>
      </Box>
    </Box>
  );
};

export default SettingsPage;
