# Activation Service

The **Activation Service** is a Java-based HTTP endpoint designed to receive activation requests, select the list of assets that can satisfy the demand, and respond with the activated assets along with their associated powers and prices.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Running the Application](#running-the-application)
- [API Usage](#api-usage)
  - [Activate Assets](#activate-assets)
- [Testing](#testing)

  
## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- Maven build tool

### Running the Application

1. **Clone the repository:**

   ```bash
   git clone https://github.com/ManiT04/Activation_API.git
   cd Activation_API
   ```

If your using an IDE like IntelliJ, Eclipse or VSCode you can use their Run button to start the application (ActivationServiceApplication), or use maven to build the project.

The application will be accessible at http://localhost:8080.

## API Usage

### Activate Assets
- Endpoint: POST /activation/activate

Activates assets based on the provided activation request.
Here is an example for the request: 
```json
{
  "date": "2024-02-21",
  "volume": 40
}
```
- date (String): Date of activation.
- volume (Integer): Number of kW needed.

A success response can be:
```json
[
    {
        "code": "A3",
        "activatedVolume": 40,
        "activationCost": 12.0
    }
]
```
### Testing
The application includes JUnit tests for the activation logic. To run the tests, you can also use the Run button of your IDE, or use maven.
