package ru.maksimov.taskmanager.model.dto;

import lombok.Data;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;
import ru.maksimov.taskmanager.model.enums.Time;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TaskDto {
    String name;
    String description;
    TaskState state;
    LocalDate startDate;
    Time time;
    Task parentTask;
    List<Task> subTasks = new ArrayList();
}
