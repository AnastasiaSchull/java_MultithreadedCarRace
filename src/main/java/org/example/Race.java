package org.example;
import java.util.ArrayList;
import java.util.List;

public class Race {
    public static void startRace(List<Thread> cars) {
        // поток с анонимным классом для обратного отсчета
        Thread countdownThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Starting countdown...");
                    for (int i = 3; i > 0; i--) {
                        System.out.println(i + "...");
                        Thread.sleep(500);
                    }
                    System.out.println("GO!!!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Countdown interrupted!");
                }
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


    public static void start() {
        int trackLength = 500; // длина трассы

        // список машин
        List<RaceCarRunnable> cars = new ArrayList<>();
        cars.add(new RaceCarRunnable("Car 1", 200, trackLength));
        cars.add(new RaceCarRunnable("Car 2", 180, trackLength));
        cars.add(new RaceCarRunnable("Car 3", 220, trackLength));
        cars.add(new RaceCarRunnable("Car 4", 190, trackLength));


        //список потоков для машин
        List<Thread> threads = new ArrayList<>();
        for (RaceCarRunnable car : cars) {
            threads.add(new Thread(car));
        }

        //централизованный старт гонки
        System.out.println("Preparing the race...");
        startRace(threads);

        // ждем когда закончатся все потоки
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + thread.getName());
            }
        }

        System.out.println("Race finished!");
    }
}
