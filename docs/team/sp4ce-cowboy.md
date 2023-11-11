---
layout: page
title: Rubesh Suresh's Project Portfolio Page
---

## Project: UniCa$h

### 1 – Project Overview

UniCa$h is a desktop application for university students who want to be more financially conscious.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. UniCa$h is designed
for users who are proficient with using the Command Line, and want effective and efficient digital
management of their finances. 

UniCa$h is written in Java 11, spanning about [20KLoC](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=T16-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=AY2324S1-CS2103-T16-3%2Ftp%5Bmaster%5D&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&isTabOnMergedGroup=true&tabOpen=true&tabType=authorship&tabAuthor=elhy1999&tabRepo=AY2324S1-CS2103-T16-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=true&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false) built with Gradle, packaged into a jar file and can be run on any
device running Java 11 or above, and it can be run locally with no network access required.

This project was developed in a team of 5 members, as part of the course requirements for 
[CS2103 Software Engineering](https://nusmods.com/courses/CS2103/software-engineering), in Semester 1 of AY23/24. 
Given below are my contributions to the project, including the features that I've implemented, the
enhancements that I've implemented, code and documentation that I've contributed and the team tasks that
I have been involved in.

---

### 2 – Project Contributions

#### Functional Features

* **Delete Transaction Command**:
  * What it does: A CLI command to remove a transaction from UniCa$h, as specified by the user.
  * Justification: As a finances management app, removal of transactions is an important core utility
    especially if the user had entered the information incorrectly or if the user wants to
    delete a transaction in general.
  * Credits: AddressBook3's implementation of the `Delete` command

* **Clear Transactions Command**:
  * What it does: A CLI command to remove all transactions from UniCa$h.
  * Justification: A user might want to clear all their previous transactions and
    start over on a blank slate.


* **Reset UniCash**: *to be added*
* **Help Command** *to be added*
* **Get Transactions**: *to be added*


* **Feature Enhancements**
  * **Find Transactions**: Added enhancements *to be added*

#### User Interface Features

* **User Interface Overhaul & Enhancement** [PRs:
[#78](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/78)
[#82](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/82)
[#86](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/86)
[#94](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/94)
[#96](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/96)]
  * The UI of UniCa$h has been enhanced with an adjustable vertical SplitPane,
  a larger `Results Display Box`, a reversed `Transactions List Panel`, color-coded 
  signed representation of currencies, and other minor UI modifications.
  * Justification: To better fit our GUI needs for UniCa$h such that the
  implementation of our chosen features and their corresponding visual
  representation would be appropriate both in the context of our target user 
  and the utility of the features themselves.
  * _Note: The original transition from AB3 to UniCa$h's UI was done by
  [Jamie](https://github.com/jamz903) as mentioned [here](jamz903.md)_


* **Rolling Balance Indicator** [PRs: [#96](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/96)]
  * A rolling balance indicator is added to the `StatusBarFooter` of the UniCa$h UI
  that can be used to visually indicate the current balance of any group
  of transactions. At start-up and by default, the user will be able to see
  their net total balance. Upon the usage of certain commands like `find`, the
  the user will be able to see their total expenditure for a group of expenses,
  total earned income for a group of incomes, or a net total expenses for a mixture
  of both.
  * Justification: With the total balance, the user will be able to get a holistic 
  sense of their current financial state. Additionally, the user will also be able to
  determine quantitatively certain spending patterns in associated or related transactions, thereby
  having a better understanding of their own financial habits.


* **Command Box History & Enhancements** [PRs:
[#110](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/110)
[#119](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/119)]
  * The `CommandBox` is made to remember the user's previous inputs, up to a
  maximum of 10 previous inputs, and detect the `ESC` key.
  * Justification: Given our target users being CLI-proficient, we want to
  provide a more intuitive and convenient means for them to interact with
  UniCa$h similar to that of a real CLI, such that they can carry out tasks
  requiring repeated usage of the same command in a much more efficient way,
  amongst other benefits of a real CLI experience.


* **StyleSheet Class** [PRs: [#96](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/96)]
  * To provide a seamless visual experience across UniCa$h, a StyleSheet class 
  was created such that all components involving any form of visual or graphical
  representation have a centralized and singular source of reference. 
  * Justification: To provide a seamless and unified visual theme across UniCa$h
  and thereby facilitate a better user-experience.
  * [Possible Future Addition]: The universality and extensibility of the StyleSheet class is such that it
  is possible to allow the user themselves to create and select certain custom themes
  which can then be applied across all visual elements in UniCa$h. 


* **Hash-based category coloring** [PRs: [#96](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/96)]
  * A sub-set of the StyleSheet class, this feature allows for a consistent color pattern
  across categories. In UniCa$h, the user is not restricted to any number of categories
    (in total). Thus, it is not possible to hard-code any color value into every single category.
    The hash-based color generation ensures that every category has a unique color assigned to
    it, without each of the color values having to be pre-determined and assigned manually.
  * Justification: Each category having the same specific color assigned to it would lead to better
  visual consistency and therefore, better overall user experience.


**Addendum**: Especially for this feature and the above mentioned StyleSheet class, the focus on colors
 consistency and user experience consistency across UniCa$h is motivated by the fact that certain scientific
  [studies](https://www.sciencedirect.com/science/article/abs/pii/S0167811623000599])
  have shown a positive correlation between color complexity and user engagement.
  Additionally, [this](https://www.diva-portal.org/smash/record.jsf?pid=diva2%3A1788177&dswid=-2283) study also
found out that certain design elements contribute to increase user engagement by young adults.
  Given that our target users (university students) are mostly young adults, The UI components are intended to
increase target user engagement with UniCa$h, which would lead to overall improvements in their personal
financial management. 


#### Decommissioned Features

Below are some of the features that I had implemented prior, but
were removed due to certain considerations.

* **Fuzzy-Find Transactions**: *feature yet to be implemented*
* **Filter Transactions**: *feature yet to be implemented*
* **Local User Guide**: *feature yet to be implemented*


#### Code
* Code contributed: [RepoSense Link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=T16-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=sp4ce-cowboy&tabRepo=AY2324S1-CS2103-T16-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

#### Documentation
* User Guide:
    * Added documentation for `DeleteTransaction` [to be added]()
    * Added documentation for `ClearTransactions` [to be added]()
* Developer Guide:
    * Migrated details of the Developer Guide from an external source (Google Docs) 
  and consolidated them in GitHub.

    
#### Project Management
* Managed release v1.3a on GitHub - Can be found [here](https://github.com/AY2324S1-CS2103-T16-3/tp/releases/tag/v1.3).

#### Community
* **Pull Requests Reviewed**: *to be added*
* **Pull Requests Opened**: *to be added*
* **Issues Assigned & Completed**: *to be added*


