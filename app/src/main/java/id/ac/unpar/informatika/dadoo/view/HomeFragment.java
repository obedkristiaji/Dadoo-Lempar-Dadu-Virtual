package id.ac.unpar.informatika.dadoo.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import id.ac.unpar.informatika.dadoo.IMainActivity;
import id.ac.unpar.informatika.dadoo.R;
import id.ac.unpar.informatika.dadoo.databinding.FragmentHomeBinding;
import id.ac.unpar.informatika.dadoo.presenter.IMainPresenter;
import id.ac.unpar.informatika.dadoo.thread.RandomThread;
import id.ac.unpar.informatika.dadoo.thread.ThreadHandler;

public class HomeFragment extends Fragment implements View.OnClickListener, SensorEventListener {
    private FragmentHomeBinding binding;
    private IMainActivity listener;
    private IMainPresenter presenter;
    private ThreadHandler handler;
    private RandomThread thread;
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private float[] accelerometerReading = new float[3];
    private int shakeThreshold = 800;
    private long lastUpdate = 0;
    private  Vibrator v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = FragmentHomeBinding.inflate(inflater, container, false).getRoot();
        this.binding = FragmentHomeBinding.bind(view);

        this.mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        this.accelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.handler = new ThreadHandler(this.presenter);

        this.v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        this.binding.btnLempar.setOnClickListener(this);
        this.binding.btnTentang.setOnClickListener(this);

        this.setGambar(4);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IMainActivity) {
            this.listener = (IMainActivity) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }

    public static HomeFragment newInstance(IMainPresenter presenter) {
        HomeFragment fragment = new HomeFragment();
        fragment.presenter = presenter;
        return fragment;
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
                    this.vibrate();
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


    public void setGambar(int num){
        if(num==1){
            this.binding.imgDadu.setImageResource(R.drawable.dadu1);
        }else if(num==2){
            this.binding.imgDadu.setImageResource(R.drawable.dadu2);
        }else if(num==3){
            this.binding.imgDadu.setImageResource(R.drawable.dadu3);
        }else if(num==4){
            this.binding.imgDadu.setImageResource(R.drawable.dadu4);
        }else if(num==5){
            this.binding.imgDadu.setImageResource(R.drawable.dadu5);
        }else if(num==6){
            this.binding.imgDadu.setImageResource(R.drawable.dadu6);
        }
    }

    public void startThread() {
        this.thread = new RandomThread(this.handler);
        this.thread.start();
    }

    public void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(200);
        }
        this.startThread();
    }

    @Override
    public void onClick(View v) {
        if (v == this.binding.btnLempar) {
            this.vibrate();
        } else if (v == this.binding.btnTentang) {
            this.listener.changePage("About");
        }
    }
}
