package pl.davidduke.todolistapi.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.davidduke.todolistapi.storage.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
