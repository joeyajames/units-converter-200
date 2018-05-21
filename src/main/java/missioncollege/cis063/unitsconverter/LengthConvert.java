package missioncollege.cis063.unitsconverter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

public class LengthConvert extends Activity {
	Spinner lengthSpinner;
	EditText lengthEdit;
	TextView[] tvLength = new TextView[9];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.length);
		lengthSpinner = (Spinner) findViewById(R.id.spnLength);
		lengthEdit = (EditText) findViewById(R.id.editLength);

		tvLength[0] = (TextView) findViewById(R.id.txtValueLength0);
		tvLength[1] = (TextView) findViewById(R.id.txtValueLength1);
		tvLength[2] = (TextView) findViewById(R.id.txtValueLength2);
		tvLength[3] = (TextView) findViewById(R.id.txtValueLength3);
		tvLength[4] = (TextView) findViewById(R.id.txtValueLength4);
		tvLength[5] = (TextView) findViewById(R.id.txtValueLength5);
		tvLength[6] = (TextView) findViewById(R.id.txtValueLength6);
		tvLength[7] = (TextView) findViewById(R.id.txtValueLength7);
		tvLength[8] = (TextView) findViewById(R.id.txtValueLength8);

		lengthEdit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				update();
			}
		});
		lengthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
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
		getMenuInflater().inflate(R.menu.length_convert, menu);
		return true;
	}

	public void update() {
		int sigFig = 5; // significant figures displayed
		if (lengthEdit.getText().toString().length() > 0) {
			double value = Double.parseDouble(lengthEdit.getText().toString());
			int fromUnits = lengthSpinner.getSelectedItemPosition();
			for (int i = 0; i < tvLength.length; i++) {
				tvLength[i].setText(String.valueOf(clipNumber(convert(value, fromUnits, i), sigFig)));
			}
		}
	}
	public double convert (double size, int fromUnits, int toUnits) {
		//conversion uses indexes: 0=inches, 1=feet, 2=yards, 3=miles, 4=mm, 5=cm, 6=m, 7=km, 8=nauts
		final double [] conv = {1.0, 12.0, 36.0, 63360.0, .0393701, .393701, 39.3701, 39370.1, 72913.39};
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
