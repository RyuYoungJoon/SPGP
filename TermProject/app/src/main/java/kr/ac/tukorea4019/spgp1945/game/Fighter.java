package kr.ac.tukorea4019.spgp1945.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea4019.spgp1945.framework.game.BaseGame;
import kr.ac.tukorea4019.spgp1945.framework.res.Metrics;
import kr.ac.tukorea4019.spgp1945.R;
import kr.ac.tukorea4019.spgp1945.framework.objects.Sprite;
import kr.ac.tukorea4019.spgp1945.framework.res.BitmapPool;

public class Fighter extends Sprite {

    private static final String TAG = Fighter.class.getSimpleName();
    private RectF targetRect = new RectF();

    //private float angle;
    private float dx, dy;
    private float tx, ty;
    private float elapsedTimeForFire;
    private float fireInterval = 1.0f / 10;

    private static Bitmap targetBitmap;
//    private static Rect srcRect;

    public Fighter(float x, float y) {
        super(x, y, R.dimen.fighter_radius, R.mipmap.plane_240);
        setTargetPosition(x, y);
        //angle = -(float) (Math.PI / 2);

        targetBitmap = BitmapPool.get(R.mipmap.target);
        fireInterval = Metrics.floatValue(R.dimen.fighter_fire_interval);
    }

    public void draw(Canvas canvas) {
//        canvas.save();
//        canvas.rotate((float) (angle * 180 / Math.PI) + 90, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
//        canvas.restore();
        if (dx != 0 || dy != 0) {
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }

    public void update() {
        float frameTime = BaseGame.getInstance().frameTime;
        elapsedTimeForFire += frameTime;
        if (elapsedTimeForFire >= fireInterval) {
            fire();
            elapsedTimeForFire -= fireInterval;
        }

        if (dx == 0)
            return;

        float dx = this.dx * frameTime;
        float dy = this.dy * frameTime;
        boolean arrived = false;
        if ((dx > 0 && x + dx > tx) || (dx < 0 && x + dx < tx)
                || (dy > 0 && y + dy > ty) || (dy <0 && y + dy < ty)) {
            dx = tx - x;
            dy = ty -y;

            x = tx;
            y = ty;
            arrived = true;
        } else {
            x += dx;
            y += dy;
        }
        dstRect.offset(dx, dy);
        if (arrived) {
            this.dx = 0;
        }
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        targetRect.set(tx - radius/2, ty - radius/2,
                tx + radius/2, ty + radius/2);
        //angle = (float) Math.atan2(ty - y, tx - x);
        dx = Metrics.size(R.dimen.fighter_speed);
        dy = Metrics.size(R.dimen.fighter_speed);
        if (tx < x || ty < y) {
            dx = -dx;
            dy = -dy;
        }
        //dy = (float) (dist * Math.sin(angle));
    }

    public void fire() {
        MainGame game = MainGame.get();
        int score = game.score.get();
        if (score > 100000) score = 100000;
        float power = 10 + score / 1000;
        Bullet bullet = Bullet.get(x, y, power);
        game.add(MainGame.Layer.bullet, bullet);
    }

}
