import { useEffect } from 'react';
import { BrowserRouter, Routes, Route, Navigate, NavLink, useNavigate } from 'react-router-dom';
import { useAuthStore } from './store';
import { ProtectedRoute } from './components/ProtectedRoute';
import { Button } from './components/UI';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Accounts from './pages/Accounts';
import Categories from './pages/Categories';
import Tags from './pages/Tags';
import Transactions from './pages/Transactions';
import Settings from './pages/Settings';
import { LayoutDashboard, Wallet, FolderOpen, Tag, ArrowLeftRight, Settings as SettingsIcon, LogOut } from 'lucide-react';
import './App.css';

function AppLayout() {
  const { username, logout } = useAuthStore();
  const navigate = useNavigate();

  return (
    <div className="app">
      <nav className="navbar">
        <div className="navbar-container">
          <div className="navbar-brand" onClick={() => navigate('/')}>
            <div className="navbar-logo">
              <Wallet size={20} color="white" />
            </div>
            FinTrack
          </div>

          <ul className="navbar-nav">
            <li>
              <NavLink to="/" end className="navbar-link">
                <LayoutDashboard size={18} />
                Dashboard
              </NavLink>
            </li>
            <li>
              <NavLink to="/accounts" className="navbar-link">
                <Wallet size={18} />
                Accounts
              </NavLink>
            </li>
            <li>
              <NavLink to="/transactions" className="navbar-link">
                <ArrowLeftRight size={18} />
                Transactions
              </NavLink>
            </li>
            <li>
              <NavLink to="/categories" className="navbar-link">
                <FolderOpen size={18} />
                Categories
              </NavLink>
            </li>
            <li>
              <NavLink to="/tags" className="navbar-link">
                <Tag size={18} />
                Tags
              </NavLink>
            </li>
          </ul>

          <div className="navbar-actions">
            <NavLink to="/settings" className="navbar-link">
              <SettingsIcon size={18} />
            </NavLink>
            <div className="navbar-user">
              <span>{username}</span>
            </div>
            <Button variant="ghost" onClick={logout}>
              <LogOut size={18} />
            </Button>
          </div>
        </div>
      </nav>

      <div className="container">
        <Routes>
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />
          <Route
            path="/accounts"
            element={
              <ProtectedRoute>
                <Accounts />
              </ProtectedRoute>
            }
          />
          <Route
            path="/transactions"
            element={
              <ProtectedRoute>
                <Transactions />
              </ProtectedRoute>
            }
          />
          <Route
            path="/categories"
            element={
              <ProtectedRoute>
                <Categories />
              </ProtectedRoute>
            }
          />
          <Route
            path="/tags"
            element={
              <ProtectedRoute>
                <Tags />
              </ProtectedRoute>
            }
          />
          <Route
            path="/settings"
            element={
              <ProtectedRoute>
                <Settings />
              </ProtectedRoute>
            }
          />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </div>
  );
}

function App() {
  const { isAuthenticated, initAuth } = useAuthStore();

  useEffect(() => {
    initAuth();
  }, [initAuth]);

  if (!isAuthenticated) {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </BrowserRouter>
    );
  }

  return (
    <BrowserRouter>
      <AppLayout />
    </BrowserRouter>
  );
}

export default App;
