package ru.maksimov.taskmanager.gui.graphic;

import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.dto.TaskDto;

import java.util.Objects;

public class Library {
    private static final String TODAY_TASK_TITLE = "" +
            "TIME   ID   TITLE                                           MAIN\n";

    private static final String TODAY_TASK = "" +
            "%s  %s    %s                                           %s \n" +
            "-----  ---  ----------------------------------------------------\n";

    private static final String TITLE = "\t\t\t\t %s \n" +
            "-------------------------------------------------------------\n\n";

    private static final String TASK_VIEW = "" +
            "описание задачи:   %s\n" +
            "родительская задача:   %s\n" +
            "подзадачи:   %s\n" +
            "статус:    %s\n" +
            "дата завершения:   %s\n" +
            "начало выполнения: %s\n";


    public static String getTodayTask(Task task) {
        return String.format(TODAY_TASK, getTaskTime(task), task.getId(), task.getName(), getMainTask(task));
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
        String time = Objects.nonNull(task.getTime()) ? task.getTime().getVal() : "NONE";
        String subTask = "NONE";
        if (Objects.nonNull(task.getSubTasks()) && task.getSubTasks().size() > 0) {
            StringBuilder builder = new StringBuilder("\n");
            for (Task t1 : task.getSubTasks()) {
                builder.append("\t\t\t\t" + t1.toString() + "\n");
            }
            subTask = builder.toString();
        }

        return String.format(TASK_VIEW, description, parent, subTask, state, date, time);
    }

    private static String getTaskTime(Task task) {
        return Objects.nonNull(task.getTime()) ? task.getTime().getVal() : "NONE ";
    }
}
