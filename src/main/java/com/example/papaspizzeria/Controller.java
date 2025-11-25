package com.example.papaspizzeria;

import com.example.papaspizzeria.sweproject.api.CustomerService;
import com.example.papaspizzeria.sweproject.api.MenuItemService;
import com.example.papaspizzeria.sweproject.api.OrderService;
import com.example.papaspizzeria.sweproject.models.Customer;
import com.example.papaspizzeria.sweproject.models.MenuItem;
import com.example.papaspizzeria.sweproject.models.Order;
import com.example.papaspizzeria.sweproject.models.OrderItem;
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
    OrderCart orderCart = OrderCart.getInstance(); // ADD THIS: Order tracking singleton

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

    // ADD THESE: For displaying order information
    @FXML private Label orderTotalLabel;
    @FXML private Text orderSummaryText;
    @FXML private Text orderDetailsText;

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
        orderCart.reset(); // MODIFIED: Clear order cart when signing out
        BackToMenu();
    }

    @FXML
    public void ToOrderScreen() throws IOException {
        orderCart.reset(); // MODIFIED: Start with fresh order
        Main.setRoot("/Crust.fxml");
    }

    @FXML
    public void ToOrderCompleteScreen() throws IOException{
        updateOrderDisplay(); // MODIFIED: Update display before showing
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

        // MODIFIED: Actually create and submit the order
        try {
            Order order = orderCart.getCurrentOrder();
            order.setItems(orderCart.getOrderItems());
            order.setEmployeeId(0); // Set to actual employee ID if needed

            // Submit order to backend API
            Order createdOrder = OrderService.createOrder(order);
            orderCart.setCurrentOrder(createdOrder);

            System.out.println("Order created successfully with ID: " + createdOrder.getId());
            Main.setRoot("/OrderComplete.fxml");
        } catch (Exception e) {
            showError("Error creating order: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private Button BackToOrderCompleteButton;

    @FXML
    public void BackToOrderComplete() throws IOException {
        updateOrderDisplay(); // MODIFIED: Update display
        Main.setRoot("/OrderComplete.fxml");
    }

    @FXML
    private Button OrderDetailsButton;

    @FXML
    public void ToOrderDetails() throws IOException {
        updateOrderDisplay(); // MODIFIED: Update display before showing details
        Main.setRoot("/OrderDetails.fxml");
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
        // Initialize topping checkboxes
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
                        // MODIFIED: Track topping selection
                        orderCart.addTopping(cb.getText());
                        System.out.println("Added topping: " + cb.getText());
                    } else {
                        selectedCount--;
                        // MODIFIED: Remove topping
                        orderCart.removeTopping(cb.getText());
                        System.out.println("Removed topping: " + cb.getText());
                    }
                    checkSelectionLimit();
                });
            }
        }

        // MODIFIED: Update display on page load
        updateOrderDisplay();
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
        orderCart.reset(); // Start fresh order
        Main.setRoot("/Crust.fxml");
    }

    // MODIFIED: Now captures selection from button userData
    @FXML
    private void handleGoToSize(ActionEvent event) throws IOException {
        // Get the button that was clicked
        Button clickedButton = (Button) event.getSource();
        String crustSelection = (String) clickedButton.getUserData();

        if (crustSelection != null) {
            orderCart.setSelectedCrust(crustSelection);
            System.out.println("Selected crust: " + crustSelection);
        }

        Main.setRoot("/Size.fxml");
    }

    // MODIFIED: Now captures selection from button userData
    @FXML
    private void handleGoToSauce(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String sizeSelection = (String) clickedButton.getUserData();

        if (sizeSelection != null) {
            orderCart.setSelectedSize(sizeSelection);
            System.out.println("Selected size: " + sizeSelection);
        }

        Main.setRoot("/Sauce.fxml");
    }

    // MODIFIED: Now captures selection from button userData
    @FXML
    private void handleGoToToppings(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String sauceSelection = (String) clickedButton.getUserData();

        if (sauceSelection != null) {
            orderCart.setSelectedSauce(sauceSelection);
            System.out.println("Selected sauce: " + sauceSelection);
        }

        Main.setRoot("/Toppings.fxml");
    }

    @FXML
    private void handleGoToBeverage(ActionEvent event) throws IOException {
        Main.setRoot("/Beverage.fxml");
    }

    // MODIFIED: Now captures selection from button userData
    @FXML
    private void handleGoToDesert(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String beverageSelection = (String) clickedButton.getUserData();

        if (beverageSelection != null) {
            orderCart.setSelectedBeverage(beverageSelection);
            System.out.println("Selected beverage: " + beverageSelection);
        }

        Main.setRoot("/Desert.fxml");
    }

    // MODIFIED: Now captures selection from button userData
    @FXML
    private void handleGoToSide(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String dessertSelection = (String) clickedButton.getUserData();

        if (dessertSelection != null) {
            orderCart.setSelectedDessert(dessertSelection);
            System.out.println("Selected dessert: " + dessertSelection);
        }

        Main.setRoot("/Side.fxml");
    }

    // MODIFIED: Creates order items and navigates to payment
    @FXML
    private void handleGoToOrderComplete(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String sideSelection = (String) clickedButton.getUserData();

        if (sideSelection != null) {
            orderCart.setSelectedSide(sideSelection);
            System.out.println("Selected side: " + sideSelection);
        }

        // BUILD ORDER ITEMS FROM SELECTIONS
        buildOrderItems();

        Main.setRoot("/PaymentMethod.fxml");
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

    // NEW METHOD: Build order items from user selections
    private void buildOrderItems() {
        try {
            // Clear existing items
            orderCart.getOrderItems().clear();

            // Create a pizza order item combining crust, size, sauce, and toppings
            // In a real implementation, you'd fetch the actual MenuItem from your database
            // For now, using estimated prices - REPLACE WITH ACTUAL API CALLS

            float pizzaPrice = calculatePizzaPrice();
            String pizzaDescription = String.format("%s %s Pizza with %s sauce and toppings: %s",
                    orderCart.getSelectedSize(),
                    orderCart.getSelectedCrust(),
                    orderCart.getSelectedSauce(),
                    String.join(", ", orderCart.getSelectedToppings())
            );

            MenuItem pizzaItem = new MenuItem(1, pizzaDescription, pizzaPrice,
                    String.join(", ", orderCart.getSelectedToppings()));
            OrderItem pizzaOrderItem = new OrderItem(pizzaItem, 1, pizzaPrice);
            orderCart.addOrderItem(pizzaOrderItem);

            // Add beverage if selected
            if (orderCart.getSelectedBeverage() != null && !orderCart.getSelectedBeverage().isEmpty()) {
                MenuItem beverageItem = new MenuItem(2, orderCart.getSelectedBeverage(), 2.99f, "");
                OrderItem beverageOrderItem = new OrderItem(beverageItem, 1, 2.99f);
                orderCart.addOrderItem(beverageOrderItem);
            }

            // Add dessert if selected
            if (orderCart.getSelectedDessert() != null && !orderCart.getSelectedDessert().isEmpty()) {
                MenuItem dessertItem = new MenuItem(3, orderCart.getSelectedDessert(), 4.99f, "");
                OrderItem dessertOrderItem = new OrderItem(dessertItem, 1, 4.99f);
                orderCart.addOrderItem(dessertOrderItem);
            }

            // Add side if selected
            if (orderCart.getSelectedSide() != null && !orderCart.getSelectedSide().isEmpty()) {
                MenuItem sideItem = new MenuItem(4, orderCart.getSelectedSide(), 5.99f, "");
                OrderItem sideOrderItem = new OrderItem(sideItem, 1, 5.99f);
                orderCart.addOrderItem(sideOrderItem);
            }

            System.out.println("Built " + orderCart.getOrderItems().size() + " order items");
            System.out.println("Total: $" + orderCart.calculateTotal());

        } catch (Exception e) {
            showError("Error building order: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // NEW METHOD: Calculate pizza price based on size and toppings
    private float calculatePizzaPrice() {
        float basePrice = 10.99f;

        // Add price based on size
        if (orderCart.getSelectedSize() != null) {
            switch (orderCart.getSelectedSize().toLowerCase()) {
                case "small":
                    basePrice = 8.99f;
                    break;
                case "medium":
                    basePrice = 10.99f;
                    break;
                case "large":
                    basePrice = 12.99f;
                    break;
                case "extra large":
                case "xlarge":
                    basePrice = 14.99f;
                    break;
            }
        }

        // Add $1.50 per topping
        int toppingCount = orderCart.getSelectedToppings().size();
        basePrice += (toppingCount * 1.50f);

        return basePrice;
    }

    // NEW METHOD: Update the order display on screen
    private void updateOrderDisplay() {
        try {
            float total = orderCart.calculateTotal();

            // Update total label if it exists
            if (orderTotalLabel != null) {
                orderTotalLabel.setText(String.format("$%.2f", total));
            }

            // Update summary text if it exists
            if (orderSummaryText != null) {
                orderSummaryText.setText(orderCart.getOrderSummary());
            }

            // Update details text if it exists
            if (orderDetailsText != null) {
                orderDetailsText.setText(orderCart.getOrderSummary());
            }

            System.out.println("Display updated - Total: $" + total);

        } catch (Exception e) {
            System.err.println("Error updating display: " + e.getMessage());
        }
    }
}