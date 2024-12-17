package org.example;

public class App 
{
    public static void main( String[] args )
    {
        int trackLength = 350; // длина трассы

        RaceCarRunnable car1 = new RaceCarRunnable("Car 1", 200, trackLength);
        RaceCarRunnable car2 = new RaceCarRunnable("Car 2", 180, trackLength);
        RaceCarRunnable car3 = new RaceCarRunnable("Car 3", 220, trackLength);

        // запускаем машины в отдельных потоках
        Thread thread1 = new Thread(car1);
        Thread thread2 = new Thread(car2);
        Thread thread3 = new Thread(car3);

        thread1.start();
        thread2.start();
        thread3.start();
    }

}
