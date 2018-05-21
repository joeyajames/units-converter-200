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

public class AreaConvert extends Activity {
	Spinner areaSpinner;
	EditText areaEdit;
	TextView[] tvArea = new TextView[9];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.area);
		areaSpinner = (Spinner) findViewById(R.id.spnArea);
		areaEdit = (EditText) findViewById(R.id.editArea);

		tvArea[0] = (TextView) findViewById(R.id.txtValueAre0);
		tvArea[1] = (TextView) findViewById(R.id.txtValueAre1);
		tvArea[2] = (TextView) findViewById(R.id.txtValueAre2);
		tvArea[3] = (TextView) findViewById(R.id.txtValueAre3);
		tvArea[4] = (TextView) findViewById(R.id.txtValueAre4);
		tvArea[5] = (TextView) findViewById(R.id.txtValueAre5);
		tvArea[6] = (TextView) findViewById(R.id.txtValueAre6);
		tvArea[7] = (TextView) findViewById(R.id.txtValueAre7);
		tvArea[8] = (TextView) findViewById(R.id.txtValueAre8);

		areaEdit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				update();
			}
		});
		areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
		getMenuInflater().inflate(R.menu.area_convert, menu);
		return true;
	}
	public double convert (double size, int fromUnits, int toUnits) {
		// 0=sqInches, 1=sqFeet, 2=sqMiles, 3=sqMM, 4=sqM, 5=sqKM, 6=Acres, 7=Hectares, 8=Ping
		final double[] conv = {1.0, 144.0, 4014489599.9, .0015500031, 1550.003, 1550003100.0, 6272640.0, 15500031.0, 5122.76};
		return size * conv[fromUnits] / conv[toUnits];
	}
	public void update() {
		int sigFig = 5; // significant figures displayed
		if (areaEdit.getText().toString().length() > 0) {
			double value = Double.parseDouble(areaEdit.getText().toString());
			int fromUnits = areaSpinner.getSelectedItemPosition();
			for (int i = 0; i < tvArea.length; i++) {
				tvArea[i].setText(String.valueOf(clipNumber(convert(value, fromUnits, i), sigFig)));
			}
		}
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
