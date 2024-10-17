package com.example.shooter;

import com.example.shooter.timer.Updatable;

public class SecondCounter implements Updatable {
    public double seconds;
    private SecondCounter.Callback[] callbacks;
    private long elapsed;

    public SecondCounter(double seconds, SecondCounter.Callback... callbacks) {
        this.seconds = seconds;
        this.callbacks = new SecondCounter.Callback[callbacks.length];

        for(int i = 0; i < callbacks.length; ++i) {
            this.callbacks[i] = callbacks[i];
        }

    }

    public boolean update(long dns) {
        this.elapsed += dns;
        if ((double)this.elapsed >= this.seconds * 1.0E10D) {
            for(int i = 0; i < this.callbacks.length; ++i) {
                this.callbacks[i].handle();
            }

            this.elapsed = 0L;
        }

        return false;
    }

    public interface Callback {
        void handle();
    }
}