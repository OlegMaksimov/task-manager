package ru.maksimov.taskmanager.service;

import ru.maksimov.taskmanager.model.Task;

import java.util.List;

/**
 * Сервис для управлением задач
 */
public interface TaskService {
    /**
     * Создание задачи
     *
     * @param taskName наименование задачи
     * @return Возвращает созданную задачу
     * @throws Exception выбрасывает ошибку при невозможности создании
     */
    Task create(String taskName) throws Exception;

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
}
