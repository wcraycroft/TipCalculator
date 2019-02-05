package edu.miracosta.cs134.wcraycroft.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Currency or Percent
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault());

    // Member variables for each component used in the app
    private Bill currentBill;
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

        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update model
                currentBill.setTipPercent(progress / 100.0);
                // Change label of tip percent
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

        // Eve
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Does nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update model
                currentBill.setAmount(Double.parseDouble(amountEditText.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}

