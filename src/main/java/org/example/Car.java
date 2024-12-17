package org.example;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Car implements Runnable {
    private final String name;
    private final int maxSpeed;

    @Override
    public void run() {
        System.out.println(name + " is running with max speed: " + maxSpeed);
    }
}

