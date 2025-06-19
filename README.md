# 🧾 Online Auction System

An interactive and responsive auction system where users can seamlessly list products, place real-time bids, and track auction statistics. The platform operates entirely on the client side, utilizing LocalStorage for data management, and simulates backend functionality using a dummy JSON API for fetching product data.

---

## 🚀 Features

- 🔒 Responsive Login & Signup Pages with validations
- 🏠 Landing page with CTA for user onboarding
- 🗂 Product listing page (pre-auction items)
- 📊 Real-time auction dashboard
- 💬 Toast notifications for bid actions & validation
- 📦 Persistent data storage using LocalStorage and Dummy JSON API integration for simulating product fetch
- ⏱ Countdown timers for each auction
- 📉 Skeleton loaders and empty state handling
- 📈 Dashboard summary cards

---

## 🧩 Flow Overview

### 1. **Landing Page**
- Users visit the home page and navigate to **Sign Up** / **Login**.

### 2. **Signup / Login**
- Users register or log in via a validated form.
- On success, redirected to the **Product Listing** page.

### 3. **Product Listing (Pre-Auction Items)**
- Lists all products fetched from `DummyJson Api`,`localStorage`.
- Each product includes image, name, category, bid price, and a **"Bid Now"** button.
- On clicking **Bid Now**, the item moves to the **Dashboard** for live bidding.

### 4. **Bidding Dashboard (Live Auctions)**

#### 🔹 Auction Items View
- Displays all active auction items with:
  - Image, name, category
  - Highest bid, total bids, time left
  - Status (Active/Ended) and **Bid** button

#### 🔹 Placing a Bid
- Clicking **Bid** opens a modal.
- **Bid Validations**:
  - Must be higher than the current highest
  - Auction must still be active
  - Dynamically updates the **recent bid**
- On success:
  - Updates DOM
  - Saves bid to `localStorage`
  - Displays a success toast

#### 🔹 Auction Timer & Status
- Each auction has a countdown timer.
- On expiry, status auto-updates to **Ended** and disables bidding.

#### 🔹 Dashboard Summary
- Summary cards show:
  - Total active auctions
  - Total bids placed
  - New users (static)
  - Estimated revenue (static)

---

## 🛠 Tech Stack

- **Languages**: HTML5, CSS3, JavaScript (ES6)
- **Design**: Bootstrap / Custom CSS
- **Data Handling**: `DummyJson Api`,`localStorage`

---

## 📦 Installation & Usage

```bash
# Clone the repository
git clone https://github.com/your-username/online-auction-system.git

# Navigate to project
cd online-auction-system

# Open with Live Server or directly open index.html
