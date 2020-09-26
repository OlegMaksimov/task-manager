package ru.maksimov.taskmanager.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.maksimov.taskmanager.model.enums.TaskState;

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
    TaskState state;

    public Task(String name) {
        this.id = countTask.incrementAndGet();
        this.name = name;
        this.state = TaskState.NEW;
    }

    private Task(Long id, String name, TaskState state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public static Task getInstance(Long id, String taskName, String taskState) {

        return new Task(id, taskName, TaskState.valueOf(taskState));
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void complete() {
        this.state = TaskState.COMPLETE;
    }

    public void delete() {
        this.state = TaskState.DELETE;
    }

    @Override
    public String toString() {
        return "" + id + ". ................... " + name;
    }
}
