package org.example;
import java.util.ArrayList;
import java.util.List;

public class Race {
    public static void start() {
        int trackLength = 500; // Длина трассы в метрах

        // список машин
        List<RaceCarRunnable> cars = new ArrayList<>();
        cars.add(new RaceCarRunnable("Car 1", 200, trackLength));
        cars.add(new RaceCarRunnable("Car 2", 180, trackLength));
        cars.add(new RaceCarRunnable("Car 3", 220, trackLength));
        cars.add(new RaceCarRunnable("Car 4", 190, trackLength));

        // создаем и запускаем потоки для каждой машины
        List<Thread> threads = new ArrayList<>();
        for (RaceCarRunnable car : cars) {
            Thread thread = new Thread(car);
            threads.add(thread);
            thread.start();
        }

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
