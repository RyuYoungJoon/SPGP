package kr.ac.tukorea4019.spgp1945.game;


import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea4019.spgp1945.R;
import kr.ac.tukorea4019.spgp1945.framework.game.BaseGame;
import kr.ac.tukorea4019.spgp1945.framework.res.Metrics;
import kr.ac.tukorea4019.spgp1945.framework.interfaces.GameObject;
import kr.ac.tukorea4019.spgp1945.framework.objects.Score;

public class MainGame extends BaseGame {
    private static final String TAG = MainGame.class.getSimpleName();
    Score score;

    public static MainGame get() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return (MainGame)singleton;
    }
    public enum Layer {
        bg1, bullet, enemy, player, bg2, ui, controller, COUNT, enemybullet
    }
    private Fighter fighter;

    public void init() {

        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());

        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(fx, fy);
        add(Layer.player, fighter);

//        score = new Score();
        int mipmapResId = R.mipmap.score2;
        float marginTop = Metrics.size(R.dimen.score_margin_top);
        float marginRight = Metrics.size(R.dimen.score_margin_right);
        float charWidth = Metrics.size(R.dimen.score_digit_width);
        score = new Score(mipmapResId, marginTop, marginRight, charWidth);
//        score.set(123456);
        add(Layer.ui, score);

        add(Layer.bg1, new VertScrollBackground(R.mipmap.stage_a, Metrics.size(R.dimen.bg_speed_city)));
        // add(Layer.bg2, new VertScrollBackground(R.mipmap.clouds, Metrics.size(R.dimen.bg_speed_cloud)));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                fighter.setTargetPosition(x, y);
                return true;
        }
        return false;
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }

    public void add(Layer layer, GameObject gameObject) {
        add(layer.ordinal(), gameObject);
    }
}
