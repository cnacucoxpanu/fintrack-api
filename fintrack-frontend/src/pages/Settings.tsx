import { Card } from '../components/UI';
import { User, Bell, Shield, Palette } from 'lucide-react';

export default function Settings() {
    return (
        <div>
            <div className="page-header">
                <h1 className="page-title">Settings</h1>
                <p className="page-subtitle">Manage your account preferences</p>
            </div>

            <div style={{ display: 'grid', gap: '1.5rem', maxWidth: '800px' }}>
                <Card>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem' }}>
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
                            <User size={24} color="var(--accent-primary)" />
                        </div>
                        <div>
                            <h3 style={{ fontSize: '1.125rem', fontWeight: '700', marginBottom: '0.25rem' }}>
                                Profile Settings
                            </h3>
                            <p style={{ color: 'var(--text-secondary)', fontSize: '0.9375rem' }}>
                                Update your personal information
                            </p>
                        </div>
                    </div>
                    <div style={{ paddingLeft: '4rem', color: 'var(--text-secondary)' }}>
                        Profile management coming soon
                    </div>
                </Card>

                <Card>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem' }}>
                        <div
                            style={{
                                width: '48px',
                                height: '48px',
                                borderRadius: 'var(--radius-md)',
                                background: 'rgba(16, 185, 129, 0.15)',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                            }}
                        >
                            <Bell size={24} color="var(--success)" />
                        </div>
                        <div>
                            <h3 style={{ fontSize: '1.125rem', fontWeight: '700', marginBottom: '0.25rem' }}>
                                Notifications
                            </h3>
                            <p style={{ color: 'var(--text-secondary)', fontSize: '0.9375rem' }}>
                                Configure notification preferences
                            </p>
                        </div>
                    </div>
                    <div style={{ paddingLeft: '4rem', color: 'var(--text-secondary)' }}>
                        Notification settings coming soon
                    </div>
                </Card>

                <Card>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem' }}>
                        <div
                            style={{
                                width: '48px',
                                height: '48px',
                                borderRadius: 'var(--radius-md)',
                                background: 'rgba(239, 68, 68, 0.15)',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                            }}
                        >
                            <Shield size={24} color="var(--danger)" />
                        </div>
                        <div>
                            <h3 style={{ fontSize: '1.125rem', fontWeight: '700', marginBottom: '0.25rem' }}>
                                Security
                            </h3>
                            <p style={{ color: 'var(--text-secondary)', fontSize: '0.9375rem' }}>
                                Manage password and security options
                            </p>
                        </div>
                    </div>
                    <div style={{ paddingLeft: '4rem', color: 'var(--text-secondary)' }}>
                        Security settings coming soon
                    </div>
                </Card>

                <Card>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem' }}>
                        <div
                            style={{
                                width: '48px',
                                height: '48px',
                                borderRadius: 'var(--radius-md)',
                                background: 'rgba(245, 158, 11, 0.15)',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                            }}
                        >
                            <Palette size={24} color="var(--warning)" />
                        </div>
                        <div>
                            <h3 style={{ fontSize: '1.125rem', fontWeight: '700', marginBottom: '0.25rem' }}>
                                Appearance
                            </h3>
                            <p style={{ color: 'var(--text-secondary)', fontSize: '0.9375rem' }}>
                                Currently using Dark Mode theme
                            </p>
                        </div>
                    </div>
                    <div style={{ paddingLeft: '4rem', color: 'var(--text-secondary)' }}>
                        Theme customization coming soon
                    </div>
                </Card>
            </div>
        </div>
    );
}