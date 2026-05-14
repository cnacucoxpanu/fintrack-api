import { useEffect, useState } from 'react';
import { useStore } from '../store';
import { Card, Button, Input, FormGroup, Select, Modal, Alert, Loader, EmptyState, Badge } from '../components/UI';
import { ArrowUpRight, ArrowDownRight, Plus, Trash2, Filter } from 'lucide-react';

export default function Transactions() {
  const {
    transactions,
    accounts,
    categories,
    tags,
    loading,
    error,
    fetchTransactions,
    fetchAccounts,
    fetchCategories,
    fetchTags,
    createTransaction,
    deleteTransaction,
    clearError,
  } = useStore();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [form, setForm] = useState({
    amount: '',
    direction: 'INCOME' as 'INCOME' | 'EXPENSE',
    accountId: '',
    categoryId: '',
    tagIds: [] as number[],
  });
  const [filterDirection, setFilterDirection] = useState<'INCOME' | 'EXPENSE' | ''>('');

  useEffect(() => {
    fetchTransactions(filterDirection || undefined);
    fetchAccounts();
    fetchCategories();
    fetchTags();
  }, [fetchTransactions, fetchAccounts, fetchCategories, fetchTags, filterDirection]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await createTransaction({
        amount: Number(form.amount),
        direction: form.direction,
        accountId: Number(form.accountId),
        categoryId: Number(form.categoryId),
        tagIds: form.tagIds,
      });
      resetForm();
    } catch (err) {
      console.error(err);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure you want to delete this transaction?')) {
      try {
        await deleteTransaction(id);
      } catch (err) {
        console.error(err);
      }
    }
  };

  const toggleTag = (tagId: number) => {
    setForm({
      ...form,
      tagIds: form.tagIds.includes(tagId)
          ? form.tagIds.filter((id) => id !== tagId)
          : [...form.tagIds, tagId],
    });
  };

  const resetForm = () => {
    setForm({
      amount: '',
      direction: 'INCOME',
      accountId: '',
      categoryId: '',
      tagIds: [],
    });
    setIsModalOpen(false);
  };

  const getAccountName = (id: number) => accounts.find((a) => a.id === id)?.name || 'Unknown';
  const getCategoryName = (id: number) => categories.find((c) => c.id === id)?.name || 'Unknown';
  const getTagName = (id: number) => tags.find((t) => t.id === id)?.name || 'Unknown';

  const totalIncome = transactions
      .filter((t) => t.direction === 'INCOME')
      .reduce((sum, t) => sum + Number(t.amount), 0);
  const totalExpense = transactions
      .filter((t) => t.direction === 'EXPENSE')
      .reduce((sum, t) => sum + Number(t.amount), 0);
  const balance = totalIncome - totalExpense;

  return (
      <div>
        <div className="page-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <h1 className="page-title">Transactions</h1>
            <p className="page-subtitle">Track your income and expenses</p>
          </div>
          <Button onClick={() => setIsModalOpen(true)}>
            <Plus size={18} />
            Add Transaction
          </Button>
        </div>

        {error && <Alert message={error} onClose={clearError} />}

        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-icon" style={{ background: 'rgba(16, 185, 129, 0.15)' }}>
              <ArrowUpRight size={24} color="var(--success)" />
            </div>
            <div className="stat-label">Total Income</div>
            <div className="stat-value" style={{ color: 'var(--success)' }}>
              ${totalIncome.toFixed(2)}
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon" style={{ background: 'rgba(239, 68, 68, 0.15)' }}>
              <ArrowDownRight size={24} color="var(--danger)" />
            </div>
            <div className="stat-label">Total Expense</div>
            <div className="stat-value" style={{ color: 'var(--danger)' }}>
              ${totalExpense.toFixed(2)}
            </div>
          </div>

          <div className="stat-card">
            <div
                className="stat-icon"
                style={{
                  background: balance >= 0 ? 'rgba(16, 185, 129, 0.15)' : 'rgba(239, 68, 68, 0.15)',
                }}
            >
              <ArrowUpRight size={24} color={balance >= 0 ? 'var(--success)' : 'var(--danger)'} />
            </div>
            <div className="stat-label">Net Balance</div>
            <div className="stat-value" style={{ color: balance >= 0 ? 'var(--success)' : 'var(--danger)' }}>
              ${balance.toFixed(2)}
            </div>
          </div>
        </div>

        {/* ИСПРАВЛЕНО: Card обернут в div */}
        <div style={{ marginBottom: '1.5rem' }}>
          <Card>
            <div className="filter-bar">
              <Filter size={18} color="var(--text-secondary)" />
              <Select
                  value={filterDirection}
                  onChange={(e) => setFilterDirection(e.target.value as any)}
                  style={{ width: '200px' }}
              >
                <option value="">All Transactions</option>
                <option value="INCOME">Income Only</option>
                <option value="EXPENSE">Expense Only</option>
              </Select>
            </div>
          </Card>
        </div>

        {loading && transactions.length === 0 ? (
            <Card>
              <Loader />
            </Card>
        ) : transactions.length === 0 ? (
            <Card>
              <EmptyState
                  icon={<ArrowUpRight size={48} color="var(--text-muted)" />}
                  message="No transactions found. Create your first transaction!"
              />
            </Card>
        ) : (
            <Card>
              <div className="table-container">
                <table>
                  <thead>
                  <tr>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Account</th>
                    <th>Category</th>
                    <th>Tags</th>
                    <th>Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  {transactions.map((tx) => (
                      <tr key={tx.id}>
                        <td>
                          <Badge variant={tx.direction === 'INCOME' ? 'success' : 'danger'}>
                            {tx.direction}
                          </Badge>
                        </td>
                        <td>
                      <span
                          style={{
                            fontWeight: '600',
                            color: tx.direction === 'INCOME' ? 'var(--success)' : 'var(--danger)',
                          }}
                      >
                        {tx.direction === 'INCOME' ? '+' : '-'}${Number(tx.amount).toFixed(2)}
                      </span>
                        </td>
                        <td>{getAccountName(tx.accountId)}</td>
                        <td>{getCategoryName(tx.categoryId)}</td>
                        <td>
                          {tx.tagIds && tx.tagIds.length > 0 ? (
                              <div style={{ display: 'flex', gap: '0.25rem', flexWrap: 'wrap' }}>
                                {tx.tagIds.map((tagId) => (
                                    <Badge key={tagId}>{getTagName(tagId)}</Badge>
                                ))}
                              </div>
                          ) : (
                              <span style={{ color: 'var(--text-muted)' }}>No tags</span>
                          )}
                        </td>
                        <td>
                          <Button variant="ghost" icon onClick={() => handleDelete(tx.id!)}>
                            <Trash2 size={16} color="var(--danger)" />
                          </Button>
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </table>
              </div>
            </Card>
        )}

        <Modal isOpen={isModalOpen} onClose={resetForm} title="Create Transaction">
          <form onSubmit={handleSubmit}>
            <FormGroup label="Amount" required>
              <Input
                  type="number"
                  step="0.01"
                  min="0.01"
                  value={form.amount}
                  onChange={(e) => setForm({ ...form, amount: e.target.value })}
                  placeholder="0.00"
                  required
              />
            </FormGroup>
            <FormGroup label="Type" required>
              <Select
                  value={form.direction}
                  onChange={(e) => setForm({ ...form, direction: e.target.value as 'INCOME' | 'EXPENSE' })}
              >
                <option value="INCOME">Income</option>
                <option value="EXPENSE">Expense</option>
              </Select>
            </FormGroup>
            <FormGroup label="Account" required>
              <Select
                  value={form.accountId}
                  onChange={(e) => setForm({ ...form, accountId: e.target.value })}
                  required
              >
                <option value="">Select an account</option>
                {accounts.map((account) => (
                    <option key={account.id} value={account.id}>
                      {account.name} (${Number(account.balance).toFixed(2)})
                    </option>
                ))}
              </Select>
            </FormGroup>
            <FormGroup label="Category" required>
              <Select
                  value={form.categoryId}
                  onChange={(e) => setForm({ ...form, categoryId: e.target.value })}
                  required
              >
                <option value="">Select a category</option>
                {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                      {category.name}
                    </option>
                ))}
              </Select>
            </FormGroup>
            <FormGroup label="Tags (Optional)">
              <div className="checkbox-group">
                {tags.map((tag) => (
                    <label key={tag.id} className="checkbox-label">
                      <input
                          type="checkbox"
                          checked={form.tagIds.includes(tag.id)}
                          onChange={() => toggleTag(tag.id)}
                      />
                      {tag.name}
                    </label>
                ))}
              </div>
            </FormGroup>
            <div className="actions">
              <Button type="submit" disabled={loading}>
                Create
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