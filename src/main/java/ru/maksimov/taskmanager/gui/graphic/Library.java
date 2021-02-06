package ru.maksimov.taskmanager.gui.graphic;

import ru.maksimov.taskmanager.model.Task;

import java.util.Objects;

public class Library {
    private static final String TODAY_TASK = "" +
            "%s  %s                                                  \n" +
            "-----  ------------------------------------------------------\n";

    private static final String TITLE = "\t\t\t\t %s \n" +
            "-------------------------------------------------------------\n\n";

    private static final String TASK_VIEW = "" +
            "описание задачи:   %s\n" +
            "родительская задача:   %s\n" +
            "статус:    %s\n" +
            "дата завершения:   %s\n" +
            "начало выполнения: %s\n";


    public static String getTodayTask(String time, String taskName) {
        return String.format(TODAY_TASK, time, taskName);
    }

    public static String getTitle(String title) {
        return String.format(TITLE, title);
    }

    public static String getTaskView(Task task) {
        String description = Objects.nonNull(task.getDescription()) ? task.getDescription() : "NONE";
        String parent = Objects.nonNull(task.getParentId()) ? task.getParentId().toString() : "NONE";
        String state = Objects.nonNull(task.getState()) ? task.getState().toString() : "NONE";
        String date = Objects.nonNull(task.getStartDate()) ? task.getStartDate().toString() : "NONE";
        String time = Objects.nonNull(task.getTime()) ? task.getTime().getVal() : "NONE";

        return String.format(TASK_VIEW, description, parent, state, date, time);
    }
}
