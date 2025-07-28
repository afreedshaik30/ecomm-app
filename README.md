
# 📦 B-Mart E-Commerce Application

A full-stack e-commerce app with user registration, JWT authentication, admin panel, cart, orders, and product management.

---

## 🛠️ Tech Stack

| Layer        | Technology                   |
|-------------|------------------------------|
| Frontend     | React (Vite), Axios, React Router |
| Backend      | Spring Boot, Spring Data JPA, Spring Security, JWT, REST APIs, Maven |
| Database     | MySQL                        |
| Docs         | Swagger UI                   |

---

## 📁 Project Structure

### 📦 `/backend` (Spring Boot)

- RESTful API with secure login/registration (JWT)
- Admin & User roles
- CRUD: Products, Users, Cart, Orders
- Role Based Authentication

### 🌐 `/frontend` (Vite + React)

- JWT-based login for User/Admin
- Cart system
- Admin panel for managing users, products, and orders
- Route guards for auth

---

## 🔐 Authentication

### `/bmart/auth`
| Method | Endpoint      | Access | Description         |
|--------|---------------|--------|---------------------|
| POST   | `/login`      | P     | Login as user/admin |

---

## 👤 Customer APIs

### `/bmart/customers`
| Method | Endpoint              | Access | Description                  |
|--------|-----------------------|--------|------------------------------|
| POST   | `/`                   | P     | Register as a customer       |
| PUT    | `/update-password`    | U      | Update own password          |
| GET    | `/me`                 | U      | Get own details              |
| GET    | `/all`                | A      | Admin fetches all customers  |
| DELETE | `/deactivate`         | U      | Deactivate own account       |
| PUT    | `/reactivate`         | U      | Reactivate own account       |

---

## 🛡️ Admin APIs

### `/bmart/admin`
| Method | Endpoint                          | Access | Description                        |
|--------|-----------------------------------|--------|------------------------------------|
| POST   | `/?secretKey=208T1A0560`          | P     | Register admin with secret key     |
| PUT    | `/update-password/{adminId}`      | A      | Admin updates password             |
| DELETE | `/deactivate/{userId}`            | A      | Admin deactivates a user           |
| PUT    | `/reactivate/{userId}`            | A      | Admin reactivates any user         |

---

## 🛒 Cart APIs

### `/bmart/cart`
| Method | Endpoint                   | Access | Description                      |
|--------|----------------------------|--------|----------------------------------|
| POST   | `/add-product/{productId}` | U      | Add product to cart              |
| GET    | `/`                        | U      | View current cart                |
| PUT    | `/increase/{productId}`    | U      | Increase product quantity        |
| PUT    | `/decrease/{productId}`    | U      | Decrease product quantity        |
| DELETE | `/remove/{productId}`      | U      | Remove product from cart         |
| DELETE | `/empty`                   | U      | Empty the entire cart            |

---

## 📦 Order APIs

### `/bmart/orders`
| Method | Endpoint             | Access | Description                           |
|--------|----------------------|--------|---------------------------------------|
| POST   | `/place`             | U      | Place an order                        |
| GET    | `/{orderId}`         | U/A    | View order (user or admin)            |
| GET    | `/`                  | U      | View own orders                       |
| GET    | `/all`               | A      | Admin views all orders                |
| GET    | `/date/{date}`       | A      | Admin views orders by date            |
| DELETE | `/{orderId}`         | U      | Delete own order                      |

---

## 📍 Address APIs

### `/bmart/address`
| Method | Endpoint           | Access | Description                            |
|--------|--------------------|--------|----------------------------------------|
| POST   | `/`                | U/A    | Add address                            |
| GET    | `/`                | U/A    | View all own addresses                 |
| PUT    | `/{addressId}`     | U/A    | Update address if owned                |
| DELETE | `/{addressId}`     | U/A    | Delete address if owned                |

---

## 🛍️ Product APIs

### `/bmart/products`
| Method | Endpoint                          | Access | Description                      |
|--------|-----------------------------------|--------|----------------------------------|
| POST   | `/add`                            | A      | Add new product                  |
| GET    | `/product-by-name/{name}`         | P     | Search product by name           |
| GET    | `/all`                            | P     | Get all products (with filters)  |
| GET    | `/category/{category}`            | P     | Get products by category         |
| GET    | `/{productId}`                    | P     | Get product by ID                |
| PUT    | `/update/{productId}`             | A      | Update product                   |
| DELETE | `/{productId}`                    | A      | Delete product                   |

---

## 💳 Payment APIs

### `/bmart/payment`
| Method | Endpoint | Access | Description                 |
|--------|----------|--------|-----------------------------|
| POST   | `/pay`   | U      | Make payment for an order   |

---

## 🙋 Logged-in User Info (Optional)

### `/bmart`
| Method | Endpoint | Access | Description                       |
|--------|----------|--------|-----------------------------------|
| GET    | `/me`    | U/A    | Get details of logged-in user     |

---

## 🔑 Access Level Legend

- **P** – No Authentication Required
- **U** – Authenticated User
- **A** – Admin
- **U/A** – Either User or Admin

---
---

# 🌐 Frontend (Vite + React)

### ✅ Prerequisites:
- Node.js v18+
- Vite
- Axios
- React-router-dom

### 🧪 Run the Frontend

```bash
npm run dev
```

Visit: [http://localhost:5173](http://localhost:5173)

---

## 🧩 Frontend Routes Overview

| Route | Page | Access |
|-------|------|--------|
| `/` | Home | Public |
| `/login`, `/register-user` | Auth | Public |
| `/product` | All products | Public |
| `/user/cart` | Cart | Auth: User |
| `/user/order-details` | Orders | Auth: User |
| `/user/profile/:id` | Profile | Auth: User |
| `/admin/admin` | Admin Dashboard | Auth: Admin |
| `/admin/add-product` | Add Product | Auth: Admin |
| `/admin/view-user/:id` | View User | Auth: Admin |
| `*` | 404 Not Found | All |

---

## 🔐 Authentication Flow

- JWT token saved in `localStorage` on login
- Axios interceptors attach token to requests
- Protected routes with:
  - `Privateroute` for Users
  - `Privaterouteadmin` for Admins

---

## 🧪 Sample Credentials

> You can pre-populate MySQL or register new accounts via APIs.

```
Admin Login:
Email: admin@bmart.com
Password: admin123

User Login:
Email: user@bmart.com
Password: user123
```

---

## 🚀 Deployment Tips

### 🔒 Backend:
- Host using: Render
- Used environment variables for secrets

### 🌐 Frontend:
- Deployed on Netlify
-  Used environment variables for secrets

---

## 🙌 Contributors

- Afreed Shaik — Full Stack Developer
