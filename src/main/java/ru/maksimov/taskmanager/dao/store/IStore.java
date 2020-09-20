package ru.maksimov.taskmanager.dao.store;

import ru.maksimov.taskmanager.model.Task;

import java.util.List;

/**
 * Интерфейс, описывающий работу с хранилищем
 */
public interface IStore {
    /**
     * Записывает задачу в хранилище
     *
     * @param task задача
     * @return true  если успешно
     */
    Boolean writeToStore(Task task);


    /**
     * Чтение записаных задач из хранилища
     *
     * @return возвращает список задач из хранилища
     */
    List<Task> readFromStore();

    /**
     * Инициализация хранилища
     *
     * @return возвращает кол-во записей в хранилище
     */
    int initStore();
}
