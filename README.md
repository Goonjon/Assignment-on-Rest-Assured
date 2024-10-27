# Dmoney API Automation with Rest Assured
### Project Summary: This project automates testing for a financial transaction system, covering scenarios like creating customers and agent, transferring money between customers, agents, and merchants, verifying deposits, withdrawals, payments, and balance checks. Each test validates transaction success and response accuracy, using JSON to store dynamic test data for seamless execution.

### Technologies I have used: 
- Language: Java
- Build System: Gradle
- Testing Frameworks: TestNG for test management, Rest Assured for API automation
- IDE: IntelliJ IDEA
- Data Management: config.properties for storing baseUrl, partnerKey, and token; JSON arrays for managing new customer and agent information to facilitate API transactions.
  
### Project Flow:
- Set up the project using Gradle with the necessary dependencies for Rest Assured and TestNG.
- Store API configurations such as baseUrl, partnerKey, and token in a config.properties file for easy access and management.
- Create a JSON array file to hold new customer and agent information for transaction operations.
- Create new users (customer, agent), transfer funds, deposit amounts, withdraw funds, and make payments.
- Chain API calls to perform transactions and assert success responses for each operation.
- Utilize TestNG for parameterized tests and report generation using Allure for clear visibility of test results.
- Execute tests using Gradle to streamline the testing process.

### How to run?
1. Open IntelliJ IDEA and select New Project.
2. Create a Java project and name it.
3. Open the project in IntelliJ: File > Open > Select and expand folder > Open as project.
4. Configure Dependencies: Ensure that you have Gradle configured. The necessary dependencies will be managed automatically through the build.gradle file.
5. To run the test suites, execute the command: ```gradle clean test```
7. To generate the Allure report, run: ```allure generate allure-results --clean```
                                       ```allure serve allure-results```

### Screenshots Of the Allure Report:
<img width="960" alt="Screenshot 2024-10-28 004331" src="https://github.com/user-attachments/assets/de23fc57-5947-42ec-81fb-69b99a359403">
<img width="960" alt="Screenshot 2024-10-28 004424" src="https://github.com/user-attachments/assets/1bba70bf-fa7c-4293-b5ca-653f316192e4">


