package id.ac.unpar.informatika.dadoo.presenter;

import id.ac.unpar.informatika.dadoo.MainActivity;

public class MainPresenter implements IMainPresenter {
    private MainActivity view;

    public MainPresenter(MainActivity view) {
        this.view = view;
    }

    @Override
    public void setAngka(String s) {
        this.view.setAngka(s);
    }
}
