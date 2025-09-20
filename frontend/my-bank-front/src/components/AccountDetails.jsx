// src/components/AccountDetails.jsx
import React from 'react';

const AccountDetails = ({ data }) => {
  return (
    <div className="card">
      <h2>Account Details</h2>
      <p><strong>Account Holder:</strong> {data.name}</p>
      <p><strong>Account Number:</strong> {data.accountNumber}</p>
      <p><strong>Current Balance:</strong> ${data.balance.toFixed(2)}</p>
    </div>
  );
};

export default AccountDetails;