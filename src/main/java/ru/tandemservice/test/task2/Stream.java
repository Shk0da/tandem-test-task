package ru.tandemservice.test.task2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveAction;

/**
 * Stream - помогает распараллелить работу по выставлению номеров
 */
public class Stream extends RecursiveAction {

    private static Map<String, Object> register = new ConcurrentHashMap<>();
    private volatile List<IElement> elements;
    private int start;
    private int end;
    private int limit;

    public Stream(List<IElement> elements) {
        this.init(elements, 0, elements.size());
    }

    private Stream(List<IElement> elements, int startNumber, int endNumber) {
        this.init(elements, startNumber, endNumber);
    }

    private void init(List<IElement> elements, int startNumber, int endNumber) {
        this.elements = elements;
        this.limit = (elements.size() / getAvailableProcessors());
        this.start = startNumber;
        this.end = endNumber;
    }

    /**
     * Делимся в зависимости от значения availableProcessors {@link Stream#getAvailableProcessors()}
     */
    @Override
    protected void compute() {
        if (end - start <= limit) {
            for (int counter = start; counter <= (end - 1) || counter <= (elements.size() - 1); counter++) {
                IElement element = elements.get(counter);
                setupNumberElement(element, counter);
            }
        } else {
            int middle = (start + end) / 2;
            invokeAll(new Stream(elements, start, middle), new Stream(elements, middle + 1, end));
        }
    }

    /**
     * Устанавливает номер элемента.
     * При дублировании пробует снова, пока другие потоки не установят порядок
     * @param element IElement
     * @param counter номер элемента
     */
    private void setupNumberElement(IElement element, int counter) {
        if (element.getNumber() != counter) {
            try {
                element.setupNumber(counter);
            } catch (IllegalStateException e) {
                setupNumberElement(element, counter);
            }
        }
    }

    /**
     * Получаем текущее значение availableProcessors системы
     * @return Integer
     */
    public static int getAvailableProcessors() {
        String registerName = "availableProcessors";
        if (!register.containsKey(registerName)) {
            Integer availableProcessors = Runtime.getRuntime().availableProcessors();
            register.put(registerName, availableProcessors);
        }

        return (Integer) register.get(registerName);
    }
}

