package com.example.papaspizzeria;

import com.example.papaspizzeria.sweproject.api.CustomerService;
import com.example.papaspizzeria.sweproject.models.Customer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    Session CustomerSession = Session.getInstance();

    @FXML private Button SignUpButton;
    @FXML private Button LogInButton;
    @FXML private Pane mainPane;
    @FXML private Button CreateAccountButton;
    @FXML private TextField PhoneNumberTextField;
    @FXML private TextField PasswordTextField;
    @FXML private TextField FirstNameTextField;
    @FXML private TextField LastNameTextField;
    @FXML private TextField AddressTextField;
    @FXML private Button BackButton;
    @FXML private Text ErrorText;
    @FXML private Button LogInButton2;
    @FXML private Text CustomerNameText;
    @FXML private Button OrderButton;
    @FXML private Button AccountSettingsButton;
    @FXML private Button ContactUsButton;
    @FXML private Button SignOutButton;
    @FXML private Button CashButton;
    @FXML private Button CardButton;
    @FXML private Button BackToSideButton;
    @FXML private Button BackToPaymentButton;
    @FXML private TextField CardNumberTextField;
    @FXML private TextField NameOnCardTextField;
    @FXML private TextField ExpirationTextField;
    @FXML private TextField CVVTextField;
    @FXML private Button PurchaseButton;
    @FXML private Label welcomeText;
    @FXML private String pizza;

    private List<CheckBox> toppingsCheckboxes;
    private int selectedCount = 0;
    @FXML private CheckBox topping1;
    @FXML private CheckBox topping2;
    @FXML private CheckBox topping3;
    @FXML private CheckBox topping4;
    @FXML private CheckBox topping5;
    @FXML private CheckBox topping6;
    @FXML private CheckBox topping7;
    @FXML private CheckBox topping8;
    @FXML private CheckBox topping9;
    @FXML private CheckBox topping10;
    @FXML private CheckBox topping11;

    @FXML
    private void SignUpClick() throws IOException {
        Main.setRoot("/SignUp.fxml");
    }

    @FXML
    private void LogInClick() throws IOException {
        Main.setRoot("/LogIn.fxml");
    }

    @FXML
    private void CreateAccount() throws IOException {
        String ProvidedPhoneNumber = PhoneNumberTextField.getText();
        String ProvidedPassword = PasswordTextField.getText();
        String ProvidedFirstName = FirstNameTextField != null ? FirstNameTextField.getText() : "";
        String ProvidedLastName = LastNameTextField != null ? LastNameTextField.getText() : "";
        String ProvidedAddress = AddressTextField != null ? AddressTextField.getText() : "";

        boolean PhoneNumberIsAllNumbers = ProvidedPhoneNumber.chars().allMatch(Character::isDigit);

        if (!PhoneNumberIsAllNumbers || ProvidedPhoneNumber.length() != 10) {
            showError("Phone number must be 10 digits (Numbers only).");
            return;
        }

        boolean PasswordUpperCaseCheck = ProvidedPassword.chars().anyMatch(Character::isUpperCase);
        boolean PasswordSpecialCharCheck = ProvidedPassword.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));

        if (!PasswordUpperCaseCheck || !PasswordSpecialCharCheck || ProvidedPassword.length() < 8) {
            showError("Password must be at least 8 characters long and include at least one uppercase letter and one special character.");
            return;
        }

        try {
            Customer newCustomer = new Customer(
                    ProvidedPhoneNumber,
                    ProvidedFirstName,
                    ProvidedLastName,
                    ProvidedAddress,
                    ProvidedPassword,
                    ""
            );

            Customer createdCustomer = CustomerService.register(newCustomer);

            CustomerSession.setPhoneNumber(createdCustomer.getPhonenumber());
            CustomerSession.setPassword(ProvidedPassword);
            CustomerSession.setCustomer(createdCustomer);

            Main.setRoot("/CustomerHome.fxml");

        } catch (Exception e) {
            if (e.getMessage().contains("Phone number alredy registered")) {
                showError("There is already an existing account with that phone number.");
            } else {
                showError("Error creating account: " + e.getMessage());
            }
        }
    }

    @FXML
    private void BackToMenu() throws IOException {
        if (CustomerSession.isLoggedIn()){
            Main.setRoot("/CustomerHome.fxml");
        } else {
            Main.setRoot("/MainMenu.fxml");
        }
    }

    @FXML
    public void LogInAttempt() throws IOException {
        String ProvidedPhoneNumber = PhoneNumberTextField.getText();
        String ProvidedPassword = PasswordTextField.getText();

        try {
            Customer customer = CustomerService.login(ProvidedPhoneNumber, ProvidedPassword);

            if (customer != null) {
                CustomerSession.setPhoneNumber(customer.getPhonenumber());
                CustomerSession.setPassword(ProvidedPassword);
                CustomerSession.setCustomer(customer);

                Main.setRoot("/CustomerHome.fxml");
            } else {
                showError("Phone number or password is incorrect");
            }
        } catch (Exception e) {
            showError("Login error: " + e.getMessage());
        }
    }

    @FXML
    public void ToContactUs() throws IOException {
        Main.setRoot("/ContactUs.fxml");
    }

    @FXML
    public void SignOut() throws IOException {
        CustomerSession.clear();
        BackToMenu();
    }

    @FXML
    public void ToOrderScreen() throws IOException {
        Main.setRoot("/Crust.fxml");
    }

    @FXML
    public void ToOrderCompleteScreen() throws IOException{
        Main.setRoot("/OrderComplete.fxml");
    }

    @FXML
    public void ToCardScreen() throws IOException{
        Main.setRoot("/Card.fxml");
    }

    @FXML
    public void BackToSide() throws IOException{
        Main.setRoot("/Side.fxml");
    }

    @FXML
    public void BackToPayment() throws IOException{
        Main.setRoot("/PaymentMethod.fxml");
    }

    @FXML
    public void Purchase() throws IOException {
        String ProvidedCardNumber = CardNumberTextField.getText();
        String ProvidedNameOnCard = NameOnCardTextField.getText();
        String ProvidedExpiration = ExpirationTextField.getText();
        String ProvidedCVV = CVVTextField.getText();

        boolean CardNumberIsAllNumbers = ProvidedCardNumber.chars().allMatch(Character::isDigit);

        if (!CardNumberIsAllNumbers || ProvidedCardNumber.length() < 15 || ProvidedCardNumber.length() > 19){
            showError("Card number must be between 15-19 digits (Numbers only).");
            return;
        }

        boolean NameOnCardIsAllLetters = ProvidedNameOnCard.chars().allMatch(ch -> Character.isLetter(ch) || Character.isWhitespace(ch));

        if (!NameOnCardIsAllLetters || ProvidedNameOnCard.isBlank()){
            showError("Name on the card must be letters only (Cannot be blank).");
            return;
        }

        if (!ProvidedExpiration.matches("(0[1-9]|1[0-2])/(\\d{2}|\\d{4})")) {
            showError("Expiration date must be in the format: MM/YY.");
            return;
        }

        String[] parts = ProvidedExpiration.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        if (parts[1].length() == 2) {
            year += 2000;
        }

        java.time.YearMonth current = java.time.YearMonth.now();
        java.time.YearMonth entered = java.time.YearMonth.of(year, month);

        if (entered.isBefore(current)){
            showError("Invalid expiration date: Must be greater than the current date.");
            return;
        }

        boolean CVVIsAllNumbers = ProvidedCVV.chars().allMatch(Character::isDigit);

        if (!CVVIsAllNumbers || ProvidedCVV.length() != 3){
            showError("CVV must be 3 digits (Numbers only).");
            return;
        }

        Main.setRoot("/OrderComplete.fxml");
    }

    private void showError(String message) {
        if (ErrorText != null) {
            ErrorText.setText(message);
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> ErrorText.setText(""));
            delay.play();
        }
    }

    @FXML
    public void initialize() {
        toppingsCheckboxes = new ArrayList<>();
        toppingsCheckboxes.add(topping1);
        toppingsCheckboxes.add(topping2);
        toppingsCheckboxes.add(topping3);
        toppingsCheckboxes.add(topping4);
        toppingsCheckboxes.add(topping5);
        toppingsCheckboxes.add(topping6);
        toppingsCheckboxes.add(topping7);
        toppingsCheckboxes.add(topping8);
        toppingsCheckboxes.add(topping9);
        toppingsCheckboxes.add(topping10);
        toppingsCheckboxes.add(topping11);

        for (CheckBox cb : toppingsCheckboxes) {
            if (cb != null) {
                cb.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        selectedCount++;
                    } else {
                        selectedCount--;
                    }
                    checkSelectionLimit();
                });
            }
        }
    }

    private void checkSelectionLimit() {
        if (selectedCount == 4) {
            try {
                goToBeverageScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void goToBeverageScreen() throws IOException {
        Main.setRoot("/Beverage.fxml");
    }

    @FXML
    private void setPizza(ActionEvent event) {
        pizza = "Pizza";
        System.out.println("Pizza: " + pizza);
    }

    @FXML
    private void handleGoToCrust(ActionEvent event) throws IOException {
        Main.setRoot("/Crust.fxml");
    }

    @FXML
    private void handleGoToSize(ActionEvent event) throws IOException {
        Main.setRoot("/Size.fxml");
    }

    @FXML
    private void handleGoToSauce(ActionEvent event) throws IOException {
        Main.setRoot("/Sauce.fxml");
    }

    @FXML
    private void handleGoToToppings(ActionEvent event) throws IOException {
        Main.setRoot("/Toppings.fxml");
    }

    @FXML
    private void handleGoToBeverage(ActionEvent event) throws IOException {
        Main.setRoot("/Beverage.fxml");
    }

    @FXML
    private void handleGoToDesert(ActionEvent event) throws IOException {
        Main.setRoot("/Desert.fxml");
    }

    @FXML
    private void handleGoToSide(ActionEvent event) throws IOException {
        Main.setRoot("/Side.fxml");
    }

    @FXML
    private void handleGoToOrderComplete(ActionEvent event) throws IOException {
        Main.setRoot("/OrderComplete.fxml");
    }

    @FXML
    private void handleBackToCrust(ActionEvent event) throws IOException {
        Main.setRoot("/Crust.fxml");
    }

    @FXML
    private void handleBackToSize(ActionEvent event) throws IOException {
        Main.setRoot("/Size.fxml");
    }

    @FXML
    private void handleBackToSauce(ActionEvent event) throws IOException {
        Main.setRoot("/Sauce.fxml");
    }

    @FXML
    private void handleBackToToppings(ActionEvent event) throws IOException {
        Main.setRoot("/Toppings.fxml");
    }

    @FXML
    private void handleBackToBeverage(ActionEvent event) throws IOException {
        Main.setRoot("/Beverage.fxml");
    }

    @FXML
    private void handleBackToDesert(ActionEvent event) throws IOException {
        Main.setRoot("/Desert.fxml");
    }

    @FXML
    private void handleBackToMainMenu(ActionEvent event) throws IOException {
        Main.setRoot("/hello-viewold.fxml");
    }
}