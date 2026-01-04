package org.example;

import java.util.EnumMap;
import java.util.Map;

public class SportsHall {

    private final Map<SeatCategory, Integer> maxSeats = new EnumMap<>(SeatCategory.class);
    private final Map<SeatCategory, Integer> occupiedSeats = new EnumMap<>(SeatCategory.class);

    public SportsHall() {
        maxSeats.put(SeatCategory.VIP, 20);
        maxSeats.put(SeatCategory.STANDARD, 50);
        maxSeats.put(SeatCategory.ECONOMY, 100);

        for (SeatCategory category : SeatCategory.values()) {
            occupiedSeats.put(category, 0);
        }
        printInitialCapacity();
    }
    private void printInitialCapacity() {
        int total = 0;

        System.out.println("Capacity of Sports Hall:");

        for (SeatCategory category : SeatCategory.values()) {
            int max = maxSeats.get(category);
            total += max;
            System.out.println("  " + category + ": " + max);
        }

        System.out.println("Total seats in the sports hall: " + total);
        System.out.println("=================================");
    }

    private void printFreeSeats() {
        System.out.println("Free seats:");
        for (SeatCategory category : SeatCategory.values()) {
            int free = maxSeats.get(category) - occupiedSeats.get(category);
            System.out.println("  " + category + ": " + free);
        }
        System.out.println("---------------------------");
    }


    // СИНХРОНИЗИРАН МЕТОД С wait/notify
    public synchronized void seatGroup(Group group) {
        SeatCategory category = group.getCategory();
        int needed = group.getSize();

        while (!hasFreeSeats(category, needed)) {
            try {
                System.out.println(Thread.currentThread().getName()
                        + " Waiting for free seats (" + category + ")");
                wait(); // нишката заспива
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // има достатъчно места
        occupiedSeats.put(category,
                occupiedSeats.get(category) + needed);

        System.out.println(Thread.currentThread().getName()
                + " accommodate a group of " + needed
                + " people (" + category + ")");

        printFreeSeats();

        notifyAll(); // събуждаме чакащите
    }

    private boolean hasFreeSeats(SeatCategory category, int needed) {
        return occupiedSeats.get(category) + needed <= maxSeats.get(category);
    }

    public void printFinalState() {
        System.out.println("\nFinal state of the hall:");
        for (SeatCategory category : SeatCategory.values()) {
            System.out.println(category + ": "
                    + occupiedSeats.get(category)
                    + " / " + maxSeats.get(category));
        }
    }
}

