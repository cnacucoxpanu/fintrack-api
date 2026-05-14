import { useEffect, useState } from 'react';
import { useStore } from '../store';
import { User } from '../types';
import { Card, Button, Input, FormGroup, Alert, Loader, Modal } from '../components/UI';
import { UserPlus, Edit2, Trash2 } from 'lucide-react';

export default function Users() {
  const { users, loading, error, fetchUsers, createUser, updateUser, deleteUser, clearError } = useStore();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingUser, setEditingUser] = useState<User | null>(null);
  const [form, setForm] = useState({ name: '', email: '', password: '' });

  useEffect(() => {
    fetchUsers();
  }, [fetchUsers]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingUser) {
        await updateUser(editingUser.id, { name: form.name, email: form.email });
      } else {
        await createUser(form);
      }
      resetForm();
    } catch (err) {
      console.error(err);
    }
  };

  const handleEdit = (user: User) => {
    setEditingUser(user);
    setForm({ name: user.name, email: user.email, password: '' });
    setIsModalOpen(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure you want to delete this user?')) {
      try {
        await deleteUser(id);
      } catch (err) {
        console.error(err);
      }
    }
  };

  const resetForm = () => {
    setForm({ name: '', email: '', password: '' });
    setEditingUser(null);
    setIsModalOpen(false);
  };

  return (
      <div>
        <div className="page-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <h1 className="page-title">Users</h1>
            <p className="page-subtitle">Manage system users and access</p>
          </div>
          <Button onClick={() => setIsModalOpen(true)}>
            <UserPlus size={18} />
            Add User
          </Button>
        </div>

        {error && <Alert message={error} onClose={clearError} />}

        {loading && users.length === 0 ? (
            <Card>
              <Loader />
            </Card>
        ) : users.length === 0 ? (
            <Card>
              <div className="empty-state" style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-muted)' }}>
                No users found. Create your first user!
              </div>
            </Card>
        ) : (
            <Card>
              {/* ИСПРАВЛЕНО: Вместо несуществующего <Table> используем стандартную структуру */}
              <div className="table-container">
                <table>
                  <thead>
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Accounts</th>
                    <th>Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  {users.map((user) => (
                      <tr key={user.id}>
                        <td>{user.id}</td>
                        <td style={{ fontWeight: '600' }}>{user.name}</td>
                        <td>{user.email}</td>
                        <td>{user.accounts?.length || 0}</td>
                        <td>
                          <div className="actions" style={{ display: 'flex', gap: '0.5rem' }}>
                            <Button variant="ghost" icon onClick={() => handleEdit(user)}>
                              <Edit2 size={16} />
                            </Button>
                            <Button variant="ghost" icon onClick={() => handleDelete(user.id)}>
                              <Trash2 size={16} color="var(--danger)" />
                            </Button>
                          </div>
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </table>
              </div>
            </Card>
        )}

        <Modal
            isOpen={isModalOpen}
            onClose={resetForm}
            title={editingUser ? 'Edit User' : 'Create User'}
        >
          <form onSubmit={handleSubmit}>
            <FormGroup label="Name" required>
              <Input
                  value={form.name}
                  onChange={(e) => setForm({ ...form, name: e.target.value })}
                  required
                  minLength={2}
                  maxLength={50}
              />
            </FormGroup>
            <FormGroup label="Email" required>
              <Input
                  type="email"
                  value={form.email}
                  onChange={(e) => setForm({ ...form, email: e.target.value })}
                  required
              />
            </FormGroup>
            {!editingUser && (
                <FormGroup label="Password" required>
                  <Input
                      type="password"
                      value={form.password}
                      onChange={(e) => setForm({ ...form, password: e.target.value })}
                      required
                      minLength={6}
                  />
                </FormGroup>
            )}
            <div className="actions" style={{ display: 'flex', gap: '1rem', marginTop: '1.5rem' }}>
              <Button type="submit" disabled={loading}>
                {editingUser ? 'Update' : 'Create'}
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