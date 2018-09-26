package com.hibicode.demo3;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BugWorkerTest {

    @Test
    public void whenFailingToParallelProcess_thenMainThreadShouldGetNotGetStuck() throws InterruptedException {
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new BugWorker(outputScraper, countDownLatch)))
                .limit(5)
                .collect(toList());

        workers.forEach(Thread::start);
        // countDownLatch.await(); // if stay like this, it will infinitely block
        // the best way
        boolean completed = countDownLatch.await(3L, TimeUnit.SECONDS);
        assertThat(completed, is(false));
    }

}
