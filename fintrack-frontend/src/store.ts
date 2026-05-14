import { create } from 'zustand';
import { User, Account, Category, Tag, Transaction } from './types';
import { authApi, userApi, accountApi, categoryApi, tagApi, transactionApi } from './api';

interface AuthState {
  token: string | null;
  username: string | null;
  isAuthenticated: boolean;
  login: (username: string, password: string) => Promise<void>;
  register: (username: string, password: string) => Promise<void>;
  logout: () => void;
  initAuth: () => void;
}

interface AppState {
  users: User[];
  accounts: Account[];
  categories: Category[];
  tags: Tag[];
  transactions: Transaction[];
  loading: boolean;
  error: string | null;

  fetchUsers: () => Promise<void>;
  // Добавлен password в аргументы для создания пользователя
  createUser: (data: Omit<User, 'id'> & { password?: string }) => Promise<void>;
  updateUser: (id: number, data: Omit<User, 'id'>) => Promise<void>;
  deleteUser: (id: number) => Promise<void>;

  fetchAccounts: () => Promise<void>;
  createAccount: (data: Omit<Account, 'id'>) => Promise<void>;
  updateAccount: (id: number, data: Omit<Account, 'id'>) => Promise<void>;
  deleteAccount: (id: number) => Promise<void>;

  fetchCategories: (name?: string) => Promise<void>;
  createCategory: (data: Omit<Category, 'id'>) => Promise<void>;
  updateCategory: (id: number, data: Omit<Category, 'id'>) => Promise<void>;
  deleteCategory: (id: number) => Promise<void>;

  fetchTags: () => Promise<void>;
  createTag: (data: Omit<Tag, 'id'>) => Promise<void>;
  updateTag: (id: number, data: Omit<Tag, 'id'>) => Promise<void>;
  deleteTag: (id: number) => Promise<void>;

  fetchTransactions: (direction?: 'INCOME' | 'EXPENSE') => Promise<void>;
  createTransaction: (data: Omit<Transaction, 'id' | 'createdAt'>) => Promise<void>;
  deleteTransaction: (id: number) => Promise<void>;

  clearError: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  token: null,
  username: null,
  isAuthenticated: false,

  initAuth: () => {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    if (token && username) {
      set({ token, username, isAuthenticated: true });
    }
  },

  login: async (username: string, password: string) => {
    const res = await authApi.login({ username, password });
    localStorage.setItem('token', res.data.token);
    localStorage.setItem('username', res.data.username);
    set({ token: res.data.token, username: res.data.username, isAuthenticated: true });
  },

  register: async (username: string, password: string) => {
    const res = await authApi.register({ username, password });
    localStorage.setItem('token', res.data.token);
    localStorage.setItem('username', res.data.username);
    set({ token: res.data.token, username: res.data.username, isAuthenticated: true });
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    set({ token: null, username: null, isAuthenticated: false });
  },
}));

export const useStore = create<AppState>((set) => ({
  users: [],
  accounts: [],
  categories: [],
  tags: [],
  transactions: [],
  loading: false,
  error: null,

  fetchUsers: async () => {
    set({ loading: true, error: null });
    try {
      const res = await userApi.getAll();
      set({ users: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
    }
  },

  createUser: async (data) => {
    set({ loading: true, error: null });
    try {
      // Используем приведение к any для совместимости с интерфейсом userApi.create
      await userApi.create(data as any);
      const res = await userApi.getAll();
      set({ users: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  updateUser: async (id, data) => {
    set({ loading: true, error: null });
    try {
      await userApi.update(id, data);
      const res = await userApi.getAll();
      set({ users: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  deleteUser: async (id) => {
    set({ loading: true, error: null });
    try {
      await userApi.delete(id);
      const res = await userApi.getAll();
      set({ users: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  fetchAccounts: async () => {
    set({ loading: true, error: null });
    try {
      const res = await accountApi.getAll();
      set({ accounts: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
    }
  },

  fetchCategories: async (name?: string) => {
    set({ loading: true, error: null });
    try {
      const res = await categoryApi.getAll(name);
      set({ categories: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
    }
  },

  fetchTags: async () => {
    set({ loading: true, error: null });
    try {
      const res = await tagApi.getAll();
      set({ tags: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
    }
  },

  fetchTransactions: async (direction?: 'INCOME' | 'EXPENSE') => {
    set({ loading: true, error: null });
    try {
      const res = await transactionApi.getAll(direction);
      set({ transactions: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
    }
  },

  createAccount: async (data) => {
    set({ loading: true, error: null });
    try {
      await accountApi.create(data);
      const res = await accountApi.getAll();
      set({ accounts: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  updateAccount: async (id, data) => {
    set({ loading: true, error: null });
    try {
      await accountApi.update(id, data);
      const res = await accountApi.getAll();
      set({ accounts: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  deleteAccount: async (id) => {
    set({ loading: true, error: null });
    try {
      await accountApi.delete(id);
      const res = await accountApi.getAll();
      set({ accounts: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  createCategory: async (data) => {
    set({ loading: true, error: null });
    try {
      await categoryApi.create(data);
      const res = await categoryApi.getAll();
      set({ categories: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  updateCategory: async (id, data) => {
    set({ loading: true, error: null });
    try {
      await categoryApi.update(id, data);
      const res = await categoryApi.getAll();
      set({ categories: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  deleteCategory: async (id) => {
    set({ loading: true, error: null });
    try {
      await categoryApi.delete(id);
      const res = await categoryApi.getAll();
      set({ categories: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  createTag: async (data) => {
    set({ loading: true, error: null });
    try {
      await tagApi.create(data);
      const res = await tagApi.getAll();
      set({ tags: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  updateTag: async (id, data) => {
    set({ loading: true, error: null });
    try {
      await tagApi.update(id, data);
      const res = await tagApi.getAll();
      set({ tags: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  deleteTag: async (id) => {
    set({ loading: true, error: null });
    try {
      await tagApi.delete(id);
      const res = await tagApi.getAll();
      set({ tags: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  createTransaction: async (data) => {
    set({ loading: true, error: null });
    try {
      await transactionApi.create(data);
      const res = await transactionApi.getAll();
      set({ transactions: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  deleteTransaction: async (id) => {
    set({ loading: true, error: null });
    try {
      await transactionApi.delete(id);
      const res = await transactionApi.getAll();
      set({ transactions: res.data, loading: false });
    } catch (err: any) {
      set({ error: err.userMessage || err.response?.data?.message || err.message, loading: false });
      throw err;
    }
  },

  clearError: () => set({ error: null }),
}));