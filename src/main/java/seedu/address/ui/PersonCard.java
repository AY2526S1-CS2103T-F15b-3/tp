package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane roles;
    @FXML
    private FlowPane tags;
    @FXML
    private Label status;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.roleName))
                .forEach(role -> roles.getChildren().add(new Label(role.roleName)));

        person.getStatus().ifPresent(s -> {
            status.setText(s.toString());
            status.setVisible(true);
            status.setManaged(true);
        });

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagFormat))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagFormat)));
    }

    // --- Getter methods for testing (package-private) ---
    // These methods are package-private and exist only for
    // PersonCardTest.java to access private @FXML fields.

    /**
     * Returns the Label component for the person's display index (ID).
     * Intended for testing only.
     *
     * @return The Label for the ID.
     */
    Label getIdLabel() {
        return id;
    }

    /**
     * Returns the Label component for the person's name.
     * Intended for testing only.
     *
     * @return The Label for the name.
     */
    Label getNameLabel() {
        return name;
    }

    /**
     * Returns the Label component for the person's phone.
     * Intended for testing only.
     *
     * @return The Label for the phone.
     */
    Label getPhoneLabel() {
        return phone;
    }

    /**
     * Returns the Label component for the person's address.
     * Intended for testing only.
     *
     * @return The Label for the address.
     */
    Label getAddressLabel() {
        return address;
    }

    /**
     * Returns the Label component for the person's email.
     * Intended for testing only.
     *
     * @return The Label for the email.
     */
    Label getEmailLabel() {
        return email;
    }

    /**
     * Returns the Label component for the person's status.
     * Intended for testing only.
     *
     * @return The Label for the status.
     */
    Label getStatusLabel() {
        return status;
    }
    
}
