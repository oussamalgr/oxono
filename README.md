# Oxono — Board Game in Java/JavaFX

## Description

Oxono is a desktop application developed in Java / JavaFX 21 as part of a school project.

It is a digital adaptation of the Oxono board game, in which a player faces a bot with different difficulty levels.

The objective: to offer a playable, ergonomic version faithful to the game rules while applying clean software architecture and advanced patterns.

## Features

### Graphical Interface (JavaFX)
- Game configuration menu
- Bot difficulty selection (Easy / Normal)
- Undo / Redo action management
- Fully developed JavaFX interface

### Special Features
- Battle against a bot equipped with algorithms adapted to the chosen difficulty

### Game Logic
- Complete implementation of official Oxono game rules
- Management of turns, valid actions and game state
- Consistent mechanisms for AI according to difficulty

## Architecture & Tools

The project applies several design patterns and principles:

- **MVC** (Model–View–Controller)
- **Observer / Observable Pattern**
- **Strategy Design Pattern** (for bot logic)
- **Command Design Pattern** (Undo/Redo)
- JavaFX event handling
- JUnit unit tests
- Maven build

These choices ensure a structured, extensible and testable project.

## Technologies

- Java 21
- JavaFX 21
- Maven
- JUnit

## Prerequisites

- JDK 21
- JavaFX 21
- Maven installed and configured

## Maven Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-media</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Author

**Oussamalgr**
