import axios, { AxiosError } from 'axios';
import { User, Account, Category, Tag, Transaction, AuthRequest, AuthResponse } from './types';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error: AxiosError<{ message?: string; code?: string; details?: string[] }>) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      window.location.href = '/login';
    }

    // Extract the backend error message
    const backendMessage = error.response?.data?.message;
    if (backendMessage) {
      // Attach the backend message to the error object for easy access
      (error as any).userMessage = backendMessage;
    }

    return Promise.reject(error);
  }
);

export const authApi = {
  login: (data: AuthRequest) => api.post<AuthResponse>('/auth/login', data),
  register: (data: AuthRequest) => api.post<AuthResponse>('/auth/register', data),
};

export const userApi = {
  getAll: () => api.get<User[]>('/users'),
  create: (data: { name: string; email: string; password: string }) => api.post<User>('/users', data),
  update: (id: number, data: { name: string; email: string }) => api.put<User>(`/users/${id}`, data),
  delete: (id: number) => api.delete(`/users/${id}`),
};

export const accountApi = {
  getAll: () => api.get<Account[]>('/accounts'),
  getById: (id: number) => api.get<Account>(`/accounts/${id}`),
  create: (data: Omit<Account, 'id'>) => api.post<Account>('/accounts', data),
  update: (id: number, data: Omit<Account, 'id'>) => api.put<Account>(`/accounts/${id}`, data),
  delete: (id: number) => api.delete(`/accounts/${id}`),
};

export const categoryApi = {
  getAll: (name?: string) => api.get<Category[]>('/categories', { params: { name } }),
  getById: (id: number) => api.get<Category>(`/categories/${id}`),
  create: (data: Omit<Category, 'id'>) => api.post<Category>('/categories', data),
  update: (id: number, data: Omit<Category, 'id'>) => api.put<Category>(`/categories/${id}`, data),
  delete: (id: number) => api.delete(`/categories/${id}`),
};

export const tagApi = {
  getAll: () => api.get<Tag[]>('/tags'),
  getById: (id: number) => api.get<Tag>(`/tags/${id}`),
  create: (data: Omit<Tag, 'id'>) => api.post<Tag>('/tags', data),
  update: (id: number, data: Omit<Tag, 'id'>) => api.put<Tag>(`/tags/${id}`, data),
  delete: (id: number) => api.delete(`/tags/${id}`),
};

export const transactionApi = {
  getAll: (direction?: 'INCOME' | 'EXPENSE') =>
    api.get<Transaction[]>('/transactions', { params: { direction } }),
  create: (data: Omit<Transaction, 'id' | 'createdAt'>) => api.post<void>('/transactions', data),
  delete: (id: number) => api.delete(`/transactions/${id}`),
};
