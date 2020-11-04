package seedu.momentum.commons.core;

import javafx.application.Platform;

/**
 * Stimulates running a thread.
 * Runs using Platform by default.
 * Change to run runnable if isRunningOnPlatform is true.
 */
public class ThreadWrapper {
    private static boolean isRunningOnPlatform;

    /**
     * Sets the is running on platform boolean.
     *
     * @param onPlatform true if it is running on platform.
     */
    public static void setIsRunningOnPlatform(boolean onPlatform) {
        isRunningOnPlatform = onPlatform;
    }

    /**
     * Run a runnable.
     *
     * @param runnable the runnable.
     */
    public static void run(Runnable runnable) {
        if (isRunningOnPlatform) {
            Platform.runLater(runnable);
        } else {
            runnable.run();
        }
    }
}
