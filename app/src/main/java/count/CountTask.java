package count;

import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import count.bianwu.count.R;

/**
 * Created by wb on 2015/9/25.
 */
public class CountTask implements Runnable {


    int sum = 0;
    private MainActivity mainActivity;
    private TextView mShowNumberTextView;
    private Button mStartButton;
    private Button mResultButton;
    private int amountNumbers = 20;
    private Double interval_time = 01.00;
    private View mainView;
    private EditText mAmountEditText;
    private EditText mIntervalEditText;
    private volatile boolean running = true;

    CountTask(View view, MainActivity activity) {
        mainView = view;
        mainActivity = activity;
        mShowNumberTextView = (TextView) mainView.findViewById(R.id.show_number);
        mStartButton = (Button) mainView.findViewById(R.id.start_button);
        mResultButton = (Button) mainView.findViewById(R.id.result_button);
        mAmountEditText = (EditText) mainView.findViewById(R.id.amount_numbers);
        mIntervalEditText = (EditText) mainView.findViewById(R.id.second_time);
        String amount = mAmountEditText.getText().toString();
        if (!TextUtils.isEmpty(amount)) {
            amountNumbers = Integer.valueOf(amount);
        }
        String interval = mIntervalEditText.getText().toString();
        if (!TextUtils.isEmpty(interval)) {
            interval_time = Double.valueOf(interval);
        }
    }

    public void terminate() {
        running = false;
    }

    public void run() {
        sum = 0;
        for (int i = 0; i < amountNumbers; i++) {
            Random random = new Random();
            final int random_Number = (int) (10 * random.nextDouble() + 1);
            // thread have to inform UI thread to update UI
            mainActivity.runOnUiThread(new Runnable() {
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
                Thread.sleep((long) (interval_time * 1000));
                if (!running)
                    break;
            } catch (InterruptedException e) {
                running = false;
                Log.i("sleep", "Error");
            }
        }
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setNumber(String.valueOf(sum));
                // set to invisible until the click result button
                mShowNumberTextView.setVisibility(View.INVISIBLE);
                mStartButton.setText("START");
                mResultButton.setEnabled(true);
            }
        });

    }


    public void setNumber(String number) {
        ColorStateList presumeColor = mainView.getResources().getColorStateList(R.color.black);
        if (presumeColor.getDefaultColor() == mShowNumberTextView.getCurrentTextColor()) {
            mShowNumberTextView.setTextColor(mainView.getResources().getColorStateList(R.color.blue));
        } else
            mShowNumberTextView.setTextColor(mainView.getResources().getColorStateList(R.color.black));
        mShowNumberTextView.setText(number);
    }
}
