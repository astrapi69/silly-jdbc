## Change log
----------------------

Version 6-SNAPSHOT
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
