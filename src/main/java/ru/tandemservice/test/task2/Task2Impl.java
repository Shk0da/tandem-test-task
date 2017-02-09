package ru.tandemservice.test.task2;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>Задание №2</h1>
 */
public class Task2Impl implements IElementNumberAssigner {

    private static volatile Task2Impl instance;

    private Task2Impl() {
    }

    public static Task2Impl getInstance() {
        Task2Impl localInstance = instance;
        if (localInstance == null) {
            synchronized (Task2Impl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Task2Impl();
                }
            }
        }

        return localInstance;
    }

    /**
     * Метод выставляет номера {@link IElement#setupNumber(int)}
     * для элементов коллекции {@code elements}
     * в порядке следования элементов в коллекции.
     *
     * @param elements элементы, которым нужно выставить номера
     */
    public void assignNumbers(final List<IElement> elements) {
        ForkJoinPool pool = new ForkJoinPool();
        Stream stream = new Stream(elements);
        pool.invoke(stream);
    }

}
