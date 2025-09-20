// src/services/api.js

// ✅ Remove or comment out BASE_URL
// const BASE_URL = 'http://localhost:8080';

export const fetchAccountDetails = async (accountNumber) => {
  const response = await fetch(`/api/accounts/details/${accountNumber}`, {
    headers: {
      'Accept': 'application/json',
    },
  });
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.statusText}`);
  }
  return response.json();
};

export const fetchBankStatement = async (userId) => {
  const response = await fetch(`/api/transactions/history/${userId}`, {
    headers: {
      'Accept': 'application/json',
    },
  });
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.statusText}`);
  }
  return response.json();
};

export const submitTransaction = async (transaction) => {
  const response = await fetch(`/api/transactions/transfer`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
    body: JSON.stringify(transaction),
  });
  if (!response.ok) {
    const message = await response.text();
    throw new Error(`Error ${response.status}: ${message}`);
  }
  return response.json();
  
};
// src/services/api.js

export const depositFunds = async (depositData) => {
  const response = await fetch(`/api/transactions/deposit`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
    body: JSON.stringify(depositData),
  });
  if (!response.ok) {
    const message = await response.text();
    throw new Error(`Error ${response.status}: ${message}`);
  }
  return response.json();
};

export const withdrawFunds = async (withdrawData) => {
  const response = await fetch(`/api/transactions/withdraw`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
    body: JSON.stringify(withdrawData),
  });
  if (!response.ok) {
    const message = await response.text();
    throw new Error(`Error ${response.status}: ${message}`);
  }
  return response.json();
};
