## Change log
----------------------

Version 6.1-SNAPSHOT
-------------

## Updates Summary

### Dependencies Updated to Latest Versions:
- **MySQL Connector Java:** `8.0.33` (latest milestone)
- **JaCoCo Ant:** `0.8.11` (latest milestone)
- **Apache Derby:** `10.17.1.0` (latest milestone)
- **Gradle Plugins:**
    - **Spotless Plugin:** `7.0.0.BETA4`
    - **Ben Manes Versions Plugin:** `0.51.0`
    - **Ajoberstar Grgit Plugin:** `5.3.0`
    - **NL Little Robots Version Catalog Update Plugin:** `0.8.5`

### Dependencies with Newer Versions Available:
- **H2 Database:** `2.2.224` → `2.3.232`
- **File Worker Library:** `17.1` → `19.0`
- **JUnit Jupiter:** `5.11.0-M1` → `5.11.3`
- **JUnit Platform Launcher:** `1.11.0-M1` → `1.11.3`
- **PostgreSQL JDBC Driver:** `42.7.3` → `42.7.4`
- **Log4j Core:** `2.17.1` → `3.0.0-beta3`
- **Lombok Library:** `1.18.32` → `1.18.36`
- **Spring Context:** `6.1.6` → `6.2.0`
- **Spring ORM:** `6.1.6` → `6.2.0`
- **SQLite JDBC Driver:** `3.45.3.0` → `3.47.1.0`

### Gradle Updates:
- **Current version:** `8.7`
- **Latest release candidate:** `8.11.1`

Version 6
-------------

ADDED:

- created new connection classes for sqllite and HyperSQL
- created new method in ConnectionsExtensions for execute directly from sql script file
- new gradle plugin spotless in version 6.5.2
- created new unit tests for execute sqlite script files

CHANGED:

- update to jdk version 17
- update gradle to new version 8.7
- update of com.github.ben-manes.versions.gradle.plugin to new version 0.51.0
- update of gradle-plugin dependency 'org.ajoberstar.grgit:grgit-gradle' in version 5.2.2
- update of gradle-plugin dependency 'com.diffplug.spotless:spotless-plugin-gradle' to new minor version 6.25.0
- update of dependency spring version to 6.1.6
- remove of testng test dependency
- update of test dependency junit-jupiter-api and junit-jupiter-engine in new version 5.11.0-M1
- moved all connection extension classes to its own package

Version 5.9
-------------

CHANGED:

- changed to new package io.github.astrapi69
- update gradle to new version 7.3.2
- update of dependency lombok version to 1.18.22
- update of dependency spring version to 5.3.14
- update of testng test dependency version to 7.4.0

Version 5.8
-------------

ADDED:

- new build system gradle
- created new factory method for create PostgreSQL database connection with JdbcUrlBean and credentials
- created new class JdbcConnectionInfo that holds all information for connect to a PostgreSQL database
- created new factory method for create PostgreSQL database connection with a single argument of JdbcConnectionInfo

CHANGED:

- changed project nature from maven to gradle nature
- extracted project properties to gradle.properties
- extracted project gradle plugin versions to buildscript.ext area in gradle.properties

Version 5.7
-------------

CHANGED:

- factory methods for create PostgreSQL and mySql database now returns an enum with creation state

Version 5.6
-------------

ADDED:

- created new factory method for create PostgreSQL database from properties

Version 5.5
-------------

ADDED:

- created new factory methods for create PostgreSQL databases

CHANGED:

- update of spring dependency to version 5.2.1.RELEASE

Version 5.4
-------------

ADDED:

- created new connection classes for H2, MySql and PostgreSQL

Version 5.3
-------------

CHANGED:

- automate closing all streams and connections with the try construct
- made method arguments null safe
- javadoc extended

Version 5.2
-------------

ADDED:

- this changelog file
- created PULL_REQUEST_TEMPLATE.md file
- created CODE_OF_CONDUCT.md file
- created CONTRIBUTING.md file
- provide package.html for the javadoc of packages
- moved classes from obsolet jcommons-lang project
