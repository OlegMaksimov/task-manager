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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Файловое хранилище данных
 */
@Repository
public class FileStore implements IStore {
    private final String fileName;

    private final Map<Long, Task> taskMap;

    private final String[] HEADERS = {"ID", "NAME", "STATE"};

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
        } catch (IOException e) {
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
                Task task = Task.getInstance(taskWrapper.getId(), taskWrapper.getName(), taskWrapper.getState());
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
    public int initStore() {
        File file = new File(fileName);
        if (!file.exists()) {
            return 0;
        }
        List<Task> taskList = readFromStore();
        if (taskList != null && taskList.size() > 0) {
            for (Task task : taskList) {
                taskMap.put(task.getId(), task);
            }
        }
        return taskMap.size();
    }

    private CellProcessor[] getCellProcessor() {
        return new CellProcessor[]{
                new ParseLong(), //  task id
                new NotNull(), // task name
                new NotNull() //  task state
        };
    }
}
