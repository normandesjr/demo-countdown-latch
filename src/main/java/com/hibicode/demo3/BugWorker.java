package com.hibicode.demo3;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BugWorker implements Runnable {

    private List<String> outputScraper;
    private CountDownLatch countDownLatch;

    public BugWorker(List<String> outputScraper, CountDownLatch countDownLatch) {
        this.outputScraper = outputScraper;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        if (true) {
            throw new RuntimeException("This is a bug...");
        }

        countDownLatch.countDown();
        outputScraper.add("Counted down");
    }

}
