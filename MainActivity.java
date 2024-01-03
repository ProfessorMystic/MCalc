package Mortgage.Calc;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sm = (SensorManager)
                getSystemService(SENSOR_SERVICE); //incorporating sensor
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.tts = new TextToSpeech(this, this); //incorporating text-to-speech

    }

    private TextToSpeech tts;

    public void glue(View v) {

        try {
            //appending user-text-input boxes to variables p,a,i
            EditText p = (EditText) findViewById(R.id.textInputEditText);
            EditText a = (EditText) findViewById(R.id.textInputEditText2);
            EditText i = (EditText) findViewById(R.id.textInputEditText3);

            //instantiating p,a,i, into strings
            String pS = p.getText().toString();
            String aS = a.getText().toString();
            String iS = i.getText().toString();

            //create new object which carries strings pS, aS, iS in heap
            Calculations cal = new Calculations();
            cal.setPrinciple(pS);
            cal.setAmortization(aS);
            cal.setInterest(iS);

            //constructing string post-input; annexing string with new line depicting balance after set amount of years

            String s = "Monthly Payment = $" + cal.computePayment("%,.2f");
            s += "\n\n";
            s += "By making this payment monthly for " + aS + " years, the mortgage will be paid in full. " +
                    "But if you terminate the mortgage on its 'nth' anniversary, " +
                    "the balance still owing depends on 'n' as below.";
            s += "\n\n";
            s += "       n        Balance";
            s += "\n\n";
            s += String.format("%8d", 0) + cal.outstandingAfter(0, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 1) + cal.outstandingAfter(1, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 2) + cal.outstandingAfter(2, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 3) + cal.outstandingAfter(3, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 4) + cal.outstandingAfter(4, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 5) + cal.outstandingAfter(5, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 10) + cal.outstandingAfter(10, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 15) + cal.outstandingAfter(15, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 20) + cal.outstandingAfter(20, "%,16.0f");
            ((TextView)findViewById(R.id.textView)).setText(s);

            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        } catch (Exception e){
            //float message depicting invalid inputs
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            label.show();
        }

    }
    @Override
    public void onInit(int status) {
        this.tts.setLanguage(Locale.US);

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];
        double a = Math.sqrt(ax * ax + ay * ay + az * az);
        if (a > 10) {
            ((EditText) findViewById(R.id.textInputEditText)).setText("");
            ((EditText) findViewById(R.id.textInputEditText2)).setText("");
            ((EditText) findViewById(R.id.textInputEditText3)).setText("");

        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
