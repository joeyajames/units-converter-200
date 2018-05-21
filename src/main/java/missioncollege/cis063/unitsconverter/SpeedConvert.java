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

public class SpeedConvert extends Activity {
	Spinner speedSpinner;
	EditText speedEdit;
	TextView[] tvSpeed = new TextView[8];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speed);
		speedSpinner = (Spinner) findViewById(R.id.spnSpeed);
		speedEdit = (EditText) findViewById(R.id.editSpeed);

		tvSpeed[0] = (TextView) findViewById(R.id.txtValueSpeed0);
		tvSpeed[1] = (TextView) findViewById(R.id.txtValueSpeed1);
		tvSpeed[2] = (TextView) findViewById(R.id.txtValueSpeed2);
		tvSpeed[3] = (TextView) findViewById(R.id.txtValueSpeed3);
		tvSpeed[4] = (TextView) findViewById(R.id.txtValueSpeed4);
		tvSpeed[5] = (TextView) findViewById(R.id.txtValueSpeed5);
		tvSpeed[6] = (TextView) findViewById(R.id.txtValueSpeed6);
		tvSpeed[7] = (TextView) findViewById(R.id.txtValueSpeed7);

		speedEdit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				update();
			}
		});
		speedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
		getMenuInflater().inflate(R.menu.speed_convert, menu);
		return true;
	}
	public void update() {
		int sigFig = 5; // significant figures displayed
		if (speedEdit.getText().toString().length() > 0) {
			double value = Double.parseDouble(speedEdit.getText().toString());
			int fromUnits = speedSpinner.getSelectedItemPosition();
			for (int i = 0; i < tvSpeed.length; i++) {
				tvSpeed[i].setText(String.valueOf(clipNumber(convert(value, fromUnits, i), sigFig)));
			}
		}
	}
	public double convert (double size, int fromUnits, int toUnits) {
		// 0=InPerSec, 1=FeetPerSec, 2=MPH, 3=cmPerSec, 4=mPerSec, 5=kmPerHr, 6=Knots, 7=Mach
		final double[] conv = {1.0, 12.0, 17.6, .393700787, 39.3700787, 10.93613298, 20.2537183,  13397.2441};
		return size * conv[fromUnits] / conv[toUnits];
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
