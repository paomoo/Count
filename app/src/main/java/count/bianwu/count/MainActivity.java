package count.bianwu.count;

import android.content.res.ColorStateList;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowNumberTextView = (TextView) findViewById(R.id.show_number);
        mStartButton = (Button)findViewById(R.id.start_button);
        mAmountEditText = (EditText)findViewById(R.id.amount_numbers);
        mIntervalEditText = (EditText)findViewById(R.id.second_time);
    }

    // UI fileds
//    private Task task = new Task(this);
    private TextView mShowNumberTextView;
    private Button mStartButton;
    private EditText mAmountEditText;
    private EditText mIntervalEditText;
    private int amountNumbers = 20;
    private Double interval_time =01.00 ;
    Thread taskThread = null;
//    private int interval_time =1;
    int sum = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startCount( View v ) {

        if(mStartButton.getText().toString().equalsIgnoreCase("START")) {
            //initialize
            sum = 0;
            String amount = mAmountEditText.getText().toString();
            if( !TextUtils.isEmpty(amount)) {
                amountNumbers = Integer.valueOf(amount);
            }
            String interval = mIntervalEditText.getText().toString();
            if(!TextUtils.isEmpty(interval)) {
                interval_time = Double.valueOf(interval);
//            interval_time = Integer.valueOf(interval);
            }
            //        mStartButton.setEnabled(false);
            mStartButton.setText(" STOP ");
            taskThread = new Thread(new Task());
            taskThread.start();
        }
        else {
            // kill thread
            //  Thread currentThread  = Thread.currentThread();
            if(taskThread != null) {
                taskThread=null;
                //   taskThread  = null;
                //  mStartButton.setText("START");
            }
        }
    }

    public void setNumber(String number) {
         ColorStateList presumeColor = getResources().getColorStateList(R.color.black);
       if(presumeColor.getDefaultColor()!=null && presumeColor.getDefaultColor() == mShowNumberTextView.getCurrentTextColor()) {
            mShowNumberTextView.setTextColor(getResources().getColorStateList(R.color.blue));
        }
        else
           mShowNumberTextView.setTextColor(getResources().getColorStateList(R.color.black));
        mShowNumberTextView.setText(number);
    }

    class Task implements Runnable {
        public void run() {
            for (int i = 0; i < amountNumbers; i++) {
                Random random = new Random();
                final int random_Number = (int) (10 * random.nextDouble() + 1);
                // thread have to inform UI thread to update UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    //stuff that updates ui
                        setNumber(String.valueOf(random_Number));
                    }
                });
                sum = sum + random_Number;
                Log.i("random_Number", "" + random_Number);
                try {
                    //  TimeUnit.SECONDS.sleep(5);
                    Thread currentThread  = Thread.currentThread();

                    if(taskThread == currentThread)
                        Thread.sleep((long) (interval_time*1000));
                    else // taskThread == null , help to check thread is null, then will not continue run
                        break;
//                    Thread.sleep(interval_time*1000);
                } catch (InterruptedException e) {
                    Log.i("sleep", "Error");
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setNumber(String.valueOf(sum));
                    //                     mStartButton.setEnabled(true);
                    mStartButton.setText("START");
                }
            });
        }
    }
}
