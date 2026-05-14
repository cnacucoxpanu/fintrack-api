export interface User {
  id: number;
  name: string;
  email: string;
  accounts?: Account[];
}

export interface Account {
  id: number;
  name: string;
  balance: number;
  userId: number;
}

export interface Category {
  id: number;
  name: string;
  type?: string;
}

export interface Tag {
  id: number;
  name: string;
}

export interface Transaction {
  id?: number;
  amount: number;
  direction: 'INCOME' | 'EXPENSE';
  accountId: number;
  categoryId: number;
  tagIds?: number[];
  createdAt?: string;
}

export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  username: string;
}

export interface ApiError {
  message: string;
  status?: number;
}
