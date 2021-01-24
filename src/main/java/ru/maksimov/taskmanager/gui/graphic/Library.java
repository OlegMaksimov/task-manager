package ru.maksimov.taskmanager.gui.graphic;

public class Library {
    private static final String TODAY_TASK = "" +
            "%s  %s                                                  \n" +
            "-----  ------------------------------------------------------\n";

    private static final String TITLE = "\t\t\t\t %s \n" +
            "-------------------------------------------------------------\n\n";


    public static String getTodayTask(String time, String taskName) {
        return String.format(TODAY_TASK, time, taskName);
    }

    public static String getTitle(String title) {
        return String.format(TITLE, title);
    }
}
