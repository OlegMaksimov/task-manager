package ru.maksimov.taskmanager.gui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Main {

    @ShellMethod(key = "task", value = "The method for managing tasks")
    public String commandHandler(
        @ShellOption String comm
    ){
        return comm;
    }
}
