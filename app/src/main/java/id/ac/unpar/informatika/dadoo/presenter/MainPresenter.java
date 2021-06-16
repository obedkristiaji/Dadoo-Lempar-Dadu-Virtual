package id.ac.unpar.informatika.dadoo.presenter;

import id.ac.unpar.informatika.dadoo.MainActivity;

public class MainPresenter implements IMainPresenter {
    private MainActivity view;
    private int angka;
    private int min = 1;
    private int max = 6;

    public MainPresenter(MainActivity view) {
        this.view = view;
    }

    @Override
    public void setGambar(int num) {
        this.view.setGambar(num);
    }

    @Override
    public void random() {
        this.angka = min + (int)(Math.random() * ((max - min) + 1));

        this.setGambar(this.angka);
    }
}
