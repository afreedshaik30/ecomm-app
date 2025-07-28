# 🛒 Bmart eCommerce Backend

This is a Spring Boot-based backend API for an eCommerce platform named **Bmart**. It supports user and admin authentication, product catalog management, cart and order functionality, secure payments, and address handling.

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

## ✅ Tech Stack

- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Maven
- REST APIs
- Swagger (recommended for API testing)

---

## 🚀 Run the App

```bash
# Clone the repo
git clone https://github.com/your-username/bmart-backend.git

# Build the project
cd bmart-backend
./mvnw clean install

# Run the app
./mvnw spring-boot:run
