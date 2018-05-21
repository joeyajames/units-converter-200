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

public class GasMileage extends Activity {
	Spinner gasMileageSpinner;
	EditText gasMileageEdit;
	TextView [] tvGasMileage = new TextView[2];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gas_mileage);
		gasMileageSpinner = (Spinner) findViewById(R.id.spnGasMileage);
		gasMileageEdit = (EditText) findViewById(R.id.editGasMileage);
		tvGasMileage[0] = (TextView) findViewById(R.id.txtValueGasMileage0);
		tvGasMileage[1] = (TextView) findViewById(R.id.txtValueGasMileage1);

		gasMileageEdit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				update();
			}
		});
		gasMileageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
		getMenuInflater().inflate(R.menu.gas_mileage, menu);
		return true;
	}
	public void update() {
		int sigFig = 5; // significant figures displayed
		if (gasMileageEdit.getText().toString().length() > 0) {
			double value = Double.parseDouble(gasMileageEdit.getText().toString());
			int fromUnits = gasMileageSpinner.getSelectedItemPosition();
			for (int i = 0; i < tvGasMileage.length; i++) {
				tvGasMileage[i].setText(String.valueOf(clipNumber(convert(value, fromUnits, i), sigFig)));
			}
		}
	}
	public double convert (double size, int fromUnits, int toUnits) {
		// 0=mpg, 1=km per liter
		final double [] conv = {1.0, 2.3521458};
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
