// src/components/BankStatement.jsx
import React from 'react';

const BankStatement = ({ data }) => {
  // ✅ SAFETY CHECK: Ensure data is an array
  if (!data || !Array.isArray(data)) {
    return <div className="card"><p>No transactions available.</p></div>;
  }

  return (
    <div className="card">
      <h2>Bank Statement</h2>
      {data.length === 0 ? (
        <p>No transactions found.</p>
      ) : (
        <ul>
          {data.map((transaction, index) => (
            <li key={transaction.id || index}>
              <p><strong>Date:</strong> {new Date(transaction.transactionDate).toLocaleString()}</p>
              <p><strong>Description:</strong> {transaction.remarks}</p>
              <p><strong>Amount:</strong> ₹{Number(transaction.amount).toFixed(2)}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default BankStatement;