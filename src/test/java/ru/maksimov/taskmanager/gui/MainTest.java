package ru.maksimov.taskmanager.gui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.service.TaskService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MainTest {

    @Mock
    private TaskService taskService;

    private Main main;
    private final String TASKNAME = "\"taskName\"";
    private final Task TASK = new Task(TASKNAME);

    @BeforeEach
    void setUp() {
        main = new Main(taskService);
    }

    @Test
    void testCreateTask() throws Exception {
        when(taskService.create(any())).thenReturn(TASK);

        String task = main.createTask(TASKNAME);

        Assertions.assertTrue(task.contains(TASKNAME));
    }

    @Test
    void testFindTask() {
        Long id = TASK.getId();
        when(taskService.read(id)).thenReturn(TASK);

        String task = main.findTask(id);

        Assertions.assertTrue(task.contains(TASKNAME));
    }

    @Test
    @Disabled(value = "присутсвует ручной ввод")
    void testUpdateTask() {
        Long id = TASK.getId();
        String newTaskName = "newTaskName";
        Task task = Task.getInstance(id, newTaskName, "NEW");
        when(taskService.update(any(Task.class))).thenReturn(task);

        String updateTask = main.updateTask(id);

        Assertions.assertTrue(updateTask.contains(newTaskName));
    }

    @Test
    void testDeleteTask() {
        Long id = TASK.getId();
        when(taskService.delete(id)).thenReturn(TASK);

        String deleteTask = main.deleteTask(id);

        Assertions.assertTrue(deleteTask.contains(TASKNAME));
    }

    @Test
    void testGetListTask() {
        List<Task> taskList = Collections.singletonList(TASK);
        when(taskService.getList()).thenReturn(taskList);

        String listTask = main.getListTask();

        Assertions.assertTrue(listTask.contains(TASKNAME));
    }

    @Test
    void testGetListTaskWithNoTask() {
        String listTask = main.getListTask();

        Assertions.assertEquals(listTask, "Нет задач");
    }
}