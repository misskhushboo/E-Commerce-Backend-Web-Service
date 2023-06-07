package org.ace.hcl.orderbillingsystem.userauthentication.repository;

import org.ace.hcl.orderbillingsystem.userauthentication.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredential, Integer> {
    Optional<UserCredential> findByUsername(String username);
}
