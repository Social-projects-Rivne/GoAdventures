package io.softserve.goadventures.registration.repository;

import org.springframework.data.repository.CrudRepository;

import io.softserve.goadventures.registration.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}