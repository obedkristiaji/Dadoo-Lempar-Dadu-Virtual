package id.ac.unpar.informatika.dadoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

import id.ac.unpar.informatika.dadoo.databinding.ActivityMainBinding;
import id.ac.unpar.informatika.dadoo.presenter.MainPresenter;
import id.ac.unpar.informatika.dadoo.thread.ThreadHandler;
import id.ac.unpar.informatika.dadoo.view.AboutFragment;
import id.ac.unpar.informatika.dadoo.view.HomeFragment;

public class MainActivity extends AppCompatActivity implements IMainActivity, SensorEventListener {
    private ActivityMainBinding binding;
    private MainPresenter presenter;
    private FragmentManager fragmentManager;
    private HomeFragment fragmentHome;
    private AboutFragment fragmentAbout;
    private ThreadHandler handler;
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private float[] accelerometerReading = new float[3];
    private int shakeThreshold = 800;
    private long lastUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        this.accelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.presenter = new MainPresenter(this);
        this.handler = new ThreadHandler(this.presenter);

        this.fragmentHome = HomeFragment.newInstance(this.presenter, this.handler);
        this.fragmentAbout = AboutFragment.newInstance(this.presenter);
        this.fragmentManager = this.getSupportFragmentManager();

        this.changePage("Home");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(this.accelerometer != null) {
            this.mSensorManager.registerListener(this, this.accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if (curTime - lastUpdate > 100) {
                long diffTime = curTime - lastUpdate;
                lastUpdate = curTime;
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                float speed = Math.abs(x + y + z - accelerometerReading[0] - accelerometerReading[1] - accelerometerReading[2]) / diffTime * 10000;
                if (speed > this.shakeThreshold) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(200);
                    }
                    this.fragmentHome.startThread();
                }
                accelerometerReading[0] = x;
                accelerometerReading[1] = y;
                accelerometerReading[2] = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //
    }

    @Override
    public void changePage(String page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        switch (page) {
            case "Home": {
                this.fragmentManager.popBackStackImmediate();
                ft.replace(binding.fragmentContainer.getId(), this.fragmentHome).addToBackStack(null);
                break;
            }
            case "About": {
                ft.replace(binding.fragmentContainer.getId(), this.fragmentAbout).addToBackStack(null);
                break;
            }
        }
        ft.commit();
    }

    @Override
    public void setGambar(int num) {
        this.fragmentHome.setGambar(num);
    }
}