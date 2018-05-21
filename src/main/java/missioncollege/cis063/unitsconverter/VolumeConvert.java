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

public class VolumeConvert extends Activity {
	Spinner volumeSpinner;
	EditText volumeEdit;
	TextView[] tvVolume = new TextView[9];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volume);
		volumeSpinner = (Spinner) findViewById(R.id.spnVolume);
		volumeEdit = (EditText) findViewById(R.id.editVolume);

		tvVolume[0] = (TextView) findViewById(R.id.txtValueVolume0);
		tvVolume[1] = (TextView) findViewById(R.id.txtValueVolume1);
		tvVolume[2] = (TextView) findViewById(R.id.txtValueVolume2);
		tvVolume[3] = (TextView) findViewById(R.id.txtValueVolume3);
		tvVolume[4] = (TextView) findViewById(R.id.txtValueVolume4);
		tvVolume[5] = (TextView) findViewById(R.id.txtValueVolume5);
		tvVolume[6] = (TextView) findViewById(R.id.txtValueVolume6);
		tvVolume[7] = (TextView) findViewById(R.id.txtValueVolume7);
		tvVolume[8] = (TextView) findViewById(R.id.txtValueVolume8);

		volumeEdit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				update();
			}
		});
		volumeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
		getMenuInflater().inflate(R.menu.volume_convert, menu);
		return true;
	}

	public void update() {
		int sigFig = 5; // significant figures displayed
		if (volumeEdit.getText().toString().length() > 0) {
			double value = Double.parseDouble(volumeEdit.getText().toString());
			int fromUnits = volumeSpinner.getSelectedItemPosition();
			for (int i = 0; i < tvVolume.length; i++) {
				tvVolume[i].setText(String.valueOf(clipNumber(convert(value, fromUnits, i), sigFig)));
			}
		}
	}
	public double convert (double size, int fromUnits, int toUnits) {
		// conv units: 0=ounces, 1=pints, 2=quarts, 3=gallons, 4=cu.ft, 5=milliLtrs, 6=centiLtrs, 7=Liters, 8=cu.meters
		final double[] conv = {1.0, 16.0, 32.0, 128.0, 957.506494, .033814023, .338140227, 33.8140227, 33814.0227018};
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
