package com.mateenmehmood.justjava;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

//This app displays an order form to order coffee.
public class MainActivity extends AppCompatActivity {

    //onCreate method created automatically to create the first activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Creating coffeeQuantity variable
    private int coffeeQuantity = 2;
    //creating static final member PRICE_OF_COFFEE
    private static final int PRICE_OF_COFFEE=5;
    //creating totalPrice variable to calculate price
    private int totalPrice = 0;
    //creating boolean variable to check if Whipped cream checkbox is checked or not
    private boolean hasWhippedCream;
    //creating static final member PRICE_OF_WHIPPED_CREAM
    private static final int PRICE_OF_WHIPPED_CREAM = 1;
    //creating boolean variable to check if hasChocolate checkbox is checked or not
    private boolean hasChocolate;
    //creating static final member PRICE_OF_CHOCOLATE
    private static final int PRICE_OF_CHOCOLATE = 2;
    //creating String variable to store user name (through EditText)
    private String yourName;

    //This method is to take user name as an input
    private void getInput() {
        //assigning name to its appropriate view
        EditText name = findViewById(R.id.name_edit_text);
        //storing user name in the yourName
        yourName = name.getText().toString();
    }


    //This method is called when the + button is pressed
    public void increment(View view) {
        if (coffeeQuantity == 100) {
            //Toast message if user tapping on (+) button even if value is 100
            Toast.makeText(this, "You could only choose 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        } else {
            coffeeQuantity = coffeeQuantity + 1;
            display(coffeeQuantity);
        }
    }

    //This method is called when the - button is pressed
    public void decrement(View view) {
        if (coffeeQuantity == 1) {
            //Toast message if user tapping on (-) button even if value is 1
            Toast.makeText(this, "There should be at least 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        } else {
            coffeeQuantity = coffeeQuantity - 1;
            display(coffeeQuantity);
        }
    }

    //This method is called when the ORDER button is clicked
    public void submitOrder(View view){
        //calling method to get user name
        getInput();
        //calling method to check state of whipped cream checkbox when submitOrder button is tapped
        isChecked();
        //calling method to calculate total price
        calculatePrice();
        //intent to send data to an email app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //This method displays the given quantity value of Coffee on the screen
    private void display(int number) {
        TextView quantityShow = findViewById(R.id.quantityTextViewCounter);
        quantityShow.setText("" + number);
    }


    //This method will get value from the checkboxes
    //Whether it is checked or not
    private void isChecked() {
        //assigning hasWhippedCream checkbox a value by its appropriate view
        CheckBox whippedCream = findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCream.isChecked();
        //assigning hasChocolate checkbox a value by its appropriate view
        CheckBox chocolate = findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolate.isChecked();
    }

    //This method is to calculate price of Coffee With toppings or without
    public void calculatePrice () {
        //conditions if the user add toppings with coffee or not (calculating price)
        if (hasChocolate && hasWhippedCream)
            totalPrice = (coffeeQuantity * PRICE_OF_COFFEE) + (coffeeQuantity * PRICE_OF_CHOCOLATE) + (coffeeQuantity * PRICE_OF_WHIPPED_CREAM);
        else if (hasChocolate)
            totalPrice = (coffeeQuantity * PRICE_OF_COFFEE) + (coffeeQuantity * PRICE_OF_CHOCOLATE);
        else if (hasWhippedCream)
            totalPrice = (coffeeQuantity * PRICE_OF_COFFEE) + (coffeeQuantity * PRICE_OF_WHIPPED_CREAM);
        else
            totalPrice = coffeeQuantity * PRICE_OF_COFFEE;
    }

    //This method creates the summary of the order.
    private String createOrderSummary () {
        String message = getString(R.string.order_summary_name, yourName);
        message += "\n" + getString(R.string.order_summary_quantity, coffeeQuantity);
        message += "\n" + getString(R.string.order_summary_add_whipped_cream, hasWhippedCream);
        message += "\n" + getString(R.string.order_summary_quantity_add_chocolate, hasChocolate);
        message += "\n" + getString(R.string.order_summary_total_price, NumberFormat.getCurrencyInstance().format(totalPrice));
        message += "\n" + getString(R.string.thank_you);
        return message;
    }
}