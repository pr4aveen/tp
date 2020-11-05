---
layout: page
title: Farrell Nah Jun Hao's Project Portfolio Page
---

## Project: Momentum

Momentum is a project management application targeted at freelancers to help them better understand their time usage
. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added time tracking to projects and tasks: `start` and `stop` commands. 
[\#19](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/19), 
[\#76](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/76)
  * What it does: Allows users to track the amount of time spent on a project through the `start` and `stop` commands.
  * Justification: This is a core feature of the product. Time tracking allows users to collect data on how they are
   spending their time.
  * Highlights:
    * This enhancement requires the addition and management of an additional `WorkDuration` data class for
    projects and tasks. 
    * Additional considerations were required to allow for multiple concurrent timers, and for timers to continue
     running while the application is closed.
    * It was challenging to implement this in a way that allows meaningful statistics to be
    calculated from the data.

* **New Feature**: Added statistics tracking. [\#40](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/40), 
[\#49](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/49)
    * What it does: Allows Momentum to calculate statistics from the time tracking data. the amount of time the user
     spends on each project and task over a predefined period of time (e.g. daily, weekly, monthly)
    * Justification: The statistics processes and presents the time tracking data in a way that is useful for the
     user, allowing them to draw insights into how they are using their time.
    * Highlights: 
        * Calculating and displaying statistics dynamically requires 
        * New UI elements had to be added to display the statistics. 
        * The statistics tracking was implemented in a way that allows for new types of statistics to be easily added
         in the future.
         
* **New Feature**: Added Navigation between projects and tasks: `home` and `view` commands 
[\#83](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/83)
    * What it does: Commands that allow users change between viewing projects, and tasks belonging to a project.
    * Justification: This is a key part of the tasks feature, the rest of which was implemented by another team
     member, allowing users to view the tasks that they have added.
    * Highlights: 
        * This enhancement affects existing commands and commands to be added in the future. It also heavily affects
         the way the Model class worksby introducing a state that needs to be managed.
        * Collaborated closely with another team member (Praveen) to integrate the changes with existing commands as
         it required a lot of code changes across the repository.
        
* **Code Refactor**: Converted an existing class into a generic class to reduce code duplication and allow for more
 flexibility.[\#197](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/197)
    * The `UniqueProjectList` class handles a list of unique projects. I refactored the class to make it a generic
     class `UniqueItemList<T>` that can handle a list of arbitrary objects.
    * This was important as the project needed to handle lists of multiple objects. Created a new UniqueXList to
     handle each project would have resulted in a lot of code duplication, and is difficult to extend to more objects.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=boundtotheearth)

* **Enhancements to existing features**:
  * Changed the way sample data is loaded if no existing data was available. Allow the application to load from a
   json file instead of having to build sample data through code. [\#135](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/135)
  * Wrote additional tests for task related features to increase coverage. [\#215](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/215)

* **Documentation**:
  * User Guide:
    * Added documentation for the features `start`, `stop`, `home`, `view` and statistics. [\#79](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/79)
    * Added additional examples for the "Date and Time Terms" section to better explain the format requirements
    . [\#128](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/128)
  * Developer Guide: [\#79](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/79)
    * Updated class diagrams to reflect the additional classes used for time tracking and statistics
    * Added implementation details of the time tracking and statistics features.
    * Created a sequence diagram to illustrate how statistics are calculated.
    * Created a class diagram to illustrate the classes involved in the time tracking process and how they interact.
    * Added user cases and user stories for time tracking features.
    
* **Community**:
  * PRs reviewed (with non-trivial review comments):
  [1](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/24)
  [2](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/45)
  [3](https://github.com/AY2021S1-CS2103T-T10-1/tp/pull/38)
  * Reported bugs and suggestions for other teams in the class (examples: [PED](https://github.com/boundtotheearth/ped/issues))
