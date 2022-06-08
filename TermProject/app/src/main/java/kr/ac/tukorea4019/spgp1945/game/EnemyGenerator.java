package kr.ac.tukorea4019.spgp1945.game;


import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea4019.spgp1945.R;
import kr.ac.tukorea4019.spgp1945.framework.game.BaseGame;
import kr.ac.tukorea4019.spgp1945.framework.interfaces.GameObject;
import kr.ac.tukorea4019.spgp1945.framework.res.Metrics;
import kr.ac.tukorea4019.spgp1945.game.Enemy;

public class EnemyGenerator implements GameObject {

    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;
    private final float spawnInterval;
    private final float fallSpeed;
    private float elapsedTime;
    private int wave;

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);
        Enemy.size = Metrics.width / 5.0f * 0.9f;
        wave = 0;
    }

    @Override
    public void update() {
        float frameTime = BaseGame.getInstance().frameTime;
        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();
            //elapsedTime -= spawnInterval;
            elapsedTime -= 5.0f;
        }
    }

    private void spawn() {
        wave++;
        Random r = new Random();
        float tenth = Metrics.width / 10;
        for (int i = 1; i <= 9; i += 6 / Enemy.level) {
            int level = (wave + 15) / 10 - r.nextInt(20);

            if (level < Enemy.MIN_LEVEL) level = Enemy.MIN_LEVEL;

            if (level > Enemy.MAX_LEVEL) level = Enemy.MAX_LEVEL;

            Enemy enemy = Enemy.get(level, i*tenth + (tenth + i*2), fallSpeed);
            MainGame.get().add(MainGame.Layer.enemy, enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }

}
