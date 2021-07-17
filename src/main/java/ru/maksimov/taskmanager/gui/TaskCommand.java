package ru.maksimov.taskmanager.gui;

import org.springframework.beans.BeanUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Clear;
import ru.maksimov.taskmanager.gui.graphic.Library;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.dto.TaskDto;
import ru.maksimov.taskmanager.model.enums.TaskState;
import ru.maksimov.taskmanager.model.enums.Time;
import ru.maksimov.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

@ShellComponent
public class TaskCommand {

    public static final String INCORECT_VALUE = "Не верное значение! Попробуйте еще раз или нажмите enter";
    private static final String TASK_FOR_TODAY = "СПИСОК ЗАДАЧ:";
    private static final String NONE_TASK = "НЕТ ЗАДАЧ";
    public static final String TASK_NOT_FOUND = "Задача не найдена!";
    public static final String NEW_NAME = "Введите новое имя задачи или нажмите Enter";
    public static final String NEW_DESCRIPTION = "Введите новое описание задачи или нажмите Enter";
    public static final String NEW_DATE = "Введите новую дату выполнения задачи(2000-12-01) или нажмите Enter";
    public static final String NEW_TIME = "Введите новое время выполнения задачи(09.00, 14.00) или нажмите Enter";
    private final TaskService service;
    private Scanner scanner;
    private final Clear clear;


    /**
     * Проверка инициализаци хранилища при первом запуске
     */
    private static Boolean isInitStore = Boolean.FALSE; // TODO: 20.09.2020 исследовать возможность инициализации через Spring

    public TaskCommand(TaskService service, Clear clear) {
        this.service = service;
        this.clear = clear;
    }

    @ShellMethod(key = "task-create", value = "The method for create tasks. Example: task-create \" <task_name> \"")
    public String createTask(
            @ShellOption String name
    ) {
        clear.clear();

        // TODO: 08.09.2020 Проблема с кавычками https://github.com/OlegMaksimov/task-manager/issues/3
//        if (!checkTaskName(name)) {
//            System.out.println("Имя задачи должно начинаться с кавычек. Смотри help task-create. ");
//            return null;
//        }

        Task task;
        try {
            task = service.create(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return getListTask();
    }

    @ShellMethod(key = "task-add-sb", value = "The method for create subTasks. " +
            "Example: task-addSubTask \" <parent_task_id> \"")
    public String addSubTask(
            @ShellOption Long id
    ) {
//        clear.clear();

        Task parentTask = service.read(id);
        if (Objects.isNull(parentTask)) {
            return TASK_NOT_FOUND;
        }
        System.out.println("Введите новое имя подзадачи");
        scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        try {
            Task task = service.createSubTask(name, id);
            parentTask.addSubTask(task.getId());
            service.update(parentTask);
            return task.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @ShellMethod(key = "task-find", value = "The method for find task by Id. Example: task-find <task_id>")
    public String findTask(
            @ShellOption Long id
    ) {
        clear.clear();

        Task task = service.read(id);
        TaskDto taskDto = makeDto(task);

        StringBuilder builder = new StringBuilder();
        builder.append(Library.getTitle(task.getName()));
        builder.append(Library.getTaskView(taskDto));

        return builder.toString();
    }

    private TaskDto makeDto(Task task) {
        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(task, taskDto);
        if (task.getParentId() > 0) {
            taskDto.setParentTask(service.read(task.getParentId()));
        }
        if (Objects.nonNull(task.getSubTasks())) {
            List<Task> subTasks = task.getSubTasks()
                    .stream()
                    .map(service::read)
                    .collect(Collectors.toList());
            taskDto.setSubTasks(subTasks);
        }
        return taskDto;
    }

    @ShellMethod(key = "task-update", value = "The method for update task. Example: task-update <task_id> ")
    public String updateTask(
            @ShellOption Long id
    ) {
        clear.clear();

        Task task = service.read(id);
        if (Objects.isNull(task)) {
            return TASK_NOT_FOUND;
        }
        scanner = new Scanner(System.in);
        System.out.println(findTask(id));

        System.out.println(NEW_NAME);
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            task.setName(newName);
        }

        System.out.println(NEW_DESCRIPTION);
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            task.setDescription(description);
        }

        System.out.println(NEW_DATE);
        boolean isContinue = true;
        while (isContinue) {
            String date = scanner.nextLine();
            if (!date.isEmpty()) {
                try {
                    task.setStartDate(LocalDate.parse(date));
                } catch (Exception e) {
                    System.out.println(INCORECT_VALUE);
                    continue;
                }
            }
            isContinue = false;
        }

        System.out.println(NEW_TIME);
        isContinue = true;
        while (isContinue) {
            String time = scanner.nextLine();
            if (!time.isEmpty()) {
                try {
                    task.setTime(Time.getByVal(time));
                } catch (IllegalArgumentException e) {
                    System.out.println(INCORECT_VALUE);
                    continue;
                }
            }
            isContinue = false;
        }

        service.update(task);
        return findTask(id);
    }

    @ShellMethod(key = "task-delete", value = "The method for delete task. Example: task-delete <task_id>")
    public String deleteTask(
            @ShellOption Long id
    ) {
        clear.clear();

        Task task = service.delete(id);
        return task.toString();
    }

    @ShellMethod(key = "task-complete", value = "The method for complete task. Example: task-complete <task_id>")
    public String CompleteTask(
            @ShellOption Long id
    ) {
        clear.clear();

        service.completeTask(id);
        return getListTask();
    }

    @ShellMethod(key = "task-list", value = "The method for output task lists. Example: task-list ")
    public String getListTask(
    ) {
        clear.clear();

        //        Проверка инициализации хранилища
        if (!isInitStore) {
            isInitStore = initStore();
        }

        List<Task> taskList = service.getList();
        String result = null;
        if (taskList != null && taskList.size() > 0) {
            result = taskList.stream()
                    .filter(e -> e.getState().equals(TaskState.NEW))
                    .sorted(Comparator.comparing(Task::getId))
                    .map(Task::toString)
                    .map(name -> name.concat("\n"))
                    .reduce("", String::concat);
        }

        StringBuilder builder = new StringBuilder();
        if (Objects.nonNull(result)) {
            builder.append(Library.getTitle(TASK_FOR_TODAY));
            builder.append(result);
        } else {
            builder.append(Library.getTitle(NONE_TASK));
        }

        return builder.toString();
    }

    private Boolean initStore() {
        try {
            service.initStore();
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
