package pl.davidduke.todolistapi.storage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "Person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(
            name = "first_name",
            nullable = false
    )
    String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    String lastName;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @OneToMany(
            mappedBy = "owner"
    )
    List<TaskEntity> tasks;
}
