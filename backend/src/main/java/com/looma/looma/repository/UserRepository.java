package com.looma.looma.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.looma.looma.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	    Optional<User> findByEmail(String email);

	    boolean existsByEmail(String email);
	
}
