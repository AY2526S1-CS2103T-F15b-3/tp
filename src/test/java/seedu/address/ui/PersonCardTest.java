package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.testfx.framework.junit5.ApplicationExtension;
import seedu.address.model.person.Person;

@ExtendWith(ApplicationExtension.class)
public class PersonCardTest {

    @BeforeAll
    public static void setupJavaFxToolkit() {
        new JFXPanel(); // Initializes the JavaFX runtime
    }

    @Test
    public void display_personWithStatus_statusLabelIsVisible() throws InterruptedException {
        // This test "covers" a person WITH a status
        Person personWithStatus = ALICE;

        final CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            PersonCard personCard = new PersonCard(personWithStatus, 1);

            assertTrue(personCard.getStatusLabel().isVisible());
            assertTrue(personCard.getStatusLabel().isManaged());
            assertEquals("PENDING", personCard.getStatusLabel().getText());

            latch.countDown();
        });

        latch.await();
    }

    @Test
    public void display_personWithoutStatus_statusLabelIsHidden() throws InterruptedException {
        // This test "covers" a person WITH no status
        Person personWithoutStatus = AMY;

        final CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            PersonCard personCard = new PersonCard(personWithoutStatus, 2);

            // Assertions use the new getter methods
            assertFalse(personCard.getStatusLabel().isVisible());
            assertFalse(personCard.getStatusLabel().isManaged());

            latch.countDown();
        });

        latch.await();
    }

    @Test
    public void display_personDetails_showsCorrectDetails() throws InterruptedException {
        // A general test to check other fields
        Person person = ALICE;
        final CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            PersonCard personCard = new PersonCard(person, 1);

            // Assertions use the new getter methods
            assertEquals("1. ", personCard.getIdLabel().getText());
            assertEquals(person.getName().fullName, personCard.getNameLabel().getText());
            assertEquals(person.getPhone().value, personCard.getPhoneLabel().getText());
            assertEquals(person.getEmail().value, personCard.getEmailLabel().getText());
            assertEquals(person.getAddress().value, personCard.getAddressLabel().getText());

            latch.countDown();
        });

        latch.await();
    }

}
