package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT user.username, user.password, user.email FROM User user")
    List <User> findAllUsers();
    User findUserById(Long id);
    User findUserByUsername(String username);
}
