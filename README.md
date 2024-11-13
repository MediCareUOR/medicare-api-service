# Pharmaceutical Drug Query System - Backend

This repository contains the backend services for the **Pharmaceutical Drug Query System**. Built with **Spring Boot**, the backend handles API requests, data processing, and database operations to manage drug information and pharmacy inventory in real time.

## Project Overview

The backend of the Pharmaceutical Drug Query System enables real-time drug availability updates, pharmacy management, and user authentication. It provides RESTful APIs that connect the frontend to the database and manage core business logic, ensuring secure, efficient access to information.

## Features

- **User Authentication**: Role-based access for customers, pharmacists, and administrators.
- **Real-Time Inventory Updates**: Pharmacies can add, update, and delete drugs from their inventory.
- **Drug Search API**: Allows users to search drugs by name, brand, or manufacturer.
- **Pharmacy Locator**: Returns nearby pharmacies that stock specific medications.
- **Notifications**: Sends email notifications (e.g., OTP for customers, approval notifications for pharmacists).
- **Role-Based Access Control**: Secure data access based on user roles.

## Technologies Used

- **Spring Boot**: Core framework for REST API development.
- **MySQL**: Database management for storing drug, pharmacy, and user data.
- **JWT (JSON Web Tokens)**: For secure authentication and authorization.
- **Google Mail API**: For sending OTPs and notifications to users.
- **Postman**: Used for API testing.

## Prerequisites

- **Java 17**: Ensure you have Java 17 installed.
- **Maven**: Used for building and managing dependencies.
- **MySQL**: Database server to manage data storage.

## LICENSE

[MIT LICENSE](LICENSE)
