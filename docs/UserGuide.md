---
layout: page
title: User Guide
---

Momentum is a **desktop app** that **helps freelancers track time spent on different projects** and **gain insights on how their time is spent**. It is optimized for **Command Line Interface(CLI) users** so that frequent tasks can be done faster by typing in commands.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `momentum.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your ProjectBook.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`project`**`n/Momentum d/CS2103T Team Project` : Adds a project named `Momentum` to the Project Book.

   * **`edit`**`3 n/NewMomentum d/newDescription` : Update the 3rd project in the current list. The name will be changed to “NewMomentum” and the description will be changed to “NewDescription”.

   * **`delete`**`3` : Deletes the 3rd project shown in the current list.

   * **`/exit`** : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

</div>

<!-- ### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help` -->

### Creating a Project: `project`

Create a project to be tracked by the application.

Format: `project n/NAME [d/DESCRIPTION] [dl/DEADLINE] [t/TAG]`

_deadline field is coming soon_

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A project can have any number of tags (including 0)
</div>

Example: `project n/Momentum d/CS2103T Team Project dl/2020-12-07 t/impt`

Result: Creates a project named “Momentum” with a description “CS2103T Team Project”, a tag "impt" and deadline 2020-10-07.

### Editing a Project: `edit`

Edit a project that has been previously created.

Format: `edit PROJECT_ID [n/NAME] [d/DESCRIPTION] [dl/DEADLINE] [t/TAG]`

_deadline field is coming soon_

* Edits the project at the specified `PROJECT_ID`.
* The id refers to the id number shown in the displayed project list.
* The id **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* Removing a description or deadline by typing `d/` or `dl/` without specifying anything after it.
* When editing tags, the existing tags of the project will be removed i.e adding of tags is not cumulative.
* You can remove all the project’s tags by typing `t/` without specifying any tags after it.

Example: `project 3 n/NewMomentum d/NewDescription dl\2020-12-07 t\normal`

Result: Updates the project with id 3. The name will be changed to “NewMomentum”, the description will be changed to “NewDescription”, all the tags will be removed and a tag named normal is added, and the deadline will be changed to 2020-12-07.

### Deleting a Project: `delete`

Deletes a project in the list.

Format: `delete PROJECT_ID`

* Deletes the project at the specified `PROJECT_ID`.
* The id refers to the id number shown in the displayed project list.
* The id **must be a positive integer** 1, 2, 3, …​

Example: `delete 2`

Result: Deletes the second project in the list.

### View

#### View All Projects : `list`

Shows a list of all projects in the project book.

Format: `list`

#### View a Project (_coming soon_)

Format: `\p PROJECT_ID`

* View the project at the specified `PROJECT_ID`.
* The id refers to the id number shown in the displayed project list.
* The id **must be a positive integer** 1, 2, 3, …​

Example: `\p 2`

Result: Navigates to the project page of the second project in the list.

#### View Home (_coming soon_)

Go to home page.

Format: `\home`

#### View Settings (_coming soon_)

Go to settings page.

Format: `\settings`

#### Filtering Projects: `find` (_coming soon_)

Finds projects.

### Exiting the Program : /`exit`
ProjectBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

Format: `/exit`

Result: Exits the program.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer? <br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Momentum home folder.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action | Format, Examples
--------|------------------
**Create** | `project n/NAME [d/DESCRIPTION] [dl/DEADLINE] [t/TAG]​` <br> e.g., `project n/Momentum d/CS2103T Team Project dl/2020-12-07 t/impt`
**Edit** | `edit PROJECT_ID n/NAME [d/DESCRIPTION] [dl/DEADLINE] [t/TAG]` <br> e.g., `project 3 n/NewMomentum d/NewDescription dl\2020-12-07 t\normal`
**Delete** | `delete PROJECT_ID` <br> e.g., `delete 3`
**Navigation** (_coming soon_) | `list` <br> `/project PROJECT_ID` <br> e.g., `\p 2` <br> `/settings`  
**Exit** | `/exit`
