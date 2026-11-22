# Looma Backend API

Base URL (local): `http://localhost:8080`

All errors return JSON in this format:

```json
{ "status": 400, "error": "Bad Request", "message": "Missing parameter: userId" }
```

CORS is open to all origins while we develop locally.

## Users

### Register a user
`POST /api/users/register`

Body (JSON):
```json
{ "username": "sara", "email": "sara@test.com", "password": "123456" }
```
- `username` max 50 chars, `email` must be a valid email (max 100), `password` 6 to 72 chars

Success `201 Created`:
```json
{ "userId": 2, "username": "sara", "email": "sara@test.com", "role": "user" }
```
Errors: `400` invalid fields, `409` email already registered

### Get a user
`GET /api/users/{id}`

Success `200 OK`: same shape as above. Error: `404` user not found.

## Wardrobe items

Allowed categories (not case sensitive, saved in the correct form): `Tops`, `Bottoms`, `Dresses`, `Shoes`, `Accessories`

### Add an item
`POST /api/items`

Body (JSON):
```json
{
  "userId": 1,
  "itemName": "Red Sneakers",
  "category": "Shoes",
  "brand": "Nike",
  "productNumber": "N300",
  "imageUrl": "/uploads/3f2a1c7e-....jpg"
}
```
- Required: `userId`, `itemName`, `category`
- Optional: `brand`, `productNumber`, `imageUrl`
- Max lengths: `itemName` 100, `brand` 50, `productNumber` 50, `imageUrl` 255

Success `201 Created`: the saved item (includes `itemId`).
Errors: `400` missing field or invalid category, `404` user not found

### Get one item
`GET /api/items/{itemId}`

Success `200 OK`: the item. Error: `404` item not found.

### Edit an item
`PUT /api/items/{itemId}`

Body: same as "Add an item".
- It replaces all fields. Optional fields that are not sent (`brand`, `productNumber`, `imageUrl`) become `null`, so always send the current values you want to keep.
- `userId` is required in the body, but the owner of the item does not change.

Success `200 OK`: the updated item.
Errors: `400` missing field or invalid category, `404` item not found

### List items (with optional category filter)
`GET /api/items?userId=1`
`GET /api/items?userId=1&category=Tops`

- `userId` is required
- `category` is optional (same allowed values as above)

Success `200 OK`:
```json
[
  {
    "itemId": 1,
    "userId": 1,
    "itemName": "White Shirt",
    "category": "Tops",
    "brand": "Zara",
    "productNumber": "Z100",
    "imageUrl": null
  }
]
```
Errors: `400` missing userId or invalid category

### Delete an item
`DELETE /api/items/{itemId}`

Success: `204 No Content` (empty body). Error: `404` item not found.

## Images

### Upload an image
`POST /api/images`

Send as `multipart/form-data` with one field named `file`.
- Allowed: JPG, PNG, WEBP, GIF. Max size 5MB.

Success `201 Created`:
```json
{ "imageUrl": "/uploads/3f2a1c7e-....jpg" }
```
Save this `imageUrl` in the item's `imageUrl` field. To show the image, use `http://localhost:8080` + `imageUrl`.

Errors: `400` no file, `413` file too large, `415` not an image