package pl.davidduke.todolistapi.storage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.davidduke.todolistapi.storage.enums.TaskStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    String description;

    @Column(
            name = "created_at",
            nullable = false
    )
    LocalDateTime createdAt;

    @Column(name = "finished_at")
    LocalDateTime finishedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TaskStatus status;

    @ManyToOne
    @JoinColumn(
            name = "person_id",
            nullable = false
    )
    UserEntity owner;
}
