package pl.davidduke.todolistapi.storage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.davidduke.todolistapi.storage.enums.Role;

import java.util.List;

@Entity
@Table(name = "_user")
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany(
            mappedBy = "owner"
    )
    List<TaskEntity> tasks;
}
