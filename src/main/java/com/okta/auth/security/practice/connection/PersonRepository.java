package com.okta.auth.security.practice.connection;

import com.okta.auth.security.practice.connection.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
}