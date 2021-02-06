package ru.maksimov.taskmanager.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.maksimov.taskmanager.dao.store.IStore;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Repository
public class TaskDaoImpl implements TaskDAO {
    private final Map<Long, Task> taskMap;
    private final IStore store;

    public TaskDaoImpl(@Qualifier("store") Map<Long, Task> taskMap,
                       @Qualifier("fileStore") IStore store) {
        this.taskMap = taskMap;
        this.store = store;
    }

    /**
     * Создание задачи
     *
     * @param task задача для записи в хранилище
     * @return возвращает задачу, сохраненную в хранилище
     */
    @Override
    public Task create(Task task) throws Exception {
        if (task == null) {
            throw new Exception("Пустая задача");
        } else if (taskMap.get(task.getId()) != null) {
            throw new Exception("Дублирование задачи");
        } else if (task.getName().isEmpty()) {
            throw new Exception("Наименование задачи отсутсвует");
        }

        taskMap.putIfAbsent(task.getId(), task);
        writeToStore();
        return taskMap.get(task.getId());
    }

    /**
     * Поиск зачдачи
     *
     * @param id номер задачи
     * @return возвращает задучу по заданному id
     */
    @Override
    public Task read(Long id) {
        return taskMap.get(id);
    }

    /**
     * Обновление задачи
     *
     * @param newTask задача с новыми данными
     * @return возвращает обновленную задачу
     */
    @Override
    public Task update(Task newTask) {
        Task task = taskMap.get(newTask.getId());
        if (task != null) {
            task = taskMap.put(newTask.getId(), newTask);
            writeToStore();
        }
        return task;
    }

    /**
     * Удаление задачи
     *
     * @param id id задачи
     * @return возвращает задачу которая была удалена из хранилища
     */
    @Override
    public Task delete(Long id) {
        Task task = taskMap.get(id);
        if (task == null) {
            return null;
        }
        task.delete();
        writeToStore();
        return taskMap.get(id);
    }

    /**
     * Получение списка задач
     *
     * @return возврашает список задач
     */
    @Override
    public List<Task> getList() {
        List<Task> tasks = new ArrayList<>(taskMap.size());
        for (Map.Entry<Long, Task> entry : taskMap.entrySet()) {
            tasks.add(entry.getValue());
        }
        return tasks;
    }

    /**
     * Очистка хранилища
     */
    @Override
    public void cleanStore() {
        taskMap.clear();
    }

    /**
     * Запись в хранилище
     */
    @Override
    public void writeToStore() {
        CompletableFuture.runAsync(() -> store.writeToStore());
    }

    /**
     * Инициализация хранилища
     */
    @Override
    public void initStore() {
        store.initStore();
    }

    /**
     * Переводит статус задачи в  COMPLETE
     *
     * @param id номер задачи
     * @return Возвращает измененную задачу
     * @see TaskState
     */
    @Override
    public Task completeTask(Long id) {
        Task task = taskMap.get(id);
        task.complete();
        writeToStore();
        return taskMap.get(id);
    }
}
