package pl.davidduke.todolistapi.storage.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.davidduke.todolistapi.storage.entities.TaskEntity;
import pl.davidduke.todolistapi.storage.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findAllByOwnerEmail(
            String ownerEmail, Pageable pageable
    );

    Optional<TaskEntity> findByOwner_EmailAndId(String owner, Long id);
}
