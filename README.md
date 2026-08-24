# 🛠️ ShohojSeba

**ShohojSeba** is an Android-based local service marketplace designed to connect customers with nearby service providers while giving administrators tools to manage the platform.

The application supports three user roles:

- 👤 **Customer**
- 🧑‍🔧 **Provider**
- 🛡️ **Admin**

ShohojSeba includes role-based navigation, service discovery, area filtering, booking management, quotation workflows, promotional pricing, notifications, favorites, reviews, service reminders, provider availability, service-area management, and administrative moderation.

The application is developed using **Kotlin** and **Jetpack Compose**, with **Supabase** providing authentication and backend data storage.

---

## 📌 Table of Contents

- [Project Overview](#-project-overview)
- [Main Objectives](#-main-objectives)
- [User Roles](#-user-roles)
- [Core Features](#-core-features)
- [Booking Workflow](#-booking-workflow)
- [Quotation Workflow](#-custom-quotation-workflow)
- [Promotional Booking](#-promotional-booking)
- [Notifications](#-notifications)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Main Data Entities](#-main-data-entities)
- [Navigation](#-application-navigation)
- [UI Design](#-ui-design)
- [Setup](#-setup-instructions)
- [Supabase Configuration](#-supabase-configuration)
- [Demo Workflows](#-demo-workflows)
- [Testing Checklist](#-testing-checklist)
- [Known Limitations](#-known-scope--limitations)
- [Security Notes](#-security-notes)
- [Repository Structure](#-repository-structure)
- [AI Usage](#-ai-usage-declaration)

---

# 📱 Project Overview

Finding a trusted local service provider can be difficult when information about services, pricing, availability, location coverage, and communication is scattered across different sources.

**ShohojSeba** provides a centralized mobile platform where customers can:

- Discover local services
- Select providers serving their area
- Create service bookings
- Request custom quotations
- Track booking progress
- Receive notifications
- Save favorite services
- Submit provider reviews

Providers can manage their:

- Services
- Availability
- Service areas
- Booking requests
- Quotations
- Job completion
- Notifications

Administrators can manage:

- Service categories
- Service areas
- Customers
- Providers
- Provider verification
- Provider account status
- Services
- Reviews

---

# 🎯 Main Objectives

The main objectives of ShohojSeba are to:

1. Provide a simple mobile platform for finding local services.
2. Support separate workflows for customers, providers, and administrators.
3. Allow customers to discover providers according to their selected service area.
4. Support both direct booking and custom quotation requests.
5. Keep customers and providers informed through in-app notifications.
6. Allow providers to control their availability and service coverage.
7. Allow customers to save favorite services and review completed jobs.
8. Provide administrative control over providers, services, categories, areas, and reviews.
9. Provide a modern and mobile-friendly interface using Jetpack Compose.

---

# 👥 User Roles

## 👤 Customer

A customer can:

- Register and log in
- Select a service area
- Browse service categories
- Search categories from the home screen
- View providers/services available in the selected area
- View provider experience, verification, and availability
- Book a service
- Select preferred booking date and time
- Enter service address and problem description
- Request a custom quotation
- Receive provider quotations
- Accept or reject quotations
- Track booking status
- View booking history
- Filter bookings according to status
- Receive booking notifications
- Open relevant booking pages from notifications
- Save completed services to favorites
- Rebook saved services
- Submit reviews after service completion
- View provider reviews
- View service reminders
- Receive promotional pricing where applicable
- Log out safely

---

## 🧑‍🔧 Provider

A provider can:

- Register and log in
- Maintain a provider profile
- Set availability status
- Select service areas
- Add new services
- Set service price and duration
- Add service descriptions
- View incoming service requests
- Accept normal bookings
- Reject bookings
- Receive quotation requests
- Send custom quotation prices
- Send quotation messages
- Track quotation status
- Complete accepted jobs
- Receive provider notifications
- Open service requests from notifications
- View currently offered services
- Navigate using a persistent provider navigation bar
- Log out safely

### Provider Availability States

Providers can set their status to:

```text
AVAILABLE
BUSY
UNAVAILABLE
```

---

## 🛡️ Admin

An administrator can:

- Log in using an Admin account
- Manage service categories
- Add service areas
- Edit service areas
- Delete service areas
- View registered customers
- View providers
- Verify providers
- Remove provider verification
- Suspend providers
- Reactivate providers
- Remove providers from active platform use
- View provider services
- Remove services
- Restore services
- View customer reviews
- Delete inappropriate reviews
- Log out safely

---

# 🚀 Core Features

## 🔐 Authentication and Role-Based Access

ShohojSeba uses **Supabase Authentication** for email/password authentication.

After login, the application determines whether the authenticated account belongs to a:

```text
CUSTOMER
PROVIDER
ADMIN
```

The user is then redirected to the appropriate dashboard.

The application also maintains the currently resolved Customer or Provider ID through an in-memory session.

During logout:

1. The user is signed out from Supabase.
2. Local session information is cleared.
3. The navigation back stack is cleared.
4. The user is redirected to the Login screen.

This prevents users from reopening protected screens using the Android Back button after logout.

---

# 📍 Area-Based Service Discovery

Customers select a service area before browsing available services.

The selected area is passed into the service-discovery workflow so the application can display providers who serve that location.

Providers can separately configure the areas where they offer their services.

This creates the relationship:

```text
Customer selects area
        ↓
Customer selects category
        ↓
Application finds providers/services
        ↓
Only relevant service-area results are displayed
```

---

# 🗂️ Service Categories and Services

Services are organized into categories.

Example workflow:

```text
Home
  ↓
Select Area
  ↓
Select Category
  ↓
Available Services
  ↓
Select Provider
  ↓
Booking
```

Providers can create services containing information such as:

- Service name
- Category
- Price
- Duration
- Description

Administrators can manage categories and moderate provider services.

---

# 🟢 Provider Availability

Providers can control whether they are currently available for work.

Available states are:

| Status | Meaning |
|---|---|
| `AVAILABLE` | Provider is currently available |
| `BUSY` | Provider is occupied |
| `UNAVAILABLE` | Provider is currently not accepting work |

Availability information is shown to customers while selecting providers.

---

# ✅ Provider Verification

Administrators can verify service providers.

Verified providers receive a verification status that can be displayed to customers during service discovery.

Admin can also remove provider verification when required.

---

# 📅 Booking Workflow

A customer booking contains information such as:

- Booking date
- Booking time
- Address
- Problem description
- Customer ID
- Provider ID
- Service ID
- Booking status

A normal booking follows the workflow:

```text
                  ┌────────────┐
                  │  Pending   │
                  └─────┬──────┘
                        │
              ┌─────────┴─────────┐
              │                   │
              ▼                   ▼
        ┌──────────┐        ┌──────────┐
        │ Accepted │        │ Rejected │
        └────┬─────┘        └──────────┘
             │
             ▼
        ┌───────────┐
        │ Completed │
        └───────────┘
```

The provider can accept or reject a Pending booking.

After completing an Accepted job, the provider marks the booking as **Completed**.

---

# 💰 Custom Quotation Workflow

Customers are not always required to accept the listed service price.

They can request a **custom quotation** from the provider.

The workflow is:

```text
Quotation Requested
        │
        ▼
Provider reviews request
        │
        ▼
Provider enters:
- Quoted Price
- Quotation Message
        │
        ▼
Quotation Sent
        │
        ├───────────────┐
        │               │
        ▼               ▼
Customer Accepts   Customer Rejects
        │               │
        ▼               ▼
    Accepted          Rejected
        │
        ▼
    Completed
```

Quotation information includes:

- Whether quotation was requested
- Quoted price
- Quotation message
- Booking status

---

# 🏷️ Promotional Booking

ShohojSeba includes a lightweight promotional booking feature for demonstration purposes.

An active Cleaning promotion can provide a percentage discount.

The booking workflow can carry:

```text
Original Price
      ↓
Discount Percentage
      ↓
Final Promotional Price
```

For example:

```text
Original Price: ৳2000
Discount:       20%
Final Price:    ৳1600
```

The provider can see that a promotion was applied and view the agreed discounted price.

> **Note:** The current promotion is implemented as an application-side demonstration feature rather than a complete admin-managed promotion engine.

---

# 🔔 Notifications

ShohojSeba provides separate notification systems for Customers and Providers.

## Customer Notifications

Customer notifications may be generated when:

- A booking is accepted
- A booking is rejected
- A quotation is received
- A service is completed

## Provider Notifications

Provider notifications may be generated when:

- A new booking is received
- A quotation is requested
- A quotation is accepted
- A quotation is rejected

Notifications support:

- Read/unread states
- Notification badges
- Mark as read
- Mark all as read
- Navigation to relevant booking/request screens

---

# ❤️ Favorites

Customers can save services to **Favorites**.

This allows customers to:

- Return to previously saved services
- View saved providers/services
- Start another booking
- Rebook services more quickly

---

# ⭐ Reviews

After a service has been completed, customers can submit reviews for providers.

Reviews can contain customer feedback and ratings.

Customers can view provider reviews before selecting a provider.

Administrators can moderate reviews and delete inappropriate content when required.

---

# ⏰ Service Reminders

ShohojSeba includes a service-reminder workflow.

Where applicable, completing a service can create a future servicing reminder.

Customers can later view upcoming maintenance/service information from the reminder screen.

---

# 🧭 Persistent Navigation

## Customer Navigation

```text
Home | Bookings | Saved | Alerts
```

## Provider Navigation

```text
Home | Requests | Add | Alerts
```

Focused screens such as the booking form and review form are intentionally kept outside the persistent bottom navigation.

---

# 🛠️ Technology Stack

## Android Application

| Technology | Usage |
|---|---|
| Kotlin | Main programming language |
| Jetpack Compose | UI development |
| Material 3 | Design system |
| Navigation Compose | Screen navigation |
| Kotlin Coroutines | Asynchronous operations |
| ViewModel | UI state management |

## Backend

| Technology | Usage |
|---|---|
| Supabase | Backend platform |
| Supabase Auth | Authentication |
| PostgREST | Database operations |
| Retrofit | REST communication where required |

## Additional Technologies

- Gson serialization annotations
- Kotlin Serialization
- Material Icons
- Compose State
- MVVM-style architecture

---

# 🏗️ Architecture

ShohojSeba follows an **MVVM-style layered architecture**.

```text
┌─────────────────────────────────┐
│          Compose UI             │
│ Screens + Reusable Components   │
└────────────────┬────────────────┘
                 │
                 ▼
┌─────────────────────────────────┐
│           ViewModels            │
│    UI State + User Actions      │
└────────────────┬────────────────┘
                 │
                 ▼
┌─────────────────────────────────┐
│          Repositories           │
│   Data / Business Operations    │
└──────────┬───────────┬──────────┘
           │           │
           ▼           ▼
┌─────────────────┐ ┌─────────────────┐
│    Supabase     │ │  Retrofit APIs  │
│ Auth/PostgREST  │ │ where required  │
└─────────────────┘ └─────────────────┘
```

## UI Layer

Contains Jetpack Compose screens and reusable components for:

- Authentication
- Customer
- Provider
- Admin
- Booking cards
- Notification cards
- Bottom navigation
- Dialogs and forms

## ViewModel Layer

Important ViewModels include:

```text
AuthViewModel
BookingViewModel
CategoryViewModel
AreaViewModel
ProviderViewModel
NotificationViewModel
ProviderNotificationViewModel
FavoriteViewModel
AdminViewModel
```

## Repository Layer

Important repositories include:

```text
AuthRepository
BookingRepository
NotificationRepository
FavoriteRepository
ServiceReminderRepository
AdminRepository
```

Repositories isolate backend/data operations from the UI layer.

---

# 🗄️ Main Data Entities

## Category

Represents a service category.

```text
category_id
category_name
```

## Customer

Represents a customer account linked with Supabase Auth.

```text
customer_id
auth_user_id
name
phone
email
```

## Provider

Represents a service provider.

```text
provider_id
auth_user_id
name
phone
email
experience
is_verified
availability_status
account_status
```

## Service

Represents a service offered by a provider.

```text
service_id
category_id
provider_id
service_name
price
duration
description
service_status
```

## Booking

Represents a customer service request.

```text
booking_id
created_at
booking_date
booking_time
address
problem_description
status
customer_id
provider_id
service_id
quotation_requested
quoted_price
quotation_message
```

Promotional price information may also be associated with the booking workflow.

## Area

Represents a supported service location.

```text
area_id
area_name
```

## Notification

Stores in-app notifications.

Typical information includes:

```text
notification_id
customer/provider target
booking_id
title
message
notification_type
is_read
created_at
```

## Review

Stores customer feedback.

Typical information includes:

```text
review_id
booking_id
customer_id
provider_id
rating
comment
created_at
```

---

# 🔄 Important Booking Statuses

ShohojSeba uses the following booking statuses:

```text
Pending
Accepted
Rejected
Completed
Quotation Requested
Quotation Sent
```

> These status values should remain consistent between the Android application and the database because multiple screens use them to determine available actions and UI states.

---

# 🗺️ Application Navigation

## Authentication Routes

```text
Landing
Login
Register
```

## Customer Routes

```text
Home
Category
Services
Booking
Customer Bookings
Review
Provider Reviews
Service Reminders
Favorites
Notifications
```

## Provider Routes

```text
Provider Dashboard
Provider Bookings / Service Requests
Provider Notifications
Add Service
```

## Admin Routes

```text
Admin Dashboard
Manage Categories
Manage Areas
Customers
Providers
Services
Reviews
```

The central Navigation Compose graph also manages:

- Role-based login routing
- Customer notification badges
- Provider notification badges
- Customer persistent navigation
- Provider persistent navigation
- Logout navigation
- Back-stack clearing

---

# 🎨 UI Design

ShohojSeba uses a consistent visual design based around:

- Teal/green primary colors
- Light mint backgrounds
- White elevated cards
- Rounded corners
- Material 3 components
- Status chips
- Clear information hierarchy
- Persistent navigation
- Readable loading states
- Empty states
- Confirmation dialogs

The UI was progressively refined to reduce:

- Overly tall cards
- Confusing layouts
- Keyboard obstruction
- Inconsistent navigation
- Unclear error/status messages

---

# ⚙️ Setup Instructions

## Prerequisites

Install:

- Android Studio
- Android SDK
- Compatible JDK
- Git

You can run the application using:

- Android Emulator
- Physical Android device

---

## Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd <YOUR_PROJECT_FOLDER>
```

---

## Open in Android Studio

1. Open **Android Studio**.
2. Select **Open**.
3. Select the ShohojSeba project folder.
4. Wait for Gradle synchronization to complete.
5. Configure required backend values.
6. Select an emulator or Android device.
7. Run the application.

---

# ☁️ Supabase Configuration

The project requires a configured **Supabase** backend.

The backend must support the entities required by the application, including:

- Customer
- Provider
- Category
- Service
- Booking
- Area
- Notification
- Review
- Provider-area relationships
- Favorites
- Service reminders

Appropriate database relationships and permissions must also be configured.

> ⚠️ **Never commit Supabase service-role keys or other private credentials to GitHub.**

---

# 🎬 Demo Workflows

## 👤 Customer Demo

```text
Login
  ↓
Select Area
  ↓
Select Category
  ↓
Select Service / Provider
  ↓
Book Service
       OR
Request Quotation
  ↓
View My Bookings
  ↓
Receive / Accept Quotation
  ↓
Provider Completes Service
  ↓
Review / Favorite / Reminder
```

---

## 🧑‍🔧 Provider Demo

```text
Login
  ↓
Set Availability
  ↓
Configure Service Areas
  ↓
Add Service
  ↓
Receive Booking / Quotation Request
  ↓
Accept / Reject / Send Quotation
  ↓
Complete Job
  ↓
View Notifications
```

---

## 🛡️ Admin Demo

```text
Login
  ↓
Admin Dashboard
  ↓
Manage Categories / Areas
  ↓
View Customers
  ↓
Verify / Suspend / Reactivate Providers
  ↓
Remove / Restore Services
  ↓
Moderate Reviews
```

---

# 🧪 Testing Checklist

## Authentication

- [ ] Customer registration succeeds
- [ ] Provider registration succeeds
- [ ] Correct role is detected after login
- [ ] Invalid credentials produce readable errors
- [ ] Customer logout clears the session
- [ ] Provider logout clears the session
- [ ] Admin logout clears the session
- [ ] Android Back does not reopen a protected screen after logout

## Customer

- [ ] Areas load correctly
- [ ] Category navigation works
- [ ] Area-based providers/services are displayed correctly
- [ ] Normal booking can be created
- [ ] Date selection works
- [ ] Time selection works
- [ ] Booking form remains usable with keyboard open
- [ ] Custom quotation can be requested
- [ ] Quotation notification is received
- [ ] Quotation can be accepted
- [ ] Quotation can be rejected
- [ ] Booking statuses update correctly
- [ ] Promotional booking applies the intended discount
- [ ] Booking filters work
- [ ] Favorites can be added/removed
- [ ] Completed jobs can be reviewed
- [ ] Service reminders can be viewed
- [ ] Notification click opens the relevant workflow

## Provider

- [ ] Provider profile loads
- [ ] Availability can be changed
- [ ] Service areas can be selected
- [ ] Service areas can be saved
- [ ] New service can be added
- [ ] Booking requests load
- [ ] Pending booking can be accepted
- [ ] Pending booking can be rejected
- [ ] Quotation request form works
- [ ] Quotation can be sent
- [ ] Accepted booking can be completed
- [ ] Provider notifications load
- [ ] Notifications can be marked as read
- [ ] Provider navigation works correctly

## Admin

- [ ] Categories can be added
- [ ] Categories can be edited
- [ ] Categories can be deleted
- [ ] Areas can be added
- [ ] Areas can be edited
- [ ] Areas can be deleted
- [ ] Customers load
- [ ] Providers load
- [ ] Provider verification works
- [ ] Provider unverification works
- [ ] Provider suspension works
- [ ] Provider reactivation works
- [ ] Provider removal works
- [ ] Services load
- [ ] Services can be removed
- [ ] Services can be restored
- [ ] Reviews load
- [ ] Reviews can be deleted
- [ ] Admin logout works correctly

---

# ⚠️ Known Scope / Limitations

- The current promotion is a demonstration-level application-side promotion rather than a complete admin-managed promotion system.
- Google Maps/location-map visualization was considered but is not included in the final implementation.
- The application currently focuses on in-app notifications rather than a complete production push-notification infrastructure.
- Some administrative actions use soft-status changes so historical records can remain available.
- Production deployment would require a complete review of Supabase Row Level Security policies and credential handling.

---

# 🔒 Security Notes

Before publishing this repository:

- ❌ Do not commit Supabase service-role keys
- ❌ Do not commit passwords
- ❌ Do not commit private API tokens
- ❌ Do not commit sensitive credentials
- ✅ Keep `local.properties` ignored
- ✅ Review API configuration files before publishing
- ✅ Configure appropriate Supabase Row Level Security policies
- ✅ Keep sensitive production configuration outside source control

---

# 📁 Repository Structure

A simplified project structure is:

```text
ShohojSeba/
│
├── app/
│   └── src/main/
│       ├── java/com/example/shohojseba/
│       │   │
│       │   ├── data/
│       │   │   ├── api/
│       │   │   ├── model/
│       │   │   ├── repository/
│       │   │   └── supabase/
│       │   │
│       │   ├── navigation/
│       │   │
│       │   ├── notification/
│       │   │
│       │   ├── ui/
│       │   │   ├── auth/
│       │   │   ├── customer/
│       │   │   ├── provider/
│       │   │   └── admin/
│       │   │
│       │   └── viewmodel/
│       │
│       └── res/
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
├── AI_DECLARATION.md
└── .gitignore
```

> The exact repository may contain additional files and classes as the implementation evolves.

---

# 📝 Development Notes

During development, particular attention was given to:

- Correct Customer/Provider foreign-key relationships in bookings
- Role-based Supabase authentication mapping
- Provider service-area filtering
- Quotation status transitions
- Customer/provider notification targeting
- Maintaining booking state after quotation actions
- Avoiding navigation dead ends
- Clearing navigation state after logout
- Making forms scroll correctly when the keyboard is visible
- Improving Customer and Provider dashboards while preserving functionality
- Providing clear Admin management actions
- Confirmation dialogs for destructive actions
- Consistent UI across all three roles

---

# 🤖 AI Usage Declaration

Generative AI assistance was used during development for:

- Code drafting
- Debugging
- Code explanation
- UI refinement
- Architecture discussions
- Workflow planning
- Testing guidance
- Documentation support

A detailed disclosure is available in:

**`AI_DECLARATION.md`**

AI-generated suggestions were adapted to the project's actual codebase, database schema, requirements, and workflows. The application was iteratively built and tested in Android Studio.

---

# 📌 Final Summary

**ShohojSeba** demonstrates a complete multi-role Android local-service marketplace connecting **Customers, Service Providers, and Administrators**.

The project combines:

- 🔐 Authentication
- 📍 Area-based service discovery
- 🛠️ Service management
- 📅 Booking management
- 💰 Custom quotations
- 🏷️ Promotional pricing
- 🔔 Notifications
- ❤️ Favorites
- ⭐ Reviews
- ⏰ Service reminders
- 🟢 Provider availability
- ✅ Provider verification
- 🛡️ Administrative moderation

All of these features are integrated into a single **Jetpack Compose Android application backed by Supabase**.
