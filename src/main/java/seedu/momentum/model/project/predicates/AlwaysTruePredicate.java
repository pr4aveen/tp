package seedu.momentum.model.project.predicates;

import seedu.momentum.model.project.TrackedItem;

public class AlwaysTruePredicate implements MomentumPredicate {
    @Override
    public boolean test(TrackedItem trackedItem) {
        return true;
    }

    @Override
    public boolean isSamePredicate(MomentumPredicate other) {
        return other instanceof AlwaysTruePredicate;
    }
}
