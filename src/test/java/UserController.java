import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;

    public UserController(Properties prop) {
        this.prop = prop;
        RestAssured.baseURI = prop.getProperty("baseUrl");
    }

    public Response login() {
        return given().contentType("application/json")
                .body("{\n" +
                        "    \"email\":\"admin@roadtocareer.net\",\n" +
                        "    \"password\":\"1234\"\n" +
                        "}\n")
                .when().post("/user/login");
    }


    public Response createUser(UserModel userModel){
        return given().contentType("application/json")
                .header("Authorization","bearer "+prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY",prop.getProperty("secretKey"))
                .body(userModel)
                .when().post("/user/create");
    }

    public Response transferToAgent(String agentPhoneNumber, int amount) {
        return given().contentType("application/json")
                .header("Authorization", "bearer " + prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                .body("{\n" +
                        "  \"from_account\": \"SYSTEM\",\n" +
                        "  \"to_account\": \"" + agentPhoneNumber + "\",\n" +
                        "  \"amount\": " + amount + "\n" +
                        "}")
                .when().post("/transaction/deposit");
    }

    public Response depositToCustomer(String fromAccount, String toAccount, int amount) {
        return given().contentType("application/json")
                .header("Authorization", "bearer " + prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                .body("{ \"from_account\": \"" + fromAccount + "\", \"to_account\": \"" + toAccount + "\", \"amount\": " + amount + " }")
                .when().post("/transaction/deposit");
    }

    public Response withdrawFromCustomer(String fromAccount, String toAccount, int amount) {
        return given().contentType("application/json")
                .header("Authorization", "bearer " + prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                .body("{ \"from_account\": \"" + fromAccount + "\", \"to_account\": \"" + toAccount + "\", \"amount\": " + amount + " }")
                .when().post("/transaction/withdraw");
    }

    public Response sendMoney(String fromAccount, String toAccount, int amount) {
        return given().contentType("application/json")
                .header("Authorization", "bearer " + prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                .body("{ \"from_account\": \"" + fromAccount + "\", \"to_account\": \"" + toAccount + "\", \"amount\": " + amount + " }")
                .when().post("/transaction/sendmoney");
    }

    public Response makePayment(String fromAccount, String toAccount, int amount) {
        return given().contentType("application/json")
                .header("Authorization", "bearer " + prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                .body("{ \"from_account\": \"" + fromAccount + "\", \"to_account\": \"" + toAccount + "\", \"amount\": " + amount + " }")
                .when().post("/transaction/payment");
    }

    public Response checkBalance(String phoneNumber) {
        return given().contentType("application/json")
                .header("Authorization", "bearer " + prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                .when().get("/transaction/balance/" + phoneNumber);
    }



}
