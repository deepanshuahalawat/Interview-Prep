# LLD Roadmap — SDE-2 (Java Backend / MANGA-FAANG)

## Target

Build the ability to take a new Low-Level Design problem and independently produce a clean, extensible, testable Java design within **45–60 minutes**, while explaining design decisions and trade-offs.

---

# 1. OOP & Object Modeling

## Core OOP
- Encapsulation
- Abstraction
- Inheritance
- Polymorphism
- Composition vs inheritance
- Association
- Aggregation
- Composition
- Dependency
- Interface vs abstract class
- Method overloading
- Method overriding

## Java-specific
- `interface`
- `abstract class`
- `final`
- `static`
- `enum`
- `record`
- `equals()` / `hashCode()`
- `Comparable` / `Comparator`
- Generics
- Collections
- Immutability
- Exception hierarchy
- Custom exceptions
- Functional interfaces
- Lambdas
- Streams

## Key interview question
> Why would you use composition instead of inheritance here?

### Goal
Given a problem, identify classes, responsibilities, relationships, and abstractions without hesitation.

---

# 2. Design Principles ⭐

Design principles should be studied separately from design patterns.

## SOLID

### S — Single Responsibility Principle
A class should have one reason to change.

### O — Open/Closed Principle
Open for extension, closed for modification.

### L — Liskov Substitution Principle
Subtypes should be substitutable for their base types.

### I — Interface Segregation Principle
Clients should not be forced to depend on methods they do not use.

### D — Dependency Inversion Principle
Depend on abstractions rather than concrete implementations.

## Other Important Principles

- DRY — Don't Repeat Yourself
- KISS — Keep It Simple
- YAGNI — You Aren't Gonna Need It
- Composition over Inheritance
- Program to an Interface, not an Implementation
- Favor Delegation over Inheritance
- Encapsulate What Varies
- Separation of Concerns
- High Cohesion
- Low Coupling
- Law of Demeter
- Principle of Least Knowledge
- Tell, Don't Ask
- Dependency Injection
- Information Hiding
- Single Level of Abstraction
- Prefer Immutability
- Fail Fast

## Priority

Focus especially on:

1. SOLID
2. Composition over Inheritance
3. Encapsulate What Varies
4. Program to an Interface
5. High Cohesion / Low Coupling
6. Separation of Concerns
7. DRY / KISS / YAGNI
8. Law of Demeter
9. Tell, Don't Ask

## How to learn principles

Do not memorize definitions only.

For each principle:

```text
Principle
   ↓
Recognize violation
   ↓
Understand why it is a problem
   ↓
Refactor the design
   ↓
Explain the trade-off
```

---

# 3. Design Patterns

Do not memorize all 23 GoF patterns equally.

## Tier 1 — Must Know

### Creational
- Factory
- Abstract Factory
- Builder
- Singleton

### Structural
- Adapter
- Decorator
- Facade

### Behavioral
- Strategy
- Observer
- State
- Command
- Chain of Responsibility
- Template Method

## Tier 2 — Know Conceptually
- Proxy
- Composite
- Iterator
- Prototype
- Mediator
- Memento
- Visitor

## Pattern Learning Framework

For every pattern understand:

```text
Problem
   ↓
Why naive approach fails
   ↓
Pattern
   ↓
Class structure
   ↓
Java implementation
   ↓
When to use
   ↓
When NOT to use
```

## Pattern → Problem Mapping

| Pattern | Example Problems |
|---|---|
| Strategy | Payment, Pricing, Routing |
| Factory | Vehicle, Payment, Notification |
| Abstract Factory | Families of related objects |
| Builder | Complex object construction |
| Singleton | Logger / configuration; understand trade-offs |
| Observer | Notification, Stock Alerts |
| State | Vending Machine, Elevator |
| Command | Chess, Undo/Redo, Queues |
| Decorator | Coffee, Notification, Pricing |
| Adapter | Payment gateways, External APIs |
| Facade | Complex subsystem |
| Chain of Responsibility | Logger, Validation |
| Template Method | Processing pipelines |
| Composite | File System |
| Proxy | Caching, Access Control |

### Important

Do not say:

> "I will use Factory + Strategy + Observer + Singleton."

Instead explain the reason:

> "Payment algorithms can vary independently, so Strategy is appropriate here."

---

# 4. UML & Domain Modeling

## UML
- Class diagrams
- Sequence diagrams
- State diagrams

Do not spend excessive time memorizing UML notation. Use UML to communicate the design.

## Domain Modeling

For every problem:

```text
Requirements
     ↓
Entities
     ↓
Relationships
     ↓
Responsibilities
     ↓
Behaviors
     ↓
Interfaces
     ↓
Interactions
```

## Identify relationships

### IS-A

```text
Car IS-A Vehicle
```

→ Inheritance

### HAS-A

```text
ParkingLot HAS-A ParkingFloor
```

→ Composition / Aggregation

### USES

```text
OrderService USES PaymentService
```

→ Dependency

## Goal

Be able to produce a reasonable class diagram in **5–10 minutes**.

---

# 5. Java Implementation for LLD

## Core Java
- Collections
- Generics
- Interfaces
- Abstract classes
- Enums
- Records
- Exception handling
- Custom exceptions
- Immutability
- `equals()` / `hashCode()`
- `Comparable` / `Comparator`

## Clean implementation
- Small classes
- Clear responsibilities
- Meaningful names
- Interface-based design
- Dependency Injection
- Avoid unnecessary abstractions
- Avoid giant classes
- Avoid giant `if-else` / `switch` blocks when behavior is expected to vary

---

# 6. Java Concurrency & Thread Safety

Important for Java backend SDE-2 LLD.

## Core Topics
- Process vs thread
- Thread safety
- Race conditions
- Critical sections
- Atomicity
- `synchronized`
- `volatile`
- `Lock`
- `ReentrantLock`
- `ReadWriteLock`
- Atomic classes
- `AtomicInteger`
- `AtomicLong`
- `ConcurrentHashMap`
- `CopyOnWriteArrayList`
- `BlockingQueue`
- `Semaphore`
- `CountDownLatch`
- `CyclicBarrier`
- ExecutorService
- Thread pools
- Future
- CompletableFuture
- Producer / Consumer
- Deadlock
- Starvation
- Livelock

## Important Java concept

Understand why:

```java
count++;
```

is not atomic.

Understand when to use:

```java
ConcurrentHashMap
```

instead of manually synchronizing a normal `HashMap`.

## Apply concurrency to
- Rate Limiter
- LRU Cache
- Logger
- Task Scheduler
- Producer / Consumer
- Thread-safe Singleton
- In-memory Cache

---

# 7. Core LLD Problems

## Level 1 — Basic

1. Tic-Tac-Toe
2. Vending Machine
3. Parking Lot
4. Library Management
5. ATM
6. Elevator
7. Snake & Ladder
8. Chess

### Objective

Learn:

- Class identification
- Responsibilities
- Relationships
- Basic SOLID
- Basic design patterns
- Clean Java implementation

---

# 8. Advanced LLD / Machine Coding

## Level 2 — SDE-2

9. Splitwise
10. Cab Booking
11. Movie Ticket Booking
12. Food Ordering
13. Car Rental
14. Meeting Room Booking
15. Notification System
16. Rate Limiter
17. Logger
18. LRU Cache
19. File System
20. In-memory Key-Value Store
21. Task Scheduler
22. Job Scheduler

## Level 3 — Strong SDE-2

23. Payment System
24. Inventory Management
25. Auction System
26. Stock Exchange / Order Matching
27. Pub/Sub System

These problems combine:

```text
OOP
+
SOLID
+
Design Patterns
+
Collections
+
Concurrency
+
Extensibility
```

---

# 9. Backend-Oriented LLD

Because the target role is Java backend SDE-2, LLD should also cover service-layer design.

## Typical architecture

```text
Controller
     ↓
Service
     ↓
Repository
     ↓
Database
```

## Topics

- Controller responsibilities
- Service responsibilities
- Repository pattern
- DTO
- Entity
- Mapper
- Validation
- Exception handling
- Custom exceptions
- Dependency Injection
- Transaction boundaries
- Idempotency
- API contracts
- Interface-based design
- Unit testing
- Mocking
- Thread safety

## Example

```java
interface PaymentService {
    PaymentResult pay(PaymentRequest request);
}
```

Possible implementations:

```text
PaymentService
      |
      +--- CardPaymentService
      +--- UpiPaymentService
      +--- WalletPaymentService
```

Be able to explain why the abstraction is useful and how a new payment method can be added.

---

# 10. Machine Coding

Machine coding is where all concepts come together.

For each problem follow:

```text
Requirements
     ↓
Domain Model
     ↓
Design Principles
     ↓
Class Diagram
     ↓
Interfaces
     ↓
Design Patterns
     ↓
Java Implementation
     ↓
Concurrency if required
     ↓
Tests
     ↓
Extension / Change Requirements
```

## Practice expectations

Start with:

- No time limit
- Focus on correctness

Then move to:

- 90 minutes
- 60 minutes
- 45–60 minutes

The final target is a clean, compilable implementation under interview conditions.

---

# 11. Interview Framework

Use the same process for every LLD question.

## Step 1 — Clarify Requirements

Ask:

- What entities exist?
- What operations are required?
- What can change?
- What are the constraints?
- Is concurrency required?
- What assumptions can I make?

## Step 2 — Identify Entities

Example:

```text
User
Order
Payment
Restaurant
DeliveryPartner
```

## Step 3 — Identify Relationships

```text
IS-A
HAS-A
USES
```

## Step 4 — Define Responsibilities

Every class should have a clear responsibility.

## Step 5 — Identify What Varies

Ask:

> What requirement is likely to change?

That often points toward an abstraction or Strategy.

## Step 6 — Define Interfaces

Example:

```java
interface PaymentStrategy {
    PaymentResult pay(PaymentRequest request);
}
```

## Step 7 — Apply Design Principles

Check:

- SRP
- OCP
- DIP
- High cohesion
- Low coupling
- Composition over inheritance
- Encapsulation

## Step 8 — Apply Patterns Only When Justified

Don't force patterns into the design.

## Step 9 — Implement

Write the core working Java code.

## Step 10 — Test

Cover:

- Happy path
- Invalid input
- Edge cases
- Failure cases
- Concurrent access where relevant

## Step 11 — Discuss Extensions

Typical interviewer follow-ups:

- Add a new payment method
- Add a new vehicle type
- Add a new pricing strategy
- Support concurrency
- Add a new notification channel
- Add persistence
- Add another external provider

Your design should accommodate changes without unnecessary modification to existing code.

---

# 12. Timed Practice

## Initial practice

```text
60–90 minutes/problem
```

Focus on design quality.

## Intermediate

```text
60 minutes/problem
```

Focus on:

- Requirements
- Class design
- Interfaces
- Core implementation
- Testing

## Interview target

```text
45–60 minutes/problem
```

### Suggested timing

```text
0–5 min
Requirements

5–15 min
Entities + relationships

15–25 min
Interfaces + class design

25–50 min
Implementation

50–60 min
Testing + extensions + trade-offs
```

---

# 13. What NOT to Over-Study

Avoid wasting time on:

- Memorizing all 23 GoF patterns
- UML theory
- Extremely complicated inheritance hierarchies
- Enterprise architecture inside a simple LLD problem
- Spring Boot internals for pure LLD
- Hundreds of lines of boilerplate
- Using Singleton everywhere
- Making every class an interface
- Forcing design patterns where they don't solve a real problem
- Over-engineering for hypothetical requirements

The goal is good engineering judgment.

---

# 14. Complete Learning Sequence

```text
PHASE 1
Java OOP & Object Modeling
        ↓
PHASE 2
Design Principles
        ↓
PHASE 3
SOLID
        ↓
PHASE 4
Design Patterns
        ↓
PHASE 5
UML & Domain Modeling
        ↓
PHASE 6
Java Implementation
        ↓
PHASE 7
Java Concurrency
        ↓
PHASE 8
Basic LLD Problems
        ↓
PHASE 9
Advanced LLD Problems
        ↓
PHASE 10
Backend LLD
        ↓
PHASE 11
Machine Coding
        ↓
PHASE 12
Timed Interview Practice
```

---

# 15. Recommended 10-Week Plan

Assuming approximately **1.5–2 hours/day**.

| Week | Focus |
|---|---|
| Week 1 | Java OOP + Object Modeling |
| Week 2 | Design Principles + SOLID |
| Week 3 | Design Patterns |
| Week 4 | UML + Domain Modeling + Basic LLD |
| Week 5 | Basic LLD Problems |
| Week 6 | Advanced Machine Coding |
| Week 7 | Advanced Machine Coding |
| Week 8 | Java Concurrency + Concurrent LLD |
| Week 9 | Backend LLD + Mixed Problems |
| Week 10 | Timed Interview Simulation |

Do not treat this as a rigid calendar. Move ahead when you can demonstrate the skill.

---

# 16. Pattern-to-Problem Learning Path

Instead of learning patterns in isolation:

```text
Strategy
   ↓
Payment
   ↓
Pricing
   ↓
Cab Booking
```

```text
State
   ↓
Vending Machine
   ↓
Elevator
```

```text
Observer
   ↓
Stock Alerts
   ↓
Notification System
```

```text
Decorator
   ↓
Coffee
   ↓
Notification / Pricing
```

```text
Composite
   ↓
File System
```

```text
Chain of Responsibility
   ↓
Logger
   ↓
Validation Pipeline
```

```text
Command
   ↓
Undo/Redo
   ↓
Chess
```

This approach builds pattern recognition rather than memorization.

---

# 17. Final SDE-2 Standard

You are LLD-ready when you can:

- Understand ambiguous requirements
- Identify domain entities
- Assign responsibilities correctly
- Choose composition vs inheritance
- Design interfaces
- Apply SOLID naturally
- Recognize useful design patterns
- Avoid unnecessary patterns
- Write clean Java
- Handle concurrency where required
- Explain trade-offs
- Extend the design when requirements change
- Write meaningful tests
- Complete a machine-coding problem in 45–60 minutes

## Final Goal

> **Don't aim to know 30 design patterns. Aim to be able to design a new system cleanly under interview pressure.**

The progression is:

**Understand → Design → Code → Test → Explain → Modify → Repeat**
