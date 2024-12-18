package org.example;
import lombok.Getter;
import lombok.Setter;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@Getter
@Setter
public class RaceCarRunnable extends Car implements Runnable {
    private int passed = 0;       // пройденная дистанция
    private final int distance;   // длина трассы
    private boolean isFinish = false;//флаг завершения гонки
    private final Random random = new Random();
    private final CountDownLatch latch; // счетчик синхронизации
    private long finishTime; // для финиша в миллисекундах

    public RaceCarRunnable(String name, int maxSpeed, int distance, CountDownLatch latch) {
        super(name, maxSpeed);
        this.distance = distance;
        this.latch = latch;
    }

    private int getRandomSpeed() {
        int minSpeed = getMaxSpeed() / 2;
        return minSpeed + random.nextInt(getMaxSpeed() - minSpeed + 1);
    }

    @Override
    public void run() {
        super.run();
        while (!isFinish) {
            int speed = getRandomSpeed();
            passed += speed; // добавляем пройденное расстояние

            if (passed >= distance) {
                passed = distance;
                isFinish = true; //машина финишировала

                // временя финиша
                finishTime = System.currentTimeMillis() - Race.startRaceTime.get();

                latch.countDown(); // -- счетчик
                System.out.println(getName() + " FINISHED in " + finishTime + " ms");
            }

            System.out.printf("%s => speed: %d; progress: %d/%d%n", getName(), speed, passed, distance);

            try {
                Thread.sleep(1000); // пауза 1 сек. между итерациями
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(getName() + " was interrupted.");
            }
        }
    }
}

