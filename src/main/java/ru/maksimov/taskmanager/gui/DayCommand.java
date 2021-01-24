package ru.maksimov.taskmanager.gui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Clear;
import ru.maksimov.taskmanager.gui.graphic.Library;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.service.TaskService;

import java.util.List;

@ShellComponent
public class DayCommand {

    private static final String TASK_FOR_TODAY = "ЗАДАЧИ НА СЕГОДНЯ:";
    private final TaskService service;
    private final Clear clear;

    public DayCommand(TaskService service, Clear clear) {
        this.service = service;
        this.clear = clear;
    }

    @ShellMethod(key = "today-list", value = "The method return the list of tasks for today>.")
    public String getTodayTask() {
        clear.clear();

        List<Task> taskList = service.getTodayTask();
        StringBuilder builder = new StringBuilder();
        builder.append(Library.getTitle(TASK_FOR_TODAY));

        for (Task task : taskList) {
            builder.append(Library.getTodayTask(task.getTime().getVal(), task.getName()));
        }

        return builder.toString();
    }
}
