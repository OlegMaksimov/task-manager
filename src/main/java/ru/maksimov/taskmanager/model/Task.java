package ru.maksimov.taskmanager.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.maksimov.taskmanager.model.enums.TaskState;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
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
    LocalTime time;

    /**
     * Родительская задача. По умолчнаю null
     */
    Long parentId;

    /**
     * Признак главной задачи дня
     */
    Boolean isMainTask = Boolean.FALSE;

    /**
     * Признак повторяющейся задачи
     */
    Boolean isRepeatableTask = Boolean.FALSE;

    /**
     * Список подзадач
     */
    List<Long> subTasks = new ArrayList<>();

    public Task(String name) {
        this.id = countTask.incrementAndGet();
        this.name = name;
        this.state = TaskState.NEW;
        this.parentId = 0L;
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

    private Task(Task task) {
        this.id = countTask.incrementAndGet();
        this.name = task.getName();
        this.description = task.getDescription();
        this.state = task.getState();
        this.startDate = task.getStartDate();
        this.time = task.getTime();
        this.parentId = task.getParentId();
        this.isMainTask = task.isMainTask;
        this.isRepeatableTask = task.isRepeatableTask;
        this.subTasks = task.getSubTasks() == null ? null : new LinkedList<>(task.getSubTasks());
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
            subTasks = new LinkedList<>();
        }
        this.subTasks.add(taskId);
    }

    public static Task clone(Task task) {
        return new Task(task);
    }

    @Override
    public String toString() {
        return "" + id + ". ................... " + name;
    }
}
