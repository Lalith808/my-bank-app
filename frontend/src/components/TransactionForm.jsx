// src/components/TransactionForm.jsx
import React, { useState } from 'react';
import { depositFunds, withdrawFunds } from '../services/api'; // ✅ Import correct functions

const TransactionForm = () => {
  const [amount, setAmount] = useState('');
  const [type, setType] = useState('deposit');
  const [accountNumber, setAccountNumber] = useState('ACC1234567'); // ← Default test account
  const [feedback, setFeedback] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFeedback('Processing...');

    if (!amount || parseFloat(amount) <= 0) {
      setFeedback('Please enter a valid amount.');
      return;
    }

    try {
      let response;
      if (type === 'deposit') {
        // ✅ Call deposit endpoint
        response = await depositFunds({
          accountNumber: accountNumber,
          amount: parseFloat(amount),
          remarks: `Deposit via form`,
        });
      } else {
        // ✅ Call withdraw endpoint
        response = await withdrawFunds({
          userId: 1, // ← Hardcoded for now (should match accountNumber owner)
          amount: parseFloat(amount),
          remarks: `Withdrawal via form`,
        });
      }

      setFeedback('Transaction successful!');
      console.log('API Response:', response);
      setAmount('');
    } catch (error) {
      setFeedback(`Error: ${error.message}`);
      console.error(error);
    }
  };

  return (
    <div className="card">
      <h2>New Transaction</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Account Number:</label>
          <input
            type="text"
            value={accountNumber}
            onChange={(e) => setAccountNumber(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Amount:</label>
          <input
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            required
            min="0.01"
            step="0.01"
          />
        </div>
        <div className="form-group">
          <label>Type:</label>
          <select value={type} onChange={(e) => setType(e.target.value)}>
            <option value="deposit">Deposit</option>
            <option value="withdrawal">Withdrawal</option>
          </select>
        </div>
        <button type="submit">Submit</button>
        {feedback && <p className="feedback-message">{feedback}</p>}
      </form>
    </div>
  );
};

export default TransactionForm;