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