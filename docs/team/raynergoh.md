# Rayner's Project Portfolio Page

### Project: TrackerGuru

TrackerGuru is a desktop address book application for property agents managing large client portfolios. It features a Command Line Interface (CLI) optimized for fast typists with a JavaFX GUI. Written in Java with ~10,000 LoC.

Given below are my contributions to the project.

* **New Feature**: Tag Group Management System
  * **What it does**: Allows creation and management of tag groups (e.g., `propertyType`, `priceRange`) to organize tags in `GROUP.VALUE` format (e.g., `propertyType.HDB`).
  * **Justification**: Property agents need to categorize contacts across multiple dimensions. This provides structured organization preventing tag proliferation and maintaining consistency.
  * **Highlights**: Implemented two-tier architecture with `Tag` and `TagGroup` classes, flexible validation (GROUP names alphanumeric, VALUE supports `.-_` for real-world data like `price.1.5M-2M`), safeguards preventing deletion of in-use groups, full JSON persistence, and command suite (`tg`, `dtg`, `ltg`).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19&tabOpen=true&tabType=authorship&tabAuthor=raynergoh&tabRepo=AY2526S1-CS2103T-F15b-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
  * ~2,196 total lines: 594 functional code, 1,127 test code, 474 documentation

* **Project management**:
  * Created and managed 31 pull requests with comprehensive descriptions
  * Coordinated tag group feature integration across Model, Logic, Storage layers
  * Managed v1.3-v1.4 milestone deliverables

* **Enhancements to existing features**:
  * Refactored `Tag` class from simple wrapper to support grouped tags with backward compatibility
  * Enhanced `AddCommand`/`EditCommand` with tag group validation
  * Extended `AddressBook`/`ModelManager` with tag group registry and CRUD operations
  * Created `JsonAdaptedTagGroup` for persistence
  * Achieved >85% test coverage with comprehensive test suites

* **Documentation**:
  * **User Guide**: Tag group commands documentation, validation rules with examples, troubleshooting section
  * **Developer Guide**: Complete implementation section with UML diagrams (class, sequence, activity), design rationale, regex pattern documentation, Model/Storage component updates

* **Community**:
  * Reviewed teammates' PRs with detailed architectural feedback
  * Helped debug tag-related validation issues
  * Participated in team planning and feature discussions

* **Tools**: Jackson (JSON), JUnit 5, TestFX, PlantUML, GitHub Actions CI/CD
