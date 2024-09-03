package pl.davidduke.todolistapi.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.davidduke.todolistapi.storage.entities.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
