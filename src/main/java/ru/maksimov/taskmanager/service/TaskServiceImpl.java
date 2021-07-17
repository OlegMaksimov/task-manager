package ru.maksimov.taskmanager.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.maksimov.taskmanager.dao.TaskDAO;
import ru.maksimov.taskmanager.model.Task;
import ru.maksimov.taskmanager.model.enums.TaskState;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDAO dao;

    public TaskServiceImpl(@Qualifier("taskDaoImpl") TaskDAO dao) {
        this.dao = dao;
    }

    /**
     * Создание задачи
     *
     * @param taskName наименование задачи
     * @return Возвращает созданную задачу
     * @throws Exception выбрасывает ошибку при невозможности создании
     */
    @Override
    public Task create(String taskName) throws Exception {
        Task task = new Task(taskName);
        return dao.create(task);
    }

    /**
     * Создание подзадачи
     *
     * @param taskName наименование задачи
     * @param parentId id родительской подзадачи
     * @return Возвращает созданную задачу
     * @throws Exception выбрасывает ошибку при невозможности создании
     */
    @Override
    public Task createSubTask(String taskName, Long parentId) throws Exception {
        Task task = new Task(taskName, parentId);
        return dao.create(task);
    }

    /**
     * Поиск зачдачи
     *
     * @param id номер задачи
     * @return возвращает задачу по заданному id
     */
    @Override
    public Task read(Long id) {
        return dao.read(id);
    }

    /**
     * Обновление задачи
     *
     * @param task задача с новыми данными
     * @return возвращает обновленную задачу
     */
    @Override
    public Task update(Task task) {
        return dao.update(task);
    }

    /**
     * Удаление задачи
     *
     * @param id номер задачи
     * @return возвращает удаленную задачу
     */
    @Override
    public Task delete(Long id) {
        return dao.delete(id);
    }

    /**
     * Получение списка задач
     *
     * @return возвращает список задач
     */
    @Override
    public List<Task> getList() {
        return dao.getList();
    }

    /**
     * Инициализация хранилища задач при первом запуске
     */
    @Override
    public void initStore() {
        dao.initStore();
    }

    /**
     * Переводит статус задачи в  COMPLETE
     *
     * @param id номер задачи
     * @return Возвращает измененную задачу
     * @see TaskState
     */
    @Override
    public Task completeTask(Long id) {
        return dao.completeTask(id);
    }

    /**
     * Возвращает список задач на сегодня
     *
     * @return {@link Task}
     */
    @Override
    public List<Task> getTodayTask() {
        List<Task> taskList = getList();
        return taskList.stream()
                .filter(e -> LocalDate.now().equals(e.getStartDate()))
                .sorted((o1, o2) -> {
                    if ((Objects.isNull(o1.getTime())) || (Objects.isNull(o2.getTime()))) {
                        return 1;
                    }
                    return o1.getTime().compareTo(o2.getTime());
                })
                .collect(Collectors.toList());
    }
}
