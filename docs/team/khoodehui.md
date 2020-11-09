---
layout: page
title: Khoo De Hui's Project Portfolio Page
---

## Project: Momentum

Momentum is a project management application targeted at freelancers to help them better understand their time usage. It is a Java application based on [AddressBook-Level3](https://github.com/se-edu/addressbook-level3). 

Given below are my contributions to the project. Code I have written can be found [here](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=khoodehui).

### Contributions

* **New Feature**: Added the ability to change settings in the application. [#119](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/119)
  * **What it does**: Allows the user to adjust some settings in the application, namely the timeframe of the statistics to show, and the theme of the application.
  * **Justification**: The settings feature enables the user to personalise the application to them, thereby offering a better user experience. In addition, being able to change the statistics timeframe can allow the user to have much better insights on how they are spending their time as well. 
  * **Highlights**: The implementation requires a good understanding on how the application handles user preferences and how the data is being stored and read. In addition, careful design considerations have to be made as well to ensure that the overall OOP integrity of the code base is maintained.

* **Enhancements to existing features**: Redesigned the GUI of the application. [#45](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/45) [#58](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/58)
  * Changes made: Redesigned the layout of the GUI, as well as come up with 2 attractive colour schemes for it.
  * Justification: Redesigning the GUI layout is necessary in order for it to be able to display all the information that we want our users to see. In addition, we believe that an attractive GUI will offer a great user experience, which can attract new users as well as ensure existing users will want to continue using the application.
  * Highlights: This enhancement was challenging as it required an in-depth analysis of how the program interacts with the GUI, as well as an in-depth understanding of how JavaFX works. Much effort was also put into styling the GUI, with the different stylesheets entirely written from scratch. A good understanding of JavaFX CSS was required as well.

* **Enhancements to existing features**: Assisted in the implementation of undo and redo for settings commands. [#230](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/230)
  * Changes made: Built up on the undo and redo feature done by a teammate to allow settings commands to be undone and redone as well.
  * Justification: As the application is primarily CLI based, and allowing the user revert settings using a quick undo or redo command will be beneficial as well.

* **Enhancements to existing features**: Enhanced statistics feature to allow for different timeframes for statistic calculation. [#119](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/119)
  * Changes made: Originally, the application can only calculate weekly statistics. I modified it so that daily and monthly statistics can be calculated as well.
  * Justification: Allowing different timeframes of statistics to be calculated gives the user more insight to how they are spending their time.

* **Project management**:
  * Managed releases `v1.3.trial` - `v1.3` (2 releases) on GitHub

* **Documentation**:
  * User Guide:
    * Added documentation for the `settings` feature.
    * Included UI theme in the UI overview section.
  * Developer Guide:
    * Update the UI class diagram to represent new UI implementation.
    * Added implementation details of the `settings` feature.
    * Added sequence diagram of the execution of a `SetCommand`.
    * Added class diagram of to illustrate how certain settings classes, `UserPrefs` and `Storage` interact which each other.

* **Community**:
  * PRs reviewed can be found [here](https://github.com/AY2021S1-CS2103T-T10-1/tp/pulls?q=is%3Apr+reviewed-by%3Akhoodehui+). Example of a PR review with non-trivial comments: [#53](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/53).
  * Reported bugs and suggestions for other teams in the class, and provided in-depth clarification when requested (examples: [1](https://github.com/khoodehui/ped/issues/4), [2](https://github.com/khoodehui/ped/issues/5), [3](https://github.com/khoodehui/ped/issues/1))
