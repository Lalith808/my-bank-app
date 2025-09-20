// src/App.jsx
import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar.jsx';
import AccountDetails from './components/AccountDetails.jsx';
import TransactionForm from './components/TransactionForm.jsx';
import BankStatement from './components/BankStatement.jsx';
import { fetchAccountDetails, fetchBankStatement } from './services/api.js';
import './styles/main.css';

function App() {
  const [activeView, setActiveView] = useState('accountDetails');
  const [accountData, setAccountData] = useState(null);
  const [statementData, setStatementData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  
  const accountNumber = "ACC1234567"; // ← Example account number — must match your backend mock/data
  const userId = 1;  

  // Fetch data from backend on component mount
  useEffect(() => {
    const getData = async () => {
      setLoading(true);
      setError('');
      try {
        const details = await fetchAccountDetails(accountNumber);
        setAccountData(details);
      } catch (err) {
        setError('Failed to fetch account details.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    getData();
  }, []);

  const handleViewChange = async (view) => {
    setActiveView(view);
    if (view === 'bankStatement' && !statementData) {
      setLoading(true);
      setError('');
      try {
        const statement = await fetchBankStatement(userId);
        setStatementData(statement);
      } catch (err) {
        setError('Failed to fetch bank statement.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <div className="container">
      <Navbar onNavigate={handleViewChange} />
      <main className="content">
        {loading && <p>Loading...</p>}
        {error && <p className="error">{error}</p>}
        {!loading && !error && (
          <>
            {activeView === 'accountDetails' && accountData && <AccountDetails data={accountData} />}
            {activeView === 'bankStatement' && statementData && <BankStatement data={statementData} />}
            {activeView === 'transactionForm' && <TransactionForm />}
          </>
        )}
      </main>
    </div>
  );
}

export default App;