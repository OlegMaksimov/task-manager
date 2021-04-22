package ru.maksimov.taskmanager.gui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Clear;
import ru.maksimov.taskmanager.gui.graphic.DaylyLibrary;
import ru.maksimov.taskmanager.gui.graphic.Library;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;
import ru.maksimov.taskmanager.model.enums.Time;
import ru.maksimov.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static ru.maksimov.taskmanager.gui.TaskCommand.*;

@ShellComponent
public class DailyCommand {

    private static final String TASK_FOR_TODAY = "ЗАДАЧИ НА СЕГОДНЯ:";
    private final TaskService service;
    private final Clear clear;
    private final TaskCommand taskCommand;

    public DailyCommand(TaskService service, Clear clear, TaskCommand taskCommand) {
        this.service = service;
        this.clear = clear;
        this.taskCommand = taskCommand;
    }

    @ShellMethod(key = "today-list", value = "The method return the list of tasks for today>.")
    public String getTodayTask() {
        clear.clear();

        List<Task> taskList = service.getTodayTask().stream()
                .filter(task -> task.getState().equals(TaskState.NEW))
                .collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        builder.append(Library.getTitle(TASK_FOR_TODAY));
        builder.append(Library.getTodayTaskTitle());

        for (Task task : taskList) {
            builder.append(Library.getTodayTask(task));
        }

        return builder.toString();
    }



    @ShellMethod(key = "today-plan", value = "The method to do planning day.")
    public String planningDay() {
        Scanner scanner = new Scanner(System.in);
        clear.clear();

        System.out.println(taskCommand.getListTask());
        while (true) {
            System.out.println("Укажите номер задачи или нажмите enter для создания новой задачи:");
            String taskId = scanner.nextLine();
            Task task;
            if (taskId.isEmpty()) {
                System.out.println("Введите наименование задачи: ");
                String taskname  = scanner.nextLine();
                try {
                    task = service.create(taskname);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    task = service.read(Long.parseLong(taskId));
                } catch (NumberFormatException e) {
                   throw new RuntimeException(e);
                }
                if (Objects.isNull(task)) {
                    throw new RuntimeException(TASK_NOT_FOUND);
                }
            }

            task.setStartDate(LocalDate.now());

            System.out.println("Это главная задача дня: (y|enter)");
            String isMainTask = scanner.nextLine();

            task.setIsMainTask("y".equalsIgnoreCase(isMainTask));

            System.out.println(NEW_TIME);
            String taskTime = scanner.nextLine();
            if (!taskTime.isEmpty()) {
                try {
                    task.setTime(Time.getByVal(taskTime));
                } catch (IllegalArgumentException e) {
                    System.out.println(INCORECT_VALUE);
                }
            }
            service.update(task);
            System.out.println("Задача добавлена.\n Добавить новую задачу? (y|enter)");

            String isContinue = scanner.nextLine();
            if (!"y".equalsIgnoreCase(isContinue)) {
                break;
            }
        }

        return getTodayTask();
    }

    @ShellMethod(key = "today-result", value = "The method return result of the day.")
    public String getResultDay() {
        clear.clear();
        List<Task> taskList = service.getTodayTask();
        return DaylyLibrary.getDayResultTemplate(taskList);
    }
}
