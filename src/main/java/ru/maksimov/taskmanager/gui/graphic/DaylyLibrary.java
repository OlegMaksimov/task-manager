package ru.maksimov.taskmanager.gui.graphic;

import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;

import java.util.List;
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
        builder.append(String.format(TODAY_TASK_RESULT, getCompletedTask(todayTask), getUnCompletedTask(todayTask),
                getMainTask(todayTask)));
        return builder.toString();
    }

    private static Object getMainTask(List<Task> todayTask) {
        StringJoiner joiner = new StringJoiner("\n");
        todayTask.stream()
                .filter(Task::getIsMainTask)
                .forEach(e -> joiner.add(e.toString() + "\t" + e.getState()));
        return joiner.toString();
    }

    private static Object getUnCompletedTask(List<Task> todayTask) {
        StringJoiner joiner = new StringJoiner("\n");
        todayTask.stream()
                .filter(task -> TaskState.NEW.equals(task.getState()))
                .forEach(e -> joiner.add(e.toString()));
        return joiner.toString();
    }

    private static String getCompletedTask(List<Task> todayTask) {
        StringJoiner joiner = new StringJoiner("\n");
        todayTask.stream()
                .filter(task -> TaskState.COMPLETE.equals(task.getState()))
                .forEach(e -> joiner.add(e.toString()));
        return joiner.toString();
    }
}
