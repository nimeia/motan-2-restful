package test.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import test.server.entity.UserEntity;

public interface UserRepos extends JpaRepository<UserEntity,Long> {
}
