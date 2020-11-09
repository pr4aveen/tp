---
layout: page
title: User Guide
---

Momentum is a **desktop app** that **helps freelancers track time spent on different projects** and **gain insights on how their time is spent**.

It is designed for people that prefer typing, so that frequent tasks can be done faster by typing in commands.

{:toc}

--------------------------------------------------------------------------------------------------------------------

<!-- # Table of contents
[1. Quick Start](#quick-start)
[2. Navigating the User Guide]
    [2.1  UI Overview]
    [2.2  Command Format]
    [2.3  Date and Time Formats]
[3. Features](#3-Features)
    [3.1 Projects and Tasks] add/edit/delete/clear/list/view/home
    [3.2  Finding Projects and Tasks]
    [3.3  Sorting Projects and Tasks]
    [3.4 Time Tracking]
    [3.5  Reminders]
    [3.6  Undo/Redo]
    [3.7 Statistics](#statistics)
    [3.8 Settings](#settings)
    [3.9 Show and Hide SideBar Components : show](#show)
    [3.10 Exiting the Program : exit](exit)
[4. FAQ](#Faq)
[5. Glossary](#Glossary)
[6. Command Summary](#command-summary)
 -->

## 1. Quick Start<a name="quick-start"></a>

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `momentum.jar` from [here](https://github.com/AY2021S1-CS2103T-T10-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for Momentum.

4. Double-click the file to start Momentum. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`add`**`n/Momentum d/CS2103T Team Project` : Adds a project named `Momentum` to the Project Book.

   * **`edit`**`3 n/NewMomentum d/newDescription` : Update the 3rd project in the current list. The name will be changed to "NewMomentum" and the description will be changed to "NewDescription".

   * **`find`**`n/NewMomentum` : Find a project that has `NewMomentum` in its name.

   * **`delete`**`3` : Deletes the 3rd project shown in the current list.

   * **`sort`**`type/deadline order/asc` : Sorts the list of projects by deadline in ascending order.

   * **`exit`** : Exits the app.

Refer to the [Features](#features) below for a more comprehensive set of features available in Momentum.

--------------------------------------------------------------------------------------------------------------------

## User Interface Overview

Momentum uses a GUI (Graphical User Interface) to collect input from you, and display information to you. Different parts of this GUI perform different functions, as explained below:

![OverviewUI](images/OverviewUI2.png)

1. **Command Box**: The place where you enter in your commands.
2. **Results Box**: Displays information about the result of executing the commands that you enter.
3. **Display List**: Displays a list of projects or tasks.
4. **Reminders Panel**: This is where reminders will appear. The panel will only be present when there are reminders that have not yet been dismissed.
5. **Active Timers Panel**: Displays all the timers that are currently running.
(see [Active Timers Panel](#active-timers-panel))
6. **Statistics Panel**: Displays the statistics data calculated by Momentum.
(see [Statistics](#statistics))
7. **Tags Panel**: Displays a collection of all the tags visible in the display list.
8. **Bottom Bar**: Displays contextual information about what you are viewing, such as the specific project that you are viewing, and the number of items in the display list hidden due to finding or sorting commands.

The active timers, statistics, tags and reminders panels can be resized to display more information by clicking and
 draging their edges.

## Command Format

Below is an explanation of the formatting used to show commands:

* Words in `UPPER_CASE` are the parameters to be supplied by the user.
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/Blog Design`.
* Items in square brackets are optional.
  e.g `n/NAME [t/TAG]` can be used as `n/Blog Design t/friend` or as `n/Blog Design`.
* Items with `…`​ after them can be used multiple times including zero times.
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.
* Items with `…` inside square brackets `[t/TAG [MORE_TAGS]...]` can take in multiple space separated arguments
  e.g. `[[t/TAG [MORE_TAGS]...]` can represent `t/friend friend family`
* Parameters can be in any order.
  e.g. if the command specifies `n/NAME dd/2020-11-05`, `dd/2020-11-05 n/NAME` is also acceptable.

## Date and Time Terms

Dates and Times are an integral part of Momentum, and you will typing dates and times regularly. Here is a guide to the format for entering dates and times in Momentum.

### Date Terms

Dates should be entered in the order `YYYY-MM-DD`

Term  | Meaning | Example
----- |-------- | -------
`YYYY`| Year    | `2020`
`MM`  | Month   | `02`
`DD`  | Day     | `09`

**Valid**: `2020-08-02`

**Invalid**: `2-8-20` Wrong number of digits.

**Invalid**: `02-08-20` Wrong order of year, month and date.

**Invalid**: `2nd August 2020` You cannot use text to enter dates.

### Time Terms

Times should be entered in 24 hour format, in the order `HH:MM:SS`

Term | Meaning | Example
-----|-------- | -------
HH   | Hour    | `16`
MM   | Minute  | `52`
SS   | Second  | `03`

**Valid**: `15:08:02`

**Invalid**: `15:8:2` (Wrong number of digits.)

**Invalid**: `8:15:2` (Wrong order of hour, minute and second.)

**Invalid**: `03:08:02PM` (Only 24 hour time is accepted.)

### Combining Date and Time

You may also have to enter both dates and times together. When entered together, dates and times should be entered in the order `YYYY-MM-DDTHH:MM:SS`. The letter `T` separates the date and time.

**Valid**: `2020-08-02T15:08:02`

**Invalid**: `15:08:02T2020-08-02` (Wrong order of date and time.)

## 2. Features<a name="2-Features"></a>

### 3. Projects and Tasks<a name="3-Projects-and-Tasks"></a>

You can add projects into Momentum to be tracked. Each project has the following information:

* Name
* Description
* Completion Status
* Deadline Date
* Deadline Time
* Reminder
* Tag

Apart from the name, all other information is optional.

Each project can also contain several tasks, each with the same information as a project.

When you first open Momentum, you will see all the projects being tracked in Momentum. You can then view the tasks for each project seperately.

:::info

**:information_source: Most commands in Momentum will do different things depending on whether you are viewing projects or tasks.**<br>
Please refer to each command for these differences.

:::

#### 3.1 View Projects: `home`<a name="#31-View-Projects-home"></a>

View all the projects being tracked by Momentum.
This is the default view when Momentum is first opened.

Format: `home`

#### 3.2 Viewing a Project's Tasks: `view`<a name="#32-Viewing-a-Project's-Tasks-view"></a>

View the tasks for a project.

Format: `view ID`

* The id refers to the id number shown in the displayed project list.
* The id **must be a positive integer** 1, 2, 3, …​

Example: `view 1`

#### 3.3 Creating a Project/Task: `add`

When looking at projects, this command will create a new project. When looking at the tasks in a project, this command will create a new task for the project.

Format: `add n/NAME [d/DESCRIPTION] [c/] [dd/DEADLINE_DATE [dt/DEADLINE_TIME]] [r/REMINDER_DATE_TIME] [t/TAG]`

* `n/NAME`
  * Name can contain alphanumeric characters (a-Z, 0-9) and spaces.
* `[d/DESCRIPTION]`
  * There is no restriction imposed on descriptions.
* `[c/]`
  * The project is incomplete by default, adding `c/` will set the completion status to complete.
* `[dd/DEADLINE_DATE [dt/DEADLINE_TIME]]`
  * The `dt\DEADLINE_TIME` is part of the the prefix `dd/`, and is optional.
  * A deadline cannot contain only the time field, but a deadline can contain only the date field.
  * The format for date of the deadline is YYYY-MM-DD, refer to [Date Terms](#Date-Terms) for more information on YYYY, MM and DD.
  * The format for time of the deadline is HH:MM:SS in 24 hour format, refer to [Time Terms](#Time-Terms) for more information on HH, MM and SS.
  * The date of the deadline cannot be earlier than the creation date of the project.
* `[r/REMINDER_DATE_TIME]`
  * Both date and time is compulsory for a reminder.
  * The format for date and time of the reminder is YYYY-MM-DDTHH:MM:SS, refer to [Date and Time Terms](#Date-and-Time-Terms) for more information on YYYY, MM, DD, HH, MM, and SS.
  * The date and time of the reminder needs to be later than the current time, refer to [Reminders](#reminders) section for more details on reminders.
* `[t/TAG]`
  * Similar to names, tags can contain alphanumeric characters (a-Z, 0-9) and spaces.

:::info
:bulb: **Tip:**

* Projects and tasks can have any number of tags (including 0).
* A project can have an empty description.
* `T` separates the date and time in a reminder.

:::

Example: `add n/Momentum d/CS2103T Team Project dd/2021-12-07 dt/11:01:12 r/2021-12-07T11:01:12 t/impt`

Result: Creates a project named "Momentum" with a description "CS2103T Team Project", a tag "impt", deadline date "2020-10-07" with deadline time "11:01:12" and reminder "2020-10-07T11:01:12".

##### Walkthrough of Creating a Project

1. Type `add n/Momentum d/CS2103T Team Project c/ dd/2021-12-07 dt/11:01:12 r/2021-12-07T11:01:12 t/impt` in the command box, and press the `Enter` key to execute it.
![Walkthrough of Creating a Project Diagram Step 1](images/AddProjectDiagram1.png)
2. The result box will display a message to indicate that the command has been executed successfully.
![Walkthrough of Creating a Project Diagram Step 2](images/AddProjectDiagram2.png)
3. A project will be added to the project list as shown below.
![Walkthrough of Creating a Project Diagram Step 3](images/AddProjectDiagram3.png)
4. On 7 December 2021, at 11:01:12, the reminder panel will be shown and the reminder of the project will be removed.
![Walkthrough of Creating a Project Diagram Step 4](images/AddProjectDiagram4.png)

#### 3.4 Editing a Project/Task: `edit`

Edits a project or task that was been previously created.

Format: `edit ID [n/NAME] [d/DESCRIPTION] [c/] [dd/DEADLINE_DATE [dt/DEADLINE_TIME]] [r/REMINDER_DATE_TIME] [t/TAG]`

* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* You can remove a description, deadline or reminder by typing `d/` or `dd/` or `r/` without specifying anything after it.

* ID
  * The id refers to the id number shown in the displayed list.
  * The id **must be a positive integer** 1, 2, 3, …​
* `n/NAME`
  * Name can contain alphanumeric characters (a-Z, 0-9) and spaces.
* `[d/DESCRIPTION]`
  * There is no restriction imposed on descriptions.
* `[c/]`
  * Adding `c/` will reverse the completion status, if the project was incomplete the completion status will change to complete.
* `[dd/DEADLINE_DATE [dt/DEADLINE_TIME]]`
  * The `dt\DEADLINE_TIME` is part of the the prefix `dd/`, and is optional.
  * A deadline cannot be edited with only the time field, but a deadline can be edited with only the date field.
  * The deadline will be replaced with an edit.
  * The format for date of the deadline is YYYY-MM-DD, refer to [Date Terms](#Date-Terms) for more information on YYYY, MM and DD.
  * The format for time of the deadline is HH:MM:SS in 24 hour format, refer to [Time Terms](#Time-Terms) for more information on HH, MM and SS.
  * The date of the deadline cannot be earlier than the creation date of the project.
* `[r/REMINDER_DATE_TIME]`
  * Both date and time is compulsory for a reminder.
  * The format for date and time of the reminder is YYYY-MM-DDTHH:MM:SS, refer to [Date and Time Terms](#Date-and-Time-Terms) for more information on YYYY, MM, DD, HH, MM, and SS.
  * The date and time of the reminder needs to be later than the current time, refer to [Reminders](#reminders) section for more details on reminders.
* `[t/TAG]`
  * Similar to names, tags can contain alphanumeric characters (a-Z, 0-9) and spaces.
  * When editing tags, the existing tags of the project will be removed i.e adding of tags is not cumulative.
  * You can remove all the project’s tags by typing `t/` without specifying any tags after it.

Example: `edit 3 n/NewMomentum d/NewDescription dd/2021-12-07 t/normal`

Result: Updates the project with id 3. The name will be changed to "NewMomentum", the description will be changed to "NewDescription", all the tags will be removed and a tag named normal is added, and the deadline will be changed to "2021-12-07".

##### Walkthrough of Editing a Task

1. In task view, type `edit 3 n/NewMomentum d/NewDescription dd/2021-12-07 r/ t/normal` in the command box, and press the `Enter` key to execute it.
![Walkthrough of Editing a Task Diagram Step 1](images/EditTaskDiagram1.png)
2. The result box will display a message to indicate that the command has been executed successfully.
![Walkthrough of Editing a Task Diagram Step 2](images/EditTaskDiagram2.png)
3. A task will be updated as shown below.
![Walkthrough of Editing a Task Diagram Step 3](images/EditTaskDiagram3.png)

#### 3.5 Deleting a Project/Task: `delete`

Deletes a project or task in the list.

Format: `delete ID`

* Deletes the project at the specified `ID`.
* The id refers to the id number shown in the displayed project list.
* The id **must be a positive integer** 1, 2, 3, …​

Example: `delete 2`

Result: Deletes the second project in the list.

#### 3.6 Project/Task Organisation

##### 3.6.1 View All Projects : `list`

When viewing projects, this command shows a list of all projects in Momentum.

When viewing a project's tasks, this command shows a list of all the tasks for the project.

Note that this command is different from the `home` command. When viewing a project's tasks, the `home` command will change the view to show all the projects being tracked by Momentum. However, the `list` will only show all the tasks for the project.

Format: `list`

##### 3.6.2 Sort Projects : `sort`

Sorts the list of displayed projects or tasks in the application.

Format: `sort [type/SORT_TYPE] [order/SORT_ORDER] [c/]`

* There are 3 types of sort.
  * `type/alpha` will sort the list of projects in alphabetical order.
  * `type/deadline` will sort the list of projects according to their deadlines.
  * `type/created` will sort the list of projects according to their date of creation.

* There are 2 sort orders.
  * `order/asc` will sort the list of projects in ascending order.
  * `order/dsc` will sort the list of projects in descending order.

* The projects can be sorted by incomplete then completed.
  * This is the default sort.
  * Add `c/` to reverse the previously set default.
  * If the sort grouped the incomplete and complete projects, `c/` will make the sort not consider the completion status of the projects.
  * Similarly, if the sorting did not group the projects by completion status, `c/` will make the sort consider the completion status of the projects.

:::info
:bulb: **Tip:**

* `type/alpha` and `order/asc` will be used as default if both sort type and order are not specified (i.e. command is `sort`)
* Current sort type will be used if the `type` is not specified but `order` is specified.
* `order/asc` will be used as default if the `order` is not specified but `type` is specified.
* For `sort type/deadline`, projects without deadlines will be ordered alphabetically after the ordered list of projects with deadlines.
* For both `sort type/deadline` and `sort type/created`, projects with same deadline or same created date will be sorted alphabetically.

:::

Example:

The following are 3 projects in the project book.

Project 1. Name: `Ant Hole`, Deadline: `2020-02-02`, Created Date: `2000-02-02`
Project 2. Name: `Brunch`, Deadline: `2010-01-01`, Created Date: `2002-09-09`
Project 3. Name: `Create Logo` , Deadline: `2040-04-04`, Created Date: `2001-01-01`

**3.6.2.3 Sorting by Default order**

Format: `sort`

* Sorts projects in alphabetical, ascending order

Result: [Project 1, Project 2, Project 3]

###### Sorting With Only Type Specified

Format: `sort type/SORT_TYPE`

* Sorts projects in a specified order
* Since order is not specified, default order is ascending

Example: `sort type/alpha`
Result: [Project 1, Project 2, Project 3]

Example: `sort type/deadline`
Result: [Project 2, Project 1, Project 3]

Example: `sort type/created`
Result: [Project 1, Project 3, Project 2]

##### Sorting With Only Order Specified

Format: `sort order/SORT_ORDER`

* Sorts projects in current project order.
* If there is no existing project order (when the application restarts), order will be alphabetical by default.

Example: `sort order/dsc` (After application restarts for the first time)
Result: [Project 3, Project 2, Project 1]

Example `sort order/asc` (Current sort type is Deadline)
Result: [Project 2, Project 1, Project 3]

##### Sorting With Both Type and Order Specified

* Sorts projects in specified type and order.
* Projects that cannot be ordered in a certain type will be ordered alphabetically.

Example: `sort type/alpha order/dsc`
Result: [Project 3, Project 2, Project 1]

Example: `sort type/deadline order/asc`
Result: [Project 2, Project 1, Project 3]

Example: `sort type/created order/dsc`
Result: [Project 2, Project 3, Project 1]

##### Filtering Projects: `find`

Searches for projects or tasks in the project book based on certain parameters.

Format: `find [match/FILTER_TYPE] [n/NAME [MORE_NAMES]...] [d/DESCRIPTION [MORE_DESCRIPTIONS]...] [t/TAG [MORE_TAGS]...] [c/COMPLETION_STATUS]`

* There are three values for the `match` parameter.
* `match/all` shows an entry only if **all** of the parameters provided in the user's input matches the user's input.
* `match/any` shows an entry as long as **any** of the parameters provided in the user's input matches the user's input.
* `match/none` shows an entry only if **none** of the parameters provided in the user's input matches the user's input.

:::info
:bulb: **Tip:**
You can only search for projects in the project view and tasks in the tasks view
:::

:::info
:bulb: **Tip:**
`match/any` will be used if the `match` type is not specified.
:::

:::info
:bulb: **Tip:**
Search parameters are not case sensitive.
:::

###### Searching by Name

* The `n/` command checks whether a project has a certain name. There can be multiple names added to this command. For example, `n/car window` will check for the projects that contain `car` or `window` in their names.
* Searching by name only requires a partial match. This means that a project with the name `carpet` and `car` can potentially be the result of searching for the term `car`.

The following walkthrough shows how a user can use the find command to search for projects with the `ad` in their names.

1. Key in the command `find n/ad` in the command window.
![Find by Name Step 1](images/FindByName1.png)
2. The result box will display a message to indicate that the command has been executed successfully:
![Find by Name Step 2](images/FindByName2.png)
3. All projects that contain `ad` in their name will be shown.
![Find by Name Step 3](images/FindByName3.png)

###### Searching by Description

* The `d/` command checks whether a project has a certain description. There can be multiple descriptions added to this command. For example, `d/sunday october` will check for the projects that contain `sunday` or `october` in their description.
* Searching by description only requires a partial match, similar to searching by name.

The following walkthrough shows how a user can use the find command to search for projects with the `discussion` in their description.

1. Key in the command `find d/discussion` in the command window.
![Find by Description Step 1](images/FindByDescription1.png)
2. The result box will display a message to indicate that the command has been executed successfully:
![Find by Description Step 2](images/FindByDescription2.png)
3. All projects that contain `discussion` in their description will be shown.
![Find by Description Step 3](images/FindByDescription3.png)

###### Searching by Tag

* The `t/` command checks whether a project has a certain tag. There can be multiple tags added to this command. For example, `t/freelance errands` will check for the projects that contain the tags `freelance` or `errands`.
* Searching by tags will require a full word match unlike searching by name or description. This means that searching for the tag `free` will not find a project with the tag `freelance`.

:::info
:bulb: **Tip:**
Searches for tags require a full match whilst searches partial matches are sufficient for searches by name and description.
:::

The following walkthrough shows how a user can use the find command to search for projects with the tag `webdesign`.

1. Key in the command `find t/webdesign` in the command window.
![Find by Tag Step 1](images/FindByTag1.png)
2. The result box will display a message to indicate that the command has been executed successfully:
![Find by Tag Step 2](images/FindByTag2.png)
3. All projects that contain the tag `webdesign` will be shown.
![Find by Tag Step 3](images/FindByTag3.png)

###### Searching by Completion Status

* There are keywords, completed and incomplete for`c/KEYWORD`. Other keywords are not accepted.
* The `c/` command checks whether a project is completed. For example, `c/completed` will check for the projects that are completed.
* When `c/` is not specified, both complete and incomplete projects will be shown.

The following walkthrough shows how a user can use the find command to search for projects that have been completed.

1. Key in the command `find c/completed` in the command window.
![Find by Completion Step 1](images/FindByCompletion1.png)
2. The result box will display a message to indicate that the command has been executed successfully:
![Find by Completion Step 2](images/FindByCompletion2.png)
3. All projects that are completed will be shown.
![Find by Completion Step 3](images/FindByCompletion3.png)

###### Searching for Multiple Parameters

 //TODO: fix bolding
Example:

If there are 3 projects in the project book:

1. Name: `Create Logo` , Description: `Make logo for startup XYZ`, Tags: `Design`
2. Name: `Write Song`, Description: `80s rock music, three minutes`, Tags: `Music`
3. Name: `Write Article`, Description: `Write an article about why Momentum is the best app out there`, Tags: `Press` and `Writing`

* `find match/any n/song article d/startup t/design` will return all three projects. This is because project 1 contains the keyword `startup` in its description and the tag `design`, project 2 contains the keyword `song` in its name and project 3 contains the keyword `article` in its name.
* `find match/all n/song article d/startup t/design` will not return any project as there is no project with `song` **and** `article` in its name **and** the `startup` in its description and the tag `design`.
* `find match/none n/song article d/startup t/design` will not return any project as there is no project that **does not** contain `song` and `article` in its name, `startup` in its description and the tag `design`.
* `find match/any n/write d/rock` will return projects 2 and 3. This is because project 2 contains `write` in its name and `rock` in its description. Project 3 also contains the word `write` in its name.
* `find match/all n/write d/rock` will only return project 2. This is because project 2 is the only project that contains both `write` in its name and `rock` in its description.
* `find match/none n/write d/rock` will return projects 1. This is because only project 1 does not contain `write` in its name and `rock` in its descriptions.

:::info
:bulb: **Tip:**
If a certain search type is used more than once, the latest entry will be used.
`find n/a n/b n/c` will only search for projects/task that contain`c` in their name.
:::

### Time Tracking

You can track the time you spend working on a project or task by starting a timer when you start working, and then
 stopping the timer once you finish.

Momentum remembers each timer that you start/stop and uses this information to calculate statistics.

#### Starting a Timer for a Project: `start`

Format: `/start ID`

* Starts a timer for the project or task at the specified `ID`.
* Only 1 timer can be running for each project or task at any time.
* The id refers to the id number shown in the displayed list.
* The id **must be a positive integer** 1, 2, 3, …​

:::info
:bulb: **Tip:**
You can run timers for more than one project or task concurrently, if you are multi-tasking.
:::

:::info
:bulb: **Tip:**
You can run timers for a project separately from its tasks. This allows you to track the time you spent on the
 project as a whole, as well as the time spent on each individual task.
:::

Example: `/start 2`

Result: Starts a timer for the second project or task in the display list.

#### Stopping a Timer for a Project: `stop`

Format: `/stop ID`

* Stops a running timer for the project or task at the specified `ID`.
* A timer can only be stopped if there is one already running.
* The id refers to the id number shown in the displayed list.
* The id **must be a positive integer** 1, 2, 3, …​

Example: `/stop 2`

Result: Stops the timer for the second project or task in the display list.

#### Active Timers Panel

On the left side of the window is the Active Timers Panel. This is where Momentum shows you all the timers that are
 currently running.

![ActiveTimersUI](images/ActiveTimersUI.png)

This panel will show you the name of the project/task, as well as when the timer was started.

#### Time Tracking Example

Here is an example of how you can use the `start` and `stop` commands to track the time that you spend on a project.
Suppose that you are going to start working on the project "Alpharad Youtube Endcard", which is the first item shown
 below:

![TimerExample1](images/TimerExample1.png)

You can perform time tracking with the following steps:

1. To start the timer, type `start 1` into the command box and press `enter`.

![TimerExample2](images/TimerExample2.png)

1. You should see the following message in the result box, indicating that the timer has been successfully started.

![TimerExample3](images/TimerExample3.png)

1. You should also see the project's name and start time in the active timers panel:

![TimerExample4](images/TimerExample4.png)

1. Now that the timer has been successfully started, you can proceed to do your work. You can also choose to close
 Momentum. The timer will continue to run even when Momentum is closed. Proceed to the next step when you are done
  with your work.

1. To stop the timer, type `stop 1` into the command box and press `enter`.

![TimerExample5](images/TimerExample5.png)

1. You should see the following message in the result box, indicating that the timer has been successfully stopped.

![TimerExample6](images/TimerExample6.png)

1. You should also see that the project has been removed from the active timers panel:

![TimerExample7](images/TimerExample7.png)

1. The statistics panel (see [statistics](#statistics)) will also be updated to reflect the time that you have spent
 working on the
 project:

![TimerExample8](images/TimerExample8.png)

## Reminders

* A reminder will be shown in the reminder panel of the sidebar at the date and time specified.
* The reminder will be removed after it is shown in the sidebar.
* If a reminder was missed, it will be greyed out.
* Refer to [Walkthrough of Creating a Project](#walkthrough-of-creating-a-project) for more details on how a reminder will be shown.
* Refer to [Walkthrough of Dismissing a Reminder](#walkthrough-of-dismissing-a-reminder) for more details on hoe to dismiss a reminder.

:::danger
:warning: **Warning**
If there are multiple reminders set at the same date and time, only one of the reminders will appear in the reminder panel. Other reminders will be missed.
:::

## Undo/Redo

Undo command undoes previous commmand and redo command redoes previously undone command.

### Undoing the Previous Command: `undo`

The undo command resets the application to the state before previous command was executed.

Format: `/undo`

Example: `start 1`, `undo`

Result: Timer for project/task at index 1 is started, then stopped and removed after undo is executed.

### Redoing the Previous Command: `redo`

The redo command redoes previously undone command and resets the application to the state before the previous undo command.

Format: `/redo`

Example: `sort type/deadline`, `undo`, `redo`

Result: Projects are sorted by deadline, then the application is reset to the sorting order before sort command was executed, then reset back to sort by deadline after redo command.

:::info
:bulb: **Tip:**

* Undo/redo feature keeps track of changes in state, and hence will not work on `help` command which does not change the state of the application.
* Redo command only works if there the previous command is `undo`.

:::

## Statistics

Momentum uses the data collected from your timers (see [time tracking](#time-tracking)) to calculate statistics. These
 statistics are automatically generated and updated whenever you make any changes to your projects and tasks, such as
  when timers for a project/task are started or stopped.
 These statistics can be seen in a panel on the left side of the window, as shown here:

![StatisticsUI1](images/StatsUI1.png)

The statistics displayed correspond to the projects or tasks currently shown in the list, and will automatically
 change when the items in the list changes (such as when you find specific projects or sort the list of projects).

![StatisticsUI2](images/StatsUI2.png)

You do not need to use any additional commands to update or view the statistics.

### Timeframes

Momentum only tracks your statistics within a particular timeframe. By default, the timeframe will be set to weekly.
You can change the timeframe through the [settings](#settings).

Momentum allows you to track the time spent within these timeframes:

* Daily
* Weekly
* Monthly

Here are the statistics being tracked by Momentum:

### Time Spent Per Project

This statistic tells you the total amount of time you have spent within the timeframe. For projects, this includes
 all the time you have spent on each individual task, as well as on the project as a whole.

![StatsUISplit](images/StatsUISplit.png)

The statistics are displayed in 2 different formats:

1. **Pie Chart**: Shows the relative time spent on each project. Useful for comparing different projects and tasks. Projects
 and tasks with less time spent on them may not be shown.
2. **Table**: Shows the exact (in minutes) amount of time spent on each project. Useful for calculations.

## Settings

You can adjust various settings in Momentum, which for now includes:

* GUI Theme
* Statistic Timeframe

Format: `set [th/THEME] [st/TIMEFRAME]`

* At least one of the optional fields must be provided.
* There are two GUI themes available, light and dark. The commands to apply them are:

  * `th/light`
  * `th/dark`

* There are three available timeframes for statistics, daily, weekly and monthly. The commands to apply them are:

  * `st/daily`
  * `st/weekly`
  * `st/monthly`

Example: `set th/dark st/daily`
Result: Sets a dark theme to the GUI and changes the statistics pane to show the time spent on projects within the day.

## Clear All Projects/Tasks : `clear`

When viewing all projects, this command deletes all the projects in Momentum, including their tasks.

When viewing a specific project's tasks, this command will delete all the tasks in the project. The project will itself will not be deleted.
Removes all projects and tasks in Momentum.

:::danger
:warning: **Warning**
This command will also delete the saved data for the projects or tasks. You can undo this operation while Momentum remains open. However, you will not be able to undo this if once you close Momentum.
:::

Format: `clear`

## Dismissing a Reminder : `dismiss`

Format: `dismiss`

* If the reminder panel of the sidebar is not visible, it cannot be dismissed.

Result: Hides the reminder panel of the sidebar.

:::danger
:warning: **Warning**
A reminder that has been dismissed cannot be shown again in the reminder panel.
Undo a dismissal to show an expired reminder in the project.
:::

### Walkthrough of Dismissing a Reminder

This walkthrough is a followup of [Walkthrough of Creating a Project](#walkthrough-of-creating-a-project).

1. After the reminder panel is shown, type `dismiss` in the command box, and press the `Enter` key to execute it.
![Walkthrough of Dismissing a Reminder Step 1](images/DismissDiagram1.png)
1. The result box will display a message to indicate that the command has been executed successfully.
![Walkthrough of Dismissing a Reminder Step 2](images/DismissDiagram2.png)
1. The reminder panel is now hidden.
![Walkthrough of Dismissing a Reminder Step 3](images/DismissDiagram3.png)

## Showing and Hiding SideBar Components : `show`

You can hide or show the tags panel of the sidebar.

Format: `show t/`

* `t/` would hide the tags if the tags panel was shown.
* `t/` would show the tags if the tags panel was hidden.

Result: Shows or Hides the tags panel of the sidebar.

### Walkthrough of Hiding Tags

1. Type `show t/` in the command box, and press the `Enter` key to execute it.
![Walkthrough of Hiding Tags Step 1](images/ShowDiagram1.png)
2. The result box will display a message to indicate that the command has been executed successfully:
   `Tags is removed from the sidebar.`
![Walkthrough of Hiding Tags Step 2](images/ShowDiagram2.png)
3. The tags panel is now hidden.
![Walkthrough of Hiding Tags Step 3](images/ShowDiagram3.png)

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

## Exiting the Program : `exit`

All project, task and timer data are saved automatically after every command. There is no need to save manually.

You can find the saved data in the following file in the same location where Momentum is located: `data/projectbook.json`

Format: `exit`

Result: Exits the program.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer? <br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Momentum home folder.

--------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------

## Glossary

* **Command Line Interface(CLI)**: Command Line Interface processes commands in the form of text
* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Project View**: View all projects in the project book.
* **Task View**: View all tasks that are added to a single project.
--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action | Format | Example
--------|-------|-----------
**View tasks in a project**| `view ID` |`view 3`
**View all projects**| `home` | `home`
**Creating a Project/Task** | `add n/NAME [d/DESCRIPTION] [c/] [dd/DEADLINE_DATE [dt/DEADLINE_TIME]] [r/REMINDER_DATE_TIME] [t/TAG]​`|  `project n/Momentum d/CS2103T Team Project dd/2021-12-07 t/impt`
**Editing a Project/Task** | `edit ID [n/NAME] [d/DESCRIPTION] [c/]  [dd/DEADLINE_DATE [dt/DEADLINE_TIME]] [r/REMINDER_DATE_TIME] [t/TAG]`| `edit 3 n/NewMomentum d/NewDescription dl/2021-12-07 r/2021-12-07T01:21:21 t/normal`
**Delete a project/task** | `delete ID` | `delete 3`
**Find a project/task** | `find [match/FILTER_TYPE] [n/NAME [MORE_NAMES]...] [d/DESCRIPTION [MORE_DESCRIPTIONS]...] [t/TAG [MORE_TAGS]...]  [c/]`  | `find match/any n/Momentum d/new t/normal`
**Show All projects/tasks** | `list` | `list`
**Start Timer** | `start ID` | `start 2`
**Stop Timer** | `stop ID` | `stop 2`
**Undo/redo** | `undo` | `redo`
**Dismissing a Reminder** | `dismiss` | `dismiss`
**Showing and Hiding SideBar Components** | `show t/` | `show t/`
**Settings** | `set [th/THEME] [st/TIMEFRAME]` | `set th/dark st/daily`
**Exit** | `exit` | `exit`
