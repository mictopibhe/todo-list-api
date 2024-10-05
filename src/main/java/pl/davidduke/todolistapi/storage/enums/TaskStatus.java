package pl.davidduke.todolistapi.storage.enums;

import java.util.Set;

public enum TaskStatus {
    PLANNED,
    WORK_IN_PROGRESS,
    POSTPONED,
    NOTIFIED,
    SIGNED,
    DONE,
    CANCELED;

    private Set<TaskStatus> allowedTransitions;

    static {
        PLANNED.allowedTransitions = Set.of(PLANNED, WORK_IN_PROGRESS, POSTPONED, CANCELED, DONE);
        WORK_IN_PROGRESS.allowedTransitions = Set.of(WORK_IN_PROGRESS, NOTIFIED, SIGNED, CANCELED, DONE);
        POSTPONED.allowedTransitions = Set.of(POSTPONED, NOTIFIED, SIGNED, CANCELED, DONE);
        NOTIFIED.allowedTransitions = Set.of(NOTIFIED, CANCELED, DONE);
        SIGNED.allowedTransitions = Set.of(SIGNED, CANCELED, DONE);
        DONE.allowedTransitions = Set.of(DONE, CANCELED);
        CANCELED.allowedTransitions = Set.of(CANCELED);
    }

    public boolean canTransitionTo(TaskStatus status) {
        return allowedTransitions.contains(status);
    }
}
