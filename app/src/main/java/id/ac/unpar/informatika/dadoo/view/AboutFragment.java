package id.ac.unpar.informatika.dadoo.view;

import android.content.Context;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import id.ac.unpar.informatika.dadoo.IMainActivity;
import id.ac.unpar.informatika.dadoo.databinding.FragmentAboutBinding;
import id.ac.unpar.informatika.dadoo.presenter.IMainPresenter;

public class AboutFragment extends Fragment implements View.OnClickListener {
    private FragmentAboutBinding binding;
    private IMainActivity listener;
    private IMainPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = FragmentAboutBinding.inflate(inflater, container, false).getRoot();
        this.binding = FragmentAboutBinding.bind(view);

        this.binding.btnKembali.setOnClickListener(this);
        Linkify.addLinks(this.binding.tvGithub, Linkify.WEB_URLS);
        Linkify.addLinks(this.binding.tvIf, Linkify.WEB_URLS);
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

    public static AboutFragment newInstance(IMainPresenter presenter) {
        AboutFragment fragment = new AboutFragment();
        fragment.presenter = presenter;
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if (v == this.binding.btnKembali) {
            this.listener.changePage("Home");
        }
    }
}
