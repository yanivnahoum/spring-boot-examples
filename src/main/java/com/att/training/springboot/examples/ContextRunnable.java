package com.att.training.springboot.examples;

public class ContextRunnable implements Runnable {
    private final Runnable runnable;
    private final String correlationId;

    ContextRunnable(Runnable runnable) {
        correlationId = LogContext.getCorrelationId();
        this.runnable = runnable;
    }

    @Override
    public void run() {
        LogContext.setCorrelationId(correlationId);
        try {
            runnable.run();
        } finally {
            LogContext.clear();
        }
    }
}
