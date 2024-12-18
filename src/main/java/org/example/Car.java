package org.example;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class Car implements Runnable {
    private final String name;
    private final int maxSpeed;

    @Override
    public void run() {
        long startTime = System.currentTimeMillis(); // начало работы потока
        String formattedTime = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(startTime));
        System.out.println(name + " is running with max speed: " + maxSpeed +
                " and started at " + formattedTime);
    }

}

