## Momentum

Momentum is a project management application targeted at freelancers to help them better understand their time usage.

Given below are my contributions to the project.

* **New Feature**: Added undo/redo feature.[\#117](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/117)
  * What it does: It allows the user to undo past commands executed in the application. Undone commands can be redone through the redo command to recover the state of the application before undo command was executed.
  * Justification: This feature improves the application and hence user experience considerably as users are now able to undo commands they executed by mistake with a single command without having to think of other ways to reverse the command.
  The redo command is also useful in cases where users want to undo a command to take a look at the previous state of the application, then revert back to the most recent state of the application before undo command was executed.
  * Highlights: This enhancement affects existing and future features as it requires strict immutability and hence restricts the design of the code. The implementation too was challenging as in order for undo/redo to work, implementation of other features had to be altered to ensure immutability of the ProjectBook on different levels. Not only was it challenging to implement, it was one of the features that had the most bugs as well, as it was very easy to break the immutability of objects, and I had to be involved in bug fixing very often.
  * Credits: The implementation idea was adapted from AddressBook [AddressBook Link](https://github.com/nus-cs2103-AY2021S1/tp.git)

* **New Feature**: Added sort feature.[\#53](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/53)
  * What it does: It allows the user to sort projects by different sort types and orders.
  * Justification: This feature improves the application and user experience as users are able to organize projects and view them in a more convenient manner according to their needs. It is also a great enhancement to the application as it helps users manage tasks more efficiently by allowing them to sort by deadline, allowing them to prioritise projects wisely. It is an advanced sort feature where different sort types can be imposed on the list together (eg. Completion status order and deadline order), and those that cannot be compared by a certain attribute (e.g. deadline) can be compared by another feature such as name.
  * Highlights: The implementation was tricky as some projects do not have fields to be compared with other projects (i.e. deadline field is optional) and hence had to think of ways to take this into account. I decided to combine Comparator.nullsLast method with another comparator to push the projects with empty fields to the end and sort them using another comparator (namely NameCompare). 

* **New Feature**: Added pie chart.[\#216](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/216)
  * What it does: It provides users with a visual representation of the statistics in the application.
  * Justification: This feature improves user experience and User Interface quality as users are able to gain insights on their time spent on each project faster with the pie chart serving as a visual aid.It also improves the quality of UI significantly.
  * Highlights: The implementation was tricky due to javafx limitations in sizing and design.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=kkangs0226&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Managed releases `v1.3.1` (1 release) on GitHub
  * Actively managed issues, especially after PE
  * 

* **Enhancements to existing features**:
  * Added Description field to projects (Pull requests [\#24](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/24))
  
* **Tests**:
  * Added tests for the `sort` command [#267](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/267), [#242](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/242)
  * Added tests for `undo` & `redo` commands [#267](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/267), [#242](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/242)

* **Documentation**:
  * User Guide:
    * Added documentation for the features `sort` and `undo/redo` [\#129](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/129)
    * Added numbering to existing user guide
  * Developer Guide:
    * Added documentation for the Architecture of `Model`, `sort` and `undo/redo`
    * Added use cases

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#38](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/38)[\#78](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/78)
  * Reported bugs and suggestions for other teams in the class (examples: [Mock PE](https://github.com/kkangs0226/ped/issues))
