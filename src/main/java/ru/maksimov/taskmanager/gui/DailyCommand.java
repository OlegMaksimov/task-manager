package ru.maksimov.taskmanager.gui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.maksimov.taskmanager.service.DailyService;

@ShellComponent
public class DailyCommand {

    private final DailyService dailyService;

    public DailyCommand(DailyService dailyService) {
        this.dailyService = dailyService;
    }

    @ShellMethod(key = "today-list", value = "The method return the list of tasks for today>.")
    public String getTodayTask() {
        return dailyService.getTodayTask();
    }


    @ShellMethod(key = "today-plan", value = "The method to do planning day.")
    public String planningDay() {
        return dailyService.planningDay();
    }

    @ShellMethod(key = "today-result", value = "The method return result of the day.")
    public String getResultDay() {
        return dailyService.getResultDay();
    }
}
