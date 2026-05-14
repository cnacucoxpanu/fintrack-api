import { useEffect, useState } from 'react';
import { useStore } from '../store';
import { Tag } from '../types';
import { Card, Button, Input, FormGroup, Modal, Alert, Loader, EmptyState } from '../components/UI';
import { Tag as TagIcon, Plus, Edit2, Trash2 } from 'lucide-react';

export default function Tags() {
  const { tags, loading, error, fetchTags, createTag, updateTag, deleteTag, clearError } = useStore();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingTag, setEditingTag] = useState<Tag | null>(null);
  const [form, setForm] = useState({ name: '' });

  useEffect(() => {
    fetchTags();
  }, [fetchTags]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingTag) {
        await updateTag(editingTag.id, form);
      } else {
        await createTag(form);
      }
      resetForm();
    } catch (err) {
      console.error(err);
    }
  };

  const handleEdit = (tag: Tag) => {
    setEditingTag(tag);
    setForm({ name: tag.name });
    setIsModalOpen(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure you want to delete this tag?')) {
      try {
        await deleteTag(id);
      } catch (err) {
        console.error(err);
      }
    }
  };

  const resetForm = () => {
    setForm({ name: '' });
    setEditingTag(null);
    setIsModalOpen(false);
  };

  return (
    <div>
      {/* Исправленная шапка для Tags */}
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
          <h1 className="page-title" style={{ margin: 0, lineHeight: 1.2 }}>Tags</h1>
          <p className="page-subtitle" style={{ margin: 0 }}>Label and organize your transactions</p>
        </div>
        <Button 
          onClick={() => setIsModalOpen(true)}
          style={{ marginTop: '4px' }}
        >
          <Plus size={18} />
          Add Tag
        </Button>
      </div>

      {error && <Alert message={error} onClose={clearError} />}

      {loading && tags.length === 0 ? (
        <Card>
          <Loader />
        </Card>
      ) : tags.length === 0 ? (
        <Card>
          <EmptyState
            icon={<TagIcon size={48} color="var(--text-muted)" />}
            message="No tags found. Create your first tag!"
          />
        </Card>
      ) : (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))', gap: '1rem' }}>
          {tags.map((tag) => (
            <Card key={tag.id}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                  <div
                    style={{
                      width: '40px',
                      height: '40px',
                      borderRadius: 'var(--radius-md)',
                      background: 'rgba(139, 92, 246, 0.15)',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                    }}
                  >
                    <TagIcon size={20} color="var(--accent-primary)" />
                  </div>
                  <div>
                    <div style={{ fontWeight: '600', fontSize: '1rem' }}>{tag.name}</div>
                  </div>
                </div>
                <div className="actions">
                  <Button variant="ghost" icon onClick={() => handleEdit(tag)}>
                    <Edit2 size={16} />
                  </Button>
                  <Button variant="ghost" icon onClick={() => handleDelete(tag.id)}>
                    <Trash2 size={16} color="var(--danger)" />
                  </Button>
                </div>
              </div>
            </Card>
          ))}
        </div>
      )}

      <Modal isOpen={isModalOpen} onClose={resetForm} title={editingTag ? 'Edit Tag' : 'Create Tag'}>
        <form onSubmit={handleSubmit}>
          <FormGroup label="Tag Name" required>
            <Input
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
              placeholder="e.g., Urgent, Personal"
              required
              maxLength={30}
            />
          </FormGroup>
          <div className="actions">
            <Button type="submit" disabled={loading}>
              {editingTag ? 'Update' : 'Create'}
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