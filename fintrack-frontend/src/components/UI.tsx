import React from 'react';
import { X } from 'lucide-react';

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
}

export const Modal: React.FC<ModalProps> = ({ isOpen, onClose, title, children }) => {
  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2 className="modal-title">{title}</h2>
          <button className="modal-close" onClick={onClose}>
            <X size={20} />
          </button>
        </div>
        <div className="modal-body">{children}</div>
      </div>
    </div>
  );
};

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'danger' | 'ghost';
  icon?: boolean;
}

export const Button: React.FC<ButtonProps> = ({
  variant = 'primary',
  icon = false,
  className = '',
  children,
  ...props
}) => (
  <button
    className={`btn btn-${variant} ${icon ? 'btn-icon' : ''} ${className}`}
    {...props}
  >
    {children}
  </button>
);

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {}

export const Input: React.FC<InputProps> = (props) => (
  <input className="form-input" {...props} />
);

interface SelectProps extends React.SelectHTMLAttributes<HTMLSelectElement> {}

export const Select: React.FC<SelectProps> = (props) => (
  <select className="form-select" {...props} />
);

interface FormGroupProps {
  label: string;
  children: React.ReactNode;
  required?: boolean;
}

export const FormGroup: React.FC<FormGroupProps> = ({ label, children, required }) => (
  <div className="form-group">
    <label className="form-label">
      {label}
      {required && <span style={{ color: 'var(--danger)' }}> *</span>}
    </label>
    {children}
  </div>
);

interface CardProps {
  children: React.ReactNode;
  glass?: boolean;
  className?: string;
}

export const Card: React.FC<CardProps> = ({ children, glass = false, className = '' }) => (
  <div className={`card ${glass ? 'card-glass' : ''} ${className}`}>
    {children}
  </div>
);

interface BadgeProps {
  children: React.ReactNode;
  variant?: 'success' | 'danger' | 'default';
}

export const Badge: React.FC<BadgeProps> = ({ children, variant = 'default' }) => (
  <span className={`badge badge-${variant}`}>{children}</span>
);

interface AlertProps {
  message: string;
  onClose: () => void;
}

export const Alert: React.FC<AlertProps> = ({ message, onClose }) => (
  <div className="alert alert-error">
    <span>{message}</span>
    <button className="modal-close" onClick={onClose}>
      <X size={18} />
    </button>
  </div>
);

export const Loader: React.FC = () => (
  <div className="loader">
    <div className="spinner"></div>
  </div>
);

interface EmptyStateProps {
  icon?: React.ReactNode;
  message: string;
}

export const EmptyState: React.FC<EmptyStateProps> = ({ icon, message }) => (
  <div className="empty-state">
    {icon && <div className="empty-state-icon">{icon}</div>}
    <p>{message}</p>
  </div>
);
