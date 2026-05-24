import { useEffect, useState } from 'react';
import { useStore } from '../store';
import { Category } from '../types';
import { Card, Button, Input, FormGroup, Modal, Alert, Loader, EmptyState, Badge, Pagination } from '../components/UI';
import { FolderOpen, Plus, Edit2, Trash2, Search } from 'lucide-react';

const ITEMS_PER_PAGE = 10;

export default function Categories() {
  const {
    categories,
    loading,
    error,
    fetchCategories,
    createCategory,
    updateCategory,
    deleteCategory,
    clearError,
  } = useStore();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingCategory, setEditingCategory] = useState<Category | null>(null);
  const [form, setForm] = useState({ name: '', type: '' });
  const [filterName, setFilterName] = useState('');
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    const timer = setTimeout(() => {
      fetchCategories(filterName || undefined);
      setCurrentPage(1); // Сбрасываем на первую страницу при поиске
    }, 300);
    return () => clearTimeout(timer);
  }, [filterName, fetchCategories]);

  // Пагинация
  const totalPages = Math.ceil(categories.length / ITEMS_PER_PAGE);
  const paginatedCategories = categories.slice(
      (currentPage - 1) * ITEMS_PER_PAGE,
      currentPage * ITEMS_PER_PAGE
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingCategory) {
        await updateCategory(editingCategory.id, form);
      } else {
        await createCategory(form);
      }
      resetForm();
    } catch (err) {
      console.error(err);
    }
  };

  const handleEdit = (category: Category) => {
    setEditingCategory(category);
    setForm({ name: category.name, type: category.type || '' });
    setIsModalOpen(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure you want to delete this category?')) {
      try {
        await deleteCategory(id);
        // Если после удаления страница опустела, переходим на предыдущую
        if (paginatedCategories.length === 1 && currentPage > 1) {
          setCurrentPage(currentPage - 1);
        }
      } catch (err) {
        console.error(err);
      }
    }
  };

  const resetForm = () => {
    setForm({ name: '', type: '' });
    setEditingCategory(null);
    setIsModalOpen(false);
  };

  return (
      <div>
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
            <h1 className="page-title" style={{ margin: 0, lineHeight: 1.2 }}>Categories</h1>
            <p className="page-subtitle" style={{ margin: 0 }}>Organize your transactions</p>
          </div>
          <Button
              onClick={() => setIsModalOpen(true)}
              style={{ marginTop: '4px' }}
          >
            <Plus size={18} />
            Add Category
          </Button>
        </div>

        {error && <Alert message={error} onClose={clearError} />}

        <Card>
          <div
              className="filter-bar"
              style={{
                marginBottom: '1.5rem',
                borderBottom: '1px solid var(--border)',
                paddingBottom: '1.5rem'
              }}
          >
            <Search size={18} color="var(--text-secondary)" />
            <Input
                placeholder="Search categories..."
                value={filterName}
                onChange={(e) => setFilterName(e.target.value)}
                style={{ maxWidth: '300px' }}
            />
          </div>

          {loading && categories.length === 0 ? (
              <Loader />
          ) : categories.length === 0 ? (
              <EmptyState
                  icon={<FolderOpen size={48} color="var(--text-muted)" />}
                  message="No categories found. Create your first category!"
              />
          ) : (
              <>
                <div className="table-container">
                  <table style={{ width: '100%', tableLayout: 'fixed' }}>
                    <thead>
                    <tr>
                      <th style={{ width: '45%' }}>Name</th>
                      <th style={{ width: '35%' }}>Type</th>
                      <th style={{ width: '20%' }}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {paginatedCategories.map((category) => (
                        <tr key={category.id}>
                          <td style={{ fontWeight: '600' }}>{category.name}</td>
                          <td>
                            {category.type ? (
                                <Badge>{category.type}</Badge>
                            ) : (
                                <span style={{ color: 'var(--text-muted)' }}>-</span>
                            )}
                          </td>
                          <td>
                            <div className="actions">
                              <Button variant="ghost" icon onClick={() => handleEdit(category)}>
                                <Edit2 size={16} />
                              </Button>
                              <Button variant="ghost" icon onClick={() => handleDelete(category.id)}>
                                <Trash2 size={16} color="var(--danger)" />
                              </Button>
                            </div>
                          </td>
                        </tr>
                    ))}
                    </tbody>
                  </table>
                </div>

                <Pagination
                    currentPage={currentPage}
                    totalPages={totalPages}
                    onPageChange={setCurrentPage}
                />
              </>
          )}
        </Card>

        <Modal
            isOpen={isModalOpen}
            onClose={resetForm}
            title={editingCategory ? 'Edit Category' : 'Create Category'}
        >
          <form onSubmit={handleSubmit}>
            <FormGroup label="Category Name" required>
              <Input
                  value={form.name}
                  onChange={(e) => setForm({ ...form, name: e.target.value })}
                  placeholder="e.g., Food, Transport"
                  required
                  maxLength={50}
              />
            </FormGroup>
            <FormGroup label="Type">
              <Input
                  value={form.type}
                  onChange={(e) => setForm({ ...form, type: e.target.value })}
                  placeholder="e.g., Essential, Lifestyle"
                  maxLength={20}
              />
            </FormGroup>
            <div className="actions">
              <Button type="submit" disabled={loading}>
                {editingCategory ? 'Update' : 'Create'}
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