package eu.upliftingflow.elastic.domain.repository;

import eu.upliftingflow.elastic.domain.model.Author;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface AuthorRepository extends ReactiveSortingRepository<Author, String> {
}
