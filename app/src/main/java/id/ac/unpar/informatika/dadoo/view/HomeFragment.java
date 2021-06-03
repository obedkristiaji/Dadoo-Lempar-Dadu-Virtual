package id.ac.unpar.informatika.dadoo.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import id.ac.unpar.informatika.dadoo.IMainActivity;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = FragmentHomeBinding.inflate(inflater, container, false).getRoot();
        this.binding = FragmentHomeBinding.bind(view);

        this.binding.btnKocok.setOnClickListener(this);
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

    public void setAngka(String s) {
        this.binding.tvHasil.setText(s);
    }

    public void startThread() {
        this.thread = new RandomThread(this.handler);
        this.thread.start();
    }

    @Override
    public void onClick(View v) {
        if(v == this.binding.btnKocok){
            this.startThread();
        }
    }
}
