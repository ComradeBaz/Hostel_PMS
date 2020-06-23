# Hostel_PMS
Property Management System

Introduction
Hostel PMS is a management system for small to medium sized hostels. A “hostel” is typically lodging for backpackers where dormitories rather than single rooms are the norm.
● Users configure the application by adding rooms, rates, currencies accepted and extra items offered to guests (eg key deposit, bar items, towels etc).
● Configurations can be updated, adding/modifying rooms and extras as necessary.
● Once configured a user can check in guests and manage the guests stay.
● On checking out a guest the user is presented with a “bill” describing the guest’s stay with totals, amount paid and any extra items due for consideration.

Softwares used
● JavaEE 6
● Java 1.8
● GlassFish 4.1.1
● MySql 5.6
● JPA
● EJB
● JSF
● JavaScript
● JQuery
● CSS

● The application is developed using JavaEE 6.
● Views are rendered on .xhtml pages using predominantly JSF tags. PrimeFaces components are used where standard tags did not provide needed functionality.
○ Pages are styled with CSS.
○ Ajax requests and Javascript are used to update views dynamically based on user actions.
● User requests are handled by @RequestScoped and @SessionScoped controllers.
● Business logic is delegated to @Stateless and @Singleton EJBs. CRUD operations are handled at this layer.
● Domain entities are mapped using @Table and @Entity annotations.

Security
● Authentication/login is handled by a POJO implementing the Filter interface.
○ Registration and login is performed on custom .xhtml pages.
○ A PBKDF2 Hasher is wired to implement password hashing.
