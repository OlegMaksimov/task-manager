package ru.maksimov.taskmanager.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Задачи и дела.
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    static AtomicLong countTask = new AtomicLong();
    Long id;
    String name;

    public Task(String name) {
        this.id = countTask.incrementAndGet();
        this.name = name;
    }

    private Task(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Task getInstance(Long id, String taskName) {
        return new Task(id, taskName);
    }

    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "" + id + ". ...................................................... " + name;
    }
}
