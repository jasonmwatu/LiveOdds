package com.karizma.android.liveodds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // variables

    float win_odd = 0, loss_odd_value = 0, draw_odd_value = 0;
    float win_stake = 0, loss_stake_value = 0, draw_stake_value = 0;
    float threshold_value = 0, dl_ratio_value = 0.1f;
    float threshold_seek_value = 0, dl_ratio_seek_value = 0;
    float win_prob, draw_prob, loss_prob, rest_prob, good_prob, combined_prob;
    float profit_amount = 0, total_stake, total_win;
    float draw_ratio, loss_ratio;
    String noticeOne = "Please enter win odd, win stake and Threshold!";
    String noticeTwo = "Current draw odd cannot make profit with set Threshold!";
    String noticeThree = "Current draw and loss odds cannot make profit with set Threshold!";
    String noticeFour = "Current win odds cannot make profit with set Threshold!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initiate seekBar views
        SeekBar thresholdSeek = findViewById(R.id.threshold_seekBar);
        SeekBar dlSeek = findViewById(R.id.dl_seekBar);
        // seekBar change listener for threshold seekBar
        thresholdSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int progressValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressValue = progress;
                threshold_seek_value = progressValue;
                threshold_value = threshold_seek_value / 100;
                displayProgressThresh();
            }

            public void onStartTrackingTouch(SeekBar seekBar){
                // Pass
            }

            public void onStopTrackingTouch(SeekBar seekBar){
                // Pass
            }
        });

        dlSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int progressValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressValue = progress;
                dl_ratio_seek_value = progressValue;
                dl_ratio_value = (dl_ratio_seek_value + 1) / 100;
                displayProgressDL();
            }

            public void onStartTrackingTouch(SeekBar seekBar){
                // Pass
            }

            public void onStopTrackingTouch(SeekBar seekBar){
                // Pass
            }
        });
    }

    public void displayProgressThresh(){
        EditText thresholdView = findViewById(R.id.threshold_view);
        thresholdView.setText(Float.toString(threshold_value));
    }

    public void displayProgressDL(){
        EditText dlRatio = findViewById(R.id.dl_ratio_view);
        dlRatio.setText(Float.toString(dl_ratio_value));
    }

    public void displayFields() {
        int threshSeekValue, dlSeekValue;

        TextView totalWin = findViewById(R.id.total_win_view);
        TextView profitView = findViewById(R.id.profit_view);

        // EditText winStake = findViewById(R.id.win_stake_view);
        EditText drawStake = findViewById(R.id.draw_stake_view);
        EditText lossStake = findViewById(R.id.loss_stake_view);

        // EditText winOdd = findViewById(R.id.win_odd_view);
        EditText drawOdd = findViewById(R.id.draw_odd_view);
        EditText lossOdd = findViewById(R.id.loss_odd_view);

        // EditText thresholdView = findViewById(R.id.threshold_view);
        EditText dlRatio = findViewById(R.id.dl_ratio_view);

        SeekBar thresholdSeek = findViewById(R.id.threshold_seekBar);
        SeekBar dlSeek = findViewById(R.id.dl_seekBar);


        totalWin.setText(Float.toString(total_win));
        dlRatio.setText(Float.toString(dl_ratio_value));

        if (profit_amount > 0) profitView.setText(Float.toString(profit_amount));
        if (draw_odd_value > 0) drawOdd.setText(Float.toString(draw_odd_value));
        if (draw_stake_value > 0) drawStake.setText(Float.toString(draw_stake_value));
        if (loss_odd_value > 0) lossOdd.setText(Float.toString(loss_odd_value));
        if (loss_stake_value > 0) lossStake.setText(Float.toString(loss_stake_value));

        if (threshold_value > 0) {
            threshSeekValue = (int) (threshold_value * 100);
            thresholdSeek.setProgress(threshSeekValue);
        }
        if (dl_ratio_value > 0) {
            dlSeekValue = (int) ((dl_ratio_value * 100) - 1);
            dlSeek.setProgress(dlSeekValue);
        }

    }

    public void readFields() {
        // Reading data entered in the fields
        String winOddStr, winStakeStr, threshStr, dlRatioStr;
        String drawOddStr, lossOddStr, drawStakeStr, lossStakeStr;
        int threshSeekValue, dlSeekValue;

        // TextView totalWin = findViewById(R.id.total_win_view);
        // TextView profitView = findViewById(R.id.profit_view);

        EditText winStake = findViewById(R.id.win_stake_view);
        EditText drawStake = findViewById(R.id.draw_stake_view);
        EditText lossStake = findViewById(R.id.loss_stake_view);

        EditText winOdd = findViewById(R.id.win_odd_view);
        EditText drawOdd = findViewById(R.id.draw_odd_view);
        EditText lossOdd = findViewById(R.id.loss_odd_view);

        EditText thresholdView = findViewById(R.id.threshold_view);
        EditText dlRatio = findViewById(R.id.dl_ratio_view);

        SeekBar thresholdSeek = findViewById(R.id.threshold_seekBar);
        SeekBar dlSeek = findViewById(R.id.dl_seekBar);

        winOddStr = winOdd.getText().toString();
        drawOddStr = drawOdd.getText().toString();
        lossOddStr = lossOdd.getText().toString();
        winStakeStr = winStake.getText().toString();
        drawStakeStr = drawStake.getText().toString();
        lossStakeStr = lossStake.getText().toString();
        threshStr = thresholdView.getText().toString();
        dlRatioStr = dlRatio.getText().toString();

        threshSeekValue = thresholdSeek.getProgress();
        dlSeekValue = dlSeek.getProgress();

        // feed entered strings to variables

        if (!winOddStr.isEmpty()) win_odd = Float.parseFloat(winOddStr);
        if (!drawOddStr.isEmpty()) draw_odd_value = Float.parseFloat(drawOddStr);
        if (!lossOddStr.isEmpty()) loss_odd_value = Float.parseFloat(lossOddStr);
        if (!winStakeStr.isEmpty()) win_stake = Float.parseFloat(winStakeStr);
        if (!drawStakeStr.isEmpty()) draw_stake_value = Float.parseFloat(drawStakeStr);
        if (!lossStakeStr.isEmpty()) loss_stake_value = Float.parseFloat(lossStakeStr);
        if (!threshStr.isEmpty()) threshold_value = Float.parseFloat(threshStr);
        if (!dlRatioStr.isEmpty()) dl_ratio_value = Float.parseFloat(dlRatioStr);

        if (threshSeekValue > 0) threshold_seek_value = threshSeekValue;
        if (dlSeekValue > 0) dl_ratio_seek_value = dlSeekValue;

    }

    public void clearVariables(){
        win_odd = 0;
        draw_odd_value = 0;
        loss_odd_value = 0;

        win_stake = 0;
        draw_stake_value = 0;
        loss_stake_value = 0;

        threshold_value = 0;
        dl_ratio_value = 0.1f;
        profit_amount = 0;

        threshold_seek_value = 0;
        dl_ratio_seek_value = 0;

    }

    public void clearViews(){
        String blank = "";

        TextView totalWin = findViewById(R.id.total_win_view);
        TextView profitView = findViewById(R.id.profit_view);

        EditText winStake = findViewById(R.id.win_stake_view);
        EditText drawStake = findViewById(R.id.draw_stake_view);
        EditText lossStake = findViewById(R.id.loss_stake_view);

        EditText winOdd = findViewById(R.id.win_odd_view);
        EditText drawOdd = findViewById(R.id.draw_odd_view);
        EditText lossOdd = findViewById(R.id.loss_odd_view);

        EditText thresholdView = findViewById(R.id.threshold_view);
        EditText dlRatio = findViewById(R.id.dl_ratio_view);

        SeekBar thresholdSeek = findViewById(R.id.threshold_seekBar);
        SeekBar dlSeek = findViewById(R.id.dl_seekBar);

        winOdd.setText(blank);
        drawOdd.setText(blank);
        lossOdd.setText(blank);
        winStake.setText(blank);
        drawStake.setText(blank);
        lossStake.setText(blank);
        thresholdView.setText(blank);
        dlRatio.setText(blank);
        totalWin.setText(blank);
        profitView.setText(blank);

        thresholdSeek.setProgress(0);
        dlSeek.setProgress(0);
    }

    public void clearVariableDL(){
        draw_odd_value = 0;
        loss_odd_value = 0;

        draw_stake_value = 0;
        loss_stake_value = 0;

        dl_ratio_value = 0.1f;
        profit_amount = 0;

        dl_ratio_seek_value = 0;
    }

    public void clearViewsDL(){
        String blank = "";

        TextView totalWin = findViewById(R.id.total_win_view);
        TextView profitView = findViewById(R.id.profit_view);

        // EditText winStake = findViewById(R.id.win_stake_view);
        EditText drawStake = findViewById(R.id.draw_stake_view);
        EditText lossStake = findViewById(R.id.loss_stake_view);

        // EditText winOdd = findViewById(R.id.win_odd_view);
        EditText drawOdd = findViewById(R.id.draw_odd_view);
        EditText lossOdd = findViewById(R.id.loss_odd_view);

        // EditText thresholdView = findViewById(R.id.threshold_view);
        EditText dlRatio = findViewById(R.id.dl_ratio_view);

        // SeekBar thresholdSeek = findViewById(R.id.threshold_seekBar);
        SeekBar dlSeek = findViewById(R.id.dl_seekBar);

        // winOdd.setText(blank);
        drawOdd.setText(blank);
        lossOdd.setText(blank);
        // winStake.setText(blank);
        drawStake.setText(blank);
        lossStake.setText(blank);
        // thresholdView.setText(blank);
        dlRatio.setText(blank);
        totalWin.setText(blank);
        profitView.setText(blank);

        dlSeek.setProgress(0);
    }

    public void calculateStake() {
        draw_stake_value = total_win / draw_odd_value;
        loss_stake_value = total_win / loss_odd_value;
    }

    public void calculate(View view) {
        // method for handling the calculate button
        // Read fields
        readFields();

        if ((win_odd > 0) && (win_stake > 0) && (threshold_value > 0)) {
            // threshold, win odd and win stake are filled
            total_win = win_odd * win_stake;
            win_prob = 1 / win_odd;
            rest_prob = 1 - win_prob;
            if (rest_prob < threshold_value){
                Context context = getApplicationContext();
                Toast.makeText(context, noticeFour, Toast.LENGTH_LONG).show();
                return;
            }
            good_prob = rest_prob - threshold_value;


            if ((draw_odd_value == 0) && (loss_odd_value == 0)) {
                // draw odd and loss odd are NOT filled
                // Context context = getApplicationContext();
                // Toast.makeText(context, noticeTwo, Toast.LENGTH_LONG).show();
                loss_ratio = dl_ratio_value;
                draw_ratio = 1 - dl_ratio_value;

                draw_prob = draw_ratio * good_prob;
                loss_prob = loss_ratio * good_prob;

                draw_odd_value = 1 / draw_prob;
                loss_odd_value = 1 / loss_prob;
                calculateStake();
                total_stake = win_stake + draw_stake_value + loss_stake_value;
                profit_amount = total_win - total_stake;
                displayFields();

            } else if ((draw_odd_value > 0) && (loss_odd_value == 0)) {
                // draw odd is filled, loss odd is NOT filled
                draw_prob = 1 / draw_odd_value;
                if (draw_prob < good_prob){
                    draw_stake_value = total_win / draw_odd_value;
                    loss_prob = good_prob - draw_prob;
                    loss_odd_value = 1 / loss_prob;
                    calculateStake();
                    total_stake = win_stake + draw_stake_value + loss_stake_value;
                    profit_amount = total_win - total_stake;
                    displayFields();
                }
                else{
                    Context context = getApplicationContext();
                    Toast.makeText(context, noticeTwo, Toast.LENGTH_LONG).show();
                }

            } else if ((draw_odd_value > 0) && (loss_odd_value > 0)) {
                // draw odd and loss odd are filled
                draw_prob = 1 / draw_odd_value;
                loss_prob = 1 / loss_odd_value;
                combined_prob = draw_prob + loss_prob;
                if (combined_prob <= good_prob) {
                    calculateStake();
                    total_stake = win_stake + draw_stake_value + loss_stake_value;
                    profit_amount = total_win - total_stake;
                    displayFields();
                }
                else{
                    Context context = getApplicationContext();
                    Toast.makeText(context, noticeThree, Toast.LENGTH_LONG).show();
                }

            }


        } else {
            // if threshold, win odd and win stake is empty
            Context context = getApplicationContext();
            Toast.makeText(context, noticeOne, Toast.LENGTH_LONG).show();
        }
    }

    public void clear(View view){
        // Resets the values
        clearVariables();
        clearViews();
    }

    public void clearDL(View view){
        // Resets value of draw and loss only
        clearVariableDL();
        clearViewsDL();
    }
}
