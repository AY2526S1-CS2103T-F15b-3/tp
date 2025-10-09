---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

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

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

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

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

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

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


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

| Priority | As a …​                     | I can …​                                                                                    | So that …​                                                                    |
|----------|-----------------------------|---------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------|
| `* *`    | potential user              | see sample contacts                                                                         | I can expect how the contacts will look like before using the App             |
| `* * *`  | new user                    | see usage instructions                                                                      | I can refer to instructions when I forget how to use the App                  |
| `*`      | new user                    | purge all sample contacts                                                                   | I can start using the app proper                                              |
| `* * *`  | new user                    | view a list of my contacts                                                                  | I can see all my current contacts                                             |
| `* * *`  | new user                    | add a new contact                                                                           | I can keep track of my growing contacts                                       |
| `* * *`  | new user                    | delete a contact                                                                            | I can remove contacts I no longer need                                        |
| `* * *`  | new user                    | view a contact's details                                                                    | I can easily reference information about a contact                            |
| `* *`    | new user                    | edit a contact's details                                                                    | I can make amends to changes easily                                           |
| `* *`    | new user                    | give an existing contact a role                                                             | I can identify the different types of my clients easily                       |
| `* *`    | new user                    | delete a role from an existing contact                                                      | I can remove roles that no longer apply to the contact                        |
| `* *`    | new user                    | assign a client a property price tag                                                        | I can identify the property price which a client is willing to buy/sell/rent  |
| `* *`    | new user                    | remove a property price tag from a client                                                   | I can remove property prices that no longer describe the client               |
| `* *`    | new user                    | update a property price tag of a client                                                     | I can easily update the most recent property prices a client want             |
| `* *`    | new user                    | give a client a property location tag                                                       | I can identify contacts by property location                                  |
| `* *`    | new user                    | remove a property location tag from a client                                                | I can remove a property location that the contact no longer owns/wants        |
| `* *`    | new user                    | give a client a status                                                                      | I can know each client's transaction status                                   |
| `* *`    | new user                    | remove the status from a client                                                             | I can remove a transaction status from a client that no longer applies        |
| `* *`    | new user                    | give a client a property size tag                                                           | I can know the size of the property a client wants/owns                       |
| `* *`    | new user                    | remove the property size tag from a client                                                  | I can remove outdated information about the property size of a client         |
| `* *`    | new user                    | give a client a property type tag                                                           | I can know the type of the property a client wants/owns                       |
| `* *`    | new user                    | remove the property type tag from a client                                                  | I can remove outdated information about the property type of a client         |
| `* *`    | new user                    | give a tenant client a rental duration tag                                                  | I can know the tenancy period of each tenant                                  |
| `* *`    | user with sensitive clients | hide contact details                                                                        | I can minimize chance of someone else seeing them by accident                 |
| `* *`    | user with many client roles | view all client roles in the addressbook                                                    | I can understand what types of roles I have already created so far            |
| `* *`    | user with many client roles | be warned about similar roles created previously                                            | I can reduce the number of duplicated roles caused by inconsistent formatting |
| `*`      | expert user                 | quickly filter tenants based on expiring rental contracts                                   | I know which tenant to follow up next                                         |
| `*`      | expert user                 | easily advance the status tag of clients to the next state (e.g. looking to buy to signing) | I can easily know the transaction status of my clients                        |
| `*`      | expert user                 | view summary statistics like total clients or total buyers                                  | I can have an overview of my own portfolio                                    |
| `*`      | expert user                 | undo commands quickly from the cli                                                          | I can quickly rectify mistakes                                                |
| `*`      | expert user                 | export my contact book                                                                      | I can migrate my data to other devices                                        |
| `*`      | expert user                 | import existing client data from a csv file                                                 | I can start from an existing database                                         |
| `* *`    | user with many contacts     | search a contact by name                                                                    | I can locate details of a person without having to go through the entire list |
| `*`      | user with many contacts     | quickly filter contacts based on their role                                                 | I can easily view a list of clients of the same role together                 |
| `*`      | user with many contacts     | quickly filter contacts based on their property location tag                                | I can easily view a list of clients of the same property location together    |
| `*`      | user with many contacts     | sort contacts by name alphabetically                                                        | I can locate them easily                                                      |
| `*`      | user with many contacts     | sort contacts by property size tag                                                          | I can explore contacts that want to buy/sell property at similar sizes        |
| `*`      | user with many contacts     | sort contacts by property price tag                                                         | I can explore contacts that want to buy/sell property at similar prices       |

### Use cases

(For all use cases below, the **System** is the 'TrackerGuru' and the **Actor** is the 'Property Agent', unless specified otherwise. The term 'User' will be synonymous to 'Property Agent')

**Use case: UC1 - Add a contact**

**Guarantees**

* The address book will not contain duplicate contacts after any operation.
* If the contact is added, all contact details provided by the user will be stored without any loss of information.

**MSS**

1. User requests to add a contact together with their relevant details.
2. TrackerGuru saves the contact and its details.
3. TrackerGuru displays a success message to the user.

   Use case ends.

**Extensions**

* 1a. TrackerGuru detects an error in the entered data (missing required fields or improper format).
    * 1a1. TrackerGuru displays an error message and the proper command format to the user.  
      Use case resumes from step 1.

* 1b. TrackerGuru detects a duplicate contact (same phone number).
    * 1b1. TrackerGuru requests confirmation to overwrite or abort.
    * 1b2. User chooses to overwrite → TrackerGuru updates the contact and displays a success message.
    * 1b3. User chooses to abort → Use case ends.

* 1c. TrackerGuru fails to save the contact due to a system error.
    * 1c1. TrackerGuru displays an error message.  
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

**Use case: UC3 - Tag contact with role**

**Guarantees**

* Role tags associated with updated contacts will be of a valid role type (buyer, seller, ...).

**MSS**

1. User requests to tag a contact with a role.
2. TrackerGuru saves the contact with its updated tags (existing tags are not overridden).
3. TrackerGuru displays a success message to the user.

   Use case ends.

**Extensions**

* 1a. TrackerGuru detects an error in the entered data (role type entered by user is invalid).
    * 1a1. TrackerGuru displays an error message and available role types to the user.  
      Use case resumes from step 1.

* 1b. TrackerGuru fails to save the updated contact due to a system error.
    * 1b1. TrackerGuru displays an error message.  
      Use case ends.

---

**Use case: UC4 - Search contact by name**

**Guarantees**

* Only contacts whose names match the given keywords will be displayed to the user (if MSS completes).
* Contact details will not be modified.

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
* All the fields in the contact will be of valid type.

**MSS**

1. User requests to edit contact with together with their relevant details.
2. System edits and saves the updated contact information.
3. System displays success message to the user. 
4. System's contact list reflects the updated contact information.

    Use case ends.

**Extensions**

* 1a. System detects an error in the entered data (invalid index).

  * 1a1. System displays error message that the specified index is invalid.
  
    Use case resumes from step 1.

* 2a. System fails to edit the contact information due to a system error.

  * 2a1. System displays an error message indicating the failure. 
  
    Use case ends.

---

**Use case: UC-6 Sort contacts**

**Guarantees**

* The contact list remains intact with no data lost or modified.
* The contact list will be displayed in the specified sorted order.

**MSS**

1. User requests to sort the contact list.
2. System sorts the contact list based on the specified criterion.
3. System displays the sorted contact list.

   Use case ends.

**Extensions**

* 1a. System detects an error in the entered data (invalid or missing sorting criterion).

  * 1a1. System displays an error message indicating that the criterion is invalid or missing.
    
    Use case resumes from step 1.

* 2a. System fails to sort the contacts due to a system error. 

  * 2a1. System displays an error message indicating the failure. 
  
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

* **client**: A specific kind of contact that represents customers of the property agent (i.e. property buyers, sellers, landlords, tenants) 
* **contact information**: Exact contact details of a person; name, phone number, email, address
* **contact tags**: Labels or categories used to group contacts
* **role**: A field that defines the function or relationship of a contact (e.g. buyer, seller, landlord, tenant) in the property business 
* **status**: A label indicating the current state of a client transaction (e.g. looking to sell, looking to buy, signing).
* **property size**: A label describing the size of a property in sqft that the client is offering/looking for
* **property type**: A label describing the type of property that the client is offering/looking for (e.g. studio, terrace, hdb)
* **property location**: A label specifying the geographical area of a property that the client is offering/looking for (e.g. Bishan, Woodlands)
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
