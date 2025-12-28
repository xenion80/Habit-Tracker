## 📡 API Endpoints

Base URL (local):

```
http://localhost:8080
```

---

## 👤 Users

### Create user

```
POST /users
```

**Body**

```json
{
  "name": "John Doe"
}
```

**Response**

* `201 Created`

---

### Get user with habits

```
GET /users/{userId}
```

**Response**

* `200 OK`
* Returns user details with associated habits

---

### Delete user

```
DELETE /users/{userId}
```

**Response**

* `204 No Content`

---

## 🔁 Habits

### Create habit for user

```
POST /users/{userId}/habits
```

**Body**

```json
{
  "title": "Morning Run"
}
```

**Response**

* `201 Created`

---

### Get all habits for user

```
GET /users/{userId}/habits?page=0&size=10&sort=id,asc
```

**Response**

* `200 OK`
* Paginated list of habits

---

### Get habit by ID

```
GET /users/{userId}/habits/{habitId}
```

**Response**

* `200 OK`
* `403 Forbidden` (if not owner)
* `404 Not Found` (if deleted or missing)

---

### Complete habit (daily)

```
POST /users/{userId}/habits/{habitId}/complete
```

**Response**

* `200 OK`
* `409 Conflict` (if already completed today)
* `403 Forbidden` (ownership violation)

---

### Rename habit

```
PATCH /users/{userId}/habits/{habitId}
```

**Body**

```json
{
  "title": "Evening Run"
}
```

**Response**

* `200 OK`
* `400 Bad Request` (blank title)
* `409 Conflict` (habit deleted)
* `403 Forbidden` (ownership violation)

---

### Delete habit (soft delete)

```
DELETE /users/{userId}/habits/{habitId}
```

**Response**

* `204 No Content`

---

### Get habit logs

```
GET /users/{userId}/habits/{habitId}/logs?from=2024-01-01&to=2024-01-31&page=0&size=10
```

**Response**

* `200 OK`
* Paginated habit completion logs
* Ownership enforced

---

## 🗂️ Categories

### Create category

```
POST /users/{userId}/categories
```

**Body**

```json
{
  "title": "Health"
}
```

**Response**

* `201 Created`

---

### Get categories for user

```
GET /users/{userId}/categories?page=0&size=10&sort=id,asc
```

**Response**

* `200 OK`

---

### Rename category

```
PATCH /users/{userId}/categories/{categoryId}
```

**Body**

```json
{
  "title": "Fitness"
}
```

**Response**

* `200 OK`
* Ownership + soft delete enforced

---

### Delete category (soft delete)

```
DELETE /users/{userId}/categories/{categoryId}
```

**Response**

* `204 No Content`
* Associated tasks are soft-deleted

---

## ✅ Tasks

### Create task under category

```
POST /users/{userId}/categories/{categoryId}/tasks
```

**Body**

```json
{
  "title": "Stretching"
}
```

**Response**

* `201 Created`

---

### Get tasks by category

```
GET /categories/{categoryId}/tasks?page=0&size=10&sort=id,asc
```

**Response**

* `200 OK`
* Only non-deleted tasks returned

---

### Complete task

```
POST /tasks/{taskId}/complete
```

**Response**

* `200 OK`

---

### Rename task

```
PATCH /users/{userId}/tasks/{taskId}
```

**Body**

```json
{
  "title": "Warm-up Stretch"
}
```

**Response**

* `200 OK`
* Ownership + soft delete enforced

---

### Delete task (soft delete)

```
DELETE /users/{userId}/tasks/{taskId}
```

**Response**

* `204 No Content`

---

## ❤️ Health Check

```
GET /actuator/health
```

**Response**

```json
{
  "status": "UP"
}
```

Used for deployment and monitoring.

---

## 🔐 Authorization Notes

* All user-owned resources enforce **ownership checks**
* Unauthorized access returns **403 Forbidden**
* Deleted resources are never exposed

---

