package ru.maksimov.taskmanager.dao.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.maksimov.taskmanager.model.Task;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileStoreTest {

    private IStore iStore;
    private final String FILENAME = "testFIle.csv";
    private File file;

    @Test
    void writeToStoreOneTask() {
        Task task = new Task("someTask");
        Map<Long, Task> taskMap = new HashMap();
        taskMap.put(task.getId(), task);
        iStore = new FileStore(FILENAME, taskMap);

        file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }
        Boolean result = iStore.writeToStore();

        Assertions.assertAll(
                () -> assertTrue(result),
                () -> assertTrue(file.exists())
        );
        file.delete();
    }

    @Test
    void writeToStoreMultipleTask() {
        Task task = new Task("someTask");
        Task task1 = new Task("someTask1");
        Task task2 = new Task("someTask2");
        Map<Long, Task> taskMap = new HashMap();
        taskMap.put(task.getId(), task);
        taskMap.put(task1.getId(), task1);
        taskMap.put(task2.getId(), task2);
        iStore = new FileStore(FILENAME, taskMap);

        file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }

        iStore.writeToStore();

        List<Task> taskList = iStore.readFromStore();
        assertEquals(3, taskList.size());

        file.delete();
    }

    @Test
    void readFromStore() {
        Task task = new Task("someTask");
        Map<Long, Task> taskMap = new HashMap();
        taskMap.put(task.getId(), task);
        iStore = new FileStore(FILENAME, taskMap);
        iStore.writeToStore();
        file = new File(FILENAME);
        List<Task> taskList = iStore.readFromStore();

        assertEquals(1, taskList.size());

        file.delete();
    }

    @Test
    void initStore() {
        Task task = new Task("someTask");
        Task task1 = new Task("someTask1");
        Task task2 = new Task("someTask2");
        Map<Long, Task> taskMap = new HashMap();
        taskMap.put(task.getId(), task);
        taskMap.put(task1.getId(), task1);
        taskMap.put(task2.getId(), task2);
        iStore = new FileStore(FILENAME, taskMap);

        file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }
        iStore.writeToStore();

        Assertions.assertEquals(3, iStore.initStore());
    }
}