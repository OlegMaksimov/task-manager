package ru.maksimov.taskmanager.dao;

import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;

import java.util.List;

/**
 * Управление задачами
 */
public interface TaskDAO {

    /**
     * Создание задачи
     *
     * @param task задача для записи в хранилище
     * @return возвращает задачу, сохраненную в хранилище
     */
    Task create(Task task) throws Exception;

    /**
     * Поиск зачдачи
     *
     * @param id номер задачи
     * @return возвращает задучу по заданному id
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
     * @param id id задачи
     * @return возвращает задачу которая была удалена из хранилища
     */
    Task delete(Long id);

    /**
     * Получение списка задач
     *
     * @return возврашает список задач
     */
    List<Task> getList();

    /**
     * Очистка хранилища
     */
    void cleanStore();

    /**
     * Запись в хранилище
     */
    void writeToStore();

    /**
     * Инициализация хранилища
     */
    void initStore();

    /**
     * Переводит статус задачи в  COMPLETE
     *
     * @param id номер задачи
     * @return Возвращает измененную задачу
     * @see TaskState
     */
    Task completeTask(Long id);
}
