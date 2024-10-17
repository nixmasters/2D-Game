package com.example.shooter;

import com.example.shooter.timer.Updatable;
import javafx.scene.text.Text;

public class ElapsedTime extends Text implements Updatable {
    private long elapsedDns;

    public boolean update(long dns) {
        this.elapsedDns += dns;
        int totalSeconds = (int)((double)this.elapsedDns / 1.0E9D);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        this.setText(String.format("%02d:%02d", minutes, seconds));
        return false;
    }
}