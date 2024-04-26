-----------------
-- STRUCTURE
-----------------
CREATE TABLE "Author" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "first_name" TEXT,
  "last_name" TEXT NOT NULL,
  "nationality" TEXT,
  "birthdate" TEXT,
  "deathdate" TEXT
);

CREATE TABLE "Book" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "title" TEXT NOT NULL,
  "publication" DATE,
  "fk_author" INTEGER NOT NULL REFERENCES "Author" ("id") ON DELETE CASCADE
);

CREATE INDEX "idx_book__fk_author" ON "Book" ("fk_author");

CREATE TABLE "Format" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "name" TEXT NOT NULL
);

CREATE TABLE "Book_format" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "publication" DATE,
  "book" INTEGER NOT NULL REFERENCES "Book" ("id") ON DELETE CASCADE,
  "format" INTEGER NOT NULL REFERENCES "Format" ("id") ON DELETE CASCADE
);

CREATE INDEX "idx_book_format__book" ON "Book_format" ("book");

CREATE INDEX "idx_book_format__format" ON "Book_format" ("format");

CREATE TABLE "Theme" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "name" TEXT NOT NULL
);

CREATE TABLE "Book_theme" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "fk_book" INTEGER NOT NULL REFERENCES "Book" ("id") ON DELETE CASCADE,
  "fk_theme" INTEGER NOT NULL REFERENCES "Theme" ("id") ON DELETE CASCADE
);

CREATE INDEX "idx_book_theme__fk_book" ON "Book_theme" ("fk_book");

CREATE INDEX "idx_book_theme__fk_theme" ON "Book_theme" ("fk_theme");

CREATE TABLE "User" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "first_name" TEXT NOT NULL,
  "last_name" TEXT NOT NULL,
  "email" TEXT NOT NULL,
  "phone" TEXT,
  "address" TEXT,
  "zip" TEXT,
  "city" TEXT NOT NULL,
  "description" TEXT,
  "created" DATETIME
);

CREATE TABLE "Borrow" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "borrowing_date" DATE,
  "returning_date" DATE,
  "fk_user" INTEGER NOT NULL REFERENCES "User" ("id") ON DELETE CASCADE,
  "fk_book" INTEGER NOT NULL REFERENCES "Book" ("id") ON DELETE CASCADE
);

CREATE INDEX "idx_borrow__fk_book" ON "Borrow" ("fk_book");

CREATE INDEX "idx_borrow__fk_user" ON "Borrow" ("fk_user");
