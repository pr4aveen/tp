---
layout: page
title: Praveen's Project Portfolio Page
---

## Project: Momentum

Momentum is a project management application targeted at freelancers to help them better understand their time usage.

Given below are my contributions to the project.

* **New Feature**: Added the ability for users to add tasks to projects.
  * What it does: allows the users to create subtasks within each project and track them.
  * Justification: This feature improves the product significantly because projects usually consist of multiple subtasks. The ability to add and track these subtasks will make the app more useful for freelancers.
  * Highlights: This enhancement affects existing commands and commands to be added in the future. It also heavily affects the way the Model class works. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required a lot changes to code all across the repository.

* **New Feature**: Added the ability for users to make complex, multi-parameter searches for projects.
  * What it does: Allows the users to key in different parameters that they want to filter tasks by. A match type can also be specified to determine whether one, all, or none of the parameters must match a project/task before the result is returned. 
  * Justification: This feature improves the product significantly because users will have multiple projects and tasks. Searching by parameters such as name, description, tags and completion status will speed up their workflow. It will also allow them to view statistics for a subset of tasks/projects. 
  * Highlights: This enhancement was initially self-contained. However, after the implementation of the tasks feature, it required a number of changes in Model and other commands for the `find` command to behave as desired.
 
* **New Feature**: Added a bottom bar for users to quickly know the state of the application. 
  * What it does: It shows users what their current view is and  shows them how many projects/tasks are currently visible. 
  * Justification: This feature improves the product significantly because users will have multiple projects and tasks. Searching by parameters such as name, description, tags and completion status will speed up their workflow. It will also allow them to view statistics for a subset of tasks/projects. 
  * Highlights: This enhancement involved a few design considerations as some private data in the model needed to be exposed.
 
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=pr4aveen&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=pr4aveen&tabRepo=AY2021S1-CS2103T-T10-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code)

* **Project management**:
  * Created GitHub organization and repository
  * Refactored the code base at the start to remove code that wasn't necessary for us to morph our product.
  * Miscellaneous GitHub related set-up (product website, protected GitHub branch, set up tests, etc.)
  * Managed release `v1.3`
  * Updated Developer Guide to include Manual Testing steps.

* **Enhancements to existing features**:
  * Bug fixes to be added

* **Documentation**:
  * User Guide:
    * Added documentation for the `find` command [Add Link]()
  * Developer Guide:
    * Added implementation details of the `find` feature.
    * Included Manual Testing steps for all features.

* **Community**:
  * Multiple PRs reviewed (with non-trivial review comments): [Add Link](), [Add Link](), [Add Link](), [Add Link]()
  * Reported bugs and suggestions for other teams in the class (examples: [Add Link](), [Add Link](), [Add Link]())
