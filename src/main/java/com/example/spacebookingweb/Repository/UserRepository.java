package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Database.View.UserInformationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);

    @Query(value = "SELECT view FROM UserInformationView view WHERE view.user_id=:id")
    List<UserInformationView> findUserInformationByUserId(Long id);
}
