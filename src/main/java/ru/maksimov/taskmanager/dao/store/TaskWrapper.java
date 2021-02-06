package ru.maksimov.taskmanager.dao.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Класс обертка для работы с хранилищем
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskWrapper {
    Long id;
    String name;
    String state;
    long parentId;
    String description;
    String startDate;
    String time;
}
