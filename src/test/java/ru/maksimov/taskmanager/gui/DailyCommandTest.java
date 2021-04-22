package ru.maksimov.taskmanager.gui;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.shell.standard.commands.Clear;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;
import ru.maksimov.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class DailyCommandTest {

    static List<Task> taskList = new ArrayList<>();
    private TaskService taskService = Mockito.mock(TaskService.class);
    private Clear clear = Mockito.mock(Clear.class);
    private TaskCommand taskCommand = Mockito.mock(TaskCommand.class);

    private DailyCommand dailyCommand = new DailyCommand(taskService, clear, taskCommand);

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
    @DisplayName("список задач на сегодня")
    void getTodayTaskReturnStringTest() {
        when(taskService.getTodayTask()).thenReturn(taskList);

        String result = dailyCommand.getTodayTask();
        System.out.println(result);

        Assertions.assertAll(
                () -> assertFalse(Strings.isEmpty(result)),
                () -> assertTrue(result.contains("ЗАДАЧИ НА СЕГОДНЯ:"))
        );
    }

    @Test
    @DisplayName("планирование задач на сегодня")
    void planningDay() {
    }

    @Test
    @DisplayName("результаты дня")
    void getDayResultContainsTitleTemplateTest() {
        when(taskService.getTodayTask()).thenReturn(taskList);
        doNothing().when(clear).clear();

        String dayResultTemplate = dailyCommand.getResultDay();
        System.out.println(dayResultTemplate);

        assertTrue(dayResultTemplate.contains("РЕЗУЛЬТАТЫ ДНЯ"));
    }
}