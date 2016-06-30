package uk.contribit.web.crawler.engine;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import uk.contribit.web.crawler.engine.BatchExecutor;

public class BatchExecutorTest {
    @Rule
    public final Timeout timeout = Timeout.seconds(5);

    @Test
    public void testSubmitAwait() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        BatchExecutor batchExecutor = new BatchExecutor(executor);

        final AtomicBoolean finishedT1 = new AtomicBoolean();
        batchExecutor.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                Assert.fail();
            }
            finishedT1.set(true);
        });
        final AtomicBoolean finishedT2 = new AtomicBoolean();
        batchExecutor.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                Assert.fail();
            }
            finishedT2.set(true);
        });
        batchExecutor.awaitCompletion();
        assertTrue(finishedT1.get());
        assertTrue(finishedT2.get());
    }
}
