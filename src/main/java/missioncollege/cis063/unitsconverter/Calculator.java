package missioncollege.cis063.unitsconverter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Calculator extends Activity {
	TextView textCalculator, textCalcBreadcrumbs;
	int operation = -1;
	double num1, num2, result, memory1 = 0;
	boolean num1isSet = false;
	// boolean num2isSet = false;
	boolean clearScreen = false;
	static int MAX_LENGTH = 14;
	static double MAX_NUM = 1000000000000d;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);
		textCalculator = (TextView) findViewById(R.id.txtCalculator);
		textCalcBreadcrumbs = (TextView) findViewById(R.id.txtCalcBreadcrumbs);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}

	// M+
    public void clickMplus (View v) {
	    if (hasNumber()) {
	        memory1 = memory1 + getNumber();
            clearScreen = true;
        }
    }
    // M-
    public void clickMminus (View v) {
        if (hasNumber()) {
            memory1 = memory1 - getNumber();
            clearScreen = true;
        }
    }
    // MR
    public void clickMR (View v) {
        textCalculator.setText("" + memory1);
        clearScreen = true;
    }
    // MC
    public void clickMC (View v) {
        memory1 = 0;
    }
    public void clickClear(View v) {
        clear();
    }

    public void clickSin (View v) {
        double temp;
        String s;
        if (hasNumber()) {
            temp = getNumber();
            s = "" + round(Math.sin(temp));
            textCalculator.setText(s);
            textCalcBreadcrumbs.setText("sin(" + Double.toString(temp) + ")");
        }
        clearScreen = true;
    }
    public void clickCos (View v) {
        double temp;
        String s;
        if (hasNumber()) {
            temp = getNumber();
            s = "" + round(Math.cos(temp));
            textCalculator.setText(s);
            textCalcBreadcrumbs.setText("cos(" + Double.toString(temp) + ")");
        }
        clearScreen = true;
    }
    public void clickTan (View v) {
        double temp;
        String s;
        if (hasNumber()) {
            temp = getNumber();
            s = "" + round(Math.tan(temp));
            textCalculator.setText(s);
            textCalcBreadcrumbs.setText("tan(" + Double.toString(temp) + ")");
        }
        clearScreen = true;
    }
    public void clickRoot (View v) {
        double temp;
        String s;
        if (hasNumber()) {
            temp = getNumber();
            if (temp > 0) {
                s = "" + round(Math.sqrt(temp));
                textCalculator.setText(s);
                textCalcBreadcrumbs.setText("sqrt(" + Double.toString(temp) + ")");
            }
            else {
                clear();
                textCalculator.setText("Error");
            }
        }
        clearScreen = true;
    }
    public void clickDel (View v) {
        String temp;
        if (hasNumber()) {
            temp = textCalculator.getText().toString();
            textCalculator.setText("" + temp.substring(0, temp.length() - 1));
        }
        else
            clear();
    }

    public void clickAbs (View v) {
        double temp;
        String s;
        if (hasNumber()) {
            temp = getNumber();
            s = "" + round(Math.abs(temp));
            textCalculator.setText(s);
            textCalcBreadcrumbs.setText("|" + Double.toString(temp) + "|");
        }
        clearScreen = true;
    }
    public void clickLn (View v) {
        double temp;
        String s;
        if (hasNumber()) {
            temp = getNumber();
            s = "" + round(Math.log(temp));
            textCalculator.setText(s);
            textCalcBreadcrumbs.setText("ln(" + Double.toString(temp) + ")");
        }
        clearScreen = true;
    }
    public void clickLog10 (View v) {
        double temp;
        String s;
        if (hasNumber()) {
            temp = getNumber();
            s = "" + round(Math.log10(temp));
            textCalculator.setText(s);
            textCalcBreadcrumbs.setText("log10(" + Double.toString(temp) + ")");
        }
        clearScreen = true;
    }
    public void clickE (View v) {
        String s = "" + round(Math.E);
        textCalculator.setText(s);
        textCalcBreadcrumbs.setText("e");
        clearScreen = true;
    }
    public void clickTbd (View v) { }

    public void clickGcf (View v) {
        performOperation();
        operation = 4;
    }
    public void clickLcm (View v) {
        performOperation();
        operation = 5;
    }
    public void clickPrime(View v) {
        String isPrime = "Prime";
        if (hasNumber()) {
            long num = (long)getNumber();
            for (long x = 2; x < (long)Math.sqrt(num) + 1; x++) {
                if (num % x == 0) {
                    isPrime = "Not Prime";
                    break;
                }
            }
            textCalculator.setText(isPrime);
            textCalcBreadcrumbs.setText("prime?(" + Double.toString(num) + ")");
            clearScreen = true;
        }
    }
    public void clickPi (View v) {
        String s = "" + round(Math.PI);
        textCalculator.setText(s);
        textCalcBreadcrumbs.setText("\u03C0");
        clearScreen = true;
    }
    // x^y
    public void clickPower (View v) {
        performOperation();
        operation = 6;
    }

    // x^2
    public void clickSquare (View v) {
        double temp;
        String s;
        if (hasNumber()) {
            temp = getNumber();
            s = "" + round(temp * temp);
            textCalculator.setText(s);
            textCalcBreadcrumbs.setText(Double.toString(temp) + " ^2");
            clearScreen = true;
        }
    }
    // 1/x
    public void clickInverse(View v) {
	    if (hasNumber()) {
	        double num = getNumber();
	        textCalculator.setText("" + round(1 / num));
            textCalcBreadcrumbs.setText("1/" + num);
            clearScreen = true;
        }
    }
    // %
    public void clickPercent(View v) {
        if (hasNumber()) {
            double num = getNumber();
            textCalculator.setText("" + (0.01 * num));
            textCalcBreadcrumbs.setText("" + num + "%");
            clearScreen = true;
        }
    }
    public void clickEquals(View v) {
        if (hasNumber()) {
            num2 = getNumber();
            getResult();
            textCalculator.setText("" + result);
            num1isSet = false;
            num2 = result = 0;
            operation = -1;
            clearScreen = true;
        }
    }

	public void click0 (View v) {
		if (!clearScreen)
			textCalculator.append("0"); 
		else {
			textCalculator.setText("0");
			clearScreen = false;
		}
	}
	public void click1 (View v) { 
		if (!clearScreen)
			textCalculator.append("1"); 
		else {
			textCalculator.setText("1");
			clearScreen = false;
		}
	}
	public void click2 (View v) { 
		if (!clearScreen)
			textCalculator.append("2"); 
		else {
			textCalculator.setText("2");
			clearScreen = false;
		}
	}
	public void click3 (View v) { 
		if (!clearScreen)
			textCalculator.append("3"); 
		else {
			textCalculator.setText("3");
			clearScreen = false;
		}
	}
	public void click4 (View v) { 
		if (!clearScreen)
			textCalculator.append("4"); 
		else {
			textCalculator.setText("4");
			clearScreen = false;
		}
	}
	public void click5 (View v) { 
		if (!clearScreen)
			textCalculator.append("5"); 
		else {
			textCalculator.setText("5");
			clearScreen = false;
		}
	}
	public void click6 (View v) { 
		if (!clearScreen)
			textCalculator.append("6"); 
		else {
			textCalculator.setText("6");
			clearScreen = false;
		}
	}
	public void click7 (View v) { 
		if (!clearScreen)
			textCalculator.append("7"); 
		else {
			textCalculator.setText("7");
			clearScreen = false;
		}
	}
	public void click8 (View v) { 
		if (!clearScreen)
			textCalculator.append("8"); 
		else {
			textCalculator.setText("8");
			clearScreen = false;
		}
	}
	public void click9 (View v) { 
		if (!clearScreen)
			textCalculator.append("9"); 
		else {
			textCalculator.setText("9");
			clearScreen = false;
		}
	}
	// only append a dot if the current display does not contain a dot
	public void clickDot (View v) { 
		if (!textCalculator.getText().toString().contains(".")) {
			if (!clearScreen)
				textCalculator.append("."); 
			else {
				textCalculator.setText(".");
				clearScreen = false;
			}
		}
	}
    public void clickPosNeg (View v) {
        double temp;
        if (textCalculator.getText().length() > 0) {
            temp = Double.parseDouble(textCalculator.getText().toString());
            textCalculator.setText("" + (-1 * temp));
        }
    }
	
	public void clickAdd(View v) {
		performOperation();
		operation = 0;
	}
	public void clickSubtract(View v) {
		performOperation();
		operation = 1;
	}
	public void clickMultiply(View v) {
		performOperation();
		operation = 2;
	}
	public void clickDivide(View v) {
		performOperation();
		operation = 3;
	}
	// perform operations with two numbers
	public void performOperation () {
		if (num1isSet) {
		    if (hasNumber()) {
                num2 = getNumber();
                getResult();
                num1 = result;
                num2 = 0;
                textCalculator.setText("" + result);
            }
            else {
                clear();
                textCalculator.setText("ERROR");
            }
		}
		else {
            if (hasNumber()) {
                num1 = getNumber();
				num1isSet = true;
			}
			else {
                clear();
                textCalculator.setText("ERROR");
            }
		}
		clearScreen = true;
	}

	private void clear() {
        num1 = num2 = result = 0;
        operation = -1;
        num1isSet = false;
        textCalculator.setText("");
        textCalcBreadcrumbs.setText("");
    }
    // works with performOperation to do operations with two numbers
	private void getResult() {
		switch (operation) {
		case 0: // add
			result = num1 + num2;
            textCalcBreadcrumbs.setText("" + num1 + "+" + num2 + "=");
			break;
		case 1: // subtract
			result = num1 - num2;
            textCalcBreadcrumbs.setText("" + num1 + "-" + num2 + "=");
			break;
		case 2: // multiply
			result = num1 * num2;
            textCalcBreadcrumbs.setText("" + num1 + "x" + num2 + "=");
			break;
		case 3: // divide
			if (num2 != 0) {
                result = num1 / num2;
                textCalcBreadcrumbs.setText("" + num1 + "\u00F7" + num2 + "=");
            }
			else 
				result = 0;
			break;
        case 4: // gcf
            result = 1;
            if (((int)num1 < num1 + 0.01) && ((int)num2 < num2 + 0.01)) {
                for (int i = 1; i <= (int) num1 && i <= (int) num2; ++i) {
                    if ((int) num1 % i == 0 && (int) num2 % i == 0) {
                        result = i;
                    }
                }
                textCalcBreadcrumbs.setText("lcm(" + num1 + ", " + num2 + ")=");
            }
            break;
        case 5: // lcm
            int gcf = 1;
            if (((int)num1 < num1 + 0.01) && ((int)num2 < num2 + 0.01)) {
                for (int i = 1; i <= (int) num1 && i <= (int) num2; ++i) {
                    if ((int) num1 % i == 0 && (int) num2 % i == 0) {
                        gcf = i;
                    }
                }
                result = ((int)num1 * (int)num2) / gcf;
                textCalcBreadcrumbs.setText("lcm(" + num1 + ", " + num2 + ")=");
            }
            break;
        case 6: // x^y
            result = Math.pow(num1, num2);
            textCalcBreadcrumbs.setText("" + num1 + "^" + num2 + "=");
            break;
		default: 
			result = 0;
		}
	}
	private boolean hasNumber() {
        if (textCalculator.getText().length() > 0) {
            try {
                Double.parseDouble(textCalculator.getText().toString());
            }
            catch(NumberFormatException nfe) {
                return false;
            }
            return true;
        }
        return false;
    }
    private double getNumber() {
        return Double.parseDouble(textCalculator.getText().toString());
    }
    private String trim (String s) {
        if (s.length() > MAX_LENGTH && s.indexOf("E") < 0) {
            return s.substring(0, MAX_LENGTH);
        }
        return s;
    }
    private double round (double num) {
        String text = Double.toString(Math.abs(num));
        double integerPlaces = text.indexOf(".");
        double roundPlaces = MAX_NUM / Math.pow(10, integerPlaces);
        return (double) Math.round(num * roundPlaces) / roundPlaces;
    }
}
