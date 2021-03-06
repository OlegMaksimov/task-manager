package ru.maksimov.taskmanager.gui.graphic;

import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.dto.TaskDto;

import java.util.Objects;

public class Library {
    private static final String TODAY_TASK_TITLE = "" +
            "TIME    MAIN  ID    TITLE                                                      \n";

    private static final String TODAY_TASK = "" +
            "%s\t%s\t  %s     %s                                            \n" +
            "-----   ----  ----  ----------------------------------------------\n";

    private static final String TITLE = "\t\t\t\t %s \n" +
            "-----------------------------------------------------------------\n\n";

    private static final String TASK_VIEW = "" +
            "описание задачи:   %s\n" +
            "родительская задача:   %s\n" +
            "подзадачи:   %s\n" +
            "статус:    %s\n" +
            "дата завершения:   %s\n" +
            "начало выполнения: %s\n";


    public static String getTodayTask(Task task) {
        return String.format(TODAY_TASK,getTaskTime(task), getMainTask(task), task.getId(), task.getName());
    }
    public static String getTodayTaskTitle(){
        return TODAY_TASK_TITLE;
    }

    private static String getMainTask(Task task) {
        return Boolean.TRUE.equals(task.getIsMainTask()) ? "*" : "";
    }

    public static String getTitle(String title) {
        return String.format(TITLE, title);
    }

    public static String getTaskView(TaskDto task) {
        String description = Objects.nonNull(task.getDescription()) ? task.getDescription() : "NONE";
        String parent = Objects.nonNull(task.getParentTask()) ? task.getParentTask().toString() : "NONE";
        String state = Objects.nonNull(task.getState()) ? task.getState().toString() : "NONE";
        String date = Objects.nonNull(task.getStartDate()) ? task.getStartDate().toString() : "NONE";
        String time = Objects.nonNull(task.getTime()) ? task.getTime().toString() : "NONE";
        String subTask = "NONE";
        if (Objects.nonNull(task.getSubTasks()) && task.getSubTasks().size() > 0) {
            StringBuilder builder = new StringBuilder("\n");
            for (Task t1 : task.getSubTasks()) {
                builder.append("\t\t\t\t");
                builder.append(t1.toString());
                builder.append("\n");
            }
            subTask = builder.toString();
        }

        return String.format(TASK_VIEW, description, parent, subTask, state, date, time);
    }

    private static String getTaskTime(Task task) {
        return Objects.nonNull(task.getTime()) ? task.getTime().toString() : "NONE ";
    }
}
