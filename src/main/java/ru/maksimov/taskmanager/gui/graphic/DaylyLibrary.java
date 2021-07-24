package ru.maksimov.taskmanager.gui.graphic;

import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Библиотека для графического отображения операций по текущему дню
 */
public class DaylyLibrary {
    private static final String TODAY_TASK_RESULT = "" +
            "Выполненные задачи: \n%s \n" +
            "Оставшиеся задачи: \n%s \n" +
            "Главные задачи дня: \n%s \n";

    /**
     * Получить информацию о результатах текущего дня
     *
     * @return Возвращает шаблон о результатах дня
     */
    public static String getDayResultTemplate(List<Task> todayTask) {
        StringBuilder builder = new StringBuilder();
        builder.append(Library.getTitle("РЕЗУЛЬТАТЫ ДНЯ"));
        if (Objects.nonNull(todayTask)) {
            builder.append(String.format(TODAY_TASK_RESULT, getCompletedTask(todayTask), getUnCompletedTask(todayTask),
                    getMainTask(todayTask)));
        }
        return builder.toString();
    }

    private static Object getMainTask(@NotNull List<Task> todayTask) {
        StringJoiner joiner = new StringJoiner("\n");
        todayTask.stream()
                .filter(Task::getIsMainTask)
                .filter(Objects::nonNull)
                .forEach(e -> joiner.add(e.toString() + "\t" + e.getState()));
        return joiner.toString();
    }

    private static Object getUnCompletedTask(@NotNull List<Task> todayTask) {
        StringJoiner joiner = new StringJoiner("\n");
        todayTask.stream()
                .filter(task -> TaskState.NEW.equals(task.getState()))
                .filter(Objects::nonNull)
                .forEach(e -> joiner.add(e.toString()));
        return joiner.toString();
    }

    private static String getCompletedTask(@NotNull List<Task> todayTask) {
        StringJoiner joiner = new StringJoiner("\n");
        todayTask.stream()
                .filter(task -> TaskState.COMPLETE.equals(task.getState()))
                .filter(Objects::nonNull)
                .forEach(e -> joiner.add(e.toString()));
        return joiner.toString();
    }
}
