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
     * @param taskName наименование задачи
     * @return Возвращает созданную задачу
     * @throws Exception выбрасывает ошибку при невозможности создании
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
     * @return возвращает задачу по заданному id
     */
    @Override
    public Task read(Long id) {
        return dao.read(id);
    }

    /**
     * Обновление задачи
     *
     * @param task задача с новыми данными
     * @return возвращает обновленную задачу
     */
    @Override
    public Task update(Task task) {
        return dao.update(task);
    }

    /**
     * Удаление задачи
     *
     * @param id номер задачи
     * @return возвращает удаленную задачу
     */
    @Override
    public Task delete(Long id) {
        return dao.delete(id);
    }

    /**
     * Получение списка задач
     *
     * @return возвращает список задач
     */
    @Override
    public List<Task> getList() {
        return dao.getList();
    }

    /**
     * Инициализация хранилища задач при первом запуске
     */
    @Override
    public void initStore() {
        dao.initStore();
    }
}
