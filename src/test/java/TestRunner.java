import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestRunner extends Setup {

    @Test(priority = 1, description = "User login")
    public void login() throws ConfigurationException {
        UserController userController = new UserController(prop);
        Response res = userController.login();
        System.out.println(res.asString());

        JsonPath jsonPath = res.jsonPath();
        String token = jsonPath.get("token");
        System.out.println(token);

        Utils.setEnvVar("token", token);
    }

    @Test(priority = 2, description = "Create two new customers and a agent")
    public void createUser() {
        UserController userController = new UserController(prop);
        Faker faker = new Faker();

        // Create Customer 1
        UserModel customer1 = new UserModel();
        customer1.setName(faker.name().fullName());
        customer1.setEmail(faker.internet().emailAddress().toLowerCase());
        customer1.setPassword("1234");
        customer1.setPhone_number("0180" + Utils.generateRandomId(1000000, 9999999));
        customer1.setNid("123456789");
        customer1.setRole("Customer");

        Response res1 = userController.createUser(customer1);
        String customer1PhoneNumber = customer1.getPhone_number();
        Utils.savePhoneNumber("customer1PhoneNumber", customer1PhoneNumber);

        // Create Customer 2
        UserModel customer2 = new UserModel();
        customer2.setName(faker.name().fullName());
        customer2.setEmail(faker.internet().emailAddress().toLowerCase());
        customer2.setPassword("1234");
        customer2.setPhone_number("0180" + Utils.generateRandomId(1000000, 9999999));
        customer2.setNid("123456789");
        customer2.setRole("Customer");

        Response res2 = userController.createUser(customer2);
        String customer2PhoneNumber = customer2.getPhone_number(); // Save phone number
        Utils.savePhoneNumber("customer2PhoneNumber", customer2PhoneNumber);

        // Create Agent
        UserModel agent = new UserModel();
        agent.setName(faker.name().fullName());
        agent.setEmail(faker.internet().emailAddress().toLowerCase());
        agent.setPassword("1234");
        agent.setPhone_number("0180" + Utils.generateRandomId(1000000, 9999999));
        agent.setNid("123456789");
        agent.setRole("Agent");

        Response res3 = userController.createUser(agent);
        String agentPhoneNumber = agent.getPhone_number(); // Save phone number
        Utils.savePhoneNumber("agentPhoneNumber", agentPhoneNumber);

        // Create Merchant for a payment procedure
        UserModel merchant = new UserModel();
        merchant.setName(faker.name().fullName());
        merchant.setEmail(faker.internet().emailAddress().toLowerCase());
        merchant.setPassword("1234");
        merchant.setPhone_number("0180" + Utils.generateRandomId(1000000, 9999999));
        merchant.setNid("123456789");
        merchant.setRole("Merchant");

        Response res4 = userController.createUser(merchant);
        String merchantPhoneNumber = merchant.getPhone_number(); // Save phone number
        Utils.savePhoneNumber("merchantPhoneNumber", merchantPhoneNumber);


        // Log or print messages for debugging
        System.out.println("Customer 1 Response: " + res1.asString());
        System.out.println("Customer 2 Response: " + res2.asString());
        System.out.println("Agent Response: " + res3.asString());
        System.out.println("Merchant Response: " + res4.asString());

    }

    @Test(priority = 3, description = "Transfer 2000 tk to Agent")
    public void transferToAgent() {
        UserController userController = new UserController(prop);

        // Retrieve agent's phone number from data.json using Utils.readJsonFile()
        JSONObject jsonData = Utils.readJsonFile();
        String agentPhoneNumber = (String) jsonData.get("agentPhoneNumber");

        // Perform the transfer operation
        Response res = userController.transferToAgent(agentPhoneNumber, 2000);

        // Assert that the status code is 201 (created/successful transaction)
        Assert.assertEquals(res.getStatusCode(), 201);

        // Assert the response message for successful deposit
        String messageActual = res.jsonPath().getString("message");
        String messageExpected = "Deposit successful"; // Adjust to the exact expected message
        Assert.assertTrue(messageActual.contains(messageExpected), "Expected message to contain: " + messageExpected);

        // Print success message to terminal
        System.out.println("Deposit successful for agent: " + agentPhoneNumber);
    }


    @Test(priority = 4, description = "Deposit 1500 tk to Customer from Agent")
    public void depositToCustomer() {
        UserController userController = new UserController(prop);

        // Retrieve the agent and customer phone numbers from data.json using Utils.readJsonFile()
        JSONObject jsonData = Utils.readJsonFile();
        String agentPhoneNumber = (String) jsonData.get("agentPhoneNumber");
        String customerPhoneNumber = (String) jsonData.get("customer1PhoneNumber");

        // Execute the deposit
        Response res = userController.depositToCustomer(agentPhoneNumber, customerPhoneNumber, 1500);

        // Assert the status code is 201 (indicating success)
        Assert.assertEquals(res.getStatusCode(), 201);

        // Assert the response message for successful deposit
        String messageActual = res.jsonPath().getString("message");
        String messageExpected = "Deposit successful"; // Adjust based on your API response
        Assert.assertTrue(messageActual.contains(messageExpected), "Expected message to contain: " + messageExpected);

        // Print success message to terminal
        System.out.println("Deposit of 1500 tk successful from agent: " + agentPhoneNumber + " to customer: " + customerPhoneNumber);
    }


    @Test(priority = 5, description = "Withdraw 500 tk by Customer to Agent")
    public void withdrawToAgent() {
        UserController userController = new UserController(prop);

        // Retrieve the customer and agent phone numbers from data.json using Utils.readJsonFile()
        JSONObject jsonData = Utils.readJsonFile();
        String customerPhoneNumber = (String) jsonData.get("customer1PhoneNumber");
        String agentPhoneNumber = (String) jsonData.get("agentPhoneNumber");

        // Execute the withdrawal
        Response res = userController.withdrawFromCustomer(customerPhoneNumber, agentPhoneNumber, 500);

        // Assert the status code is 201 (indicating success)
        Assert.assertEquals(res.getStatusCode(), 201);

        // Assert the response message for successful withdrawal
        String messageActual = res.jsonPath().getString("message");
        String messageExpected = "Withdraw successful"; // Adjust based on your API response
        Assert.assertTrue(messageActual.contains(messageExpected), "Expected message to contain: " + messageExpected);

        // Print success message to terminal
        System.out.println("Withdrawal of 500 tk successful from customer: " + customerPhoneNumber + " to agent: " + agentPhoneNumber);
    }


    @Test(priority = 6, description = "Send 500 tk to another customer")
    public void sendMoneyToAnotherCustomer() {
        UserController userController = new UserController(prop);

        // Retrieve customer phone numbers from data.json using Utils.readJsonFile()
        JSONObject jsonData = Utils.readJsonFile();
        String customer1PhoneNumber = (String) jsonData.get("customer1PhoneNumber");
        String customer2PhoneNumber = (String) jsonData.get("customer2PhoneNumber");

        // Execute the money send operation
        Response res = userController.sendMoney(customer1PhoneNumber, customer2PhoneNumber, 500);

        // Assert the status code is 201 (indicating success)
        Assert.assertEquals(res.getStatusCode(), 201);

        // Assert the response message for successful money sending
        String messageActual = res.jsonPath().getString("message");
        String messageExpected = "Send money successful"; // Adjust this based on your API response
        Assert.assertTrue(messageActual.contains(messageExpected), "Expected message to contain: " + messageExpected);

        // Print success message to terminal
        System.out.println("Successfully sent 500 tk from customer: " + customer1PhoneNumber + " to customer: " + customer2PhoneNumber);
    }

    @Test(priority = 7, description = "Make a payment to merchant")
    public void paymentToMerchant() {
        UserController userController = new UserController(prop);

        // Retrieve phone numbers from data.json
        JSONObject jsonData = Utils.readJsonFile();
        String customer2PhoneNumber = (String) jsonData.get("customer2PhoneNumber");
        String merchantPhoneNumber = (String) jsonData.get("merchantPhoneNumber");

        // Execute the payment operation
        Response res = userController.makePayment(customer2PhoneNumber, merchantPhoneNumber, 100);

        // Assert the status code is 201 (success)
        Assert.assertEquals(res.getStatusCode(), 201);

        // Assert the response message for successful payment
        String messageActual = res.jsonPath().getString("message");
        String messageExpected = "Payment successful"; // Adjust based on actual API response
        Assert.assertTrue(messageActual.contains(messageExpected), "Expected message to contain: " + messageExpected);

        // Print success message to terminal
        System.out.println("Successfully made a payment of 100 tk from customer: " + customer2PhoneNumber + " to merchant: " + merchantPhoneNumber);
    }


    @Test(priority = 8, description = "Check balance of the recipient customer")
    public void checkCustomerBalance() {
        UserController userController = new UserController(prop);

        // Retrieve phone number of the customer from data.json
        JSONObject jsonData = Utils.readJsonFile();
        String customer2PhoneNumber = (String) jsonData.get("customer2PhoneNumber");

        // Execute the balance inquiry
        Response res = userController.checkBalance(customer2PhoneNumber);

        // Assert the status code is 200 (OK)
        Assert.assertEquals(res.getStatusCode(), 200);

        // Assert the response message for successful balance check
        String messageActual = res.jsonPath().getString("message");
        String messageExpected = "User balance"; // Adjust based on actual API response
        Assert.assertTrue(messageActual.contains(messageExpected), "Expected message to contain: " + messageExpected);

        // Retrieve and print the balance from the response
        String balance = res.jsonPath().getString("balance"); // Assuming 'balance' is the field returned in the response
        System.out.println("Balance of the recipient customer (" + customer2PhoneNumber + ") is: " + balance);
    }




    @AfterMethod
    public void delay() throws InterruptedException {
        Thread.sleep(2000);
    }

}
