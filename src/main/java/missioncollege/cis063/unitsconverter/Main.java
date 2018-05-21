package missioncollege.cis063.unitsconverter;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		String[] categories = {"Angles", "Area", "Calculator", "Currency", "Gas Mileage", "Length/Distance",
				"Number Systems", "Speed", "Temperature", "Volume", "Weight"};
		setListAdapter(new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, categories));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Intent i = new Intent(this, Convert.class);
		// i.putExtra("position", position);
		// startActivity(i);
		Intent i;
		switch (position) {
		case 0:
			i = new Intent(this, AnglesConvert.class);
			startActivity(i);
			break;
		case 1:
			i = new Intent(this, AreaConvert.class);
			startActivity(i);
			break;
		case 2:
			i = new Intent(this, Calculator.class);
			startActivity(i);
			break;
		case 3:
			i = new Intent(this, CurrencyConvert.class);
			startActivity(i);
			break;
		case 4:
			i = new Intent(this, GasMileage.class);
			startActivity(i);
			break;
		case 5:
			i = new Intent(this, LengthConvert.class);
			startActivity(i);
			break;
		case 6:
			i = new Intent(this, NumbersConvert.class);
			startActivity(i);
			break;	
		case 7:
			i = new Intent(this, SpeedConvert.class);
			startActivity(i);
			break;
		case 8:
			i = new Intent(this, TemperatureConvert.class);
			startActivity(i);
			break;	
		/*case 8:
			i = new Intent(this, TimeConvert.class);
			startActivity(i);
			break;*/
		case 9:
			i = new Intent(this, VolumeConvert.class);
			startActivity(i);
			break;
		case 10:
			i = new Intent(this, WeightConvert.class);
			startActivity(i);
			break;	
		}
		
	}

}
