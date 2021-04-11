package ru.maksimov.taskmanager.dao.store;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;
import ru.maksimov.taskmanager.model.enums.Time;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Файловое хранилище данных
 */
@Repository
public class FileStore implements IStore {
    private final String fileName;

    private final Map<Long, Task> taskMap;

    private final String[] HEADERS = {"ID", "NAME", "STATE", "PARENTID", "DESCRIPTION", "STARTDATE", "TIME", "SUBTASKS"};

    public FileStore(@Value("${store.filename}") String fileName, @Qualifier("store") Map<Long, Task> taskMap) {
        this.fileName = fileName;
        this.taskMap = taskMap;
    }

    /**
     * Записывает задачу в хранилище
     *
     * @return true  если успешно
     */
    @Override
    public Boolean writeToStore() {
        try (
                ICsvBeanWriter writer = new CsvBeanWriter(new FileWriter(fileName),
                        CsvPreference.STANDARD_PREFERENCE)
        ) {
            for (Map.Entry<Long, Task> entry : taskMap.entrySet()) {
                Task task = entry.getValue();
                writer.write(task, HEADERS, getCellProcessor());
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * Чтение записаных задач из хранилища
     *
     * @return возвращает список задач из хранилища
     */
    @Override
    public List<Task> readFromStore() {
        try (
                ICsvBeanReader reader = new CsvBeanReader(new FileReader(fileName),
                        CsvPreference.STANDARD_PREFERENCE)
        ) {
            List<Task> taskList = new ArrayList<>();
            TaskWrapper taskWrapper;
            while ((taskWrapper = reader.read(TaskWrapper.class, HEADERS, getCellProcessor())) != null) {
                Task task = Task.builder()
                        .id(taskWrapper.getId())
                        .name(taskWrapper.getName())
                        .state(TaskState.valueOf(taskWrapper.getState()))
                        .parentId(taskWrapper.getParentId())
                        .description(taskWrapper.getDescription())
                        .build();
                if (Objects.nonNull(taskWrapper.getStartDate())) {
                    task.setStartDate(LocalDate.parse(taskWrapper.getStartDate()));
                }
                if (Objects.nonNull(taskWrapper.getTime()) && !taskWrapper.getStartDate().isEmpty()) {
                    task.setTime(Time.valueOf(taskWrapper.getTime()));
                }
                if (Objects.nonNull(taskWrapper.getSubTasks()) && !Objects.equals("[]", taskWrapper.getSubTasks())) {
                    String[] split = taskWrapper.getSubTasks().replaceAll("\\[", "")
                            .replaceAll("\\]", "")
                            .replaceAll(" ", "")
                            .split(",");
                    for (String s : split) {
                        task.addSubTask(Long.valueOf(s.trim()));
                    }
                }
                taskList.add(task);
            }
            return taskList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Инициализация хранилища
     *
     * @return возвращает кол-во записей в хранилище
     */
    @PostConstruct
    public int initStore() {
        File file = new File(fileName);
        if (!file.exists()) {
            return 0;
        }
        List<Task> taskList = readFromStore();
        if (taskList != null && taskList.size() > 0) {
            Long maxCountTask = 0L;
            for (Task task : taskList) {
                if (maxCountTask < task.getId()) {
                    maxCountTask = task.getId();
                }
                taskMap.put(task.getId(), task);
            }
            Task.countTask.set(maxCountTask);
        }
        return taskMap.size();
    }

    private CellProcessor[] getCellProcessor() {
        return new CellProcessor[]{
                new ParseLong(),    // id
                new NotNull(),      // name
                new NotNull(),      // state
                new ParseLong(),       // parentId
                new org.supercsv.cellprocessor.Optional(), // description
                new org.supercsv.cellprocessor.Optional(),// date
                new org.supercsv.cellprocessor.Optional(), //time
                new org.supercsv.cellprocessor.Optional() //subTasks
        };
    }
}
