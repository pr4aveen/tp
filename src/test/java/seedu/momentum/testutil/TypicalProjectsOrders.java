package seedu.momentum.testutil;

import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.BENSON;
import static seedu.momentum.testutil.TypicalProjects.CARL;
import static seedu.momentum.testutil.TypicalProjects.DANIEL;
import static seedu.momentum.testutil.TypicalProjects.ELLE;
import static seedu.momentum.testutil.TypicalProjects.FIONA;
import static seedu.momentum.testutil.TypicalProjects.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.UniqueTrackedItemList;

public class TypicalProjectsOrders {

    public static List<TrackedItem> getOrderedProjectBookByAlphabeticalAscending() {
        List<TrackedItem> trackedItems = new ArrayList<>(Arrays.asList(
                ALICE,
                BENSON,
                CARL,
                DANIEL,
                ELLE,
                FIONA,
                GEORGE));
        return trackedItems;
    }

    public static List<TrackedItem> getOrderedProjectBookByAlphabeticalDescending() {
        List<TrackedItem> trackedItems = new ArrayList<>(Arrays.asList(
                GEORGE,
                FIONA,
                ELLE,
                DANIEL,
                CARL,
                BENSON,
                ALICE));
        return trackedItems;
    }

    public static List<TrackedItem> getOrderedProjectBookByDeadlineAscending() {
        List<TrackedItem> trackedItems = new ArrayList<>(Arrays.asList(
                FIONA,
                ELLE,
                ALICE,
                BENSON,
                CARL,
                DANIEL,
                GEORGE));
        return trackedItems;
    }

    public static List<TrackedItem> getOrderedProjectBookByDeadlineDescending() {
        List<TrackedItem> trackedItems = new ArrayList<>(Arrays.asList(
                BENSON,
                ALICE,
                ELLE,
                FIONA,
                CARL,
                DANIEL,
                GEORGE));
        return trackedItems;
    }

    public static List<TrackedItem> getOrderedProjectBookByCreatedDateAscending() {
        ProjectBook projectBook = new ProjectBook();
        List<TrackedItem> trackedItems = new ArrayList<>(Arrays.asList(
                ALICE,
                BENSON,
                FIONA,
                DANIEL,
                ELLE,
                GEORGE,
                CARL));
        return trackedItems;
    }

    public static List<TrackedItem> getOrderedProjectBookByCreatedDateDescending() {
        List<TrackedItem> trackedItems = new ArrayList<>(Arrays.asList(
                CARL,
                GEORGE,
                ELLE,
                DANIEL,
                FIONA,
                BENSON,
                ALICE));
        return trackedItems;
    }

    public static UniqueTrackedItemList getUniqueProjectList(List<TrackedItem> trackedItems) {
        UniqueTrackedItemList uniqueTrackedItemList = new UniqueTrackedItemList();
        for (TrackedItem p : trackedItems) {
            uniqueTrackedItemList.add(p);
        }
        return uniqueTrackedItemList;
    }

}
