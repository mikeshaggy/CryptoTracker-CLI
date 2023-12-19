# CryptoTracker

CryptoTracker is a Java-based application designed to manage cryptocurrency portfolios. It allows users to track their trades, view portfolio details, and interact with a user-friendly interface.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Dependencies](#dependencies)
- [License](#license)
- [Author](#author)

## Overview

CryptoTracker is a text-based RPG game developed in Java, created as a coursework assignment for the Object-Oriented Programming in Java course during the second semester of Computer Science studies at the Polish-Japanese Academy of Information Technology.

## Features

- **Portfolio Management:** Track and manage cryptocurrency trades.
- **Interactive User Interface:** JavaFX library provides a visually appealing UI.
- **Real-Time Data:** Utilizes CoinAPI to fetch real-time exchange rates.
- **Data Serialization:** Save and load portfolios for future reference.

## Project Structure

The project is organized into several key packages:

- `app.model`: Contains classes representing the core model of the application, such as Portfolio and Trade.
- `app`: Houses the main application class (App), user interface components, and additional utility classes.
- `app.ui`: Handles user interface functionality.
- `app.model`: Includes classes for managing API calls and fetching cryptocurrency data.
- `app.model`: Manages data serialization and file operations.

## Technologies Used

- Java
- JavaFX
- Maven
- Jackson Databind
- JUnit 5
- AssertJ
- Mockito

## System Requirements

To run the application, ensure you have the following installed:

- Java Development Kit (JDK) 21 or newer
- Maven

## Installation

1. Clone the repository: `git clone https://github.com/your-username/CryptoTracker.git`
2. Navigate to the project directory
3. Install dependencies with Maven: `mvn clean install`
4. Run the application: `mvn exec:java`

## Usage

- Launch the application with the `mvn exec:java` command.
- Follow the on-screen menu to manage portfolios, add trades, and view portfolio details.

## Testing

The project includes JUnit 5 tests to ensure the functionality of key components. Run tests with `mvn test`.

## Dependencies

- [Jackson Databind](https://github.com/FasterXML/jackson-databind)
- [JUnit 5](https://junit.org/junit5/)
- [AssertJ](https://assertj.github.io/doc/)
- [Mockito](https://site.mockito.org/)
- [Byte Buddy](https://bytebuddy.net/)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

**Michał Bagan**

- [GitHub](https://github.com/mikeshaggy)
- [LinkedIn](www.linkedin.com/in/michalbagan/)