# Automated Grocery Inventory Dashboard

## Project Overview

The Automated Grocery Inventory Dashboard is a Spring Boot and MySQL based web application designed to manage grocery inventory efficiently.

The system provides a centralized platform for managing grocery items, inventory levels, stock transactions, and stock status. It is intended to reduce manual inventory management and provide a structured way to monitor stock information.

## Objectives

- Manage grocery item information.
- Maintain current inventory quantities.
- Record stock-in and stock-out transactions.
- Monitor stock availability.
- Identify low-stock items.
- Provide a structured backend for future dashboard and automation features.

## Technology Stack

- **Backend:** Java, Spring Boot
- **Build Tool:** Maven
- **Database:** MySQL
- **ORM:** Spring Data JPA / Hibernate
- **Frontend:** Thymeleaf
- **Version Control:** Git
- **Repository:** GitHub

## Project Structure

automated-grocery-inventory-dashboard/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── .mvn/
├── .gitignore
├── pom.xml
├── mvnw
└── mvnw.cmd

**#### Future Work**
- [ ] Dashboard analytics and visualizations
- [ ] Automated reorder recommendations
- [ ] Supplier management
- [ ] Automated notifications
- [ ] Advanced reporting

**## Admin and Customer Role Access**

### Completed

- Added Admin and Customer profile switching without login or authentication.
- Admin can:
  - Create, update, and delete inventory items.
  - Perform Stock In and Stock Out transactions.
  - View inventory exception alerts.
  - Access transaction history.
- Customer can:
  - View inventory.
  - Search inventory.
  - Filter inventory by category.
- Customer access is restricted from:
  - Adding, editing, or deleting items.
  - Performing Stock In or Stock Out transactions.
  - Accessing Transactions and Alerts pages.
- Implemented session-based role persistence across pages.
- Added backend access control for restricted Admin functionality.
- Added role-based sidebar navigation.
- Verified Customer and Admin access after merging the feature into `develop`.
