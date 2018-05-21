package missioncollege.cis063.unitsconverter;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

public class CurrencyConvert extends Activity {
    Spinner currencySpinner;
    EditText currencyEdit;
    TextView[] tvCurrency = new TextView[8];
    ProgressBar progressBar;
    String[] units = {"USD_USD", "EUR_USD", "GBP_USD", "JPY_USD", "CAD_USD", "CNY_USD", "AUD_USD", "MXN_USD"};
    double[] conv = new double[8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency);
        currencySpinner = (Spinner) findViewById(R.id.spnCurrency);
        currencyEdit = (EditText) findViewById(R.id.editCurrency);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tvCurrency[0] = (TextView) findViewById(R.id.txtValueCurrency0);
        tvCurrency[1] = (TextView) findViewById(R.id.txtValueCurrency1);
        tvCurrency[2] = (TextView) findViewById(R.id.txtValueCurrency2);
        tvCurrency[3] = (TextView) findViewById(R.id.txtValueCurrency3);
        tvCurrency[4] = (TextView) findViewById(R.id.txtValueCurrency4);
        tvCurrency[5] = (TextView) findViewById(R.id.txtValueCurrency5);
        tvCurrency[6] = (TextView) findViewById(R.id.txtValueCurrency6);
        tvCurrency[7] = (TextView) findViewById(R.id.txtValueCurrency7);
        new RetrieveRates().execute("");
        conv[0] = 1.0;

        currencyEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                update();
            }
        });
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                update();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.currency_convert, menu);
        return true;
    }
    public double convert (double size, int fromUnits, int toUnits) {
        return size * conv[fromUnits] / conv[toUnits];
    }
    public void update() {
        if (currencyEdit.getText().toString().length() > 0) {
            double value = Double.parseDouble(currencyEdit.getText().toString());
            int fromUnits = currencySpinner.getSelectedItemPosition();
            for (int i = 0; i < tvCurrency.length; i++) {
                tvCurrency[i].setText(String.valueOf(round(convert(value, fromUnits, i))));
            }
        }
    }
    public static double round(double num) {
        return (Math.round(num * 100.0))/100.0;
    }
    class RetrieveRates extends AsyncTask<String, String, String> {
        private Exception exception;
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            tvCurrency[3].setText("");
        }
        protected String doInBackground(String... args) {
            for (int i = 1; i < units.length; i++) {
                try {
                    URL url = new URL("http://free.currencyconverterapi.com/api/v5/convert?q=" + units[i] + "&compact=y");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        String s = stringBuilder.toString();
                        int start = s.indexOf("{\"val\":") + 7;
                        int end = s.indexOf("}");
                        conv[i] = Double.parseDouble(s.substring(start, end));
                    }
                    finally{
                        urlConnection.disconnect();
                    }
                }
                catch(Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }
            return null;
        }
        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
        }
    }
}
