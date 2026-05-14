import { useEffect } from 'react';
import { useStore } from '../store';
import { Card, Loader, EmptyState } from '../components/UI';
import { Wallet, TrendingUp, TrendingDown, DollarSign, ArrowUpRight, ArrowDownRight } from 'lucide-react';

export default function Dashboard() {
  const { accounts, transactions, fetchAccounts, fetchTransactions, loading } = useStore();

  useEffect(() => {
    fetchAccounts();
    fetchTransactions();
  }, [fetchAccounts, fetchTransactions]);

  const totalBalance = accounts.reduce((sum, acc) => sum + Number(acc.balance), 0);
  const totalIncome = transactions
    .filter((t) => t.direction === 'INCOME')
    .reduce((sum, t) => sum + Number(t.amount), 0);
  const totalExpense = transactions
    .filter((t) => t.direction === 'EXPENSE')
    .reduce((sum, t) => sum + Number(t.amount), 0);
  const netBalance = totalIncome - totalExpense;

  const recentTransactions = transactions.slice(0, 5);

  if (loading && accounts.length === 0) {
    return <Loader />;
  }

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Dashboard</h1>
        <p className="page-subtitle">Overview of your financial status</p>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <div
            className="stat-icon"
            style={{ background: 'rgba(139, 92, 246, 0.15)' }}
          >
            <Wallet size={24} color="var(--accent-primary)" />
          </div>
          <div className="stat-label">Total Balance</div>
          <div className="stat-value">${totalBalance.toFixed(2)}</div>
        </div>

        <div className="stat-card">
          <div
            className="stat-icon"
            style={{ background: 'rgba(16, 185, 129, 0.15)' }}
          >
            <TrendingUp size={24} color="var(--success)" />
          </div>
          <div className="stat-label">Total Income</div>
          <div className="stat-value" style={{ color: 'var(--success)' }}>
            ${totalIncome.toFixed(2)}
          </div>
        </div>

        <div className="stat-card">
          <div
            className="stat-icon"
            style={{ background: 'rgba(239, 68, 68, 0.15)' }}
          >
            <TrendingDown size={24} color="var(--danger)" />
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
              background:
                netBalance >= 0
                  ? 'rgba(16, 185, 129, 0.15)'
                  : 'rgba(239, 68, 68, 0.15)',
            }}
          >
            <DollarSign
              size={24}
              color={netBalance >= 0 ? 'var(--success)' : 'var(--danger)'}
            />
          </div>
          <div className="stat-label">Net Balance</div>
          <div
            className="stat-value"
            style={{ color: netBalance >= 0 ? 'var(--success)' : 'var(--danger)' }}
          >
            ${netBalance.toFixed(2)}
          </div>
        </div>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1.5rem', marginBottom: '2rem' }}>
        <Card>
          <h2 style={{ fontSize: '1.25rem', fontWeight: '700', marginBottom: '1.5rem' }}>
            Accounts
          </h2>
          {accounts.length === 0 ? (
            <EmptyState message="No accounts yet" />
          ) : (
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
              {accounts.map((account) => (
                <div
                  key={account.id}
                  style={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    padding: '1rem',
                    background: 'var(--bg-tertiary)',
                    borderRadius: 'var(--radius-md)',
                    border: '1px solid var(--border)',
                  }}
                >
                  <div>
                    <div style={{ fontWeight: '600' }}>
                      {account.name}
                    </div>
                  </div>
                  <div style={{ fontSize: '1.25rem', fontWeight: '700' }}>
                    ${Number(account.balance).toFixed(2)}
                  </div>
                </div>
              ))}
            </div>
          )}
        </Card>

        <Card>
          <h2 style={{ fontSize: '1.25rem', fontWeight: '700', marginBottom: '1.5rem' }}>
            Recent Transactions
          </h2>
          {recentTransactions.length === 0 ? (
            <EmptyState message="No transactions yet" />
          ) : (
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
              {recentTransactions.map((tx) => (
                <div
                  key={tx.id}
                  style={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    padding: '1rem',
                    background: 'var(--bg-tertiary)',
                    borderRadius: 'var(--radius-md)',
                    border: '1px solid var(--border)',
                  }}
                >
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                    <div
                      style={{
                        width: '40px',
                        height: '40px',
                        borderRadius: 'var(--radius-sm)',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        background:
                          tx.direction === 'INCOME'
                            ? 'rgba(16, 185, 129, 0.15)'
                            : 'rgba(239, 68, 68, 0.15)',
                      }}
                    >
                      {tx.direction === 'INCOME' ? (
                        <ArrowUpRight size={20} color="var(--success)" />
                      ) : (
                        <ArrowDownRight size={20} color="var(--danger)" />
                      )}
                    </div>
                    <div>
                      <div style={{ fontWeight: '600', marginBottom: '0.25rem' }}>
                        {tx.direction === 'INCOME' ? 'Income' : 'Expense'}
                      </div>
                      <div style={{ fontSize: '0.875rem', color: 'var(--text-secondary)' }}>
                        {tx.createdAt
                          ? new Date(tx.createdAt).toLocaleDateString()
                          : 'Today'}
                      </div>
                    </div>
                  </div>
                  <div
                    style={{
                      fontSize: '1.125rem',
                      fontWeight: '700',
                      color: tx.direction === 'INCOME' ? 'var(--success)' : 'var(--danger)',
                    }}
                  >
                    {tx.direction === 'INCOME' ? '+' : '-'}${Number(tx.amount).toFixed(2)}
                  </div>
                </div>
              ))}
            </div>
          )}
        </Card>
      </div>
    </div>
  );
}