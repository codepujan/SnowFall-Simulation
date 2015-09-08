package nepalimutu.com.snowfalltest;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SnowFallActivity extends AppCompatActivity implements StartFall {

    private Toolbar mToolbar;
    //The Field Members for Animation Task
    private Animation animationFalling;
    private float mScale;
    private Rect mDisplaySize = new Rect();
    private int screenwidth;
    private Button start,stop;
    private RelativeLayout mRootLayout;
    private boolean initialized;
    private SnowAnimation myanimation;
    private boolean falling;
    public static  SnowFallActivity myreference;
    private BackgroundSound b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow_fall);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        animationFalling = AnimationUtils.loadAnimation(this, R.anim.falling);
       setSupportActionBar(mToolbar);
        start=(Button)findViewById(R.id.animate);
        stop=(Button)findViewById(R.id.pauseanimation);
        myreference=this;




        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParameterFragment paramfragment = new ParameterFragment();
                paramfragment.show(getSupportFragmentManager(), "Simulation  Parameter");

           }



        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myanimation!=null){
        myanimation.Pause();
                }
            }
        });


        //Leave This One Just For Few Moments
       b = new BackgroundSound();
       b.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_snow_fall, menu);
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


    @Override
    public void SnowFall() {
        if(myanimation!=null) {
            myanimation.Pause();
        }//Pause teh Existing One !!
        myanimation = new SnowAnimation(SnowFallActivity.this);
    }





    public class BackgroundSound extends AsyncTask<Void, Void, Void> {
        private MediaPlayer player;
        @Override
        protected Void doInBackground(Void... params) {
            player = MediaPlayer.create(SnowFallActivity.this, R.raw.jinglebells);
            player.setLooping(true); // Set looping
            player.setVolume(100,100);
            player.start();
            return null;
        }

        protected void pause(){
            player.pause();
        }

        protected void resume(){
            if(player!=null)
                player.start();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(b!=null){
            b.pause();

        }
    }

    public void onResume(){
        super.onResume();
        if(b!=null){
            b.resume();
        }
    }









}
