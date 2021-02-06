package ru.maksimov.taskmanager.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.maksimov.taskmanager.dao.store.FileStore;
import ru.maksimov.taskmanager.dao.store.IStore;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.maksimov.taskmanager.TestUtils.getTask;

@ExtendWith(SpringExtension.class)
class TaskDaoImplTest {

    @Configuration
    static class Config {
        @Bean
        public IStore getStore() {
            return new FileStore("Test.csv", getStoreMap());
        }

        @Bean(name = "testStore")
        public Map<Long, Task> getStoreMap() {
            return new HashMap<>();
        }

        @Bean
        public TaskDAO getDao() {
            return new TaskDaoImpl(getStoreMap(), getStore());
        }
    }

    @Autowired
    private TaskDAO taskDAO;

    private Task task;

    @BeforeEach
    void setUp() {
        taskDAO.cleanStore();
        task = makeTask();
    }

    @Test
    void testCreate() throws Exception {
        Task safeTask = taskDAO.create(task);

        assertEquals(task.getId(), safeTask.getId());
    }

    @Test
    void testCreateWithEmptyName() {
        task.setName("");

        Exception e = assertThrows(Exception.class, () -> taskDAO.create(task));
        String expectedMessage = "Наименование задачи отсутсвует";

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void testCreateFailWithNull() {
        Exception e = assertThrows(Exception.class, () -> taskDAO.create(null));
        String expectedMessage = "Пустая задача";

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void testDoublePutValue() throws Exception {
        Task task1 = taskDAO.create(task);

        Exception e = assertThrows(Exception.class, () -> taskDAO.create(task));
        String expectedMessage = "Дублирование задачи";

        assertNotNull(task1);
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void testRead() throws Exception {
        taskDAO.create(task);

        Task checkTask = taskDAO.read(task.getId());

        assertNotNull(checkTask);
    }

    @Test
    void testReadFail() {
        Task checkTask = taskDAO.read(11L);

        assertNull(checkTask);
    }

    @Test
    void testUpdate() throws Exception {
        final String NEW_NAME = "NEW NAME";
        taskDAO.create(task);

        task.setName(NEW_NAME);
        Task newTask = taskDAO.update(task);

        assertAll(
                () -> assertEquals(newTask.getId(), task.getId()),
                () -> assertEquals(newTask.getName(), NEW_NAME)
        );
    }

    @Test
    void testUpdateFail() {
        Task task = makeTask();

        Task result = taskDAO.update(task);

        assertNull(result);
    }

    @Test
    void testDelete() throws Exception {
        taskDAO.create(task);

        Task result = taskDAO.delete(task.getId());

        assertEquals(TaskState.DELETE, result.getState());
    }

    @Test
    void testDeleteFail() {
        Task task = makeTask();

        Task result = taskDAO.delete(task.getId());

        assertNull(result);
    }

    @Test
    void testGetTaskList() throws Exception {
        taskDAO.create(task);

        List<Task> resultList = taskDAO.getList();

        assertEquals(1L, resultList.size());
    }

    @Test
    void testGetTaskListWithNoTask() {

        List<Task> resultList = taskDAO.getList();

        assertEquals(0L, resultList.size());
    }

    @Test
    void writeToStore() {
        taskDAO.writeToStore();
    }

    @Test
    void completeTask() throws Exception {
        taskDAO.create(task);

        Task checkTask = taskDAO.completeTask(task.getId());

        Assertions.assertEquals(TaskState.COMPLETE, checkTask.getState());
    }

    private Task makeTask() {
        return getTask();
    }
}