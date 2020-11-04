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
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;

/**
 * Manages the scheduling of reminders in the project book.
 */
public class ReminderManager {
    public static final String TASK_REMINDER = "Project: %s\nTask: %s";
    public static final String PROJECT_REMINDER = "Project: %s";

    private static final Logger LOGGER = LogsCenter.getLogger(ReminderManager.class);

    private static final String EMPTY_STRING = "";

    protected final ProjectBook projectBook;
    protected Timer timer;
    private final StringProperty currReminder;

    /**
     * Instantiates a new Reminder manager.
     *
     * @param projectBook the project book.
     */
    public ReminderManager(ProjectBook projectBook) {
        this.projectBook = projectBook;
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
     * Reschedule all reminders in the project book.
     */
    public void rescheduleReminder() {
        LOGGER.info("Rescheduling all reminders");
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
            LOGGER.info("Rescheduling reminder for project:" + project.getName() + " at " + project.getReminder());
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
            LOGGER.info("Rescheduling reminder for task:" + task.getName() + " from project: " + project.getName()
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
        if (!project.getReminder().canSchedule()) {
            TimerTask timerTask = new ReminderTimerTask(project);
            this.timer.schedule(timerTask, project.getReminder().toDate());    
        }        
    }

    /**
     * Schedule the reminder of a {@code task}.
     *
     * @param project the project
     * @param task    the task
     */
    public void scheduleReminder(Project project, Task task) {
        if (!task.getReminder().canSchedule()) {
            TimerTask timerTask = new ReminderTimerTask(task, project);
            this.timer.schedule(timerTask, task.getReminder().toDate());    
        }        
    }

    /**
     * Update current reminder to be displayed.
     *
     * @param project the project
     * @param task    the task
     */
    public void updateCurrReminder(Project project, Task task) {
        this.currReminder.set(String.format(TASK_REMINDER, project.getName(), task.getName()));
        LOGGER.info("Current reminder updated to:" + currReminder);
    }

    /**
     * Update current reminder to be displayed.
     *
     * @param project the project
     */
    public void updateCurrReminder(Project project) {
        this.currReminder.set(String.format(PROJECT_REMINDER, project.getName()));
        LOGGER.info("Current reminder updated to:" + currReminder);
    }

    /**
     * Returns true if the current reminder is empty, false otherwise.
     *
     * @return the boolean.
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
     * Gets reminder.
     *
     * @return the reminder.
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

        ReminderManager other = (ReminderManager) obj;
        return projectBook.equals(other.projectBook)
                && currReminder.get().equals(other.currReminder.get());
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
            return task.<Runnable>map(taskObj -> () -> projectBook.removeReminder(project, taskObj))
                    .orElseGet(() -> () -> projectBook.removeReminder(project));
        }

        @Override
        public void run() {
            LOGGER.info("reminder running:");
            System.out.println(project + " " + task);
            ThreadWrapper.run(getUpdateReminder());
            ThreadWrapper.run(getRemoveReminder());
        }
    }
}
