//@@author khoodehui
package seedu.momentum.ui;

import java.util.Comparator;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.momentum.model.tag.Tag;

/**
 * A UI component to display the current tags that exist inside the project book.
 */
public class TagsDisplay extends UiPart<Region> {

    private static final String FXML = "TagsDisplay.fxml";

    private Set<Tag> tagsSet;

    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code TagsDisplay}.
     *
     * @param tagsSet Set of tags in the project book.
     */
    public TagsDisplay(Set<Tag> tagsSet) {
        super(FXML);
        this.tagsSet = tagsSet;
        fillTags();
    }

    private void fillTags() {
        tagsSet.stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
