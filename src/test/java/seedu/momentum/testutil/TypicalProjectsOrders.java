//@@author kkangs0226
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

public class TypicalProjectsOrders {

    public static List<TrackedItem> getOrderedProjectBookByAlphabeticalAscendingCompleted() {
        return new ArrayList<>(Arrays.asList(
                ALICE,
                DANIEL,
                ELLE,
                FIONA,
                BENSON,
                CARL,
                GEORGE));
    }

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

    public static List<TrackedItem> getOrderedProjectBookByAlphabeticalDescendingCompleted() {
        return new ArrayList<>(Arrays.asList(
                FIONA,
                ELLE,
                DANIEL,
                ALICE,
                GEORGE,
                CARL,
                BENSON));
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

    public static List<TrackedItem> getOrderedProjectBookByDeadlineAscendingCompleted() {
        return new ArrayList<>(Arrays.asList(
                FIONA,
                ELLE,
                ALICE,
                DANIEL,
                BENSON,
                CARL,
                GEORGE));
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

    public static List<TrackedItem> getOrderedProjectBookByDeadlineDescendingCompleted() {
        return new ArrayList<>(Arrays.asList(
                ALICE,
                ELLE,
                FIONA,
                DANIEL,
                BENSON,
                CARL,
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

    public static List<TrackedItem> getOrderedProjectBookByCreatedDateAscendingCompleted() {
        return new ArrayList<>(Arrays.asList(
                ALICE,
                FIONA,
                DANIEL,
                ELLE,
                BENSON,
                GEORGE,
                CARL));
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

    public static List<TrackedItem> getOrderedProjectBookByCreatedDateDescendingCompleted() {
        return new ArrayList<>(Arrays.asList(
                ELLE,
                DANIEL,
                FIONA,
                ALICE,
                CARL,
                GEORGE,
                BENSON));
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

}
