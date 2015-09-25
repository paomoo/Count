package count.bianwu.count;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Thread taskThread = null;
    // UI fileds
    private Button mStartButton;
    private TextView mShowNumberTextView;
    private int amountNumbers = 20;
    private Double interval_time =01.00 ;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartButton = (Button) findViewById(R.id.start_button);
        mShowNumberTextView = (TextView) findViewById(R.id.show_number);
        view = findViewById(android.R.id.content);
    }

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
        mShowNumberTextView.setText("");
        mShowNumberTextView.setVisibility(View.VISIBLE);
        if(mStartButton.getText().toString().equalsIgnoreCase("START")) {
            //initialize
            mStartButton.setText(" STOP ");
            taskThread = new Thread(new CountTask(view, this));
            taskThread.start();
        }
        else {
            // kill thread
            if(taskThread != null) {
                taskThread=null;
            }
        }
    }

    public void result(View v) {
        mShowNumberTextView.setVisibility(View.VISIBLE);

    }

    public Thread getTaskThread() {
        return taskThread;
    }

}
