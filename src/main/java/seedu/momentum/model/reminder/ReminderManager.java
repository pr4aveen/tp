//@@author claracheong4
package seedu.momentum.model.reminder;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.commons.core.ThreadWrapper;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;

/**
 * Manages the scheduling of reminders in the model.
 */
public class ReminderManager {
    public static final String TASK_REMINDER = "Project: %s\nTask: %s";
    public static final String PROJECT_REMINDER = "Project: %s";

    private static final Logger LOGGER = LogsCenter.getLogger(ReminderManager.class);

    private static final String EMPTY_STRING = "";

    protected final Model model;
    protected Timer timer;
    private final StringProperty currReminder;

    /**
     * Instantiates a new Reminder manager.
     *
     * @param model The model associated to the instance of the application.
     */
    public ReminderManager(Model model) {
        this.model = model;
        this.currReminder = new SimpleStringProperty();
        this.currReminder.set(EMPTY_STRING);
        this.timer = new Timer();
        LOGGER.info("Initialised reminder manager");
    }

    private void resetTimer() {
        this.timer.cancel();
        this.timer = new Timer();
    }

    /**
     * Reschedule all reminders in the model.
     */
    public void rescheduleReminder() {
        LOGGER.info("Rescheduling all reminders");
        resetTimer();
        model.rescheduleReminder();
    }

    /**
     * Reschedule the reminder of a {@code project} and its tasks.
     *
     * @param project The project to reschedule the reminder of.
     */
    public void rescheduleReminder(Project project) {
        if (!project.getReminder().isEmpty()) {
            LOGGER.info("Rescheduling reminder for project:" + project.getName() + " at " + project.getReminder());
            scheduleReminder(project);
        }
        project.rescheduleReminder(this); // reschedule tasks
    }

    /**
     * Reschedule the reminder of a {@code task}.
     *
     * @param project The project containing the task to be rescheduled.
     * @param task    The task to reschedule the reminder of.
     */
    public void rescheduleReminder(Project project, Task task) {
        if (!task.getReminder().isEmpty()) {
            LOGGER.info("Rescheduling reminder for task:" + task.getName() + " from project: " + project.getName()
                    + " at " + task.getReminder());
            scheduleReminder(project, task);
        }
    }

    /**
     * Schedule the reminder of a {@code project}.
     *
     * @param project The project to schedule a reminder in.
     */
    private void scheduleReminder(Project project) {
        if (project.getReminder().canSchedule()) {
            LOGGER.info("reminder scheduled for " + project);
            TimerTask timerTask = new ReminderTimerTask(project);
            this.timer.schedule(timerTask, project.getReminder().toDate());
        }
    }

    /**
     * Schedule the reminder of a {@code task}.
     *
     * @param project The project containing the task to have a reminder scheduled in.
     * @param task    The task to schedule a reminder in.
     */
    private void scheduleReminder(Project project, Task task) {
        if (task.getReminder().canSchedule()) {
            LOGGER.info("reminder scheduled for " + task + " in " + project);
            TimerTask timerTask = new ReminderTimerTask(task, project);
            this.timer.schedule(timerTask, task.getReminder().toDate());
        }
    }

    /**
     * Update current reminder to be displayed.
     *
     * @param project The project containing the task with the reminder to be displayed.
     * @param task    The task with the reminder to be displayed.
     */
    public void updateCurrReminder(Project project, Task task) {
        this.currReminder.set(String.format(TASK_REMINDER, project.getName(), task.getName()));
        LOGGER.info("Current reminder updated to:" + currReminder);
    }

    /**
     * Update current reminder to be displayed.
     *
     * @param project The project with the reminder to be displayed.
     */
    public void updateCurrReminder(Project project) {
        this.currReminder.set(String.format(PROJECT_REMINDER, project.getName()));
        LOGGER.info("Current reminder updated to:" + currReminder);
    }

    /**
     * Checks if the current reminder is empty.
     *
     * @return True if the current reminder is empty, false otherwise.
     */
    public BooleanProperty isReminderEmpty() {
        BooleanProperty booleanProperty = new SimpleBooleanProperty();
        booleanProperty.set(this.currReminder.get().isEmpty());
        return booleanProperty;
    }

    /**
     * Removes the current reminder.
     */
    public void removeReminder() {
        this.currReminder.set(EMPTY_STRING);
        LOGGER.info("reminder removed");
    }

    /**
     * Gets the current reminder.
     *
     * @return The current reminder.
     */
    public StringProperty getReminder() {
        return this.currReminder;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ReminderManager)) {
            return false;
        }

        return currReminder.get().equals(((ReminderManager) obj).currReminder.get());
    }

    private class ReminderTimerTask extends TimerTask {
        private final Project project;
        private final Optional<Task> task;

        private ReminderTimerTask(Project project) {
            this.project = project;
            this.task = Optional.empty();
        }

        private ReminderTimerTask(Task task, Project project) {
            this.project = project;
            this.task = Optional.of(task);
        }

        private Runnable getUpdateReminder() {
            return task.<Runnable>map(taskObj -> () -> updateCurrReminder(project, taskObj))
                    .orElseGet(() -> () -> updateCurrReminder(project));
        }

        private Runnable getRemoveReminder() {
            return task.<Runnable>map(taskObj -> () -> model.removeReminder(project, taskObj))
                    .orElseGet(() -> () -> model.removeReminder(project));
        }

        @Override
        public void run() {
            LOGGER.info("reminder running:");
            ReminderManager.this.timer.cancel();
            ThreadWrapper.run(getUpdateReminder());
            ThreadWrapper.run(getRemoveReminder());
        }
    }
}
