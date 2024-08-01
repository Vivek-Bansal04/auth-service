package com.okta.auth.security.practice.connection;

import com.okta.auth.security.practice.connection.AnotherService;
import com.okta.auth.security.practice.connection.ExternalService;
import com.okta.auth.security.practice.connection.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SampleService {
    private final AnotherService anotherService;
    private final PersonRepository personRepository;
    private final ExternalService externalService;

    public SampleService(AnotherService anotherService, PersonRepository personRepository, ExternalService externalService) {
        this.anotherService = anotherService;
        this.personRepository = personRepository;
        this.externalService = externalService;
    }

    public void hello() {
        System.out.println(personRepository.findAll());
    }

    public void withExternalServiceCall() {
        //so it doesn't acquire connection here
        externalService.externalCall();
        //it acquires connection after this in case osiv is enabled
        System.out.println(personRepository.findAll());
        externalService.externalCall();
    }

    @Transactional
    public void withExternalServiceCallAfter() {
//        System.out.println(personRepository.findAll());
        externalService.externalCall();
    }

    @Transactional
    public void withNestedTransaction() {
        System.out.println(personRepository.findAll());
        anotherService.runsInNewTransaction();
    }
}