import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuthStore } from '../store';
import { Card, Button, Input, FormGroup, Alert } from '../components/UI';
import { LogIn } from 'lucide-react';

export default function Login() {
  const navigate = useNavigate();
  const login = useAuthStore((state) => state.login);
  const [form, setForm] = useState({ username: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await login(form.username, form.password);
      navigate('/');
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || err.message || 'Invalid credentials';
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
      <div style={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        padding: '2rem',
        background: 'linear-gradient(135deg, #0f172a 0%, #1e1b4b 50%, #312e81 100%)',
        backgroundAttachment: 'fixed'
      }}>
        {/* FIXED: Wrapped Card in a div because Card does not accept the style prop */}
        <div style={{ maxWidth: '420px', width: '100%' }}>
          <Card>
            <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
              <div style={{
                width: '64px',
                height: '64px',
                background: 'linear-gradient(135deg, var(--accent-primary), var(--accent-secondary))',
                borderRadius: 'var(--radius-lg)',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                margin: '0 auto 1rem',
                boxShadow: '0 8px 24px rgba(139, 92, 246, 0.4)'
              }}>
                <LogIn size={32} color="white" />
              </div>
              <h1 style={{ fontSize: '1.875rem', fontWeight: '700', marginBottom: '0.5rem' }}>
                Welcome to FinTrack
              </h1>
              <p style={{ color: 'var(--text-secondary)' }}>
                Sign in to manage your finances
              </p>
            </div>

            {error && <Alert message={error} onClose={() => setError('')} />}

            <form onSubmit={handleSubmit}>
              <FormGroup label="Username" required>
                <Input
                    type="text"
                    value={form.username}
                    onChange={(e) => setForm({ ...form, username: e.target.value })}
                    placeholder="Enter your username"
                    required
                    autoFocus
                />
              </FormGroup>

              <FormGroup label="Password" required>
                <Input
                    type="password"
                    value={form.password}
                    onChange={(e) => setForm({ ...form, password: e.target.value })}
                    placeholder="Enter your password"
                    required
                />
              </FormGroup>

              <Button
                  type="submit"
                  disabled={loading}
                  style={{ width: '100%', marginBottom: '1rem' }}
              >
                {loading ? 'Signing in...' : 'Sign In'}
              </Button>

              <p style={{ textAlign: 'center', color: 'var(--text-secondary)', fontSize: '0.9375rem' }}>
                Don't have an account?{' '}
                <Link
                    to="/register"
                    style={{ color: 'var(--accent-primary)', textDecoration: 'none', fontWeight: '600' }}
                >
                  Sign up
                </Link>
              </p>
            </form>
          </Card>
        </div>
      </div>
  );
}