# SwiftWheels – Car Rental (Java Swing + PostgreSQL)

Desktop car-rental application: customers browse cars, SUVs and motorcycles, rent them with a
contract form and return them later; admins manage the fleet and see revenue / stock statistics.

The database layer is written in PL/pgSQL (functions, procedures, triggers and a view).

## Quick start (Windows)

1. **PostgreSQL 14+** must be running.
2. Create the database and demo data (nothing is ever dropped):
   ```powershell
   powershell -ExecutionPolicy Bypass -File scripts\setup-database.ps1
   ```
3. Run the app – either
   - download `SwiftWheels-<version>-windows.zip` (built with `gradlew packageZip`), unzip it and start
     **`SwiftWheels.exe`** (no Java installation needed), or
   - from source: `gradlew run`

| Demo account | Password | Role |
|---|---|---|
| `admin@swiftwheels.com` | `Admin123!` | admin (Account page → *Admin Mode*) |
| `demo@swiftwheels.com` | `Demo123!` | customer with one active rental |

### Database connection

Defaults: `jdbc:postgresql://localhost:5432/swiftwheels`, user / password `swiftwheels`.
To change them copy [`db.properties.example`](db.properties.example) to `db.properties` next to
`SwiftWheels.exe` (or in the project folder), or set `SWIFTWHEELS_DB_URL`, `SWIFTWHEELS_DB_USER`,
`SWIFTWHEELS_DB_PASSWORD`.

## Building

Requires a JDK 17+ (Gradle itself is downloaded by the wrapper).

```bash
gradlew run          # start the app
gradlew test         # unit tests + DB/UI tests (DB tests are skipped if PostgreSQL is down)
gradlew packageExe   # build/jpackage/SwiftWheels/SwiftWheels.exe  (bundles a trimmed Java runtime)
gradlew packageZip   # build/distributions/SwiftWheels-<version>-windows.zip
```

The screens are designed with the **IntelliJ GUI Designer** (`*.form` files). IntelliJ normally
injects the generated UI code itself; the Gradle build does the same with JetBrains'
`instrumentIdeaExtensions` task, so the project builds from the command line and still opens
and edits normally in IntelliJ (*File → Open → build.gradle*).

## Project layout

```
database/schema.sql            tables, functions, procedures, triggers, admin view
database/seed.sql              demo users, vehicles and rentals
scripts/setup-database.ps1     creates role + database and loads both files
src/main/java/com/swiftwheels/
  Main.java                    start-up (checks the DB connection first)
  db/Database.java             connection settings (db.properties / env vars)
  model/                       Vehicle, Car, Jeep, Motorcycle, Customer + *Repository (all SQL)
  security/PasswordHasher.java PBKDF2 password hashing
  ui/                          LoginRegisterPanel, MainAppPanel (+ .form), Images, Dialogs
src/main/resources/images/     UI artwork and demo vehicle photos
```

Vehicle photos uploaded by an admin are stored in `%USERPROFILE%\SwiftWheels\vehicle-images`.

## Changes in 1.1

- Builds and runs outside IntelliJ (Gradle + forms instrumentation), one-click Windows `.exe`.
- Database settings in one place instead of hard-coded `YOUR_PASSWORD` in five classes.
- Runnable SQL (the old script had invalid comments, missing `;` and wrong table order).
- **Security:** passwords are stored as salted PBKDF2 hashes (old plain-text passwords are upgraded
  on the next login); admin rights come from `kullanici.is_admin` instead of typing "admin" into a box.
- Returning a vehicle no longer deletes the rental, so revenue in the admin panel stays correct.
- Fixed: SUV fuel / traction swapped on update, rent validation (`&&`/`||` bug, invalid dates
  crashed the form, return date "tomorrow" was rejected), double "returned" message on failure,
  missing default images, images not found when started from another folder, table headers not shown,
  e-mails other than `.com` rejected.
- Clearer error messages from the database, input validation for vehicles and rentals, tests.

## Authors

Mehmet Durucu, Diyar Alma, Umut Tosun
