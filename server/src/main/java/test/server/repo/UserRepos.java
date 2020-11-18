package test.server.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import test.server.entity.UserEntity;

import java.util.List;

public interface UserRepos extends JpaRepository<UserEntity, Long> {

    final static String sql = "select user from UserEntity as user where user.name = ?1 ";
    final static String nativeSql = "select * from user where name = ?1 ";

    List<UserEntity> findAllByName(String name);

    @Query(sql)
    List<UserEntity> queryByUserName(String userName);

    @Query(value = nativeSql, nativeQuery = true)
    List<UserEntity> queryByUserNameUseNativeSql(String userName);

    @Query(value = sql)
    Page<UserEntity> queryPageByUserName(String userName, Pageable pageable);

    @Query(value = nativeSql, nativeQuery = true)
    Page<UserEntity> queryPageByUserNameUseNativeSql(String userName, Pageable pageable);
}
