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

import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.UniqueTrackedItemList;

public class TypicalProjectsOrders {

    public static List<TrackedItem> getOrderedProjectBookByAlphabeticalAscending() {
        return new ArrayList<>(Arrays.asList(
                ALICE,
                BENSON,
                CARL,
                DANIEL,
                ELLE,
                FIONA,
                GEORGE));
    }

    public static List<TrackedItem> getOrderedProjectBookByAlphabeticalDescending() {
        return new ArrayList<>(Arrays.asList(
                GEORGE,
                FIONA,
                ELLE,
                DANIEL,
                CARL,
                BENSON,
                ALICE));
    }

    public static List<TrackedItem> getOrderedProjectBookByDeadlineAscending() {
        return new ArrayList<>(Arrays.asList(
                FIONA,
                ELLE,
                ALICE,
                BENSON,
                CARL,
                DANIEL,
                GEORGE));
    }

    public static List<TrackedItem> getOrderedProjectBookByDeadlineDescending() {
        return new ArrayList<>(Arrays.asList(
                BENSON,
                ALICE,
                ELLE,
                FIONA,
                CARL,
                DANIEL,
                GEORGE));
    }

    public static List<TrackedItem> getOrderedProjectBookByCreatedDateAscending() {
        return new ArrayList<>(Arrays.asList(
                ALICE,
                BENSON,
                FIONA,
                DANIEL,
                ELLE,
                GEORGE,
                CARL));
    }

    public static List<TrackedItem> getOrderedProjectBookByCreatedDateDescending() {
        return new ArrayList<>(Arrays.asList(
                CARL,
                GEORGE,
                ELLE,
                DANIEL,
                FIONA,
                BENSON,
                ALICE));
    }

    public static List<TrackedItem> getOrderedProjectBookByReminderAscending() {
        return new ArrayList<>(Arrays.asList(
                GEORGE,
                BENSON,
                ALICE,
                CARL,
                DANIEL,
                ELLE,
                FIONA));
    }

    public static List<TrackedItem> getOrderedProjectBookByReminderDescending() {
        return new ArrayList<>(Arrays.asList(
                FIONA,
                ELLE,
                DANIEL,
                CARL,
                ALICE,
                BENSON,
                GEORGE));
    }

    public static UniqueTrackedItemList getUniqueProjectList(List<TrackedItem> trackedItems) {
        UniqueTrackedItemList uniqueTrackedItemList = new UniqueTrackedItemList();
        for (TrackedItem p : trackedItems) {
            uniqueTrackedItemList.add(p);
        }
        return uniqueTrackedItemList;
    }

}
