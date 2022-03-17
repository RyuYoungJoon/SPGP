package kr.ac.tukorea4019.imageswitcher;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = MainActivity.class.getSimpleName();
    protected static int[] RESIDS = new int[]{
        R.mipmap.cat1,
        R.mipmap.cat2,
        R.mipmap.cat3,
        R.mipmap.cat4,
        R.mipmap.cat5
    };

    protected int page;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        setPage(1);
    }

    public void onBtnPrev(View view) {
        Log.d(TAG, "Prev Button Pressed");
        setPage(page-1);
    }
    public void onBtnNext(View view) {
        Log.d(TAG,"Next Button Pressed");
        setPage(page+1);
    }

    private void setPage(int newPage) {
        ImageButton btnPrev = findViewById(R.id.btnPrev);
        ImageButton btnNext = findViewById(R.id.btnNext);

        btnNext.setEnabled(true);
        btnPrev.setEnabled(true);

        page = newPage;
        String text = page + " / "+RESIDS.length;
        TextView tv = findViewById(R.id.pageText);
        tv.setText(text);

        ImageView iv = findViewById(R.id.contentImageView);
        iv.setImageResource(RESIDS[page-1]);

            if (page <= 1) {
                btnPrev.setEnabled(false);
                return;
            } else if (page >= 5) {
                btnNext.setEnabled(false);
                return;
            }

    }
}