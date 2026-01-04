package org.example;

import java.util.List;

public class EntranceTask implements Runnable {

    private final SportsHall hall;
    private final Group group;
    private final String entranceName;

    public EntranceTask(String entranceName, SportsHall hall, Group group) {
        this.entranceName = entranceName;
        this.hall = hall;
        this.group = group;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(entranceName);
        hall.seatGroup(group);
    }
}
