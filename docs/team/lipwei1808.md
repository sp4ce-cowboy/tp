---
layout: page
title: Tan Lip Wei's Project Portfolio Page
---

### Project: UniCa$h

UniCa$h is a finance tracking application for university students who want to be more financially conscious.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 19 kLoC.

Given below are my contributions to the project.

* **Transaction Model**: pull requests [\#58](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/58), [\#85](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/85), [\#81](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/81), [\#66](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/66), [\#72](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/72), [\#97](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/97), [\#116](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/116), [\#121](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/121)
    * What it does: Provides an internal encapsulation of a `Transaction` in UniCa$h. By taking inspiration from the `Person` class in AB3, I developed the `Transacation` class along with its attributes.
    * Justification: It is necessary to provide an internal representation of a `Transaction` as it serves as the primary object used throughout UniCa$h. Helps to map out business logic into code through the attributes in the `Transaction` class.
    * Highlights: By taking inspiration from the implementation of `Tag` and `UniquePersonList` in AB3, I developed the `UniqueCategoryList` as an attribute in `Transaction`. This enforced uniqueness in the `Category` added to a `Transaction`.
    * Challenges: Before we developed `Transaction`, we had thought to split the class into 2 separate classes `Income` and `Expense`. However, this proved to be a less effective approach which led to the pivoting to 1 consolidated `Transaction` class during `milestone 1.2`.
* **UniCash and TransactionList Model**: pull requests  [\#53](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/53), [\#121](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/121), [\#146](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/146)
    * What it does: The combination of the 2 `TransactionList` and `UniCash` models provides an internal encapsulation of transactions in UniCa$h. 
    * Justification: As our application serves as a platform for users to manage transactions, we had to encapsulate certain key business logic of transactions into a class.
    * Highlights: I developed the 2 classes by taking inspiration from the implementation of `UniquePersonList` and `AddressBook` classes in AB3. I also morphed the implementation to fit into our business needs of allowing duplicate transactions.
      * Later into the project, we discovered a potential room where a bug could arise due to there being too many `Transaction`. Hence, I later on added more constraints to the `TransactionList` class where a limit of 100000 `Transaction` was set.
* **Add Transaction Command**: pull requests [\#53](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/53), [\#95](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/95)
    * What it does: Provide a CLI command for users to add a transaction into UniCa$h.
    * Justification: As an application optimised for CLI users, I had to come up with the command as well as the related fields to add a transaction.
    * Highlights: To account for an intuitive process to add a `Transaction`, we had to minimise the number of compulsary fields.
      * One highlight was the `DateTime` field where we automatically set the current date and time as the default value if not set explicitly by the user.
    * Challenges: There was a small challenge in mocking the `LocalDateTime.now()` method in test cases, which required another constructor with an added parameter of a `Clock`. This allowed test cases to manually input a `Clock` which could be used for mocking the current date and time.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=t16-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=lipwei1808&tabRepo=AY2324S1-CS2103-T16-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
    * Managed releases `v1.3b` (1 release) on GitHub

* **Enhancements to existing features**:
  * **Help Command**: pull requests [\#128](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/128), [\#135](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/135)
    * What it does: Provides users minimal and sufficient help to successfully execute commands.
    * Justification: The previous implementation of AB3 `help` would show a pop up with a link to our User Guide. This proved to be cumbersome as users would have to spend time switching back and forth the application in order to run commands. Hence, I allowed for the use of `help COMMAND_WORD` to get help for a specific command within the application.
    * Highlights: This allowed users to successfully get the description of a command, command format and example command all within the application.

* **Documentation**:
    * User Guide:
        * Set up structure skeleton for User Guide for easy and structured documentation by the team.
        * Added documentation for own features like `AddTransactionCommand` and `HelpCommand`.
    * Developer Guide:
        * Added implementation details of model features like `Tranasaction`, `TransactionList` and `Budget` models.
        * Added documentation on commands implemented like `AddTransactionCommand` and `HelpCommand`.

* **Community**:
    * PRs reviewed : refer to [here](https://github.com/AY2324S1-CS2103-T16-3/tp/pulls?q=is%3Apr+reviewed-by%3Alipwei1808)

