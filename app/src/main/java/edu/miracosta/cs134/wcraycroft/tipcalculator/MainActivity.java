package edu.miracosta.cs134.wcraycroft.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Currency or Percent
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault());

    // Declare model
    private Bill currentBill;

    // Member variables for each component used in the app
    private EditText amountEditText;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;
    private SeekBar percentSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all member variables in onCreate
        amountEditText = findViewById(R.id.amountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);
        percentSeekBar = findViewById(R.id.percentSeekBar);

        // Instantiate model
        currentBill = new Bill();
        // Set tip percentage to match seek bar
        currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0);

        // SeekBar change event
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update model
                currentBill.setTipPercent(progress / 100.0);
                // Change label of tip percent, tip amount and total amount
                percentTextView.setText(percent.format(currentBill.getTipPercent()));
                tipTextView.setText(currency.format(currentBill.getTipAmount()));
                totalTextView.setText(currency.format(currentBill.getTotalAmount()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Does nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Does nothing
            }
        });

        // EditText change event
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Does nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update model
                try {
                    currentBill.setAmount(Double.parseDouble(s.toString()));
                } catch (NumberFormatException e){
                    Log.w("TipCalculator", "NumberFormatException thrown. Attempted to parse double on empty string?");
                }
                // Update labels
                tipTextView.setText(currency.format(currentBill.getTipAmount()));
                totalTextView.setText(currency.format(currentBill.getTotalAmount()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}

