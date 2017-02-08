package ru.tandemservice.test.task1;

import java.util.List;

/**
 * Задание №1
 */
public class Task1Impl implements IStringRowsListSorter {

    private static volatile Task1Impl instance;

    private Task1Impl() {
    }

    public static Task1Impl getInstance() {
        Task1Impl localInstance = instance;
        if (localInstance == null) {
            synchronized (Task1Impl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Task1Impl();
                }
            }
        }

        return localInstance;
    }

    public void sort(final List<String[]> rows, final int columnIndex) {
        try {
            rows.sort(new SmartTreeComparator(columnIndex));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Сортировка не применима, нет колонки с номером " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
