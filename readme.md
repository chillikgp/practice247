The implementation focuses heavily on **Section A (User Profile/Attempts)** and **Section B (PIQ & SDT)** of your PRD. The core "Practice Engine" (Section D: OIR, TAT, WAT, SRT) and "Learning Module" (Section C) are currently missing from the code.

### 1. User Account & Profile Management
[cite_start]**PRD Reference:** Section A & A2 (Auth & Profile)[cite: 181, 192].
The code implements basic account creation and retrieval. It simplifies the PRD's distinct Auth/Profile split into a single `AccountController`.

* **POST** `/account/first-time`
    * **Code Purpose:** Handles user onboarding. Creates a new account if the email doesn't exist.
    * [cite_start]**PRD Mapping:** Corresponds roughly to `POST /auth/signup`[cite: 184].
* **GET** `/account/{id}`
    * **Code Purpose:** Fetches account details by ID.
    * [cite_start]**PRD Mapping:** Implements `GET /user/profile`[cite: 194].
* **PUT** `/account/{id}`
    * **Code Purpose:** Updates basic profile info (Name, Phone).
    * [cite_start]**PRD Mapping:** Implements `PUT /user/profile`[cite: 196].

### 2. SSB Attempts (History & Upcoming)
[cite_start]**PRD Reference:** Section A2 (Upcoming & Past SSBs)[cite: 198, 199].
The code uses a single controller to manage all attempts, rather than separating endpoints for "past" and "upcoming" as suggested in the PRD.

* **POST** `/ssb/attempts`
    * **Code Purpose:** Bulk upsert (insert or update) SSB attempts. It handles versioning automatically.
    * **PRD Mapping:** Consolidates `PUT /user/upcoming-ssb` and `POST` actions for past attempts.
* **GET** `/ssb/attempts/{accountId}`
    * **Code Purpose:** Fetches a list of all SSB attempts for a user.
    * [cite_start]**PRD Mapping:** Implements `GET /user/past-ssb`[cite: 199].
* **DELETE** `/ssb/attempt/{id}`
    * **Code Purpose:** Deletes a specific attempt entry.

### 3. Personal Information Questionnaire (PIQ)
[cite_start]**PRD Reference:** Section B1 (PIQ)[cite: 201].
The implementation matches the PRD requirement for version handling and fetching the latest entry.

* **POST** `/piq/{accountId}`
    * **Code Purpose:** Saves a new version of the PIQ details.
    * [cite_start]**PRD Mapping:** Implements `POST /piq`[cite: 203].
* **GET** `/piq/{accountId}/latest`
    * **Code Purpose:** Retrieves the most recent PIQ version.
    * [cite_start]**PRD Mapping:** Implements `GET /piq/latest`[cite: 202].
* **GET** `/piq/{accountId}/history`
    * **Code Purpose:** Returns a list of all previous PIQ versions (useful for the "History" view mentioned in VO).

### 4. Self-Description Test (SDT)
[cite_start]**PRD Reference:** Section B2 (SDT)[cite: 206].
This module is the most feature-complete, containing logic for fetching questions, submitting answers, and handling both AI and Mentor reviews.

* **GET** `/sdt/questions?random={boolean}`
    * **Code Purpose:** Fetches SDT questions. Can return all active questions or a random set of 5.
* **POST** `/sdt/question`
    * **Code Purpose:** (Admin/Setup) API to insert new questions into the database.
* **POST** `/sdt/answer/{accountId}`
    * **Code Purpose:** Submits user answers for SDT. Creates a new version automatically.
    * [cite_start]**PRD Mapping:** Implements `POST /sdt`[cite: 208].
* **GET** `/sdt/answer/{accountId}/latest`
    * **Code Purpose:** Gets the latest submitted answers.
    * [cite_start]**PRD Mapping:** Implements `GET /sdt/latest`[cite: 207].
* **GET** `/sdt/answer/{accountId}/history`
    * **Code Purpose:** Retrieves full history of SDT submissions.

### 5. Evaluation & Feedback (Mentor & AI)
[cite_start]**PRD Reference:** Section F (Human Evaluation) & E (AI Evaluation)[cite: 246, 254].
The code includes specific endpoints to update feedback on SDT submissions. These APIs allow the system to store the evaluation results defined in the PRD.

* **PUT** `/sdt/answer/{accountId}/{version}/reviewer-comments`
    * **Code Purpose:** Updates the generic "Reviewer Comments" field for a specific SDT version.
    * [cite_start]**PRD Mapping:** Supports `POST /mentor/evaluation/{id}/submit`[cite: 260].
* **PUT** `/sdt/answer/{accountId}/{version}/reviewer-question-comments`
    * **Code Purpose:** Allows a mentor to add specific comments to specific questions within an SDT submission.
    * [cite_start]**PRD Mapping:** Supports detailed feedback/annotations[cite: 262].
* **PUT** `/sdt/answer/{accountId}/{version}/ai-comments`
    * **Code Purpose:** Updates the "AI Review Comments" field.
    * [cite_start]**PRD Mapping:** Supports the storage of results from `POST /ai/*-evaluate`[cite: 246].

### Summary of Missing Features (vs PRD)
The following PRD sections are **not** present in the current code:
* [cite_start]**Learning Module (Section C):** No APIs for Flashcards, Defence GK, or Static Content[cite: 211].
* [cite_start]**Practice Engine (Section D):** No APIs for OIR, TAT, WAT, or SRT questions or attempts[cite: 217].
* [cite_start]**Habit & Motivation (Section H):** No APIs for streaks or motivation feed[cite: 270].
* [cite_start]**BYOQ (Section G):** No "Bring Your Own Question" logic[cite: 263].
