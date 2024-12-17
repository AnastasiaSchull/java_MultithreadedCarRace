package org.example;
import lombok.Getter;
import lombok.Setter;
import java.util.Random;

@Getter
@Setter
public class RaceCarRunnable extends Car implements Runnable {
    private int passed = 0;       // пройденная дистанция
    private final int distance;   // длина трассы
    private boolean isFinish = false;//флаг завершения гонки
    private final Random random = new Random();

    public RaceCarRunnable(String name, int maxSpeed, int distance) {
        super(name, maxSpeed);
        this.distance = distance;
    }

    private int getRandomSpeed() {
        int minSpeed = getMaxSpeed() / 2;
        return minSpeed + random.nextInt(getMaxSpeed() - minSpeed + 1);
    }

    @Override
    public void run() {
        while (!isFinish) {
            int speed = getRandomSpeed();
            passed += speed;

            if (passed >= distance) {
                passed = distance;
                isFinish = true;
            }

            System.out.printf("%s => speed: %d; progress: %d/%d%n", getName(), speed, passed, distance);

            try {
                Thread.sleep(500); // пауза пол сек. между итерациями
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(getName() + " прерван.");
            }
        }
        System.out.println(getName() + " финишировал!");
    }
}

