package ru.maksimov.taskmanager.service;

import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;

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
     * Создание подзадачи
     *
     * @param taskName наименование задачи
     * @param parentId id родительской подзадачи
     * @return Возвращает созданную задачу
     * @throws Exception выбрасывает ошибку при невозможности создании
     */
    Task createSubTask(String taskName, Long parentId) throws Exception;

    /**
     * Поиск зачдачи
     *
     * @param id номер задачи
     * @return возвращает задачу по заданному id
     */
    Task read(Long id);

    /**
     * Обновление задачи
     *
     * @param task задача с новыми данными
     * @return возвращает обновленную задачу
     */
    Task update(Task task);

    /**
     * Удаление задачи
     *
     * @param id номер задачи
     * @return возвращает удаленную задачу
     */
    Task delete(Long id);

    /**
     * Получение списка задач
     *
     * @return возвращает список задач
     */
    List<Task> getList();

    /**
     * Инициализация хранилища задач при первом запуске
     */
    void initStore();

    /**
     * Переводит статус задачи в  COMPLETE
     *
     * @param id номер задачи
     * @return Возвращает измененную задачу
     * @see TaskState
     */
    Task completeTask(Long id) throws Exception;

    /**
     * Возвращает список задач на сегодня
     *
     * @return {@link Task}
     */
    List<Task> getTodayTask();

//    /**
//     * Создание подзадачи
//     *
//     * @return
//     */
//    String addSubTask();
}
