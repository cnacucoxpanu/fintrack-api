import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuthStore } from '../store';
import { Card, Button, Input, FormGroup, Alert } from '../components/UI';
import { UserPlus } from 'lucide-react';

export default function Register() {
  const navigate = useNavigate();
  const register = useAuthStore((state) => state.register);
  const [form, setForm] = useState({ username: '', password: '', confirmPassword: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (form.username.length < 3) {
      setError('Username must be at least 3 characters');
      return;
    }

    if (form.password !== form.confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    if (form.password.length < 6) {
      setError('Password must be at least 6 characters');
      return;
    }

    setLoading(true);

    try {
      await register(form.username, form.password);
      navigate('/');
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || err.message || 'Registration failed';
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
        {/* ИСПРАВЛЕНО: Card обернут в div */}
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
                <UserPlus size={32} color="white" />
              </div>
              <h1 style={{ fontSize: '1.875rem', fontWeight: '700', marginBottom: '0.5rem' }}>
                Create Account
              </h1>
              <p style={{ color: 'var(--text-secondary)' }}>
                Start managing your finances today
              </p>
            </div>

            {error && <Alert message={error} onClose={() => setError('')} />}

            <form onSubmit={handleSubmit}>
              <FormGroup label="Username" required>
                <Input
                    type="text"
                    value={form.username}
                    onChange={(e) => setForm({ ...form, username: e.target.value })}
                    placeholder="Choose a username"
                    required
                    autoFocus
                    minLength={3}
                    maxLength={50}
                />
              </FormGroup>

              <FormGroup label="Password" required>
                <Input
                    type="password"
                    value={form.password}
                    onChange={(e) => setForm({ ...form, password: e.target.value })}
                    placeholder="Create a password"
                    required
                    minLength={6}
                />
              </FormGroup>

              <FormGroup label="Confirm Password" required>
                <Input
                    type="password"
                    value={form.confirmPassword}
                    onChange={(e) => setForm({ ...form, confirmPassword: e.target.value })}
                    placeholder="Confirm your password"
                    required
                />
              </FormGroup>

              <Button
                  type="submit"
                  disabled={loading}
                  style={{ width: '100%', marginBottom: '1rem' }}
              >
                {loading ? 'Creating account...' : 'Create Account'}
              </Button>

              <p style={{ textAlign: 'center', color: 'var(--text-secondary)', fontSize: '0.9375rem' }}>
                Already have an account?{' '}
                <Link
                    to="/login"
                    style={{ color: 'var(--accent-primary)', textDecoration: 'none', fontWeight: '600' }}
                >
                  Sign in
                </Link>
              </p>
            </form>
          </Card>
        </div>
      </div>
  );
}