---
layout: page
title: Cheong Ying Yi Clara's Project Portfolio Page
---

## Project: Momentum

Momentum is a project management application targeted at freelancers to help them better understand their time usage.

Given below are my contributions to the project.

* **New Feature**: Added created date to project. [\#38](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/38)
  * What it does: It allows the user to track the creation date of a project.
  * Justification: This feature would enable users to sort projects by its creation date after sorting is implemented and easily visualise the progress of a project and plan accordingly.

* **New Feature**: Added deadline to project. [\#38](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/38) [\#78](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/78)
  * What it does: It allows the user to track the deadline of a project.
  * Justification: This feature would enable users to sort projects by its deadline after sorting is implemented and easily visualise the deadline of a project and plan accordingly.

* **New Feature**: Added completion status to project. [\#88](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/88) [\#200](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/200) [\#243](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/243)
  * What it does: It allows the user to track the progress of the project, and sort and find projects based on the completion status of the project.
  * Justification: Users can easily visualise the completion status of a project and plan accordingly.
  * Highlights:
    * Added support for users to easily change the status of the project by inversing the status instead of specifying it.
    * Sorting by completion status is persisted.

* **New Feature**: Added reminder to project. [\#90](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/90) [\#95](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/95) [\#231](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/231)
  * What it does: Reminder will be shown in a reminder panel of the sidebar when the date and time of the reminder is reached. Added support to hide the reminder panel using a dismiss command.
  * Justification: Users can generate reminders for important tasks and projects.
  * Highlights:
    * It is not possible to run threads directly in javaFX and it is not possible to run runnables on the javaFX platform for automated tests. Considerations were made to switch the way reminders are run when platforms are different.
    * A new class `ReminderManager` was added to the model to manage the scheduling and running of the reminders.
    * A new panel is added to the sidebar to show reminders.
    * Listeners and properties are used to ensure that the GUI updates when a reminder runs or when the dismiss command is executed.

* **New Feature**: Added show command. [\#212](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/212)
  * What it does: Show or hide the tags pane.
  * Highlights:
    * Listeners and properties are used to ensure that the GUI updates when a show command is executed.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=claracheong4&sort=groupTitle&sortWithin=title&since=2020-08-14&until=2020-11-09&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=claracheong4&tabRepo=AY2021S1-CS2103T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

* **Enhancements to existing features**:
  * Rename addressbook to momentum. [\#12](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/12)
  * Added a `JsontoModel` class to convert json fields to fields in model for classes in the `storage` module. [\#90](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/90)
  * Updated dummy data. [\#127](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/127)
  * Wrote additional tests to increase coverage. [\#205](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/205) [\#208](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/208)
  * Bug Fixes:
    * Fix scheduling of reminders. [\#99](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/99)
    * Fix bug related to dismiss command. [\#109](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/99)
    * Fix bug related to maintenance of sort order. [\#229](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/229)

* **Documentation**:
  * User Guide:
    * Added command summary. [\#13](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/13)
    * Added documentation for the features timer(`start`, `end`) and tags. [\#13](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/13)
    * Added documentation for created date, deadline, completion status, reminder, show and dismiss command. [\#265](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/265)
    * Added documentation for date and time terms. [\#265](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/265)
    * Added walkthroughs for add project, edit task, dismiss reminder and hide tags. [\#269](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/269)
    * Update sort and find command documentation related to completion status. [\#265](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/265)
  * Developer Guide:
    * Update Storage section of Design. [\#263](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/263)
    * Added design considerations for deadline and reminder. [\#263](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/263)
    * Added user stories for timer, created date, deadline, completion status, reminder, dismiss and show command. [\#15](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/15)
    * Added use case for timer `start` and `end`, adding a project, dismiss a reminder and hide tags. [\#15](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/15) [\#263](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/263)

* **Community**:
  * [PRs reviewed](https://github.com/AY2021S1-CS2103T-T10-1/tp/pulls?q=is%3Apr+reviewed-by%3Aclaracheong4)
  * Reported bugs and suggestions for other teams in the class. (examples: [PED](https://github.com/claracheong4/ped/issues))
