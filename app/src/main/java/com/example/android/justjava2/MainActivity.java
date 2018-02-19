package com.example.android.justjava2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.justjava2.R;
/**
 •	This app displays an order form to order coffee.
 */


public class MainActivity extends AppCompatActivity {


    int quantity = 2;
    private String AMOUNT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Use onSaveInstanceState(Bundle) and onRestoreInstanceState
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(AMOUNT, quantity);
    }
    //onRestoreInstanceState
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.


        quantity = savedInstanceState.getInt(AMOUNT);
        displayQuantity(quantity);

    }


    public void submitOrder(View view) {
        EditText enterName = (EditText)findViewById(R.id.nameBar);
        String showName = enterName.getText().toString(); //gets you the contents of edit text
        CheckBox whippedCreamCheckbox = (CheckBox)findViewById(R.id.whipped_cream_box);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox)findViewById(R.id.chocolate_box);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(showName, price, hasWhippedCream, hasChocolate);




        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Java_order) + showName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * Calculates the price of the order.
     *
     * @param hasChocolate price if has chocolate
     *  @param hasWhippedCream if has whipped cream
     *
     *@return total price
     */

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if(hasWhippedCream){
            basePrice = basePrice + 1;
        }
        if(hasChocolate){
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }


    /**create summary of order
     *
     * @param showName name of customer
     * @param hasChocolate to add chocolate topping to order
     *@param hasWhippedCream to add whipped cream to order
     * @param price of order
     * @return text summary
     */
    private String createOrderSummary(String showName, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage = getString(R.string.summary_order_name) + showName;
        priceMessage +=  "\n" + getString(R.string.add_cream) + hasWhippedCream;
        priceMessage += "\n" + getString(R.string.Chocolate) + hasChocolate;
        priceMessage +=  "\n" + getString(R.string.Quantity) + quantity;
        priceMessage += "\n" + getString(R.string.total) + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {

        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 cups of Coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 cup of Coffee!", Toast.LENGTH_SHORT).show();
            return;
        }



        quantity = quantity - 1;
        displayQuantity(quantity);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
