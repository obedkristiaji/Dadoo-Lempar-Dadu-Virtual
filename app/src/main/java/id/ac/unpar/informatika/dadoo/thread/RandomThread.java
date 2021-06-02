package id.ac.unpar.informatika.dadoo.thread;

import android.util.Log;

public class RandomThread implements Runnable {
    private Thread thread = new Thread(this);
    private boolean start = false;
    private ThreadHandler handler;
    private int angka;
    private int min = 1;
    private int max = 6;

    public RandomThread(ThreadHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            while(start) {
                this.angka = min + (int)(Math.random() * ((max - min) + 1));
                this.handler.setGambar(this.angka);
                Log.d("angka", String.valueOf(angka));
                this.start = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        this.start = true;
        this.thread.start();
    }
}
