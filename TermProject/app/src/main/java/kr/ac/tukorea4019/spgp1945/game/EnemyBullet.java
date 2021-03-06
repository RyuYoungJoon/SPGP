package kr.ac.tukorea4019.spgp1945.game;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea4019.spgp1945.framework.game.BaseGame;
import kr.ac.tukorea4019.spgp1945.framework.interfaces.BoxCollidable;
import kr.ac.tukorea4019.spgp1945.framework.interfaces.GameObject;
import kr.ac.tukorea4019.spgp1945.framework.res.Metrics;
import kr.ac.tukorea4019.spgp1945.R;
import kr.ac.tukorea4019.spgp1945.framework.interfaces.Recyclable;
import kr.ac.tukorea4019.spgp1945.framework.game.RecycleBin;

import kr.ac.tukorea4019.spgp1945.framework.res.BitmapPool;
public class EnemyBullet implements GameObject,BoxCollidable,Recyclable {
    private static final String TAG = Bullet.class.getSimpleName();
    protected float x, y;
    protected final float length;
    protected final float speed;
    protected RectF boundingBox = new RectF();
    protected float power;

    protected static Paint paint;
    protected static float laserWidth;

    //    protected static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static EnemyBullet get(float x, float y, float power) {
        EnemyBullet bullet = (EnemyBullet) RecycleBin.get(EnemyBullet.class);
        if (bullet != null) {
//            Bullet bullet = recycleBin.remove(0);
//            Log.d(TAG, "Recycle: " + recycleBin.size() + " bullets");
            bullet.set(x, y, power);
            return bullet;
        }
        return new EnemyBullet(x, y, power);
    }
    private void set(float x, float y, float power) {
        this.x = x;
        this.y = y;
        this.power = power;
    }
    private EnemyBullet(float x, float y, float power) {
        this.x = x;
        this.y = y;
        this.power = power;
        this.length = Metrics.size(R.dimen.laser_length);
        this.speed = Metrics.size(R.dimen.laser_speed);

        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.GREEN);
            laserWidth = Metrics.size(R.dimen.laser_width);
            paint.setStrokeWidth(laserWidth);
        }

        Log.d(TAG, "Created: " + this);
    }
    @Override
    public void update() {
        BaseGame game = BaseGame.getInstance();
        float frameTime = game.frameTime;
        y -= speed * frameTime;

        float hw = laserWidth / 2;
        boundingBox.set(x - hw, y, x + hw, y + length);

        if (y < 0) {
            game.remove(this);
//            recycleBin.add(this);
//            Log.d(TAG, "Remove: " + recycleBin.size() + " bullets");
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x, y + length, paint);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingBox;
    }

    @Override
    public void finish() {

    }

    public float getPower() {
        return power;
    }
}
