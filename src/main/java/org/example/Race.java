package org.example;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class Race {
    //для хранения времени старта гонки
    public static final AtomicLong startRaceTime = new AtomicLong();

    public static void startRace(List<Thread> cars) {
        Thread countdownThread = new Thread(() -> {
                try {
                    System.out.println("Starting countdown...");
                    for (int i = 3; i > 0; i--) {
                        System.out.println(i + "...");
                        Thread.sleep(500);
                    }
                    System.out.println("GO!!!");

                    // время старта гонки
                    startRaceTime.set(System.currentTimeMillis());

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Countdown interrupted!");
                }
        });

        // запускотсчета
        countdownThread.start();

        // ждем окончания обратного отсчета
        try {
            countdownThread.join();
        } catch (InterruptedException e) {
            System.out.println("Countdown was interrupted!");
        }

        // запускаем потоки машинок
        for (Thread car : cars) {
            car.start();
        }
    }

    public static void start() throws InterruptedException {
        int trackLength = 500; // длина трассы
        CountDownLatch latch = new CountDownLatch(4);
        // список машин
        List<RaceCarRunnable> cars = new ArrayList<>();
        cars.add(new RaceCarRunnable("Car 1", 200, trackLength, latch));
        cars.add(new RaceCarRunnable("Car 2", 180, trackLength, latch));
        cars.add(new RaceCarRunnable("Car 3", 220, trackLength, latch));
        cars.add(new RaceCarRunnable("Car 4", 190, trackLength, latch));

        //список потоков для машин
        List<Thread> threads = new ArrayList<>();
        for (RaceCarRunnable car : cars) {
            threads.add(new Thread(car));
        }

        //централизованный старт гонки
        System.out.println("Preparing the race...");
        startRace(threads);

        latch.await(); // ждем завершения всех потоков

        cars.sort(Comparator
                .comparingLong(RaceCarRunnable::getFinishTime)
                .thenComparing(RaceCarRunnable::getName));

        System.out.println("Race results:");
        for (RaceCarRunnable car : cars) {
            System.out.printf("%s FINISHED in %d ms%n", car.getName(), car.getFinishTime());
        }

        System.out.println("Winner: " + cars.get(0).getName());

        System.out.println("Race finished!");
    }
}
