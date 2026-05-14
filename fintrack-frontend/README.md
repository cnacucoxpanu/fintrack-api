# FinTrack - Professional Financial Management System

Enterprise-grade financial management application with modern architecture and security best practices.

## Security Audit Summary

### ✅ Fixed Security Issues

1. **Authentication Endpoints Protection**
   - Removed unauthorized access to `/api/transactions/**` and `/api/accounts/**`
   - All endpoints now require JWT authentication except `/api/auth/**`

2. **JWT Token Error Handling**
   - Added try-catch block in `JwtAuthenticationFilter` to handle malformed tokens
   - Security context is properly cleared on token validation failures

3. **N+1 Query Prevention**
   - Added `@EntityGraph` to `TransactionRepository.findByDirection()` method
   - Optimized lazy loading with proper fetch strategies

### Backend Architecture

**Technology Stack:**
- Spring Boot 3.x
- Spring Security with JWT
- JPA/Hibernate with optimized queries
- PostgreSQL database
- BCrypt password encoding

**Security Features:**
- Stateless JWT authentication
- CSRF protection disabled (stateless API)
- Password encryption with BCrypt
- Session management: STATELESS
- Protected endpoints with role-based access

### Frontend Architecture

**Technology Stack:**
- React 18 with TypeScript
- Zustand for state management
- Axios with JWT interceptors
- React Router v6
- Lucide React icons

**Design System:**
- Deep slate/charcoal theme (#0f172a, #1e293b, #334155)
- Mesh gradient background
- Glassmorphism effects with backdrop-blur
- Professional color palette:
  - Primary: #8b5cf6 (Violet)
  - Success: #10b981 (Emerald)
  - Danger: #ef4444 (Red)
  - Warning: #f59e0b (Amber)

## Setup Instructions

### Backend Setup

1. Configure database in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fintrack
spring.datasource.username=your_username
spring.datasource.password=your_password
```

2. Set JWT secret (IMPORTANT - change in production):
```properties
jwt.secret=your-base64-encoded-secret-key-minimum-256-bits
jwt.expiration=86400000
```

3. Run the application:
```bash
./gradlew bootRun
```

### Frontend Setup

1. Install dependencies:
```bash
cd fintrack-frontend
npm install
```

2. Start development server:
```bash
npm run dev
```

3. Access the application at `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Protected Endpoints (Require JWT)
- `GET /api/users` - List all users
- `GET /api/accounts` - List all accounts
- `POST /api/accounts` - Create account
- `GET /api/transactions` - List transactions
- `POST /api/transactions` - Create transaction
- `GET /api/categories` - List categories
- `GET /api/tags` - List tags

## Features

### Core Functionality
- ✅ User authentication and authorization
- ✅ Account management with balance tracking
- ✅ Transaction recording (income/expense)
- ✅ Category organization
- ✅ Tag system for flexible labeling
- ✅ Real-time dashboard statistics
- ✅ Advanced filtering and search

### Relationships
- **OneToMany**: User → Accounts
- **ManyToMany**: Transaction ↔ Tags
- **ManyToOne**: Transaction → Account, Category

### UI/UX Features
- Modern dark theme with gradient mesh background
- Glassmorphism cards with backdrop blur
- Smooth animations and transitions
- Responsive design for all devices
- Interactive data visualization
- Real-time balance calculations

## Security Best Practices

### Production Deployment Checklist

1. **JWT Configuration**
   - Generate strong random secret key (minimum 256 bits)
   - Use environment variables for sensitive data
   - Set appropriate token expiration time

2. **Database Security**
   - Use strong database passwords
   - Enable SSL/TLS for database connections
   - Implement database connection pooling

3. **HTTPS**
   - Enable HTTPS in production
   - Use valid SSL certificates
   - Implement HSTS headers

4. **CORS Configuration**
   - Restrict allowed origins to your domain
   - Remove development URLs from production config

5. **Logging**
   - Implement proper logging without exposing sensitive data
   - Monitor authentication failures
   - Set up alerts for suspicious activities

## Code Quality Standards

- Clean, modular architecture
- Professional English-only codebase
- Comprehensive error handling
- Optimized database queries
- Type-safe TypeScript implementation
- Consistent code formatting

## Performance Optimizations

- EntityGraph for eager loading prevention
- Query result caching
- Lazy loading with proper fetch strategies
- Optimized bundle size with Vite
- Efficient state management with Zustand

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## License

Proprietary - All rights reserved

## Support

For issues and feature requests, please contact the development team.
