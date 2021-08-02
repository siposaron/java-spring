package eu.upliftingflow.elastic.domain.service;

import eu.upliftingflow.elastic.domain.model.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    /**
     * Creates the author indexed document
     * @param author {@link Author}
     * @return the saved {@link Author}
     */
    Mono<Author> createAuthor(final Author author);

    /**
     * Returns all authors
     * @return
     */
    Flux<Author> getAuthors();

    /**
     * Returns author by id
     * @return
     */
    Mono<Author> getAuthorById(final String id);

    /**
     * Deletes the author by id
     * @return
     */
    Mono<Void> deleteAuthorById(final String id);

    /**
     * Updates the author indexed document
     * @param id the author id
     * @param author {@link Author}
     * @return the saved {@link Author}
     */
    Mono<Author> updateAuthor(final String id, final Author author);
}
