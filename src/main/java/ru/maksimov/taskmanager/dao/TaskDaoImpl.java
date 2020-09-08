package ru.maksimov.taskmanager.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.maksimov.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TaskDaoImpl implements TaskDAO {
    private Map<Long, Task> taskMap;

    public TaskDaoImpl(@Qualifier("store") Map<Long, Task> taskMap) {
        this.taskMap = taskMap;
    }

    /**
     * Создание задачи
     *
     * @param task
     * @return
     */
    @Override
    public Task create(Task task) throws Exception {
        if (task == null) {
            throw new Exception("Пустой задача");
        } else if (taskMap.get(task.getId()) != null) {
            throw new Exception("Дублирование задачи");
        } else if (task.getName().isEmpty()) {
            throw new Exception("Наименование задачи отсутсвует");
        }


        taskMap.putIfAbsent(task.getId(), task);
        return taskMap.get(task.getId());
    }

    /**
     * Поиск зачдачи
     *
     * @param id номер задачи
     * @return
     */
    @Override
    public Task read(Long id) {
        return taskMap.get(id);
    }

    /**
     * Обновление задачи
     *
     * @param task
     * @return
     */
    @Override
    public Task update(Task task) {
        return taskMap.put(task.getId(), task);
    }

    /**
     * Удаление задачи
     *
     * @param id
     * @return
     */
    @Override
    public Task delete(Long id) {
        Task task = taskMap.get(id);

        if (task == null) {
            return null;
        }

        return taskMap.remove(task.getId());
    }

    /**
     * Получение списка задач
     *
     * @return
     */
    @Override
    public List<Task> getList() {
        List<Task> tasks = new ArrayList<>(taskMap.size());
        for (Map.Entry<Long, Task> entry : taskMap.entrySet()) {
            tasks.add(entry.getValue());
        }
        return tasks;
    }

    @Override
    public void cleanStore() {
        taskMap.clear();
    }
}
