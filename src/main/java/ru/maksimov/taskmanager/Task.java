package ru.maksimov.taskmanager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Задачи и дела.
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    String name;
}
