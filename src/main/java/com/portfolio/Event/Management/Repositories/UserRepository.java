package com.portfolio.Event.Management.Repositories;

import com.portfolio.Event.Management.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
