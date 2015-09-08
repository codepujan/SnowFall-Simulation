package nepalimutu.com.snowfalltest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pujan paudel on 9/6/2015.
 */
public class SnowAnimation {
    private float mScale;
    private Rect mDisplaySize = new Rect();
    private int screenwidth;
    private Activity activity;
    private RelativeLayout mRootLayout;
    private ValueAnimator myanimator;
    private Timer animationtimer;
    private Timer stoptimer;
    private int[] SNOW={R.drawable.snow_1,R.drawable.snow_2,R.drawable.snow_3};
    private int animspeed;
    private boolean snowfall=true;

        public SnowAnimation(Activity acv){
        this.activity=acv;



        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getRectSize(mDisplaySize);
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mScale =0.4f;

        mRootLayout = (RelativeLayout)activity.findViewById(R.id.main_layout);//The Main layout

        animationtimer =new Timer();
        animationtimer.schedule(new ExeTimerTask(), 0, Constants.getAnimDensity());
            //If We Need a Timer to Stop Snowfall, we can set it here

           // stoptimer=new Timer();
        //stoptimer.schedule(new SnowStopTimeTask(), 20000);//Stop After 20  milliseconds


    }


    private Handler animationStop=new Handler(){
        @Override
    public void handleMessage(Message msg){
            super.handleMessage(msg);
            snowfall=false;
        }

    };

      private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //THE PArt of Animating random Sprites
            //We Don't need this now. do we
            if (snowfall) {
                int viewId = new Random().nextInt(SNOW.length);
                Drawable d = activity.getResources().getDrawable(SNOW[viewId]);
                LayoutInflater inflate = LayoutInflater.from(activity);
                ImageView imageView = (ImageView) inflate.inflate(R.layout.ani_image_view, null);
                imageView.setImageDrawable(d);

                mRootLayout.addView(imageView);


                RelativeLayout.LayoutParams animationLayout = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                int position = new Random().nextInt(5);
                Log.d("Origin", String.valueOf(position));
                switch (position) {
                    case 0:
                        animationLayout.setMargins(50, (int) (-150 * mScale), 0, 0);//Originate the Snow From the LeftMost Corner
                        break;

                    case 1:
                        animationLayout.setMargins(150, (int) (-150 * mScale), 0, 0);//Originate the Snow From the LeftMost Corner

                        break;

                    case 2:
                        animationLayout.setMargins(250, (int) (-150 * mScale), 0, 0);//Originate the Snow From the LeftMost Corner

                        break;
                    case 3:
                        animationLayout.setMargins(550, (int) (-150 * mScale), 0, 0);//Originate the Snow From the LeftMost Corner

                    case 4:
                        animationLayout.setMargins(350, (int) (-150 * mScale), 0, 0);//Originate the Snow From the LeftMost Corner

                        break;

                    case 5:
                        animationLayout.setMargins(20, (int) (-150 * mScale), 0, 0);//Originate the Snow From the LeftMost Corner

                        break;
                    default:
                        animationLayout.setMargins(400, (int) (-150 * mScale), 0, 0);//Originate the Snow From the LeftMost Corner
                }

                //A Random Value For the Origin
                long origin = new Random().nextInt(mDisplaySize.width());
                Log.d("Random VAlue", String.valueOf(origin));
                animationLayout.width = (int) origin;
                animationLayout.height = (int) (60 * mScale);

                startAnimation(imageView);
            }
        }
    };



    public void startAnimation(final ImageView aniView) {

        aniView.setPivotX(aniView.getWidth()/2);
        aniView.setPivotY(aniView.getHeight()/2);

        long delay = new Random().nextInt(Constants.MAX_DELAY);

         myanimator = ValueAnimator.ofFloat(0, 1);
        //For Varying Animation Duration

         int speedrand=new Random().nextInt(4);
        Log.d("Animation Speed",String.valueOf(speedrand));

        switch (speedrand){
            case 0:
                animspeed=Constants.ANIM_DURATION/3; // Fastest One
                break;
            case 1:
                animspeed=Constants.ANIM_DURATION/2; // Fast

                break;
            case 2:
                animspeed=Constants.ANIM_DURATION; // Lil Beet Fatser Oen

                break;
            case 3:
                animspeed=Constants.ANIM_DURATION*2; // Lil Beet Slower Oen

                break;
            case 4:
                animspeed=Constants.ANIM_DURATION*3; // Lil Beet Slower Oen

                break;
            default:
                animspeed=Constants.ANIM_DURATION*3; // Lil Beet Slower Oen

                break;
        }
        myanimator.setDuration(animspeed);
        myanimator.setInterpolator(new AccelerateInterpolator());
        myanimator.setStartDelay(delay);


        //aniView :)
        myanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            int angle = 50 + (int) (Math.random() * 101);
            int movex = new Random().nextInt(mDisplaySize.right);

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) (animation.getAnimatedValue())).floatValue();

                aniView.setRotation(angle * value);
                aniView.setTranslationX((movex - 40) * value);
                aniView.setTranslationY((mDisplaySize.bottom + (150 * mScale)) * value);
                aniView.setAlpha(1 - value);
            }
        });

        myanimator.start();

    }


    private class ExeTimerTask extends TimerTask {
        @Override
        public void run() {
            // we don't really use the message 'what' but we have to specify something.
            mHandler.sendEmptyMessage(Constants.EMPTY_MESSAGE_WHAT);
        }
    }


    private class SnowStopTimeTask extends TimerTask{
        @Override
        public void run() {
            // we don't really use the message 'what' but we have to specify something.
            animationStop.sendEmptyMessage(Constants.EMPTY_MESSAGE_WHAT);
            stoptimer.cancel();
        }

    }

    public void Pause(){
    animationtimer.cancel();
    }

    public void Resume(){
        animationtimer=null;//Free the Existing one if there
        animationtimer =new Timer();
        animationtimer.schedule(new ExeTimerTask(),0,Constants.getAnimDensity());
    }
}
