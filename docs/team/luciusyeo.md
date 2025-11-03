---
  layout: default.md
  title: "Lucius Yeo's Project Portfolio Page"
---

## Project: TrackerGuru (Lucius)

TrackerGuru is a desktop address book application for property agents managing large client portfolios. It features a Command Line Interface (CLI) optimized for fast typists with a JavaFX GUI. Written in Java with ~10,000 LoC.
Given below are my contributions to the project.

* **New Feature**: Role Field
    * What it does: Introduces a `Role` field for contacts (e.g., `Buyer`, `Seller`), enabling agents to tag clients by their business relationship.
    * Justification: Property agents handle diverse client roles. This allows them to recall context-specific relationships.
    * Highlights: 
      * Designed an extensible **role parsing and validation framework**, supporting mixed-case inputs and compound names like `Co-buyer` while validating against copy-paste errors like `- Tenant`.
      * Added **duplicate and case-sensitivity checks** to prevent redundant roles (e.g. `r/Buyer r/buyer`).
      * Integrated seamlessly into **add/edit commands**, **JSON storage**, and **UI display**. 
      * Wrote **comprehensive test coverage** for role parsing, validation, and persistence.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&tabOpen=true&tabType=authorship&tabAuthor=luciusyeo&tabRepo=AY2526S1-CS2103T-F15b-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false&filteredFileName=) (~943 total lines: 290 functional code, 327 test code, 326 documentation)

* **Project management**:
    * **Managed 26 PRs** with clear descriptions, labels, and milestones.
    * **Reviewed 41 PRs** for teammates.
    * **Coordinated integration** of the Role feature across `Logic`, `Model`, `Storage`, and `UI` layers.

* **Documentation**:
    * **User Guide**: 1) Refined Quick Start Section 2) Owned Role documentation throughout UG 3) Refined Clear Command Section 4) Owned Data Storage sections.
    * **Developer Guide**: 1) Owned Instructions for manual testing, Product Scope and User Stories 2) Updated Glossary 3) Refined UML Diagrams.

* **Community**:
    * Reviewed teammates' PRs with detailed feedback
    * Helped debug role-related validation issues
    * Participated in team planning and guided teammates with feature enhancements

* **Tools**: Jackson (JSON), JUnit 5, TestFX, PlantUML, GitHub Actions CI/CD
