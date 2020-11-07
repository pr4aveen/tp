package seedu.momentum.model.project.predicates;

import java.util.function.Predicate;

import seedu.momentum.model.project.TrackedItem;

public class MomentumPredicate implements Predicate<TrackedItem> {

    @Override
    public boolean test(TrackedItem trackedItem) {
        return true;
    }
}
