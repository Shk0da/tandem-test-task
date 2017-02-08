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
     * Распараллеливает если есть для этого ресурсы, иначе нет смысла.
     * @param elements элементы, которым нужно выставить номера
     */
    public void assignNumbers(final List<IElement> elements) {
        if (Stream.getAvailableProcessors() > 1) {
            ForkJoinPool pool = new ForkJoinPool();
            Stream stream = new Stream(elements);
            pool.invoke(stream);
        } else {
            AtomicInteger counter = new AtomicInteger(0);
            elements.spliterator().forEachRemaining(element -> {
                int number = counter.getAndIncrement();
                if (element.getNumber() != number) {
                    element.setupNumber(number);
                }
            });
        }
    }

}
