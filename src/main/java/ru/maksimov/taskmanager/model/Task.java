package ru.maksimov.taskmanager.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.maksimov.taskmanager.model.enums.TaskState;
import ru.maksimov.taskmanager.model.enums.Time;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Задачи и дела.
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class Task {

    /**
     * Общее количество задач. На основании этого поля генерируется id
     */
    public static AtomicLong countTask = new AtomicLong();

    Long id;

    /**
     * Наименование задачи
     */
    String name;

    /**
     * описание задачи
     */
    String description;

    /**
     * Статус задачи
     */
    TaskState state;

    /**
     * Дата начало
     */
    LocalDate startDate;

    /**
     * Время начало задачи
     */
    Time time;

    /**
     * Родительская задача. По умолчнаю null
     */
    Long parentId;

    /**
     * Признак главной задачи дня
     */
    Boolean isMainTask;

    /**
     * Список подзадач
     */
    List<Long> subTasks = new ArrayList();

    public Task(String name) {
        this.id = countTask.incrementAndGet();
        this.name = name;
        this.state = TaskState.NEW;
        this.parentId = Long.valueOf(0);
    }

    public Task(String name, Long parentId) {
        this.id = countTask.incrementAndGet();
        this.name = name;
        this.state = TaskState.NEW;
        this.parentId = parentId;
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

    public void addSubTask(Long taskId) {
        if (Objects.isNull(this.subTasks)) {
            subTasks = new ArrayList<>();
        }
        this.subTasks.add(taskId);
    }

    @Override
    public String toString() {
        return "" + id + ". ................... " + name;
    }
}
