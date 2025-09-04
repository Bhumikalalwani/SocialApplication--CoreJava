# SocialChat Core Java Starter (WeChat/LINE style)

A clean **Core Java (no frameworks)** backend skeleton showing solid OOP and design patterns for a chat-first social app.

## Quick start (IntelliJ)
1. File → New → Project from Existing Sources… → select this folder → choose **Maven**.
2. Wait for indexing. JDK: set to **17** (Project Structure → Project SDK).
3. Right-click `Main` → **Run 'Main.main()'**. You should see a short demo in the console.

## Modules
- `models` — Users, Messages, Posts, Groups
- `services` — UserService, ChatService, PostService, NotificationService
- `storage` — in-memory singleton Database
- `utils` — helpers (ID generator)

Extend freely: add encryption, pagination, persistence, etc.
