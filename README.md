ShohojSeba

ShohojSeba is an Android-based local service marketplace designed to connect customers with nearby service providers and give administrators tools to manage the platform. The application supports three user roles—Customer, Provider, and Admin—with role-based navigation, booking management, quotation workflows, notifications, reviews, favorites, service reminders, provider availability, service-area filtering, promotions, and administrative moderation.

The application is implemented in Kotlin using Jetpack Compose for the UI and Supabase for authentication and backend data storage.

1. Project Overview

Finding a trusted local service provider can be difficult when service information, pricing, availability, location coverage, and communication are scattered across different sources. ShohojSeba provides a single mobile application where customers can discover services, select providers who serve their area, create bookings or request quotations, track service progress, receive notifications, save favorite services, and submit reviews.

Providers can manage their services, service areas, availability, booking requests, quotations, job completion, and notifications. Administrators can manage categories and service areas, view users, verify or restrict providers, moderate services, and remove inappropriate reviews.

2. Main Objectives

The project aims to:

Provide a simple mobile platform for finding local services.

Support separate workflows for customers, providers, and administrators.

Allow customers to filter services according to their selected service area.

Support both direct booking and custom quotation requests.

Keep customers and providers informed through in-app notifications.

Allow providers to control their availability and service coverage.

Allow customers to save favorite services and review completed jobs.

Provide administrative control over platform content, providers, services, areas, categories, and reviews.

Provide a modern, readable, mobile-friendly user interface using Jetpack Compose.

3. User Roles

3.1 Customer

A customer can:

Register and log in.

Select a service area.

Browse service categories.

Search categories from the home screen.

View providers/services available in the selected area.

See provider information such as experience, verification, and availability.

Book a service using a preferred date, time, address, and problem description.

Request a custom quotation instead of accepting the listed price.

Receive a provider quotation and accept or reject it.

Track booking status.

View booking history using status filters.

Receive in-app notifications for important booking events.

Open relevant booking pages from notifications.

Save completed services to favorites.

Rebook a saved service.

Submit reviews after service completion.

View provider reviews.

View service reminders.

Use promotional pricing where an active in-app promotion applies.

Log out safely.

3.2 Provider

A provider can:

Register and log in.

Maintain a provider profile.

Set availability to Available, Busy, or Unavailable.

Select and save the areas in which services are offered.

Add services with price, duration, category, and description.

View all incoming customer service requests.

Accept or reject normal bookings.

Receive custom quotation requests.

Send a quotation price and message to the customer.

See whether a quotation is waiting for customer response.

Complete accepted jobs.

Receive provider-side notifications.

Open service requests directly from notifications.

View the provider's currently offered services.

Use persistent provider navigation between Home, Requests, Add Service, and Alerts.

Log out safely.

3.3 Admin

An administrator can:

Log in using an Admin account.

Manage service categories.

Add, edit, and delete service areas.

View registered customers.

View and manage providers.

Verify or remove provider verification.

Suspend, reactivate, or remove provider accounts from active platform use.

View provider services.

Remove or restore services.

View customer reviews.

Delete inappropriate reviews.

Log out safely.

4. Core Features

Authentication and Role-Based Access

Supabase Authentication is used for email/password authentication. After login, the application checks whether the authenticated user is a Customer, Provider, or Admin and routes the user to the correct dashboard.

The application stores the currently resolved Customer or Provider ID in an in-memory UserSession. Logout signs out from Supabase, clears local session IDs, clears the active navigation stack, and returns the user to Login.

Area-Based Service Discovery

Customers select a service area before browsing services. The application uses the selected area when navigating to the service listing so only providers associated with that service area can be shown.

Providers can manage the set of areas where they provide services from their dashboard.

Service Categories and Services

Services are grouped by categories. Customers browse categories and then open the available services within a selected category and area.

Providers can add new services. Administrators can manage categories and can remove or restore provider services.

Provider Availability

Providers can set their availability status to:

AVAILABLE

BUSY

UNAVAILABLE

This availability information is shown to customers when choosing a provider/service.

Provider Verification

Administrators can verify providers. Verified providers receive a verified status that can be shown to customers during service discovery.

Booking Workflow

A customer booking contains information such as:

Booking date

Booking time

Address

Problem description

Customer ID

Provider ID

Service ID

Booking status

A normal booking generally follows this lifecycle:

Pending
   |
   +--> Accepted --> Completed
   |
   +--> Rejected

The provider can accept or reject a Pending booking. After an Accepted job is completed, the provider marks it as Completed.

Custom Quotation Workflow

Customers can request a custom quotation instead of using the normal listed price.

The quotation workflow is:

Quotation Requested
        |
        v
Provider enters quoted price + quotation message
        |
        v
Quotation Sent
        |
        +--> Customer Accepts --> Accepted --> Completed
        |
        +--> Customer Rejects --> Rejected

Quotation-related information is stored with the booking, including quoted price, quotation message, and quotation-request state.

Promotional Booking

The application includes a lightweight demonstration promotion flow. An active Cleaning promotion can provide a percentage discount to the customer. The booking flow can carry:

Original price

Discount percentage

Final promotional price

The provider can see when a booking contains an applied promotion and the agreed discounted price.

This promotion is intentionally implemented as an application-side demonstration feature rather than a full admin-managed promotion engine.

Notifications

ShohojSeba includes separate notification flows for customers and providers.

Customer notifications may be generated when:

A booking is accepted.

A booking is rejected.

A quotation is received.

A service is completed.

Provider notifications may be generated when:

A new booking/request is received.

A quotation is requested.

A quotation is accepted.

A quotation is rejected.

Notifications can be marked as read. Unread counts are displayed as badges in navigation/header elements. Notification cards can redirect users to the relevant booking/request screen.

Favorites

Customers can save services to Favorites, particularly after completing a service. The Favorites screen allows users to return to saved services and start another booking.

Reviews

After a booking is completed, a customer can leave a review for the provider. Reviews can be displayed on provider review screens. Administrators can view and delete reviews when moderation is required.

Service Reminders

The project includes a service reminder flow. When applicable, completing a service can create a future servicing reminder, allowing the customer to view upcoming maintenance information later.

Persistent Navigation

Customer main navigation:

Home | Bookings | Saved | Alerts

Provider main navigation:

Home | Requests | Add | Alerts

Focused task screens such as the booking form or review screen are intentionally kept outside the persistent customer bottom navigation.

5. Technology Stack

Android Application

Language: Kotlin

UI: Jetpack Compose

Design system: Material 3

Architecture: MVVM-style layered structure

Navigation: Android Navigation Compose

Async work: Kotlin Coroutines / viewModelScope

Backend

Backend platform: Supabase

Authentication: Supabase Auth

Database access: Supabase PostgREST / Kotlin client

REST communication: Retrofit is also used in parts of the data layer, particularly booking-related API access.

Additional Android Libraries / Concepts

Gson serialization annotations for selected API models

Kotlin serialization for Supabase models

Material Icons

Compose state management

6. Architecture

The application follows an MVVM-style layered architecture:

+-------------------------------+
|         Compose UI            |
| Screens / reusable components |
+---------------+---------------+
                |
                v
+-------------------------------+
|          ViewModels           |
| UI state + user actions       |
+---------------+---------------+
                |
                v
+-------------------------------+
|         Repositories          |
| Data/business operations      |
+----------+----------+---------+
           |          |
           v          v
+---------------+  +----------------+
|   Supabase    |  | Retrofit APIs  |
| Auth/PostgREST|  | where required |
+---------------+  +----------------+

UI Layer

Contains Jetpack Compose screens and reusable UI components for Customer, Provider, Admin, authentication, navigation, booking cards, notification cards, and bottom navigation bars.

ViewModel Layer

ViewModels expose observable Compose state and coordinate UI actions with repositories. Examples include:

AuthViewModel

BookingViewModel

CategoryViewModel

AreaViewModel

ProviderViewModel

NotificationViewModel

ProviderNotificationViewModel

FavoriteViewModel

AdminViewModel

Repository Layer

Repositories isolate backend/data operations from UI code. Examples include:

AuthRepository

BookingRepository

NotificationRepository

FavoriteRepository

ServiceReminderRepository

AdminRepository

7. Main Data Entities

The project uses data entities/tables centered around the following structure.

Category

Represents service categories.

Typical fields:

category_id

category_name

Customer

Represents customer profiles connected to Supabase Auth.

Typical fields:

customer_id

auth_user_id

name

phone

email

Provider

Represents service providers.

Typical fields:

provider_id

auth_user_id

name

phone

email

experience

is_verified

availability_status

account_status

Service

Represents a service offered by a provider.

Typical fields:

service_id

category_id

provider_id

service_name

price

duration

description

service_status

Booking

Represents a customer service request.

Typical fields include:

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

promotional price fields where applicable

Area

Represents supported service locations.

Typical fields:

area_id

area_name

Notification

Stores in-app notifications for booking and quotation events.

Typical fields include:

notification ID

customer/provider target ID as applicable

booking ID

title

message

notification type

read state

creation time

Review

Stores customer feedback for completed services/providers.

Typical fields include:

review ID

booking ID

customer ID

provider ID

rating

comment

created time

8. Important Booking Statuses

The application uses status strings to drive UI and workflow logic. Important statuses include:

Pending
Accepted
Rejected
Completed
Quotation Requested
Quotation Sent

These values should remain consistent between the application and database because multiple screens use them to determine available actions and visual status indicators.

9. Project Navigation

The central Navigation Compose graph contains routes for:

Authentication

Landing

Login

Register

Customer

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

Provider

Provider Dashboard

Provider Bookings / Service Requests

Provider Notifications

Add Service

Admin

Admin Dashboard

Manage Categories

Manage Areas

Customers

Providers

Services

Reviews

The navigation graph also handles role-based login routing, customer/provider unread notification badges, and logout navigation with back-stack clearing. fileciteturn41file0

10. UI Design

The final interface uses a consistent ShohojSeba visual language:

Teal/green primary colors

Light mint backgrounds

White elevated cards

Rounded corners

Material 3 components

Compact status chips

Clear hierarchy for booking and provider information

Persistent navigation for Customer and Provider main sections

Readable empty/loading states

Confirmation dialogs for destructive or important actions

The UI was progressively refined to reduce overly tall cards, confusing layouts, keyboard obstruction, and inconsistent navigation.

11. Setup Instructions

Prerequisites

Install:

Android Studio

Android SDK required by the project

JDK version compatible with the Gradle configuration

Git

A physical Android device or Android Emulator can be used to run the application.

Clone the Repository

git clone <YOUR_GITHUB_REPOSITORY_URL>
cd <YOUR_PROJECT_FOLDER>

Open in Android Studio

Open Android Studio.

Select Open.

Select the project root folder.

Allow Gradle synchronization to complete.

Configure any required local backend values.

Run the application on an emulator or Android device.

12. Supabase Configuration

The project requires a configured Supabase project containing the corresponding authentication setup, tables, foreign-key relationships, and permissions/policies used by the application.

At minimum, the backend must support the entities required by the code, including Customer, Provider, Category, Service, Booking, Area, Notification, Review, provider-area relationships, favorites, and service reminders where implemented.

Do not commit private secrets such as Supabase service-role keys to GitHub.

local.properties and other machine-specific/private configuration should remain ignored by Git.

13. Running the Application

Start the app.

Choose Login or Registration.

Register as a Customer or Provider, or use an existing account.

After authentication, the app detects the role and sends the user to the appropriate dashboard.

Customer Demo Flow

Login
 -> Select Area
 -> Select Category
 -> Select Service/Provider
 -> Book Service OR Request Quotation
 -> View My Bookings
 -> Receive/Accept Quotation if applicable
 -> Provider completes service
 -> Review / Favorite / Reminder

Provider Demo Flow

Login
 -> Set Availability
 -> Configure Service Areas
 -> Add Service
 -> Receive Booking / Quotation Request
 -> Accept / Reject / Send Quotation
 -> Complete Job
 -> View Notifications

Admin Demo Flow

Login
 -> Manage Categories / Areas
 -> View Customers
 -> Verify / Suspend / Reactivate / Remove Providers
 -> Remove / Restore Services
 -> Moderate Reviews

14. Testing Checklist

The following areas should be tested before submission/demo.

Authentication

Customer registration succeeds.

Provider registration succeeds.

Correct role is detected after login.

Invalid credentials produce readable errors.

Customer logout clears session and prevents Back from reopening Home.

Provider logout clears session and prevents Back from reopening Dashboard.

Admin logout clears session and prevents Back from reopening Admin.

Customer

Areas load correctly.

Service category navigation works.

Area-based services/providers are shown correctly.

Normal booking can be created.

Date/time selection works.

Booking screen remains usable when the keyboard is visible.

Custom quotation can be requested.

Customer receives quotation notification.

Customer can accept/reject quotation.

Booking status updates correctly.

Promotional Cleaning booking applies the intended discount.

Booking history filters work.

Favorites can be added/removed.

Completed job can be reviewed.

Service reminders can be viewed.

Notification click opens the relevant workflow.

Provider

Provider profile loads.

Availability can be changed.

Service areas can be selected and saved.

New service can be added.

Booking requests load.

Pending booking can be accepted/rejected.

Quotation request form works.

Quotation can be sent.

Accepted booking can be completed.

Provider notifications load and can be marked as read.

Provider navigation works between Home, Requests, Add, and Alerts.

Admin

Categories can be added, edited, and deleted.

Areas can be added, edited, and deleted.

Customers load.

Providers load.

Provider verification/unverification works.

Provider suspension/reactivation works.

Provider removal works as designed.

Services load.

Services can be removed/restored.

Reviews load.

Review deletion works.

15. Known Scope / Limitations

The current promotion is a demonstration-level in-app promotion rather than a complete admin-managed promotion system.

Google Maps/location-map visualization was considered but is not part of the final implemented workflow.

The app currently focuses on in-app notifications rather than a complete production push-notification infrastructure.

Some administrative actions use soft-status changes so historical records can remain available.

Production deployment would require a full security review of Supabase Row Level Security policies and credential handling.

16. Security Notes

Before publishing the repository:

Do not commit Supabase service-role keys.

Do not commit passwords or private tokens.

Keep local.properties ignored.

Review all API configuration files before pushing publicly.

Use Supabase Row Level Security policies appropriate to Customer, Provider, and Admin access.

17. Recommended Repository Structure

A simplified project layout is:

ShohojSeba/
|
|-- app/
|   |-- src/main/java/com/example/shohojseba/
|   |   |-- data/
|   |   |   |-- api/
|   |   |   |-- model/
|   |   |   |-- repository/
|   |   |   `-- supabase/
|   |   |
|   |   |-- navigation/
|   |   |
|   |   |-- notification/
|   |   |
|   |   |-- ui/
|   |   |   |-- auth/
|   |   |   |-- customer/
|   |   |   |-- provider/
|   |   |   `-- admin/
|   |   |
|   |   `-- viewmodel/
|   |
|   `-- src/main/res/
|
|-- gradle/
|-- build.gradle.kts
|-- settings.gradle.kts
|-- README.md
|-- AI_DECLARATION.md
`-- .gitignore

The exact repository may contain additional files/classes as the implementation evolves.

18. Development Notes

During development, particular attention was given to:

Correct Customer/Provider foreign-key relationships in bookings.

Role-based Supabase Auth mapping.

Provider service-area filtering.

Quotation status transitions.

Customer/provider notification targeting.

Maintaining booking state after quotation actions.

Avoiding navigation dead ends.

Clearing navigation state after logout.

Making forms scroll correctly when the software keyboard is visible.

Polishing Customer and Provider dashboards while preserving existing functionality.

Making Admin management actions explicit with confirmation dialogs.

19. AI Usage Declaration

Generative AI assistance was used during development for code drafting, debugging, explanation, UI refinement, architecture discussion, testing guidance, and documentation support. The detailed disclosure is provided in:

AI_DECLARATION.md

The project was iteratively reviewed and tested in Android Studio, and AI-produced suggestions were adapted to the application's actual database schema, existing codebase, and required workflows.

20. License / Academic Use

This repository was created as an academic/software-development project. Add a formal license only if redistribution or reuse terms are required by the project owner or course.

21. Final Summary

ShohojSeba demonstrates a multi-role Android service marketplace with a working Customer–Provider–Admin flow. It combines authentication, service discovery, area-based provider selection, booking management, quotations, promotional pricing, notifications, favorites, reviews, service reminders, provider availability, provider verification, and administrative moderation in a single Jetpack Compose application backed by Supabase.
