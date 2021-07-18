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
import static ru.maksimov.taskmanager.TestUtils.getTask;

class FileStoreTest {

    private IStore iStore;
    private final String FILENAME = "testFIle.csv";
    private File file;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void writeToStoreOneTask() throws Exception {
        Task task = getTask();
        Map<Long, Task> taskMap = new HashMap();
        taskMap.put(task.getId(), task);
        iStore = new FileStore(FILENAME, taskMap);

        file = new File(FILENAME);
        if (file.exists()) {
            boolean isDel = file.delete();
            if (!isDel) {
                throw new Exception("Не удалось удалить задачу");
            }
        }
        Boolean result = iStore.writeToStore();

        Assertions.assertAll(
                () -> assertTrue(result),
                () -> assertTrue(file.exists())
                            );
        boolean delete = file.delete();
        assert (delete);
    }


    @Test
    void writeToStoreMultipleTask() throws Exception {
        Task task = getTask();
        Task task1 = getTask();
        Task task2 = getTask();
        task1.setIsRepeatableTask(Boolean.FALSE);

        Map<Long, Task> taskMap = new HashMap<>();
        taskMap.put(task.getId(), task);
        taskMap.put(task1.getId(), task1);
        taskMap.put(task2.getId(), task2);
        iStore = new FileStore(FILENAME, taskMap);

        file = new File(FILENAME);
        if (file.exists()) {
            boolean isDel = file.delete();
            if (!isDel) {
                throw new Exception("Не удалось удалить задачу");
            }
        }

        iStore.writeToStore();

        List<Task> taskList = iStore.readFromStore();
        assertEquals(3, taskList.size());

        boolean delete = file.delete();
        assert (delete);
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void readFromStore() {
        Task task = getTask();
        Task task1 = getTask();
        Task task2 = getTask();
        task.addSubTask(task1.getId());
        task.addSubTask(task2.getId());
        Map<Long, Task> taskMap = new HashMap();
        taskMap.put(task.getId(), task);
        taskMap.put(task1.getId(), task1);
        taskMap.put(task2.getId(), task2);
        iStore = new FileStore(FILENAME, taskMap);
        iStore.writeToStore();
        file = new File(FILENAME);
        List<Task> taskList = iStore.readFromStore();

        assertEquals(3, taskList.size());

        boolean delete = file.delete();
        assert (delete);
    }

    @Test
    void initStore() {
        Task task = getTask();
        Task task1 = getTask();
        Task task2 = getTask();
        task.addSubTask(task2.getId());
        Map<Long, Task> taskMap = new HashMap<>();
        taskMap.put(task.getId(), task);
        taskMap.put(task1.getId(), task1);
        taskMap.put(task2.getId(), task2);
        iStore = new FileStore(FILENAME, taskMap);

        file = new File(FILENAME);
        if (file.exists()) {
            boolean delete = file.delete();
        }
        iStore.writeToStore();

        Assertions.assertEquals(3, iStore.initStore());

        boolean delete = file.delete();
        assert delete;
    }

}