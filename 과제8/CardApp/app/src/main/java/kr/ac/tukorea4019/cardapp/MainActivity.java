package kr.ac.tukorea4019.cardapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;

public class MainActivity<openCardCount> extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] BUTTON_IDS = new int[]{
            R.id.card_00,R.id.card_01,R.id.card_02,R.id.card_03,
            R.id.card_10,R.id.card_11,R.id.card_12,R.id.card_13,
            R.id.card_20,R.id.card_21,R.id.card_22,R.id.card_23,
            R.id.card_30,R.id.card_31,R.id.card_32,R.id.card_33
    };


    private int[] resIds = new int[]{
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_kd,R.mipmap.card_qh,
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_kd,R.mipmap.card_qh,
    };


    private ImageButton previousImageButton;
    private int flips;
    private TextView scoreTextView;
    private int OpenCardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        scoreTextView = findViewById(R.id.scoreTextView);
        startGame();

    }



    private void startGame() {

        Random r = new Random();

        for(int i=0;i<resIds.length;++i){
            int t = r.nextInt(resIds.length);

            int id = resIds[t];
            resIds[t] = resIds[i];
            resIds[i] = id;
        }


        OpenCardCount = BUTTON_IDS.length;
        for(int i=0;i<BUTTON_IDS.length;i++){
            ImageButton btn = findViewById(BUTTON_IDS[i]);
            int resId = resIds[i];
            btn.setImageResource(R.mipmap.card_blue_back);
            btn.setVisibility(View.VISIBLE);
            btn.setTag(resId);
        }

        setScore(0);
        previousImageButton = null;
    }

    public void onBtnRestart(View view) {
        Log.d(TAG," Restart");
        askRetry();
    }

    private void askRetry() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.RESTART);
        builder.setMessage(R.string.restart_alert_msg);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startGame();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        AlertDialog alert = builder.create();
        alert.show();


    }

    public void onBtnCard(View view) {

        ImageButton imageButton = (ImageButton) view;

        int cardIndex = fintButtonIndex(view.getId());
        Log.d(TAG,"Card: "+cardIndex);

        if(previousImageButton == imageButton) {
            Log.d(TAG,"Same image Button");
            Toast.makeText(this, R.string.same_card,Toast.LENGTH_SHORT).show();


            return;
        }

        int resId = (Integer) imageButton.getTag();
        imageButton.setImageResource(resId);

        if(previousImageButton != null) {
            int previousResourceId = (Integer)previousImageButton.getTag();
            if(resId == previousResourceId){
                imageButton.setVisibility(View.INVISIBLE);
                previousImageButton.setVisibility(View.INVISIBLE);
                OpenCardCount -=2;
                if(OpenCardCount == 0)
                    askRetry();

                previousImageButton = null;
            }
            else {
                imageButton.setImageResource(resId);
                setScore(flips+1);
                previousImageButton.setImageResource(R.mipmap.card_blue_back);
                previousImageButton = imageButton;

            }

        }
        else{
            setScore(flips+1);
            imageButton.setImageResource(resId);
            previousImageButton = imageButton;

        }



    }

    private void setScore(int score) {
        flips = score;
        Resources res = getResources();
        String format = res.getString(R.string.flips_for);
        String text = String.format(format,score);
        scoreTextView.setText(text);

    }

    private int fintButtonIndex(int id) {
        for(int i=0; i<BUTTON_IDS.length;i++){
            if(id == BUTTON_IDS[i])
                return i;
        }
        return -1;
    }
}