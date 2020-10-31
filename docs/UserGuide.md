---
layout: page
title: User Guide
---

Momentum is a **desktop app** that **helps freelancers track time spent on different projects** and **gain insights on how their time is spent**. 

It is designed for people that prefer typing, so that frequent tasks can be done faster by typing in commands.

*include purpose of the document* e.g. this user guide contains all the commands you need to be able to use the product effectively

*terminology used*

* Table of Content
{:toc}

--------------------------------------------------------------------------------------------------------------------


<!-- # Table of contents
[1. Quick Start](#quick-start)
    [2. Features](#2-Features)
[3. Projects and Tasks](#3-Projects-and-Tasks)
    [3.1. View Projects: home](#31-View-Projects-home)
&nbsp;&nbsp;&nbsp;&nbsp; [3.2. Viewing a Project’s Tasks: view](#view)
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[3.3. Creating a Project/Task: add](#add)
    [3.4. Editing a Project/Task: edit](#edit)
    [3.5. Deleting a Project/Task: delete](#delete)
    [3.6. Project/Task Organisation](#organisation)
    [3.7. Time Tracking](#time-tracking)
    [3.8. Undo/Redo](#undoredo)
    [3.9. Statistics](#statistics)
    [3.10. Settings](#settings)
    [3.11. Clear All Projects/Tasks: clear](#clear)
    [3.12. Show and Hide SideBar Components : show](#show)
    [3.13. Exiting the Program : exit](exit)
[4. FAQ](#Faq)
[5. Glossary](#Glossary)
    [5.1. Date and Time Terms](#datetime)
[6. Command Summary](#command-summary)
 -->

## 1. Quick Start<a name="quick-start"></a>

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `momentum.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for Momentum.

4. Double-click the file to start Momentum. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`add`**`n/Momentum d/CS2103T Team Project` : Adds a project named `Momentum` to the Project Book.

   * **`edit`**`3 n/NewMomentum d/newDescription` : Update the 3rd project in the current list. The name will be changed to “NewMomentum” and the description will be changed to “NewDescription”.
   
   * **`find`**`n/NewMomentum` : Find a project that has `NewMomentum` in its name.

   * **`delete`**`3` : Deletes the 3rd project shown in the current list.
   
   * **`sort`**`type/deadline order/asc` : Sorts the list of projects by deadline in ascending order.
   
   * **`undo`** : Undoes the previous command (Sort command is revoked).

   * **`exit`** : Exits the app.

Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Command Format
Below is an explanation of the formatting used to show commands:

* Words in `UPPER_CASE` are the parameters to be supplied by the user.
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.
* Items in square brackets are optional.
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
* Items with `…`​ after them can be used multiple times including zero times.
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.
* Items with `…` inside square brackets `[t/TAG [MORE_TAGS]...]` can take in multiple space separated arguments
  e.g. `[[t/TAG [MORE_TAGS]...]` can represent `t/friend friend family` 
* Parameters can be in any order.
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

## Date and Time Terms
Dates and Times are an integral part of Momentum, and you will typing dates and times regularly. Here is a guide to the format for entering dates and times in Momentum.

### Date Terms
Dates should be entered in the order `YYYY-MM-DD`

Term | Meaning | Example
-----|-------- | -------
`YYYY` | Year    | 2020
`MM`   | Month   | 02
`SS`   | Day     | 09

**Valid**: 2020-08-02 

**Invalid**: 2-8-20: Wrong number of digits.
**Invalid**: 02-08-20: Wrong order of year, month and date.
**Invalid**: 2nd August 2020: You cannot use text to enter dates.

### Time Terms
Times should be entered in 24 hour format, in the order `HH:MM:SS`

Term | Meaning | Example
-----|-------- | -------
HH   | Hour    | 16
MM   | Minute  | 52
SS   | Second  | 03
 
**Valid**: 15-08-02 

**Invalid**: 2-8-20: Wrong number of digits.
**Invalid**: 02-08-20: Wrong order of year, month and date.
**Invalid**: 2nd August 2020: You cannot use text to enter dates.

## 2. Features<a name="2-Features"></a>

<!-- ### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help` -->

## 3. Projects and Tasks<a name="3-Projects-and-Tasks"></a>
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

<div markdown="block" class="alert alert-info">

**:information_source: Most commands in Momentum will do different things depending on whether you are viewing projects or tasks.**<br>
Please refer to each command for these differences.

</div>

### 3.1 View Projects: `home`<a name="#31-View-Projects-home"></a>

View all the projects being tracked by Momentum.
This is the default view when Momentum is first opened.

Format: `home`

### 3.2 Viewing a Project's Tasks: `view`<a name="#32-Viewing-a-Project's-Tasks-view"></a>
View the tasks for a project.

Format: `view ID`

* The id refers to the id number shown in the displayed project list.
* The id **must be a positive integer** 1, 2, 3, …​

Example: `view 1`

### 3.3 Creating a Project/Task: `add`

When looking at projects, this command will create a new project. When looking at the tasks in a project, thsi command will create a new task for the project.

Format: `add n/NAME [d/DESCRIPTION] [c/] [dd/DEADLINE_DATE] [dt/DEADLINE_TIME] [r/REMINDER_DATE_TIME] [t/TAG]`

* The project is incomplete by default, adding `/c` will set the completion status to complete. 
* The format for date of the deadline is YYYY-MM-DD, refer to [Date Terms](#Date-Terms) for more information on YYYY, MM and DD.
* The format for time of the deadline is HH:MM:SS in 24 hour format, refer to [Time Terms](#Time-Terms) for more information on HH, MM and SS.
* The date of the deadline cannot be earlier than the creation date of the project.
* Both date and time is compulsory for a reminder.
* The format for date and time of the reminder is YYYY-MM-DDTHH:MM:SS, refer to [Date and Time Terms](#Date-and-Time-Terms) for more information on YYYY, MM, DD, HH, MM, and SS. 
* The date and time of the reminder needs to be later than the current time.

<div markdown="block" class="alert alert-primary">

:bulb: **Tip:**<br>
* Projects and tasks can have any number of tags (including 0).
* A deadline of a project can include time.
* A project can have an empty description.
* `T` separates the date and time in a reminder.
* A reminder will be shown in the Reminder component of the sidebar at the date and time specified.
* The reminder will be removed after it is shown in the sidebar.

</div>

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

</div>

Example: `add n/Momentum d/CS2103T Team Project dd/2020-12-07 dt/11:01:12 r/2020-12-07:11:01:12 t/impt`

Result: Creates a project named “Momentum” with a description “CS2103T Team Project”, a tag "impt", deadline date "2020-10-07" with deadline time "11:01:12" and reminder "2020-10-07T11:01:12".

### 3.4 Editing a Project/Task: `edit`

Edits a project or task that was been previously created.

Format: `edit ID [n/NAME] [d/DESCRIPTION] [c/] [dd/DEADLINE_DATE [dt/DEADLINE_TIME]] [t/TAG]`

* The id refers to the id number shown in the displayed list.
* The id **must be a positive integer** 1, 2, 3, …​
* Adding `/c` will reverse the completion status, if the project was incomplete the completion status will change to complete. 
* The format for date of the deadline is YYYY-MM-DD, refer to [Date Terms](#Date-Terms) for more information on YYYY, MM and DD.
* The format for time of the deadline is HH:MM:SS in 24 hour format, refer to [Time Terms](#Time-Terms) for more information on HH, MM and SS.
* The date of the deadline cannot be earlier than the creation date of the project.
* Both date and time is compulsory for a reminder.
* The format for date and time of the reminder is YYYY-MM-DDTHH:MM:SS, refer to [Date and Time Terms](#Date-and-Time-Terms) for more information on YYYY, MM, DD, HH, MM, and SS. 
* The date and time of the reminder needs to be later than the current time.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* Removing a description or deadline by typing `d/` or `dd/` without specifying anything after it.
* When editing tags, the existing tags of the project will be removed i.e adding of tags is not cumulative.
* You can remove all the project’s tags by typing `t/` without specifying any tags after it.

Example: `project 3 n/NewMomentum d/NewDescription dd/2020-12-07 t/normal`

Result: Updates the project with id 3. The name will be changed to “NewMomentum”, the description will be changed to “NewDescription”, all the tags will be removed and a tag named normal is added, and the deadline will be changed to "2020-12-07".

### 3.5 Deleting a Project/Task: `delete`

Deletes a project or task in the list.

Format: `delete ID`

* Deletes the project at the specified `PROJECT_ID`.
* The id refers to the id number shown in the displayed project list.
* The id **must be a positive integer** 1, 2, 3, …​

Example: `delete 2`

Result: Deletes the second project in the list.

### 3.6 Project/Task Organisation

#### 3.6.1 View All Projects : `list`

When viewing projects, this command shows a list of all projects in Momentum.

When viewing a project's tasks, this command shows a list of all the tasks for the project.

Format: `list`

#### 3.6.2 Sort Projects : `sort`

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
    * Add `c/` to disable this and sort without taking into account of completion status.
   
<div markdown="span" class="alert alert-primary">

:bulb: **Tip:**<br>
* `type/alpha` and `order/asc` will be used as default if both sort type and order are not specified (i.e. command is `sort`)
* Current sort type will be used if the `type` is not specified but `order` is specified.
* `order/asc` will be used as default if the `order` is not specified but `type` is specified.
* For `sort type/deadline`, projects without deadlines will be ordered alphabetically after the ordered list of projects with deadlines.
* For both `sort type/deadline` and `sort type/created`, projects with same deadline or same created date will be sorted alphabetically.

</div>

Example:

The following are 3 projects in the project book.

Project 1. Name: `Ant Hole`, Deadline: `2020-02-02`, Created Date: `2000-02-02`
Project 2. Name: `Brunch`, Deadline: `2010-01-01`, Created Date: `2002-09-09`
Project 3. Name: `Create Logo` , Deadline: `2040-04-04`, Created Date: `2001-01-01`

**3.6.2.3 Sorting by Default order**

Format: `sort`

* Sorts projects in alphabetical, ascending order

Result: [Project 1, Project 2, Project 3]

##### Sorting With Only Type Specified 

Format: `sort type/SORT_TYPE`

* Sorts projects in a specified order
* Since order is not specified, default order is ascending

Example: `sort type/alpha`
Result: [Project 1, Project 2, Project 3]

Example: `sort type/deadline`
Result: [Project 2, Project 1, Project 3]

Example: `sort type/created`
Result: [Project 1, Project 3, Project 2]

#### Sorting With Only Order Specified 

Format: `sort order/SORT_ORDER`

* Sorts projects in current project order.
* If there is no existing project order (when the application restarts), order will be alphabetical by default.

Example: `sort order/dsc` (After application restarts for the first time)
Result: [Project 3, Project 2, Project 1]

Example `sort order/asc` (Current sort type is Deadline)
Result: [Project 2, Project 1, Project 3]

#### Sorting With Both Type and Order Specified

* Sorts projects in specified type and order.
* Projects that cannot be ordered in a certain type will be ordered alphabetically.

Example: `sort type/alpha order/dsc`
Result: [Project 3, Project 2, Project 1]

Example: `sort type/deadline order/asc`
Result: [Project 2, Project 1, Project 3]

Example: `sort type/created order/dsc`
Result: [Project 2, Project 3, Project 1]

#### Filtering Projects: `find`

Searches for projects or tasks in the project book based on certain parameters.

Format: `find [match/FILTER_TYPE] [n/NAME [MORE_NAMES]...] [d/DESCRIPTION [MORE_DESCRIPTIONS]...] [t/TAG [MORE_TAGS]...] [c/COMPLETION_STATUS]`

* There are two values for the `match` command.
* `match/all` requires **all** parameters to match their respective entries in the project for it to be shown.
* `match/any` shows the project as long as any parameter matches the user's input.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:**
You can only search for projects in the project view and tasks in the tasks view

</div>

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:**
`match/any` will be used if the `match` type is not specified.

</div>

Example:

If there are 3 projects in the project book:

1. Name: `Create Logo` , Description: `Make logo for startup XYZ`, Tags: `Design`
2. Name: `Write Song`, Description: `80s rock music, three minutes`, Tags: `Music`
3. Name: `Write Article`, Description: `Write and article about why Momentum is the best app out there`, Tags: `Press` and `Writing`

* `find match/any n/song article d/startup t/design` will return all three projects. This is because project 1 contains the keyword `startup` in its description and the tag `design`, project 2 contains the keyword `song` in its name and project 3 contains the keyword `article` in its name. 
* `find match/all n/song article d/startup t/design` will not return any project as there is no project with `song` **and** `article` in its name **and** the `startup` in its description and the tag `design`.
* `find match/any n/write d/rock` will return projects 2 and 3. This is because project 2 contains `write` in its name and `rock` in its description. Project 3 also contains the word `write` in its name.
* `find match/all n/write d/rock` will only return project 2. This is because project 2 is the only project that contains both `write` in its name and `rock` in its description. 

##### Searching by name: 
* The `n/` command checks whether a project has a certain name. There can be multiple names added to this command. For example, `n/car window` will check for the projects that contain `car` or `window` in their names.
* Searching by name only requires a partial match. This means that a project with the name `carpet` and `car` can potentially be the result of searching for the term `car`.

##### Searching by description: 
* The `d/` command checks whether a project has a certain description. There can be multiple descriptions added to this command. For example, `d/sunday october` will check for the projects that contain `sunday` or `october` in their description.
* Searching by description only requires a partial match, similar to searching by name.

##### Searching by tag: 
* The `t/` command checks whether a project has a certain tag. There can be multiple tags added to this command. For example, `t/freelance errands` will check for the projects that contain the tags `freelance` or `errands`.
* Searching by tags will require a full word match unlike searching by name or description. This means that searching for the tag `free` will not find a project with the tag `freelance`.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:**
Searches for tags require a full match whilst searches partial matches are sufficient for searches by name and description.

</div>

#### Searching by Completion Status

* There are keywords, completed and incomplete for`c/KEYWORD`. Other keywords are not accepted.
* The `c/` command checks whether a project is completed. For example, `t/completed` will check for the projects that are completed.
* When `c/` is not specified, both complete and incomplete projects will be shown.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:**<br>
If a certain search type is used more than once, the latest entry will be used.
`find n/a n/b n/c` will only search for projets/task that contain`c` in their name.

</div>

## Time Tracking
You can track the time you spend working on a project by starting a timer when you start working, and then stopping the timer once you finish.

Momentum remembers each timer that you start/stop and uses this information to calculate statistics.

### Starting a Timer for a Project: `start`

Format: `start PROJECT_ID`

* Starts a timer for the project at the specified `PROJECT_ID`.
* Only 1 timer can be running for a project at any time.
* The id refers to the id number shown in the displayed project list.
* The id **must be a positive integer** 1, 2, 3, …​

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:**
You can run timers for more than one project concurrently, if you are multi-tasking.

</div>

Example: `start 2`

Result: Starts a timer for the second project in the list.

### Stopping a Timer for a Project: `stop`

Format: `stop PROJECT_ID`

* Stops a running timer for the project at the specified `PROJECT_ID`.
* A timer can only be stopped if there is one already running.
* The id refers to the id number shown in the displayed project list.
* The id **must be a positive integer** 1, 2, 3, …​

Example: `stop 2`

Result: Stops the timer for the second project in the list.

## Undo/Redo
Undo command undoes previous commmand and redo command redoes previously undone command.

### Undoing the Previous Command: `undo`
The undo command resets the application to the state before previous command was executed.

Format: `undo`

Example: `start 1`, `undo`

Result: Timer for project/task at index 1 is started, then stopped and removed after undo is executed.


### Redoing the Previous Command: `redo`
The redo command redoes previously undone command and resets the application to the state before the previous undo command.

Format: `redo`

Example: `sort type/deadline`, `undo`, `redo`

Result: Projects are sorted by deadline, then the application is reset to the sorting order before sort command was executed, then reset back to sort by deadline after redo command.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:**<br>
* Undo/redo feature keeps track of changes in state, and hence will not work on `help` command which does not change the state of the application.
* Redo command only works if there the previous command is `undo`.

</div>

## Statistics
Statistics are automatically generated and updated whenever projects are 
added/deleted/changed, and when timers are started/stopped. They can be seen in the bottom left of the window. You do not need to use any additional commands to update or view statistics.

Here are the statistics being tracked by Momentum:
### Time Spent Per Project
This statistic tells you the total amount of time you have spent within a timeframe. By default, the timeframe will be set to weekly. You can change the timeframe through the [settings](#settings).

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
Removes all projects and tasks in Momentum.

:::danger
:warning: **Warning**
This will remove **everything** in Momentum, including all saved data.
:::

Format: `clear`

## Show and Hide SideBar Components : `show`
You can hide or show compoenents in the sidebar.

The sidebar has four components:

* Reminder
* Tags
* Timers
* Time Spent

Currently, only the Reminder component is supported.

Format: `show [r/]`
* At least one of the optional fields must be provided.
* `r/` would dismiss the reminder by hiding the reminder component.

Example: `show r/`
Result: Hides the Reminder component of the sidebar.

:::danger
:warning: **Warning**
A reminder that has been dismissed cannot be shown again.
:::

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

Term | Meaning
-----|--------
GUI  | Stands for Graphical User Inferface. It is the interface of the application which you would interact with. 

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action | Format | Example
--------|-------|-----------
**View tasks in a project**| `view ID` |`view 3`
**View all projects**| `home` | `home `
**Create new project/task** | `add n/NAME [d/DESCRIPTION] [c/] [dd/DEADLINE_DATE [dt/DEADLINE_TIME]] [r/REMINDER_DATE_TIME] [t/TAG]​`|  `project n/Momentum d/CS2103T Team Project dd/2020-12-07 t/impt`
**Edit existing project/task** | `edit ID n/NAME [d/DESCRIPTION] [c/]  [dd/DEADLINE_DATE [dt/DEADLINE_TIME]] [r/REMINDER_DATE_TIME] [t/TAG]`| `edit 3 n/NewMomentum d/NewDescription dl/2020-12-07 r/2020-12-07T01:21:21 t/normal`
**Delete a project/task** | `delete ID` | `delete 3`
**Find a project/task** | `find [match/FILTER_TYPE] [n/NAME [MORE_NAMES]...] [d/DESCRIPTION [MORE_DESCRIPTIONS]...] [t/TAG [MORE_TAGS]...]  [c/]`  | `find match/any n/Momentum d/new t/normal`
**Show All projects/tasks** | `list` | `list`
**Start Timer** | `start ID` | `start 2`
**Stop Timer** | `stop ID` | `stop 2`
**Undo/redo** | `undo` | `redo`
**Show and Hide Sidebar Components** | `show [r/]` | `show r/`
**Settings** | `set [th/THEME] [st/TIMEFRAME]` | `set th/dark st/daily`
**Exit** | `exit` | `exit`
