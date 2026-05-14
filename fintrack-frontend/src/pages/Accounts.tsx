import { useEffect, useState } from 'react';
import { useStore } from '../store';
import { Account } from '../types';
import { Card, Button, Input, FormGroup, Select, Modal, Alert, Loader, EmptyState } from '../components/UI';
import { Wallet, Plus, Edit2, Trash2 } from 'lucide-react';

export default function Accounts() {
  const {
    accounts,
    users,
    loading,
    error,
    fetchAccounts,
    fetchUsers,
    createAccount,
    updateAccount,
    deleteAccount,
    clearError,
  } = useStore();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingAccount, setEditingAccount] = useState<Account | null>(null);
  const [form, setForm] = useState({ name: '', balance: '', userId: '' });

  useEffect(() => {
    fetchAccounts();
    fetchUsers();
  }, [fetchAccounts, fetchUsers]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const data = {
        name: form.name,
        balance: Number(form.balance),
        userId: Number(form.userId),
      };
      if (editingAccount) {
        await updateAccount(editingAccount.id, data);
      } else {
        await createAccount(data);
      }
      resetForm();
    } catch (err) {
      console.error(err);
    }
  };

  const handleEdit = (account: Account) => {
    setEditingAccount(account);
    setForm({
      name: account.name,
      balance: String(account.balance),
      userId: String(account.userId),
    });
    setIsModalOpen(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure you want to delete this account?')) {
      try {
        await deleteAccount(id);
      } catch (err) {
        console.error(err);
      }
    }
  };

  const resetForm = () => {
    setForm({ name: '', balance: '', userId: '' });
    setEditingAccount(null);
    setIsModalOpen(false);
  };

  const getUserName = (userId: number) => users.find((u) => u.id === userId)?.name || 'Unknown';

  return (
    <div>
      {/* Исправленная шапка: заголовок и кнопка на одном уровне */}
      <div 
        className="page-header" 
        style={{ 
          display: 'flex', 
          justifyContent: 'space-between', 
          alignItems: 'flex-start', 
          marginBottom: '2rem' 
        }}
      >
        <div style={{ display: 'flex', flexDirection: 'column', gap: '0.25rem' }}>
          <h1 className="page-title" style={{ margin: 0, lineHeight: 1.2 }}>Accounts</h1>
          <p className="page-subtitle" style={{ margin: 0 }}>Manage your financial accounts</p>
        </div>
        
        <Button 
          onClick={() => setIsModalOpen(true)}
          style={{ marginTop: '4px' }}
        >
          <Plus size={18} />
          Add Account
        </Button>
      </div>

      {error && <Alert message={error} onClose={clearError} />}

      {/* Блок OneToMany удален для чистоты интерфейса */}

      {loading && accounts.length === 0 ? (
        <Card>
          <Loader />
        </Card>
      ) : accounts.length === 0 ? (
        <Card>
          <EmptyState
            icon={<Wallet size={48} color="var(--text-muted)" />}
            message="No accounts found. Create your first account!"
          />
        </Card>
      ) : (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(320px, 1fr))', gap: '1.5rem' }}>
          {accounts.map((account) => (
            <Card key={account.id}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1rem' }}>
                <div
                  style={{
                    width: '48px',
                    height: '48px',
                    borderRadius: 'var(--radius-md)',
                    background: 'rgba(139, 92, 246, 0.15)',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                  }}
                >
                  <Wallet size={24} color="var(--accent-primary)" />
                </div>
                <div className="actions">
                  <Button variant="ghost" icon onClick={() => handleEdit(account)}>
                    <Edit2 size={16} />
                  </Button>
                  <Button variant="ghost" icon onClick={() => handleDelete(account.id)}>
                    <Trash2 size={16} color="var(--danger)" />
                  </Button>
                </div>
              </div>
              <h3 style={{ fontSize: '1.125rem', fontWeight: '700', marginBottom: '0.5rem' }}>
                {account.name}
              </h3>
              <div style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', marginBottom: '1rem' }}>
                Owner: {getUserName(account.userId)}
              </div>
              <div style={{ fontSize: '1.875rem', fontWeight: '700', color: 'var(--accent-primary)' }}>
                ${Number(account.balance).toFixed(2)}
              </div>
            </Card>
          ))}
        </div>
      )}

      <Modal
        isOpen={isModalOpen}
        onClose={resetForm}
        title={editingAccount ? 'Edit Account' : 'Create Account'}
      >
        <form onSubmit={handleSubmit}>
          <FormGroup label="Account Name" required>
            <Input
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
              placeholder="e.g., Main Checking"
              required
            />
          </FormGroup>
          <FormGroup label="Balance" required>
            <Input
              type="number"
              step="0.01"
              min="0"
              value={form.balance}
              onChange={(e) => setForm({ ...form, balance: e.target.value })}
              placeholder="0.00"
              required
            />
          </FormGroup>
          <FormGroup label="Owner" required>
            <Select
              value={form.userId}
              onChange={(e) => setForm({ ...form, userId: e.target.value })}
              required
            >
              <option value="">Select a user</option>
              {users.map((user) => (
                <option key={user.id} value={user.id}>
                  {user.name}
                </option>
              ))}
            </Select>
          </FormGroup>
          <div className="actions">
            <Button type="submit" disabled={loading}>
              {editingAccount ? 'Update' : 'Create'}
            </Button>
            <Button type="button" variant="secondary" onClick={resetForm}>
              Cancel
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}