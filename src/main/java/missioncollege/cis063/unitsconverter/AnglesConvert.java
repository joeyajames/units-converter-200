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

public class AnglesConvert extends Activity {
	Spinner anglesSpinner;
	EditText anglesEdit;
	TextView [] tvAngles = new TextView[2];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.angles);
		anglesSpinner = (Spinner) findViewById(R.id.spnAngles);
		anglesEdit = (EditText) findViewById(R.id.editAngles);
		tvAngles[0] = (TextView) findViewById(R.id.txtValueAngles0);
		tvAngles[1] = (TextView) findViewById(R.id.txtValueAngles1);

		anglesEdit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				update();
			}
		});
		anglesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
		getMenuInflater().inflate(R.menu.angles_convert, menu);
		return true;
	}
	public void update() {
		int sigFig = 5; // significant figures displayed
		if (anglesEdit.getText().toString().length() > 0) {
			double value = Double.parseDouble(anglesEdit.getText().toString());
			int fromUnits = anglesSpinner.getSelectedItemPosition();
			for (int i = 0; i < tvAngles.length; i++) {
				tvAngles[i].setText(String.valueOf(clipNumber(convert(value, fromUnits, i), sigFig)));
			}
		}
	}
    public double convert (double size, int fromUnits, int toUnits) {
		// 0=degrees, 1=radians
        final double[] conv = {1.0, 57.2957795};
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
