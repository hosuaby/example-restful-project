package io.hosuaby.restful.repositories;

import io.hosuaby.restful.domain.Teapot;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB repository for teapots. No exceptions are thrown at the repository
 * level. This repository is not thread safe. Thread safe must be assured at
 * service layer.
 */
public interface TeapotRepository extends MongoRepository<Teapot, String> {

}
