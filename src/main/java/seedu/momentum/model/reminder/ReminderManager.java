package seedu.momentum.model.reminder;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;

/**
 * Manages the scheduling of reminders in the project book.
 */
public class ReminderManager {
    private final ProjectBook projectBook;
    private StringProperty currReminder;
    private Timer timer;

    private static final Logger logger = LogsCenter.getLogger(ReminderManager.class);

    /**
     * Instantiates a new Reminder manager.
     *
     * @param projectBook the project book.
     */
    public ReminderManager(ProjectBook projectBook) {
        this.projectBook = projectBook;
        this.currReminder = new SimpleStringProperty();
        this.timer = new Timer();
        logger.info("Initialised reminder manager");
    }

    private void resetTimer() {
        this.timer.cancel();
        this.timer = new Timer();
    }

    /**
     * Reschedule all reminders in the project book.
     */
    public void rescheduleReminder() {
        logger.info("Rescheduling all reminders");
        resetTimer();
        projectBook.rescheduleReminder(this);
    }

    /**
     * Reschedule the reminder of a {@code project} and its tasks.
     *
     * @param project the project
     */
    public void rescheduleReminder(Project project) {
        if (!project.getReminder().isEmpty()) {
            logger.info("Rescheduling reminder for project:" + project.getName() + " at " + project.getReminder());
            scheduleReminder(project);
        }
        project.rescheduleReminder(this); // reschedule tasks
    }

    /**
     * Reschedule the reminder of a {@code task}.
     *
     * @param project the project
     * @param task    the task
     */
    public void rescheduleReminder(Project project, Task task) {
        if (!task.getReminder().isEmpty()) {
            logger.info("Rescheduling reminder for task:" + task.getName() + " from project: " + project.getName()
                    + " at " + task.getReminder());
            scheduleReminder(project, task);
        }
    }

    /**
     * Schedule the reminder of a {@code project}.
     *
     * @param project the project
     */
    public void scheduleReminder(Project project) {
        TimerTask timerTask = new ReminderTimerTask(project);
        this.timer.schedule(timerTask, project.getReminder().toDate());
    }

    /**
     * Schedule the reminder of a {@code task}.
     *
     * @param project the project
     * @param task    the task
     */
    public void scheduleReminder(Project project, Task task) {
        TimerTask timerTask = new ReminderTimerTask(task, project);
        this.timer.schedule(timerTask, task.getReminder().toDate());
    }

    /**
     * Update current reminder to be displayed.
     *
     * @param project the project
     * @param task    the task
     */
    public void updateCurrReminder(Project project, Task task) {
        this.currReminder.set("Reminder: " + task.getName() + " of " + project.getName());
        logger.info("Current reminder updated to:" + currReminder);
    }

    /**
     * Update current reminder to be displayed.
     *
     * @param project the project
     */
    public void updateCurrReminder(Project project) {
        this.currReminder.set("Reminder: " + project.getName());
        logger.info("Current reminder updated to:" + currReminder);
    }

    /**
     * Returns true if the current reminder is empty, false otherwise.
     *
     * @return the boolean
     */
    public boolean isReminderEmpty() {
        return this.currReminder.get() == null;
    }

    /**
     * Removes the current reminder.
     */
    public void removeReminder() {
        this.currReminder = new SimpleStringProperty();
        logger.info("reminder removed");
    }

    /**
     * Gets reminder.
     *
     * @return the reminder.
     */
    public String getReminder() {
        return this.currReminder.get() == null ? "" : this.currReminder.get();
    }

    private class ReminderTimerTask extends TimerTask {
        private final Project project;
        private final Optional<Task> task;

        public ReminderTimerTask(Project project) {
            this.project = project;
            this.task = Optional.empty();
        }

        public ReminderTimerTask(Task task, Project project) {
            this.project = project;
            this.task = Optional.of(task);
        }

        private Runnable getUpdateReminder() {
            return task.<Runnable>map(taskObj -> () -> updateCurrReminder(project, taskObj))
                    .orElseGet(() -> () -> updateCurrReminder(project));
        }

        private Runnable getRemoveReminder() {
            return task.<Runnable>map(taskObj -> () -> projectBook.removeReminder(project, taskObj))
                    .orElseGet(() -> () -> projectBook.removeReminder(project));
        }

        @Override
        public void run() {
            logger.info("reminder running");
            Platform.runLater(getUpdateReminder());
            Platform.runLater(getRemoveReminder());
        }
    }
}
