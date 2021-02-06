package ru.maksimov.taskmanager;

import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;
import ru.maksimov.taskmanager.model.enums.Time;

import java.time.LocalDate;
import java.util.Random;

public class TestUtils {

    public static String getAlphabeticString() {
        return getAlphabeticString(10);
    }

    public static String getAlphabeticString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String getNumeric() {
        return getNumeric(10);
    }

    public static String getNumeric(int targetStringLength) {
        int leftLimit = 48; // letter 'a'
        int rightLimit = 57; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static Task getTask() {
        return Task.builder()
                .id(Long.valueOf(getNumeric(1)))
                .name(getAlphabeticString())
                .state(TaskState.NEW)
                .parentId(Long.valueOf(getNumeric(1)))
                .description(getAlphabeticString())
                .startDate(LocalDate.now())
                .time(Time.NINE)
                .build();
    }
}
