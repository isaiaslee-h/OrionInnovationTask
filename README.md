# OrionInnovationTask
# Restbooker API Tests

This project contains automated API tests for the [Restful Booker API](https://restful-booker.herokuapp.com/), implemented in **Java** using **JUnit 5** and **RestAssured**.

## 📂 Project Structure

restbooker-api-tests
├── pom.xml # Maven configuration
├── src
│ ├── main/java/com/isaias
│ │ └── Main.java # Sample entry point
│ └── test/java/com/isaias
│ ├── framework
│ │ ├── BaseTest.java # Base setup for tests
│ │ └── AuthClient.java # Authentication utility
│ └── tests
│ └── BookingApiTests.java # API test cases
├── bash.sh # Shell script to run tests
└── target/ # Compiled classes & build output

markdown
Copy code

## 🚀 Getting Started

### Prerequisites
- **Java 11+**
- **Maven 3.6+**

### Clone the Repository
```bash
git clone https://github.com/your-username/restbooker-api-tests.git
cd restbooker-api-tests
Run Tests
You can run tests using Maven:

bash
Copy code
mvn clean test
Or using the included script:

bash
Copy code
bash src/test/java/bash.sh
🧪 Example Test
Example test from BookingApiTests.java:

java
Copy code
@Test
void canListBookingIds() {
    List<Integer> bookingIds =
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/booking")
        .then()
            .statusCode(200)
            .extract().jsonPath().getList("bookingid");

    assertThat(bookingIds).isNotEmpty();
}
📦 Dependencies
The project uses:

JUnit 5 - Test framework

RestAssured - API testing library

AssertJ - Fluent assertions

(See pom.xml for full list)

✨ Author
Isaias Iñiguez – Software Developer in Test
