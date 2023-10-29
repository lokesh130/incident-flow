import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, TextField, Button } from '@mui/material';

const AlertPopup = ({ followup: alert, onClose, onSend }) => {
  const [message, setMessage] = useState('');

  const handleSend = () => {
    // Perform any logic needed before sending the message
    onSend(message);
    // Close the popup
    onClose();
  };

  return (
    <Dialog open={!!alert} onClose={onClose}>
    <DialogTitle style={{ color: 'black' }}>{alert?.title}</DialogTitle>
    <DialogContent style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', paddingTop: '10px'}}>
        <TextField
        label="New Mail"
        multiline
        rows={4}
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        />
        <Button variant="contained" onClick={handleSend} style={{ marginTop: '15px', backgroundColor: 'black', color: '#fff' }}>
        Send
        </Button>
    </DialogContent>
    </Dialog>
  );
};

export default AlertPopup;
