package seedu.momentum.model.project.predicates;

import java.util.function.Predicate;

import seedu.momentum.model.project.TrackedItem;

public interface MomentumPredicate extends Predicate<TrackedItem> {

    boolean isSamePredicate(MomentumPredicate other);
}
