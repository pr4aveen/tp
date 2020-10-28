---
layout: page
title: User Guide
---

Momentum is a **desktop app** that **helps freelancers track time spent on different projects** and **gain insights on how their time is spent**. 

It is designed for people that prefer typing, so that frequent tasks can be done faster by typing in commands.

*include purpose of the document* e.g. this user guide contains all the commands you need to be able to use the product effectively

*terminology used*

[TOC]

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

:::info

**:information_source: Most commands in Momentum will do different things depending on whether you are viewing projects or tasks.**<br>
Please refer to each command for these differences.

:::

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

:::info
:bulb: **Tip:**
* Projects and tasks can have any number of tags (including 0).
* A deadline of a project can include time.
* A project can have an empty description.
* `T` separates the date and time in a reminder.
* A reminder will be shown in the Reminder component of the sidebar at the date and time specified.
* The reminder will be removed after it is shown in the sidebar.
:::


Example: `add n/Momentum d/CS2103T Team Project dd/2020-12-07 dt/11:01:12 r/2020-12-07:11:01:12 t/impt`

Result: Creates a project named “Momentum” with a description “CS2103T Team Project”, a tag "impt", deadline date "2020-10-07" with deadline time "11:01:12" and reminder "2020-10-07T11:01:12".

### 3.4 Editing a Project/Task: `edit`