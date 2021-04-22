package ru.maksimov.taskmanager.gui.graphic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class DaylyLibraryTest {

    static List<Task> taskList = new ArrayList<>();

    @BeforeAll
    static void init() {
        Task task1 = Task.builder()
                .id(1L)
                .name("test1")
                .startDate(LocalDate.now())
                .state(TaskState.COMPLETE)
                .isMainTask(Boolean.FALSE)
                .build();
        taskList.add(task1);
        Task task2 = Task.builder()
                .id(2L)
                .name("test2")
                .startDate(LocalDate.now())
                .state(TaskState.COMPLETE)
                .isMainTask(Boolean.TRUE)
                .build();
        taskList.add(task2);
        Task task3 = Task.builder()
                .id(3L)
                .name("test3")
                .startDate(LocalDate.now())
                .state(TaskState.NEW)
                .isMainTask(Boolean.FALSE)
                .build();
        taskList.add(task3);
        Task task4 = Task.builder()
                .id(4L)
                .name("test4")
                .startDate(LocalDate.now())
                .state(TaskState.NEW)
                .isMainTask(Boolean.TRUE)
                .build();
        taskList.add(task4);
    }

    @Test
    void getDayResultTemplateNotNullTest() {
        Assertions.assertNotNull(DaylyLibrary.getDayResultTemplate(taskList));
    }

    @Test
    void getDayResultTemplateHaveCompletedTaskTest() {
        System.out.println(DaylyLibrary.getDayResultTemplate(taskList));
    }

}