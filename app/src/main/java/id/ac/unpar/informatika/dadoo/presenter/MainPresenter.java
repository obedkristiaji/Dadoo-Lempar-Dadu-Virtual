package id.ac.unpar.informatika.dadoo.presenter;

import id.ac.unpar.informatika.dadoo.MainActivity;

public class MainPresenter implements IMainPresenter {
    private MainActivity view;

    public MainPresenter(MainActivity view) {
        this.view = view;
    }

    @Override
    public void setGambar(int num) {
        this.view.setGambar(num);
    }
}
