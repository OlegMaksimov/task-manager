package ru.maksimov.taskmanager.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.maksimov.taskmanager.dao.TaskDAO;
import ru.maksimov.taskmanager.model.Task;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDAO dao;

    public TaskServiceImpl(@Qualifier("taskDaoImpl") TaskDAO dao) {
        this.dao = dao;
    }

    /**
     * Создание задачи
     *
     * @param taskName
     * @return
     */
    @Override
    public Task create(String taskName) throws Exception {
        Task task = new Task(taskName);
        return dao.create(task);
    }

    /**
     * Поиск зачдачи
     *
     * @param id номер задачи
     * @return
     */
    @Override
    public Task read(Long id) {
        return dao.read(id);
    }

    /**
     * Обновление задачи
     *
     * @param task
     * @return
     */
    @Override
    public Task update(Task task) {
        return dao.update(task);
    }

    /**
     * Удаление задачи
     *
     * @param id
     * @return
     */
    @Override
    public Task delete(Long id) {
        return dao.delete(id);
    }

    /**
     * Получение списка задач
     *
     * @return
     */
    @Override
    public List<Task> getList() {
        return dao.getList();
    }
}
