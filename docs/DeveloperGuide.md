---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3

---

# TrackerGuru Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

* Libraries used:
  * [JavaFX](https://openjfx.io/) - For the graphical user interface
  * [Jackson](https://github.com/FasterXML/jackson) - For JSON data storage and parsing
  * [JUnit5](https://github.com/junit-team/junit5) - For unit and integration testing

* Tools used:
  * [Gradle](https://gradle.org/) - For build automation and dependency management
  * [PlantUML](https://plantuml.com/) - For UML diagram generation
  * [MarkBind](https://markbind.org/) - For generating the project documentation website
  * [CheckStyle](https://checkstyle.sourceforge.io/) - For code style checking

* Documentation:
  * The structure and format of this Developer Guide is adapted from the [AB3 Developer Guide](https://se-education.org/addressbook-level3/DeveloperGuide.html)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture
<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-F15b-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-F15b-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S1-CS2103T-F15b-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S1-CS2103T-F15b-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.
* Note: Some commands like `ListCommand`, `StatsCommand`, `ClearCommand`, `ExitCommand`, and `HelpCommand` do not require parsers as they take no parameters.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S1-CS2103T-F15b-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has:
- A **Tag list** in the `AddressBook`, which `Person` references
- A **Role list** in the `AddressBook`, which `Person` references
- A **TagGroup set** in the `AddressBook`, which `Tag` optionally references

This allows `AddressBook` to:
- Only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects
- Only require one `Role` object per unique role, instead of each `Person` needing their own `Role` objects
- Maintain a centralized collection of `TagGroup` objects for organizing tags into categories (e.g., "PropertyType", "Location")

Tags can optionally belong to a TagGroup, enabling structured organization and group-based filtering of contacts.

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S1-CS2103T-F15b-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

Address book data is persisted as a JSON file through `JsonSerializableAddressBook`, which contains:

* A list of `JsonAdaptedPerson` objects (each containing `JsonAdaptedTag` and `JsonAdaptedRole` objects)

* A list of `JsonAdaptedTagGroup` objects representing tag categories



This structure enables centralized storage of Tag Groups alongside person data, maintaining consistency with the Model component's Tag Group management.
### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.


### Duplicate Handling

#### Implementation

The duplicate handling mechanism ensures that no two contacts in the address book can have the same phone number or email. This is important for property agents who need to maintain unique contact information for each client, as these fields serve as critical identifiers for communication and identification.

The feature is implemented through checks in the `Model` component, specifically:

* `Model#hasSamePhoneNumber(Person person)` — Checks if any existing person in the address book has the same phone number as the given person.
* `Model#hasSameEmail(Person person)` — Checks if any existing person in the address book has the same email as the given person.

These operations are exposed in the `Model` interface and implemented in `ModelManager`, which delegates the checks to `AddressBook` and ultimately to `UniquePersonList`.

Given below is an example usage scenario and how the duplicate handling mechanism behaves. For illustration purposes, we will use phone number as the example, though the same logic applies to email checks.

Step 1. The user attempts to add a new contact John with phone number 12345678 by executing the command `add n/John p/12345678 e/john@example.com a/123 Street r/Buyer s/Pending`. The `AddCommand` is created and executed.

Step 2. During execution, `AddCommand` performs duplicate validation checks for phone number and email.
   * Checks for duplicate phone number by calling `Model#hasSamePhoneNumber(toAdd)` (illustrated in the sequence diagram below)
   * Checks for duplicate email by calling `Model#hasSameEmail(toAdd)`

   Each check cascades through the components:
   * `ModelManager` calls the corresponding `AddressBook` method (e.g. `AddressBook#hasSamePhoneNumber(person)`)
   * `AddressBook` calls the corresponding `UniquePersonList` method (e.g. `UniquePersonList#containsPhoneNumber(person)`)
   * `UniquePersonList` iterates through all persons to check if any existing person has the same field value

Step 3. If any of the checks return true (e.g. the phone number 12345678 already exists belonging to another contact Alice), `AddCommand` throws a `CommandException` with an appropriate error message such as "This phone number already exists in the address book".

Step 4. If all checks pass (return false), the new contact is successfully added to the address book.

The following sequence diagram shows how the duplicate phone number check works during the execution of an add command (the same pattern applies to email checks):

<puml src="diagrams/DuplicatePhoneHandlingSequenceDiagram.puml"/>

<box type="info" seamless>

**Note:** Name duplication is not checked because property agents may legitimately have multiple contacts with the same name (e.g. common names like "John Tan"). Phone numbers and emails are more reliable unique identifiers for contact management.

</box>

#### Design considerations:

**Aspect: When to perform the duplicate field checks:**

* **Alternative 1 (current choice):** Check during command execution in `AddCommand` and `EditCommand`.
  * Pros:
    * Simple and straightforward implementation.
    * Error detection happens at the business logic layer, providing clear feedback to users.
    * Consistent with existing duplicate person checking mechanism.
    * Allows for specific error messages for each field type.
  * Cons:
    * The checks are performed separately in multiple command classes, leading to some code duplication.

* **Alternative 2:** Enforce uniqueness constraints at the `UniquePersonList` level.
  * Pros:
    * Centralized validation logic in the model layer.
    * Automatically applies to all operations that modify the person list.
  * Cons:
    * Less control over error messages for different commands.

**Aspect: Scope of the uniqueness checks:**

* **Alternative 1 (current choice):** Phone numbers and emails must be globally unique across all contacts.
  * Pros:
    * Prevents confusion and data integrity issues.
    * Aligns with real-world expectation that these fields uniquely identify individuals.
    * Simplifies contact management for property agents who rely on these identifiers.
  * Cons:
    * May be overly restrictive in some scenarios.

* **Alternative 2:** Allow duplicate fields but warn the user.
  * Pros:
    * More flexible for edge cases.
    * Still alerts users to potential data entry errors.
  * Cons:
    * May lead to confusion when the same contact information appears for multiple people.
    * Could result in unintended duplicates if users ignore warnings.

### Tag Group Management feature

#### Overview

The Tag Group Management feature allows users to organize tags into logical categories (Tag Groups). This helps property agents categorize and manage their contacts more effectively by grouping related tags together (e.g., `propertyType`, `location`, `priceRange`).

Tag Groups provide the following benefits:
* **Better organization**: Group related tags together for easier management
* **Improved filtering**: Filter contacts by Tag Group categories
* **Clearer structure**: Instantly see which category each tag belongs to

#### Tag Validation

**Regex Patterns:**

1. **Standalone tags:** `^[a-zA-Z0-9]+$`
    - Alphanumeric only, no spaces
    - Example: `VIP`, `priority`

2. **Grouped tags:** `^([a-zA-Z0-9]+)\.([a-zA-Z0-9][a-zA-Z0-9.\-_]*)$`
    - GROUP: `[a-zA-Z0-9]+` (alphanumeric only)
    - Separator: Exactly one dot (`.`)
    - VALUE:
        - Must start with alphanumeric: `[a-zA-Z0-9]`
        - Can contain: `[a-zA-Z0-9.\-_]*` (alphanumeric, dots, hyphens, underscores)
    - Example: `priceRange.1.5M-2M`

This relaxed validation for VALUES allows property agents to use more descriptive tag values like `500k-1M`, `1.5M-2M`, or `Bishan-North`.

#### Implementation

The Tag Group Management feature is implemented through multiple layers of the application:

##### Model Layer

**Core Classes:**
* `TagGroup`: Represents a Tag Group with an alphanumeric name. Ensures immutability and validates names using regex patterns.
* `Tag`: Modified to optionally reference a `TagGroup`. Tags can exist independently or be associated with a group.
* `AddressBook`: Maintains a `Set<TagGroup>` to store all created Tag Groups. Provides methods to add, remove, and check Tag Groups.
* `Model` interface: Exposes operations for Tag Group management (`addTagGroup`, `deleteTagGroup`, `hasTagGroup`, `isTagGroupInUse`).

**Key Methods:**
* `Model#addTagGroup(TagGroup)`: Adds a new Tag Group to the address book if it doesn't already exist.
* `Model#deleteTagGroup(TagGroup)`: Removes a Tag Group from the address book after validation.
* `Model#hasTagGroup(TagGroup)`: Checks if a Tag Group exists in the address book.
* `Model#isTagGroupInUse(TagGroup)`: Checks if any person's tags reference the specified Tag Group. Uses Java Streams with `flatMap()` and `anyMatch()` for efficient checking.

##### Logic Layer

**Command Classes:**
* `TagGroupCommand`: Creates a new Tag Group or lists all existing Tag Groups. Handles both parameterized (create) and parameterless (list) executions.
* `DeleteTagGroupCommand`: Deletes a Tag Group after checking that it's not in use by any person's tags.

**Parser Classes:**
* `TagGroupCommandParser`: Parses user input for the `tg` command. Distinguishes between listing (no arguments) and creating (with GROUP_NAME argument).
* `DeleteTagGroupCommandParser`: Parses user input for the `dtg` command and validates the Tag Group name.

**Command Execution Flow:**
1. User enters a Tag Group command (e.g., `tg propertyType`)
2. `AddressBookParser` routes to the appropriate parser
3. Parser validates input and creates the command object
4. Command executes by interacting with the `Model`
5. Result is returned to the UI layer

##### Storage Layer

**Storage Classes:**
* `JsonAdaptedTagGroup`: Jackson-friendly adapter class for `TagGroup` serialization/deserialization.
* `JsonSerializableAddressBook`: Extended to include a `List<JsonAdaptedTagGroup>` field for persisting Tag Groups.

**Persistence Flow:**
* Tag Groups are serialized to JSON alongside persons in `addressbook.json`
* On application startup, Tag Groups are deserialized and loaded into the `AddressBook`
* Tag Groups persist across sessions automatically

#### Usage Scenarios

##### Scenario 1: Creating a Tag Group

**User Goal:** Create a Tag Group called `propertyType`

**Steps:**
1. User executes `tg propertyType`
2. `AddressBookParser` creates a `TagGroupCommandParser`
3. `TagGroupCommandParser` parses the input and creates a `TagGroupCommand` with the group name
4. `TagGroupCommand#execute()` checks if the Tag Group already exists using `Model#hasTagGroup()`
5. If it doesn't exist, `Model#addTagGroup()` is called to add the Tag Group
6. Success message "Tag Group created: propertyType" is displayed to the user

**Sequence Diagram:**

<puml src="diagrams/TagGroupCommandSequenceDiagram.puml" alt="TagGroupCommandSequenceDiagram" />

The sequence diagram above illustrates the interaction between Logic and Model components when creating a Tag Group.

##### Scenario 2: Listing Tag Groups

**User Goal:** View all created Tag Groups

**Steps:**
1. User executes `tg` (without arguments)
2. `AddressBookParser` creates a `TagGroupCommandParser`
3. `TagGroupCommandParser` detects no arguments and creates a `TagGroupCommand` for listing
4. `TagGroupCommand#execute()` retrieves all Tag Groups using `Model#getTagGroupList()`
5. List of Tag Groups is formatted and displayed to the user

##### Scenario 3: Deleting a Tag Group

**User Goal:** Delete an unused Tag Group called `propertyType`

**Steps:**
1. User executes `dtg propertyType`
2. `AddressBookParser` creates a `DeleteTagGroupCommandParser`
3. `DeleteTagGroupCommandParser` parses the input and creates a `DeleteTagGroupCommand`
4. `DeleteTagGroupCommand#execute()` performs validation:
    - Checks if the Tag Group exists using `Model#hasTagGroup()`
    - Checks if it's in use using `Model#isTagGroupInUse()`
5. If validation passes, `Model#deleteTagGroup()` is called
6. Success message "Tag Group deleted: propertyType" is displayed

**Activity Diagram:**

<puml src="diagrams/DeleteTagGroupActivityDiagram.puml" alt="DeleteTagGroupActivityDiagram" />

The activity diagram above shows the decision flow when deleting a Tag Group, including validation steps.

#### Design Considerations:

**Aspect: Tag Group Deletion Policy**

**Alternative 1 (current choice)**: Prevent deletion if Tag Group is in use
- **Pros**: Data integrity maintained, prevents orphaned tags
- **Cons**: Requires manual tag cleanup before group deletion
- **Rationale**: Chosen to avoid accidental data loss and maintain referential integrity

**Alternative 2**: Cascade delete all associated tags
- **Pros**: Simpler workflow for bulk cleanup
- **Cons**: High risk of unintended data loss, no undo mechanism

**Alternative 3**: Convert to untagged status
- **Pros**: Preserves tag associations while removing group
- **Cons**: Creates inconsistent state, defeats purpose of grouping

**Aspect: Tag Group storage structure**

* **Alternative 1 (current choice):** Store as a `Set<TagGroup>` in `AddressBook`
    * Pros: Simple and efficient; automatic duplicate prevention
    * Pros: Fast lookup for existence checks (O(1) on average with HashSet)
    * Cons: Not ordered; Tag Groups displayed in arbitrary order

* **Alternative 2:** Store as a `Map<String, TagGroup>` keyed by Tag Group name
    * Pros: Slightly faster lookup by name (explicit key-based access)
    * Cons: Redundant since `TagGroup` already contains its name
    * Cons: Requires additional boilerplate code for synchronization

* **Alternative 3:** Store as a sorted list for ordered display
    * Pros: Tag Groups displayed in alphabetical order
    * Cons: Slower insertion and duplicate checking (O(n))
    * Cons: Additional complexity in maintaining sort order

**Justification:** Alternative 1 was chosen for its simplicity and efficiency. The unordered nature of the Set is not a significant drawback since Tag Groups can be sorted when displayed if needed.

**Aspect: Tag and TagGroup relationship design**

* **Alternative 1 (current choice):** Tags optionally reference TagGroup; TagGroups don't maintain lists of Tags
    * Pros: Simple unidirectional relationship; easier to maintain consistency
    * Pros: Tags can exist independently without a group
    * Cons: Finding all tags in a group requires scanning all persons' tags

* **Alternative 2:** Bidirectional relationship where TagGroups maintain lists of associated Tags
    * Pros: Easy to find all tags in a group
    * Cons: Complex bidirectional synchronization required
    * Cons: Harder to maintain consistency when tags are added/removed from persons

**Justification:** Alternative 1 was chosen to minimize complexity and avoid synchronization issues. The use case of "finding all tags in a group" is rare compared to checking if a tag belongs to a group.

#### Validation Rules

The following validation rules are enforced for Tag Group operations:

1. **Tag Group names:**
    * Must be alphanumeric (letters and numbers only)
    * Cannot contain spaces or special characters
    * Validated using regex pattern: `^[a-zA-Z0-9]+$`
    * Validation occurs in the `TagGroup` constructor

2. **Creating Tag Groups:**
    * Duplicate Tag Group names are not allowed
    * Checked using `Model#hasTagGroup()` before adding
    * Case-sensitive comparison (e.g., `PropertyType` ≠ `propertytype`)

3. **Deleting Tag Groups:**
    * Tag Group must exist in the address book
    * Tag Group cannot be in use by any person's tags
    * Checked using `Model#isTagGroupInUse()` which:
        - Iterates through all persons
        - Flattens all tags using `flatMap()`
        - Uses `anyMatch()` to check if any tag references the group

4. **Tags with groups:**
    * Format: `t/GROUP.VALUE` (e.g. `t/propertyType.HDB`)
    * **GROUP** (before the dot) must:
      - Be alphanumeric only
      - Match an existing Tag Group name
    * **VALUE** (after the dot) can contain:
        - Alphanumeric characters
        - Dots (`.`), hyphens (`-`), and underscores (`_`)
        - Must start with an alphanumeric character
        - Cannot contain spaces
    * Validated during tag parsing using regex patterns in `Tag.isValidTagFormat()`

#### Error Handling

The feature implements comprehensive error handling for various edge cases:

| Error Scenario | Command | Error Message |
|----------------|---------|---------------|
| Invalid Tag Group name (contains spaces) | `tg property type` | Tag Group names should be alphanumeric |
| Invalid Tag Group name (special chars) | `tg property-type!` | Tag Group names should be alphanumeric |
| Duplicate Tag Group | `tg propertyType` (when it exists) | This Tag Group already exists in the address book. |
| Delete non-existent Tag Group | `dtg location` (doesn't exist) | The Tag Group location does not exist. |
| Delete Tag Group in use | `dtg propertyType` (when in use) | This Tag Group is currently in use and cannot be deleted. Please remove all tags associated with this group first. |


### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* is a property agent with many different kinds of contacts
* has a need to manage a big number of contacts
* has a need to track information about each contact
* has a need to categorize different contacts
* prefer desktop apps over other types
* is a fast typist, preferring typing to mouse interactions
* is comfortable using CLI apps

**Value proposition**: manage and organise different kinds of contacts and their information faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                 | I can …​                                                         | So that …​                                                                               |
|----------|-------------------------|------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| `* *`    | potential user          | see sample contacts                                              | I can expect how the contacts will look like before using the App                        |
| `* * *`  | new user                | access help instructions easily                                  | I can refer to instructions when I forget how to use the App                             |
| `* *`    | new user                | delete all sample contacts at once                               | I can start using the app proper quickly                                                 |
| `* * *`  | new user                | view all existing contacts in a list                             | I can see all my current contacts easily                                                 |
| `* * *`  | new user                | add a new contact with contact information                       | I can keep track of my growing contacts                                                  |
| `* * *`  | new user                | delete a contact and all its contact information                 | I can remove contacts I no longer need                                                   |
| `* * *`  | new user                | view a contact's full contact information                        | I can easily reference all information about a contact                                   |
| `* *`    | new user                | edit a contact's generic contact information                     | I can make amends to changes easily                                                      |
| `* * *`  | new user                | assign one or more roles to a contact during creation            | I can easily recall my professional relationship with each contact                       |
| `* *`    | new user                | delete a role from an existing contact                           | I can remove roles that no longer apply to the contact                                   |
| `* *`    | new user                | add a role to an existing contact                                | I can easily add roles to contacts without having to recreate the whole contact          |
| `* * *`  | new user                | assign a status to a contact during creation                     | I can track each client's transaction status                                             |
| `* *`    | new user                | delete a status from an existing contact                         | I can remove its status if it no longer applies to the contact                           |
| `* *`    | new user                | add a status to an existing contact                              | I can freely decide to track status of contacts at a later time                          |
| `* * *`  | new user                | create Tag Groups such as property price or location             | I can easily group contacts under business-related categories                            |
| `* * *`  | new user                | delete an existing Tag Group                                     | I can clean up Tag Groupings that I no longer want to track about my contacts            |
| `* *`    | new user                | view all existing Tag Groups in a list                           | I can easily view my current Tag Groups so I do not create duplicates                    |
| `* * *`  | new user                | create a contact with grouped tags                               | I can easily assign a specific tag values within a Tag Group when creating a contact     |
| `* *`    | new user                | create a contact with standalone tags unrelated to any Tag Group | I can still track unique metrics about a contact that do not belong to any Tag Group yet |
| `* *`    | expert user             | list all client roles                                            | I can understand what types of roles I have already created so far                       |
| `* *`    | expert user             | be warned when creating a role that already exists               | I can reduce the number of duplicated roles created                                      |
| `* *`    | expert user             | filter contacts quickly across some roles, status or Tag Groups  | I can easily look for specific contacts that match some unique contact information       |
| `* *`    | expert user             | easily record the date and history of each status change         | I can easily track when each deal is closed or monitor past transactions                 |
| `*`      | expert user             | undo commands quickly                                            | I can quickly rectify mistakes                                                           |
| `*`      | expert user             | redo commands quickly                                            | I can quickly make similar transactions                                                  |
| `*`      | expert user             | apply advanced filters on contacts using boolean-style filtering | I can have finer control over filtering                                                  |
| `*`      | expert user             | filter contacts with substring matching                          | I can filter more generically if I am unsure of the exact details                        |
| `*`      | expert user             | export my contact book                                           | I can migrate my data to other devices                                                   |
| `*`      | expert user             | import existing client data from a csv file                      | I can start from an existing database                                                    |
| `* *`    | user with many contacts | find a contact by exact name                                     | I can find if a contact exist without having to go through the entire list               |
| `* *`    | user with many contacts | find a contact with a substring match of the name                | I can find a contact if I forgot how to spell his exact name                             |
| `* *`    | user with many contacts | view statistics of clients grouped by their transaction status   | I can quickly see how many clients are pending, completed, or have no status             |

### Use cases

(For all use cases below, the **System** is the 'TrackerGuru' and the **Actor** is the 'Property Agent', unless specified otherwise. The term 'User' will be synonymous with 'Property Agent')

**Use case: UC1 - Add a contact**

**Guarantees**

* The address book will not contain duplicate contacts after any operation.
* If the contact is added, all contact information provided by the user will be stored without any loss of information.

**MSS**

1. User requests to add a contact.
2. TrackerGuru saves the contact and its details.
3. TrackerGuru displays a success message to the user.

   Use case ends.

**Extensions**

* 1a. TrackerGuru detects an error in the entered data (missing required fields or improper format).
    * 1a1. TrackerGuru displays an error message and the proper command format to the user.
      Use case resumes from step 1.

* 1b. TrackerGuru detects a duplicate contact (same phone number or email).
    * 1b1. TrackerGuru displays an error message.
      Use case ends.

* 1c. Contact uses a Tag Group that has not been created yet.
    * 1c1. TrackerGuru displays an error message indicating invalid command format.
    * 1c2. User <u>Creates a Tag Group (UC6)</u>.
      Use case resumes from step 1.

* 1d. TrackerGuru fails to save the contact due to a system error.
    * 1d1. TrackerGuru displays an error message.
      Use case ends.

---

**Use case: UC2 - Delete a contact**

**MSS**

1. User requests to delete a contact.
2. TrackerGuru deletes the contact.
3. TrackerGuru displays a success message to the user.

   Use case ends.

**Extensions**

* 1a. TrackerGuru detects that the contact’s unique identifier is missing in the entered data.
    * 1a1. TrackerGuru requests for the contact’s unique identifier.
      Use case resumes from step 1.

* 1b. TrackerGuru cannot find the specified contact’s unique identifier.
    * 1b1. TrackerGuru requests for a valid unique identifier.
    * 1b2. User enters a new unique identifier.
      Steps 1b1–1b2 are repeated until the unique identifier is one that exists.
      Use case resumes from step 2.

* 1c. TrackerGuru fails to delete the contact due to a system error.
    * 1c1. TrackerGuru informs the user.
      Use case ends.

* *a. At any time, User chooses to cancel the deletion request.
  Use case ends.

---

**Use case: UC3 - Filter contacts by roles, statuses, and Tag Groups**

**Guarantees**

* The filtered list will only contain contacts that match any of the specified filter criteria.
* The original contact data remains unchanged after filtering.

**MSS**

1. User requests to filter contacts by specifying one or more criteria such as role, status, and/or Tag Group.
2. TrackerGuru processes the filter criteria and retrieves matching contacts.
3. TrackerGuru displays the filtered list of contacts to the user.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command format (e.g. missing prefixes or incorrect syntax).
    * 1a1. TrackerGuru displays an error message and the correct command format.
      Use case resumes from step 1.

* 1b. User specifies no filter criteria.
    * 1b1. TrackerGuru displays an error message indicating invalid command format.
      Use case resumes from step 1.

* 1c. TrackerGuru encounters a system error while filtering.
    * 1c1. TrackerGuru displays an error message.
      Use case ends.

---

**Use case: UC4 - Search contact by name**

**Guarantees**

* Only contacts whose names match the given keywords will be displayed to the user (if MSS completes).
* Contact information will not be modified.

**MSS**

1. User requests for contacts whose name matches the given keywords.
2. TrackerGuru displays all matching contacts.
3. TrackerGuru displays a success message to the user.

   Use case ends.

**Extensions**

* 1a. TrackerGuru detects an error in the entered data (missing required fields or improper format).
    * 1a1. TrackerGuru displays an error message and proper command format to the user.
      Use case resumes from step 1.

* 1b. TrackerGuru fails to search contacts due to a system error.
    * 1b1. TrackerGuru displays an error message.
      Use case ends.

---

**Use case: UC-5 Edit a contact**

**Guarantees**

* Existing contact information will be updated to the address book only if MSS completes.
* All the field values in the contact will be valid.

**MSS**

1. User requests to edit contact with together with their relevant details.
2. TrackerGuru edits and saves the updated contact information.
3. TrackerGuru displays success message to the user.
4. TrackerGuru's contact list reflects the updated contact information.

    Use case ends.

**Extensions**

* 1a. TrackerGuru detects an error in the entered data (invalid index).
    * 1a1. TrackerGuru displays error message that the specified index is invalid.
    Use case resumes from step 1.

* 2a. TrackerGuru fails to edit the contact information due to a system error.
    * 2a1. TrackerGuru displays an error message indicating the failure.
    Use case ends.

---

**Use case: UC6 - Create a Tag Group**

**Guarantees**

* Each Tag Group created will have a unique name within the address book.
* Once created, the Tag Group will be available for assigning tags to contacts.

**MSS**

1. User requests to create a Tag Group.
2. TrackerGuru saves the Tag Group name.
3. TrackerGuru displays a success message to the user.

   Use case ends.

**Extensions**

* 1a. TrackerGuru detects that the Tag Group name is missing or improperly formatted.
    * 1a1. TrackerGuru displays an error message and shows the correct command format.
      Use case resumes from step 1.

* 1b. TrackerGuru detects a duplicate Tag Group name.
    * 1b1. TrackerGuru displays an error message indicating that the Tag Group already exists.
      Use case ends.

* 1c. TrackerGuru fails to save the Tag Group due to a system error.
    * 1c1. TrackerGuru displays an error message.
      Use case ends.


### Non-Functional Requirements

#### Performance/Capacity requirements
- The system should be able to store up to 200 contacts at once
- The system should load and display contact lists within 2 seconds, even at maximum capacity
- The system should boot within 3 seconds on a computer with at least 8GB RAM and an Intel Core i5/Ryzen 5-class processor (or equivalent)

#### Technical requirements
- The system should work on any computer that runs Java 17
- The system should not have a remote server
- The system should be functional without internet connection
- The system should only use local storage to store contacts
- The system should support using a local .json file to store and retrieve contact data

#### Reliability requirements
- The system should not lose saved data in the event of unexpected termination

#### Security requirements
- The system should not transmit contact data over the internet

#### Maintainability requirements
- The code should be modular and well documented
- The system should allow adding new contact fields without major refactoring

#### Quality requirements
- The system should provide help and usage instructions
- Command syntax should be consistent and documented
- All operations can be completed in no more than one typed line
- The system should display clear error messages for invalid inputs instead of crashing


### Glossary

* **address book**: The collection of all contacts and their associated data stored in TrackerGuru, persisted locally as a JSON file
* **client**: A specific type of contact that represents customers of the property agent (i.e. property buyers, sellers, landlords, tenants)
* **contact information**: Exact contact details of a person; name, phone number, email, address
* **role**: A field that defines the function or relationship of a contact (e.g. buyer, seller, landlord, tenant) in the property business
* **status**: A label indicating the current state of a client transaction (e.g. Pending, Completed).
* **tag**: A simple standalone label used to categorize or describe a contact
* **grouped tag**: A label described by a tag value belonging to a Tag Group
* **Tag Group**: A category for organizing related tags together (e.g. "PropertyType", "Location"), enabling structured tag management and group-based filtering of contacts
* **tag value**: A specific value of a Tag Group (e.g. "Condo" of "PropertyType")
* **sample contact**: Preloaded example contacts provided in TrackerGuru to help new users explore app features before entering their own data
* **help instructions**: User guide in the TrackerGuru webpage to assist users in learning how to use the app
* **new user**: A first-time user of TrackerGuru who has not yet created any personal contacts
* **expert user**: A user who is already familiar with TrackerGuru’s features and performs advanced operations such as complex filtering or data management
* **boolean-style filtering**: A method to filter contacts by combining multiple criteria precisely using AND ("match all") and OR ("match any") logic (e.g. filter contacts that have a specific role AND a certain status, or contacts that have a specific tag)
* **substring match**: A method to match a contact as long as the search term is anywhere within the target field (e.g. 'b' can match 'buyer')

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for Manual Testing**

The following are sample instructions for manually testing TrackerGuru.
They serve as a starting point; you are encouraged to perform **exploratory testing** beyond these examples to uncover edge cases.

<box type="info" seamless>

**Note**:
Each test case includes the command to execute and the expected outcome.
Testers should verify that error and success messages match the described behavior.
</box>

### Launch and shutdown

1. **Initial launch**

   1. Download the `.jar` file and place it into an empty folder.

   2. Double-click the file to launch.
   <br> **Expected**: The GUI opens with sample contacts loaded. The window size may not be optimal initially.

2. **Saving window preferences**

   1. Resize the window and move it to a different location. Close the window.

      2. Close and relaunch the application by double-clicking the `.jar` file <br>
          Expected: The previous window size and location are retained.

### Deleting a person

1. **Deleting While All Persons Are Shown**

   1. **Prerequisites**: Use `list` command to show all persons. Ensure there are multiple persons listed.

   2. **Test case**: `delete 1`<br>
      Expected: The first contact is deleted. The status message shows details of the deleted person.

   3. **Test case**: `delete 0`<br>
      Expected: Error message shown. No person is deleted.

   4. **Other invalid commands to try**: `delete`, `delete x` (where `x` > list size)<br>
      Expected: Error message displayed. No changes to list.

### Creating and managing Tag Groups

1. **Creating a New Tag Group**

    1. **Test case**: `tg PropertyType`<br>
       Expected: Tag Group "PropertyType" created successfully. Confirmation message shown.

   2. **Test case**: `tg PropertyType`<br>
       Expected: Error message shown indicating the Tag Group already exists.

   3. **Test case**: `tg Property Type` (with space)<br>
       Expected: Error message shown indicating Tag Group names must be alphanumeric with no blanks.

   4. **Test case**: `tg` <br>
      Expected: A list of all your created Tag Groups.

   5. **Other invalid commands to try**: `tg tag-Group`, `tg tagGroup 123!`<br>
       Expected: Error message about invalid format.

2. **Deleting a Tag Group**

    1. **Prerequisites**: Create a Tag Group `Location` using `tg Location` that is not referenced by any contact's tags.
   Use `tg Property Type` and add a tag of this Tag Group to the first contact using `edit 1 t/PropertyType.HDB`

   2. **Test case**: `dtg Location`<br>
      Expected: Tag Group "Location" is deleted. Success message shown.

   3. **Test case**:  `dtg PropertyType` <br>
      Expected: Error message indicating the Tag Group is in use and cannot be deleted.

   4. **Test case**: `dtg NonExistent`<br>
      Expected: Error message indicating Tag Group does not exist.

   5. **Other invalid commands to try**: `dtg`, `dtg 123!`<br>
      Expected: Error message about invalid format.

3. **Using tags with Tag Groups**

    1. **Prerequisites**: Create a Tag Group "PropertyType" using `tg PropertyType`.

   2. **Test case**: Add contact with grouped tag: `add n/John Doe p/98765432 e/johnd@example.com a/123 Street r/Buyer t/PropertyType.HDB`<br>
       Expected: Contact added with tag "PropertyType.HDB". Success message shown.

   3. **Test case**: Add contact with tag referencing non-existent group: `add n/Jane Doe p/98765433 e/janed@example.com a/456 Street r/Seller t/Nonexistent.Condo`<br>
       Expected: Error message indicating Tag Group "Nonexistent" does not exist.

### Creating and managing roles
1. **Adding roles to a person**
   1. **Prerequisites**: List all persons using the `list` command. There should be multiple persons in the list.

   2. **Test case**: `edit 1 r/Buyer`
   Expected: The first contact’s role list is replaced with a single role “Buyer”. Success message shown.

   3. **Test case**: `edit 1 r/Buyer r/Investor`
   Expected: The first contact’s roles are replaced with “Buyer” and “Investor”. Success message shown.

   4. **Test case**: `edit 1 r/`
   Expected: All roles are removed from the first contact. Success message shown.

    5. **Test case**: `edit 1 r/_Admin or edit 1 r/-Leader`
    Expected: Error message indicating that roles cannot start with hyphen or underscore.

   6. **Other incorrect commands to try**: `edit 1 r/Inval!d`
   Expected: Error message about invalid role format.

### Filtering by Tag Groups, status or roles
1. **Basic filtering**
    1. **Prerequisites**:
       - Create Tag Groups `PropertyType` and `Location`.
       - Add tags `t/PropertyType.HDB`, `t/Location.East` to some contacts.
       - Add roles `Buyer`, `Seller`, `Investor` to some contacts.
       - Assign statuses `Pending` and `Completed` to some contacts.
   2. **Test case**: `filter tg/PropertyType`
      Expected: Lists all contacts with the “PropertyType.HDB” tag.

   3. **Test case**: `filter r/Buyer`
   Expected: Lists all contacts with the “Buyer” role (case-insensitive).

   4. **Test case**: `filter s/Pending`
   Expected: Lists all contacts with status “Pending” (case-insensitive).

2. **Multiple filter fields**

   1. **Test case**: `filter tg/PropertyType tg/Location`
   Expected: Shows contacts that have tags from either Tag Groups.

   2. **Test case**: `filter r/Buyer r/Investor`
   Expected: Shows contacts that have either roles.

   3. **Test case**: `filter s/Pending s/Completed`
   Expected: Shows contacts with either status.

   4. **Test case**: `filter tg/PropertyType r/Buyer s/Pending`
   Expected: Lists only contacts that satisfy either criteria: Tag Group `PropertyType`, role `Buyer`, and status `Pending`.

3. **Invalid and edge cases**

   1. **Test case**: `filter`
   Expected: Error message shown.

   2. **Test case**: `filter t/` or `filter r/` or `filter s/`
   Expected: Error message shown, filter values must be specified.

   3. **Test case**: `filter t/UnknownTag r/UnknownRole`
   Expected: No contacts shown, success message with empty list.

### Saving data

1. **Dealing with missing/corrupted data files**

    1. **Simulating a corrupted data file:**
        - Locate the data file at `[JAR file location]/data/addressbook.json`
        - Open it with a text editor
        - Delete a random closing brace `}` or bracket `]`
        - Save and close the file
        - Relaunch TrackerGuru

       **Expected:** TrackerGuru starts with an empty address book and shows a warning message about corrupted data. The corrupted file is not overwritten, unless the user makes changes to the empty address book.

    1. **Simulating a missing data file:**
        - Delete the `addressbook.json` file from the `data` folder
        - Relaunch TrackerGuru

       **Expected:** TrackerGuru starts with sample contacts (default data).

    1. **Simulating invalid Tag Group references:**
        - Open `addressbook.json`
        - Find a person with a tag like `"PropertyType.HDB"`
        - In the `tagGroups` array, delete the `{"tagGroupName": "PropertyType"}` entry
        - Save and relaunch

       **Expected:** For data resilience, TrackerGuru commands still work normally with Tag Group `PropertyType` and does not affect normal operations.
   Only when listing using `tg`, `PropertyType` will not appear.

## **Appendix: Future Enhancement**

Team size: 5

1. **Allow Optional Contact Fields in Add Command**: Currently, the add command requires users to input all four contact details: name, phone number, email, and address. This rigid requirement can be inconvenient for property agents, who often have only partial information. We plan to enhance the add command to allow these fields to be optional. Users will be able to add a new contact with any combination of available details, such as only a name and phone number. Missing fields will be stored as empty values and displayed as N/A in the contact list. 
This enhancement will provide greater flexibility and a smoother user experience, allowing agents to store and manage client information more conveniently without being restricted by mandatory fields.

2. **Support Incremental Editing for Roles, Statuses, and Tags**: Currently, the edit command overwrites all existing fields such as roles (r/), statuses (s/), and tags (t/) whenever these parameters are provided. This causes all prior data to be lost, even if the user’s intention was only to add or remove a single value.
We plan to enhance the edit command to differentiate between different types of edit operations, namely adding, removing, and replacing data. By distinguishing between these different edit command types, this enhancement will prevent accidental data loss, provide users with finer control over their edits and make the command behavior more intuitive for everyday use.
