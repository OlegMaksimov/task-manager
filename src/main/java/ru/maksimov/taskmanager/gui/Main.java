package ru.maksimov.taskmanager.gui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;
import ru.maksimov.taskmanager.service.TaskService;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@ShellComponent
public class Main {

    private final TaskService service;

    private Scanner scanner;

    /**
     * Проверка инициализаци хранилища при первом запуске
     */
    private static Boolean isInitStore = Boolean.FALSE; // TODO: 20.09.2020 исследовать возможность инициализации через Spring

    public Main(TaskService service) {
        this.service = service;
    }

    @ShellMethod(key = "task-create", value = "The method for create tasks. Example: task-create \" <task_name> \"")
    public String createTask(
            @ShellOption String name
    ) {
        // TODO: 08.09.2020 Проблема с кавычками https://github.com/OlegMaksimov/task-manager/issues/3
//        if (!checkTaskName(name)) {
//            System.out.println("Имя задачи должно начинаться с кавычек. Смотри help task-create. ");
//            return null;
//        }

//        Проверка инициализации хранилища
        if (!isInitStore) {
            isInitStore = initStore();
        }

        Task task;
        try {
            task = service.create(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return task.toString();
    }

    @ShellMethod(key = "task-find", value = "The method for find task by Id. Example: task-find <task_id>")
    public String findTask(
            @ShellOption Long id
    ) {
        //        Проверка инициализации хранилища
        if (!isInitStore) {
            isInitStore = initStore();
        }

        Task task = service.read(id);
        return task.toString();
    }

    @ShellMethod(key = "task-update", value = "The method for update task. Example: task-update <task_id> ")
    public String updateTask(
            @ShellOption Long id
    ) {
        scanner = new Scanner(System.in);
        Task task = service.read(id);
        System.out.println(task);

        System.out.println("Введите новое имя задачи");
        String newName = scanner.nextLine();

        task.setName(newName);
        service.update(task);
        return task.toString();
    }

    @ShellMethod(key = "task-delete", value = "The method for delete task. Example: task-delete <task_id>")
    public String deleteTask(
            @ShellOption Long id
    ) {
        Task task = service.delete(id);
        return task.toString();
    }

    @ShellMethod(key = "task-complete", value = "The method for complete task. Example: task-complete <task_id>")
    public String CompleteTask(
            @ShellOption Long id
    ) {
        Task task = service.completeTask(id);
        return task.toString();
    }

    @ShellMethod(key = "task-list", value = "The method for output task lists. Example: task-list ")
    public String getListTask(
    ) {
        String TITLE = "\t\t СПИСОК ЗАДАЧ \n";

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
        return result != null ? TITLE + result : "Нет задач";
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
