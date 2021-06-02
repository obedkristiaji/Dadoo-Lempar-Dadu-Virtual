package id.ac.unpar.informatika.dadoo.thread;

import android.os.Handler;
import android.os.Message;

import id.ac.unpar.informatika.dadoo.presenter.IMainPresenter;

public class ThreadHandler extends Handler {
    private IMainPresenter presenter;
    private int MSG_SET_ANGKA = 0;

    public ThreadHandler(IMainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == this.MSG_SET_ANGKA) {
            int angka = (int) msg.obj;
            this.presenter.setGambar(angka);
        }
    }

    public void setAngka(int angka) {
        Message msg = new Message();
        msg.what = MSG_SET_ANGKA;
        msg.obj = angka;

        this.sendMessage(msg);
    }
}
