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

public class WeightConvert extends Activity {
	Spinner weightSpinner;
	EditText weightEdit;
	TextView[] tvWeight = new TextView[8];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weight);
		weightSpinner = (Spinner) findViewById(R.id.spnWeight);
		weightEdit = (EditText) findViewById(R.id.editWeight);

		tvWeight[0] = (TextView) findViewById(R.id.txtValueWeight0);
		tvWeight[1] = (TextView) findViewById(R.id.txtValueWeight1);
		tvWeight[2] = (TextView) findViewById(R.id.txtValueWeight2);
		tvWeight[3] = (TextView) findViewById(R.id.txtValueWeight3);
		tvWeight[4] = (TextView) findViewById(R.id.txtValueWeight4);
		tvWeight[5] = (TextView) findViewById(R.id.txtValueWeight5);
		tvWeight[6] = (TextView) findViewById(R.id.txtValueWeight6);
		tvWeight[7] = (TextView) findViewById(R.id.txtValueWeight7);

		weightEdit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				update();
			}
		});
		weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
		getMenuInflater().inflate(R.menu.weight_convert, menu);
		return true;
	}

	public void update() {
		int sigFig = 5; // significant figures displayed
		if (weightEdit.getText().toString().length() > 0) {
			double value = Double.parseDouble(weightEdit.getText().toString());
			int fromUnits = weightSpinner.getSelectedItemPosition();
			for (int i = 0; i < tvWeight.length; i++) {
				tvWeight[i].setText(String.valueOf(clipNumber(convert(value, fromUnits, i), sigFig)));
			}
		}
	}
	public double convert (double size, int fromUnits, int toUnits) {
		// conv index: 0=ounces, 1=pounds, 2=tons, 3=grams, 4=kg, 5=metric tons, 6=grains, 7=drams
		final double[] conv = {1.0, 16.0, 32000.0, .03527396195, 35.274, 35274.0, .00228571, .0625};
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
