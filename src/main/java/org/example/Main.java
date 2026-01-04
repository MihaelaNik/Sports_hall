package org.example;


import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) throws InterruptedException {

        SportsHall hall = new SportsHall();

        // 10 ГРУПИ
        List<Group> groups = List.of(
                new Group(5, SeatCategory.VIP),
                new Group(7, SeatCategory.VIP),

                new Group(10, SeatCategory.STANDARD),
                new Group(12, SeatCategory.STANDARD),

                new Group(15, SeatCategory.ECONOMY),
                new Group(20, SeatCategory.ECONOMY),

                new Group(6, SeatCategory.VIP),
                new Group(8, SeatCategory.STANDARD),

                new Group(25, SeatCategory.ECONOMY),
                new Group(30, SeatCategory.ECONOMY)
        );

        // 4 ВХОДА = 4 НИШКИ
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Random random = new Random();

        for (Group group : groups) {
            // Генерираме случаен вход между 1 и 4
            int entrance = random.nextInt(4) + 1;

            // Създаваме и подаваме задачата
            executor.execute(
                    new EntranceTask("Entrance " + entrance, hall, group)
            );
        }

        executor.shutdown();
        boolean b = executor.awaitTermination(1, TimeUnit.MINUTES);

        hall.printFinalState();
    }
}

