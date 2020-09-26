package ru.maksimov.taskmanager.dao.store;

import ru.maksimov.taskmanager.model.Task;

import java.util.List;

/**
 * Интерфейс, описывающий работу с хранилищем
 */
public interface IStore {

    /**
     * Записать данные в хранилище.
     * Проверяет наличие файла хранилища и если есть
     * удаляет файл и создает новый
     *
     * @return true если успешно
     */
    Boolean writeToStore();

    /**
     * Чтение записаных задач из хранилища
     *
     * @return возвращает список задач из хранилища
     */
    List<Task> readFromStore();

    /**
     * Инициализация хранилища.
     * Проверяет наличие файла и если есть восстанавливает
     * ранее записанные задачи.
     *
     * @return возвращает кол-во записей в хранилище
     */
    int initStore();
}
