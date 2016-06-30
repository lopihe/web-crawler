package uk.contribit.web.crawler.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

/**
 * Executor keeping track of the number of jobs submitted providing a way to
 * wait for it to run and finish everything.
 *
 */
class BatchExecutor {
    private final ExecutorService executor;
    private final Phaser phaser = new Phaser(1);

    public BatchExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public void submit(Runnable runnable) {
        phaser.register();
        executor.submit(() -> {
            try {
                runnable.run();
            }
            finally {
                phaser.arriveAndDeregister();
            }
        });
    }

    public void awaitCompletion() {
        phaser.arriveAndAwaitAdvance();
    }
}