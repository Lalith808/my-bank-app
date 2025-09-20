// src/components/Navbar.jsx
import React from 'react';

const Navbar = ({ onNavigate }) => {
  return (
    <nav className="navbar">
      <h1 className="navbar-brand">Bank App</h1>
      <ul>
        <li><button onClick={() => onNavigate('accountDetails')}>Account Details</button></li>
        <li><button onClick={() => onNavigate('bankStatement')}>Bank Statement</button></li>
        <li><button onClick={() => onNavigate('transactionForm')}>New Transaction</button></li>
      </ul>
    </nav>
  );
};

export default Navbar;