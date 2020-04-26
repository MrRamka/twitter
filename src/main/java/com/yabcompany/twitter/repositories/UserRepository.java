package com.yabcompany.twitter.repositories;

import com.yabcompany.twitter.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
JpaRepository extends PagingAndSortingRepository which in turn extends CrudRepository.
Their main functions are:
-CrudRepository mainly provides CRUD functions.
-PagingAndSortingRepository provides methods to do pagination and sorting records.
-JpaRepository provides some JPA-related methods such as flushing the persistence
 context and deleting records in a batch.
*/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);


}
