package ru.maksimov.taskmanager.dao;

import ru.maksimov.taskmanager.model.Task;

import java.util.List;

/**
 * Управление задачами
 */
public interface TaskDAO {

    /**
     * Создание задачи
     *
     * @param task
     * @return
     */
    Task create(Task task) throws Exception;

    /**
     * Поиск зачдачи
     *
     * @param id номер задачи
     * @return
     */
    Task read(Long id);

    /**
     * Обновление задачи
     *
     * @param task
     * @return
     */
    Task update(Task task);

    /**
     * Удаление задачи
     *
     * @param id
     * @return
     */
    Task delete(Long id);

    /**
     * Получение списка задач
     *
     * @return
     */
    List<Task> getList();

    void cleanStore();
}
