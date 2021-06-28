package id.ac.unpar.informatika.dadoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import id.ac.unpar.informatika.dadoo.databinding.ActivityMainBinding;
import id.ac.unpar.informatika.dadoo.presenter.MainPresenter;
import id.ac.unpar.informatika.dadoo.view.AboutFragment;
import id.ac.unpar.informatika.dadoo.view.HomeFragment;

public class MainActivity extends AppCompatActivity implements IMainActivity {
    private ActivityMainBinding binding;
    private MainPresenter presenter;
    private FragmentManager fragmentManager;
    private HomeFragment fragmentHome;
    private AboutFragment fragmentAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.presenter = new MainPresenter(this);

        this.fragmentHome = HomeFragment.newInstance(this.presenter);
        this.fragmentAbout = AboutFragment.newInstance(this.presenter);
        this.fragmentManager = this.getSupportFragmentManager();

        this.changePage("Home");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (this.fragmentManager.getBackStackEntryCount() == 0) {
            this.closeApplication();
        }
    }

    @Override
    public void changePage(String page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        switch (page) {
            case "Home": {
                ft.replace(binding.fragmentContainer.getId(), this.fragmentHome, "Home").addToBackStack(null);
                this.fragmentManager.popBackStackImmediate();
                break;
            }
            case "About": {
                ft.replace(binding.fragmentContainer.getId(), this.fragmentAbout, "About").addToBackStack(null);
                break;
            }
        }
        ft.commit();
    }

    @Override
    public void closeApplication() {
        this.moveTaskToBack(true);
        this.finish();
    }

    @Override
    public void setGambar(int num) {
        this.fragmentHome.setGambar(num);
    }
}