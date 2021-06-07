package id.ac.unpar.informatika.dadoo.view;

import android.content.Context;
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

public class HomeFragment extends Fragment implements View.OnClickListener {
    private FragmentHomeBinding binding;
    private IMainActivity listener;
    private IMainPresenter presenter;
    private ThreadHandler handler;
    private RandomThread thread;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = FragmentHomeBinding.inflate(inflater, container, false).getRoot();
        this.binding = FragmentHomeBinding.bind(view);

        this.binding.btnLempar.setOnClickListener(this);
        this.binding.btnTentang.setOnClickListener(this);
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

    public static HomeFragment newInstance(IMainPresenter presenter, ThreadHandler handler) {
        HomeFragment fragment = new HomeFragment();
        fragment.handler = handler;
        fragment.presenter = presenter;
        return fragment;
    }
      
    public void setGambar(int num){
        this.binding.tvHome.setVisibility(View.GONE);
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

    @Override
    public void onClick(View v) {
        if (v == this.binding.btnLempar) {
            Vibrator vib = (Vibrator)  getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vib.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vib.vibrate(200);
            }
            this.startThread();
        } else if (v == this.binding.btnTentang) {
            this.listener.changePage("About");
        }
    }
}
