package missioncollege.cis063.unitsconverter;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TemperatureConvert extends Activity {
	Spinner temperatureSpinner;
	EditText temperatureEdit;
	TextView [] tvTemperature = new TextView[3];
	//TextView textValueTemperature0, textValueTemperature1, textValueTemperature2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temperature);
		temperatureSpinner = (Spinner) findViewById(R.id.spnTemperature);
		temperatureEdit = (EditText) findViewById(R.id.editTemperature);
		tvTemperature[0] = (TextView) findViewById(R.id.txtValueTemperature0);
		tvTemperature[1] = (TextView) findViewById(R.id.txtValueTemperature1);
		tvTemperature[2] = (TextView) findViewById(R.id.txtValueTemperature2);

		temperatureEdit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				update();
			}
		});
		temperatureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
		getMenuInflater().inflate(R.menu.temperature_convert, menu);
		return true;
	}
	public void update() {
		int sigFig = 5; // significant figures displayed
		if (temperatureEdit.getText().toString().length() > 0) {
			double value = Double.parseDouble(temperatureEdit.getText().toString());
			int fromUnits = temperatureSpinner.getSelectedItemPosition();
			for (int i = 0; i < tvTemperature.length; i++) {
				tvTemperature[i].setText(String.valueOf(clipNumber(convert(value, fromUnits, i), sigFig)));
			}
		}
	}
	public double convert (double size, int fromUnits, int toUnits) {
		// 0=fahrenheit, 1=celsius, 2=kelvin
		switch (toUnits) {
			case 0: // toFahrenheit
				if (fromUnits == 0)
					return size;
				else if (fromUnits == 1)
					return size * 1.8 + 32.0;
				else
					return size * 1.8 - 459.67;
			case 1: // toCelsius
				if (fromUnits == 0)
					return (size - 32.0) / 1.8;
				else if (fromUnits == 1)
					return size;
				else
					return size - 273.15;
			case 2: // toKelvin
				if(fromUnits == 0)
					return (size + 459.67) / 1.8;
				else if (fromUnits == 1)
					return size + 273.15;
				else
					return size;
		}
		return size;
	}
	public static double clipNumber(double num, int n) {
		// num is the number; n is number of significant figures returned
	    if(num == 0) 
	        return 0;

	    final double d = Math.ceil(Math.log10(num < 0 ? -num: num));
	    final int power = n - (int) d;

	    final double magnitude = Math.pow(10, power);
	    final long shifted = Math.round(num*magnitude);
	    return shifted/magnitude;
	}

}
