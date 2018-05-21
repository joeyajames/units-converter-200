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
import android.widget.Toast;

public class NumbersConvert extends Activity {
	EditText numberEdit;
	Spinner numberSpinner;
	TextView tvDecimal, tvBinary, tvOctal, tvHex, tvRoman;
	int intNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numbers);
		numberEdit = (EditText)findViewById(R.id.txtNumber);
		numberSpinner = (Spinner)findViewById(R.id.spnNumbers);
		tvDecimal = (TextView)findViewById(R.id.txtDec);
		tvBinary = (TextView)findViewById(R.id.txtBi);
		tvOctal = (TextView)findViewById(R.id.txtOct);
		tvHex = (TextView)findViewById(R.id.txtHex);
		tvRoman = (TextView)findViewById(R.id.txtRoman);

        numberEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                update();
            }
        });
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
		getMenuInflater().inflate(R.menu.numbers_convert, menu);
		return true;
	}
    public void update() {
        int fromUnits = numberSpinner.getSelectedItemPosition();
        String strValue;
        if (numberEdit.getText().toString().length() > 0) {
            strValue = numberEdit.getText().toString();

            switch (fromUnits) {
                case 0:
                    intNumber = getInt(strValue, 10);
                    break;

                case 1: // Binary
                    intNumber = getInt(strValue, 2);
                    break;

                case 2: // Octal
                    intNumber = getInt(strValue, 8);
                    break;

                case 3: // Hexadecimal
                    intNumber = getInt(strValue, 16);
                    break;
                case 4: // Roman Numerals
                    if (isRoman(strValue)) {
                        intNumber = romanToArabic(strValue);
                        showResult(intNumber);
                    }
                    else
                        showError();
                    break;
            }
            if (intNumber > 0)
                showResult(intNumber);
            else
                showError();
        }
    }
    public int getInt(String s, int base) {
        try { return Integer.parseInt(s, base); }
        catch (Exception e) { showError(); }
        return -1;
    }
	public void showResult(int decNumber) {
		tvDecimal.setText(Integer.toString(decNumber));
		tvBinary.setText(Integer.toBinaryString(decNumber));
		tvOctal.setText(Integer.toOctalString(decNumber));
		tvHex.setText(Integer.toHexString(decNumber));
		tvRoman.setText(toRoman(decNumber));
	}
    public void showError() {
        tvDecimal.setText("Invalid input.");
        tvBinary.setText("Invalid input.");
        tvOctal.setText("Invalid input.");
        tvHex.setText("Invalid input.");
        tvRoman.setText("Invalid input.");
    }

	// *** Roman Numeral specific methods
	
	enum Numeral {
        I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100), CD(400), D(500), CM(900), M(1000), ;
        int weight;

        Numeral(int weight) {
            this.weight = weight;
        }
    };

    public static String toRoman(long n) {
        StringBuilder buf = new StringBuilder();
        final Numeral[] values = Numeral.values();
        for (int i = values.length - 1; i >= 0; i--) {
            while (n >= values[i].weight) {
                buf.append(values[i]);
                n -= values[i].weight;
            }
        }
        return buf.toString();
    }
    
    // New code for Roman Numeral conversion to Arabic
    
    enum Roman {
        I(1,'I'), V(5,'V'), X(10,'X'), L(50,'L'), C(100,'C'), D(500,'D'), M(1000,'M'), ;
        int aNumeral;
        char rNumeral;

        Roman(int aNumeral, char rNumeral) { // Constructor
           this.aNumeral = aNumeral;
           this.rNumeral = rNumeral;
        }
     };

    // String is a valid Roman numeral
    public static boolean isRoman(String s) {
        if (s.length() < 1) { // Rule 1: Cannot be blank
           return false;
        } 
        if ( !isRomanNumChar(s) ) {
           return false;
        }
        if ( !isRomanSeq(s) ) {
           return false;
        }
        return true; // All tests were passed
     }  // End isRoman

    public static boolean isRomanNumChar(String s) {
        final Roman[] values = Roman.values();
        int sChars = s.length()-1; // Max character position number
        int rChars = values.length-1; // Max character element number
        
        for (int j = sChars; j >= 0; j-- ) {// Progress backwards
           for (int i = 0; i <= rChars; i++ ) { // Progress from I to V to X, ...
              if ( values[i].rNumeral == s.charAt(j) ){ 
                 i = rChars; // Leave for i loop
              } else if ( i == rChars ) { // String char not a valid Roman numeral
                    //System.out.printf( "\n\"%s\" is not a valid Roman numeral character.\n",
                    //   s.charAt(j) );
                    return false;
              }
           } // End for i
        } // End for j
        return true;
     }
     
    public static boolean isRomanSeq(String s) {
        final Roman[] values = Roman.values();
        int sChars = s.length()-1; // Max character position number
        int rChars = values.length-1; // Max character element number
        int curr =0;
        int prev1=0;
        int prev2=0;
        int prev3=0;
        
        for (int j = sChars; j >= 0; j-- ) {// Progress backwards
           for (int i = 0; i <= rChars; i++ ) { // Progress from I to V to X, ...
              if ( values[i].rNumeral == s.charAt(j) ){ 
                 prev3 = prev2;
                 prev2 = prev1;
                 prev1 = curr;
                 curr = values[i].aNumeral;
                 switch(curr) {
                    case 1:
                    case 10:
                    case 100:
                    case 1000:
                       if ( curr + prev1 + prev2 + prev3 == curr * 4 ) { // Ex: IIII
                          return false;
                       } else if ( curr == prev2 && curr < prev1) { // Ex: IVI
                          return false;
                       } else if ( curr == prev1 && curr < prev2 ) { // EX: IIV
                          return false;
                       } else if ( curr + prev1 >= curr + ( curr * 50 ) ) {
                          return false;
                       }
                       break;
                       
                    case 5:
                    case 50:
                    case 500:
                    case 5000:
                       if ( curr + prev1 > 6 * (curr / 5) ) { // Ex: VI, LX is max allowable value
                          return false;
                       }
                       break;
                       
                 } // End switch 
              } // End if
           } // End i loop
        } // End j loop
        return true;
     } // End isRomanSeq
     
    public static int romanToArabic(String s) {
        final Roman[] values = Roman.values();
        int sChars = s.length()-1; // Max character position number
        int rChars = values.length-1; // Max character element number
        int curr = 0;
        int prev1 = 0;
        int arabicNum = 0;
        for (int j = sChars; j >= 0; j-- ) {// Progress backwards
           for (int i = 0; i <= rChars; i++ ) { // Progress from I to V to X, ...
              if ( values[i].rNumeral == s.charAt(j) ){
                 prev1 = curr;
                 curr = values[i].aNumeral;
                 if ( curr >= prev1 ) {
                    arabicNum += curr;
                 } else {
                    arabicNum -= curr;
                 }
              }
           }
        }
        return arabicNum;
     }
     // end new code for Roman Numerals
} // End Class
