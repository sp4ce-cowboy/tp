---
layout: page
title: Rubesh Suresh's Project Portfolio Page
---

#### Project: UniCa$h

UniCa$h is a desktop application for university students who want to be more financially conscious.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. UniCa$h is designed
for users who are proficient with CLI. It is written in Java 11, spanning about [20 KLoC](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=T16-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=AY2324S1-CS2103-T16-3%2Ftp%5Bmaster%5D&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&isTabOnMergedGroup=true&tabOpen=true&tabType=authorship&tabAuthor=elhy1999&tabRepo=AY2324S1-CS2103-T16-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=true&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false).
Code contributed by me: [RepoSense](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=T16-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=sp4ce-cowboy&tabRepo=AY2324S1-CS2103-T16-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false). A summary of my contributions are given below, with more detailed
explanations in the respective PRs.

**Functional Features**

* **Delete Transaction Command**: [PRs: [#62](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/62)]
  {_**Credits:** AB3's `Delete` command._}
  * A command to remove a specified transaction from UniCa$h.
  **Justification:** Core utility of a finance tracking app.

* **Clear Transactions Command**: [PRs:
[#63](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/63)
[#138](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/138/)]
  {_**Credits:** AB3's `Clear` command._}
  * **What it does:** A command to remove all transactions, and a parser class.
  **Justification:** To allow the user to start on a clean slate. Parser ensures that
  mass-deletion is intentional, adding a layer of safety.

* **Reset UniCash Command**: [PRs:
[#90](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/90)
[#138](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/138/)]
  * **What it does:** A Command to restore UniCa$h to its original state with
  the same default transactions that appear when the app is opened for the first time.
  **Justification:** An easy way for users and testers of the application alike to re-start
  using UniCa$h from its default state.

* **Help Command & UniCash Welcome Window** [PRs:
  [#96](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/96)]
  * **What it does:** A command to open the Help Window with the 
  URL to UniCa$h's user guide, and also display a Message with a collated list of commands available,
  which is also set as the start-up Welcome Window.
  **Justification:** To allow the user quick access to an overview of commands available.
  _**Note:** The Help Command has been enhanced by [Lip Wei](https://github.com/lipwei1808)
        as mentioned [here](lipwei1808.md)._
 
* **Get Transaction Command** [PRs: 
[#101](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/101)]
  * **What it does:** A Command to see expanded details of a specific transaction.
  **Justification:** To allow a user to see details of a transaction whose properties' lengths
  can extend beyond the UI's ability to display them. **Highlights:** Complements the Find Command
  whereby a user can search for some transactions and use `get` to see full details.

* **Find Transactions Command Enhancement**: [PRs:
  [#101](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/101)
  [#138](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/138)]
  * **What it does:** A command to allow the user to search for specific transactions based
  on certain keywords.
  **Justification:** To allow the user find specific transactions especially if there are
  many transactions stored.
  **Highlights:** Complements the Get Command and the Rolling Balance Indicator. A user can tabulate
  expenses for any combinations of transactions based on the given keywords. 
  **Challenges:** A discrete predicate class had to be conceived for each property and an appropriate
  means for matching keywords to transactions had to be devised.
  **Possible Future Additions:** The Find command is limited to Name, Category and Location. This
  can be expanded to include the other properties in the future.
  _Note: The initial Find Command was implemented by [Jamie](https://github.com/jamz903)
  as mentioned [here](jamz903.md)_

**User Interface Features**

* **User Interface Overhaul & Enhancement** [PRs:
[#78](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/78)
[#82](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/82)
[#86](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/86)
[#94](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/94)
[#96](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/96)]
  * The UI of UniCa$h has been enhanced with an adjustable vertical `SplitPane`,
  a larger `Results Display Box`, a reversed `Transactions List Panel`, custom fonts,
  custom app icon, CSS additions, and other miscellaneous UI changes.
  **Justification:** To better fit our GUI needs corresponding to the visual
  representation of our implemented features for UniCa$h.
  _Note: The original UI transition was done by [Jamie](https://github.com/jamz903) as mentioned [here](jamz903.md)_

* **Rolling Balance Indicator** [PRs: [#96](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/96)]
  * What it does: Added to the `StatusBarFooter` of the UniCa$h UI to explicitly indicate the current balance of any group
  of transactions. At start-up the user will see their net total balance. With certain commands like `find`, the
  the user will see their net total expenditure for a group of transactions.
  * **Justification:** For the user to get a holistic sense of their current financial state and
    quantitatively determine certain spending patterns in related transactions.
* **Command Box History & Enhancements** [PRs:
[#110](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/110)
[#119](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/119)]
  * The `CommandBox` can remember up to a maximum of 10 previous user inputs,
  which can be traversed with the up/down arrow keys. `ESC` will clear the command box.
  **Justification:** To provide our CLI-proficient target users a more intuitive,
  convenient and efficient means for them to interact with UniCa$h, similar to a real CLI.

* **UI/UX Visual Features** 
  * A **StyleSheet class** [PRs: [#96](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/96)] was created such that all components involving any form of graphical
  representation have a centralized source of reference. **[Highlights/Possible Future Addition]:**
  The universality and extensibility of the StyleSheet class is such that it is possible to allow the
  user themselves to create and select certain custom themes which can then be applied across
  all visual elements in UniCa$h.
  * **Hash-based Category Coloring** [PRs: [#96](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/96)]
  This feature allows for a consistent color scheme across unique categories without having to hard-code colors, as 
  there is no limit for total category count in UniCa$h.
  **Justification:** To provide a seamless and consistent visual experience across UniCa$h for a better user-experience.
  _**Highlights:** The motivation for color consistency comes from these
  ([1](https://www.sciencedirect.com/science/article/abs/pii/S0167811623000599),
  [2](https://www.diva-portal.org/smash/record.jsf?pid=diva2%3A1788177&dswid=-2283)) studies that show positive correlation between
  color complexity and target user engagement, which is also elaborated in the DG._


**Decommissioned Features** (Some features that I had implemented prior but removed due to various constraints)

* **Local User Guide**: [PRs: [#82](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/82)]
  * **What it was:** A PDF version of the UGthat the user can access directly from UniCa$h 
  **Motivation for Implementation:** Given the NFR of offline usage, it would be useful for the User to
  have local access to the UG.
  **Justification for Removal:** Unreliable to depend on the host machine to display the PDF and
  difficult to test. Furthermore, we were allowed to assume that all users have access to the UG.

* **Fuzzy-Find Transactions**: [PRs: [#110](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/110)]
  * **What it was:** The initial implementation of the existing Find Command. It searched all properties including
  DateTime, Type, and Amount. Matches were a logical `OR` of all keywords.
  **Motivation for Implementation:** A user can search for any keyword across any property freely,
  emulating a fuzzy-finding functionality. This would greatly improve transaction retrieval speed.
  **Justification for Removal:** Feature is too expansive in its breadth of search and might be outside
  the scope of UniCa$h, and other functional constraints like insufficient testability.

* **Filter Transactions**: [PRs: [#130](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/130)]
  * **What it was:** Intended to complement the Fuzzy-Find transaction feature. While the Fuzzy-Find
  acted like a logical `OR` amongst keywords, Filter was intended to act as a logical `AND`, a direct inverse analogue.
  **Motivation for Implementation:** Combined with the Fuzzy-Find feature, the user would have maximum control
  over the scope of transactions to search. Combined with the Rolling Balance Indicator, the user would be able to tabulate total expenses for any conceivable
  combination of transaction properties.
  **Justification for Removal:** Same as Fuzzy-Find.
  Both features were simplified and integrated into the existing Find Command.
[PR: [#138](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/138)]

**Documentation**
* User Guide:
  * Added documentation for own features (Delete, Clear, Reset, Find, Get, Help)
  * Added annotated descriptions and overview of UI and UI Features
* Developer Guide:
  * Migrated details of the DG from an external source (G Docs) and consolidated them in GitHub.
  * Set up DG skeleton for structured and convenient documentation by the team.
  * Added documentation for own features (Delete, Clear, Reset, Find, Get, Help)
  * Added documentation for UI and features (Command Box, Rolling Balance Indicator, Transactions Panel)

**Project Management & Team Tasks**
* Created and initialized the CS2103-T16-3 organization on GitHub
* Created a centralized meeting minutes repository for the team
[here](https://docs.google.com/document/d/1EI2YeRjxt59N0oc0bauGXI06uFQLI_O54JJci_-JKGc/edit)
* Created a testing branch for in UniCa$h
[PR: [#126](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/126)]
* Added a TestCommand for the above branch, intended for internal testing of UniCa$h.
[PRs: [#126](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/126)]
* Refactored the codebase to refer to UniCash instead of AB3.
[PR: [#77](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/62)]
* Managed release [v1.3b](https://github.com/AY2324S1-CS2103-T16-3/tp/releases/tag/v1.3b)
and [v1.3.1](https://github.com/AY2324S1-CS2103-T16-3/tp/releases/tag/v1.3.1) on GitHub,
also made and released demo video for release v1.3b [here](https://github.com/AY2324S1-CS2103-T16-3/tp/releases/tag/v1.3b)

**Community**
* **Pull Requests Reviewed**: Refer [here](https://github.com/AY2324S1-CS2103-T16-3/tp/pulls?q=is%3Apr+reviewed-by%3Asp4ce-cowboy)
[Reviewed 41 PRs, with 91 PR comments in total thus far.]
* **Pull Requests Opened**: Refer [here](https://github.com/AY2324S1-CS2103-T16-3/tp/pulls?q=is%3Apr+author%3Asp4ce-cowboy+)
[Opened 33 PRs. Almost all major PRs have full details and/or screenshots.]
* **Issues Assigned & Completed**: Refer [here](https://github.com/AY2324S1-CS2103-T16-3/tp/issues?q=is%3Aissue+author%3Asp4ce-cowboy+is%3Aclosed+)


