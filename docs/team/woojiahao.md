---
layout: page
title: Jia Hao's Project Portfolio Page
---

### Project: UniCa$h 

UniCa$h is a finance tracking application for university students who want to be more financially conscious. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 20 kLoC.

Given below are my contributions to the project.

* **Get Total Expenditure**: pull requests [\#206](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/206), [\#143](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/143), [\#115](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/115), [\#80](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/80), [\#65](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/65)
  * What it does: retrieves the total expenditure of the user given a mandatory month and optional category and year. Only includes the spending on expense transactions.
  * Justification: our users may want to view an overview of their total expenditure in a month to more accurately set their budget.
* **Budget Management Logic**: pull requests [\#129](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/129), [\#145](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/145)
  * What it does: provides users with a way to create a global daily/weekly/monthly budget to track their overall expenditure with respect to the budget.
  * Justification: after understanding their total expenditure, users may want to then set budgets to better manage and track their spending.
  * Highlights: modified the persistence system to also store the budget information.
  * Notes: the budget model and preliminary logic were implemented by Lip Wei and Jamie respectively.
* **Setup TestFX for UI testing**: pull requests [\#93](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/93)
  * What it does: provides ways to automatically perform UI testing such as button clicks or keyboard inputs.
  * Justification: ensure that common manual testing cases are checked without human intervention.
  * Highlights: increased unit test coverage from ~80% to > 90%, modified the CI using GitHub Actions and Gradle to ensure that reported coverage on CodeCov includes UI tests.
  * Notes: set up the preliminary set of UI tests and provided a [guide on writing UI tests](../DeveloperGuide.html#ui-testing), Rubesh, Ernest, and Lip Wei had added individual UI tests based off on that.
* **Command Usage Message Builder**: pull requests [\#117](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/117)
  * What it does: generates command usage messages in a standardised format.
  * Justification: generating raw command usage `MESSAGE_USAGE` with `String` concatenation is prone to human error and may not be uniform across commands.
  * Highlights: using builder pattern to implement usage message builder.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=t16-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=woojiahao&tabRepo=AY2324S1-CS2103-T16-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Managed releases `v1.4` (1 release) on GitHub

* **Enhancements to existing features**:
  * Refactor codebase to remove any dependency to AB3 (Pull requests [#75](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/75))
  * Integrate UI testing reporting with CodeCov, increasing visible test coverage from ~80% without UI coverage to > 90% with UI coverage (Pull request [\#93](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/93))
    * Includes changes to Gradle configuration and GitHub actions, more documentation in [developer guide for Continuous Integration](../DeveloperGuide.html#continuous-integration-ci)
  * Migrate file persistence from AB3 to work with Transaction and Budgets (Pull requests [\#68](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/68) and [\#129](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/129))

* **Documentation**:
  * All:
    * Fix `kramdown` table of contents to work with Jekyll website (Pull request [\#139](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/139))
    * Implement custom callout elements for documentation (Pull request [\#175](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/175))
    * Implement "Back to Top" button for documentation pages (Pull request [\#175](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/175))
  * User Guide:
    * Added documentation for own features like get total expenditure and budget management logic
    * Added documentation on command breakdown and constraints of argument and prefixes
    * Migrated old AB3 layout to use new layout including TOC and callouts
  * Developer Guide:
    * Added implementation details of own features like get total expenditure and budget management logic
    * Added documentation on continuous integration
    * Added documentation on architecture and storage component
    * Migrated old AB3 layout to use new layout including TOC and callouts

* **Community**:
  * PRs reviewed (with non-trivial review comments): refer to [here](https://github.com/AY2324S1-CS2103-T16-3/tp/pulls?q=is%3Apr+reviewed-by%3Awoojiahao)
    * Reviewed **~65** of the **~140** PRs opened with **~216** comments as per the [public dashboard](https://nus-cs2103-ay2324s1.github.io/dashboards/contents/tp-comments.html) (as of 13 Nov 2023)
  * Contributed to forum discussions: refer to [here](https://nus-cs2103-ay2324s1.github.io/dashboards/contents/forum-activities.html) 
    * Provided assistance and participated in discussions on the forum with **~70** posts

* **Tools**:
  * Integrated TestFX to the project (Pull request [\#93](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/93))
