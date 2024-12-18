package org.example;

public class  App
{
    public static void main( String[] args )
    {
        try {
            System.out.println("Starting the race...");
            Race.start();
        } catch (InterruptedException e) {
            System.out.println("The race was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
