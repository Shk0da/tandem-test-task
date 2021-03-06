package ru.tandemservice.test.task2;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class Task2ImplTest {

    private final Task2Impl task2 = Task2Impl.getInstance();
    private final ElementExampleImpl.Context context = new ElementExampleImpl.Context();
    private List<IElement> elements = Collections.synchronizedList(new ArrayList<>());

    @Before
    public void setUp() throws Exception {
        int maxValue = 2_147_483_647;
        int countElements = 152_251;
        for (int count = 10; count < countElements; count++) {
            int number = (count % 2 == 0) ? (count + countElements) : count;
            if (number >= maxValue) continue;
            IElement element = new ElementExampleImpl(context, number);
            elements.add(element);
        }

        elements.add(new ElementExampleImpl(context, 2));
        elements.add(new ElementExampleImpl(context, 3));
        elements.add(new ElementExampleImpl(context, 9));
        elements.add(new ElementExampleImpl(context, 5));
    }

    @Test
    public void assignNumbers() throws Exception {
        Long start = System.currentTimeMillis();
        task2.assignNumbers(elements);
        Long end = System.currentTimeMillis();

        System.out.println("Operation Count: " + context.getOperationCount());
        System.out.println("Time spent: " + (end - start) + " ms");

        AtomicInteger counter = new AtomicInteger(0);
        elements.forEach(item -> assertEquals(item.getNumber(), counter.getAndIncrement()));
    }

}