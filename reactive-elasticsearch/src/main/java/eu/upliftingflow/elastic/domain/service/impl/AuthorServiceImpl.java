package eu.upliftingflow.elastic.domain.service.impl;

import eu.upliftingflow.elastic.domain.model.Author;
import eu.upliftingflow.elastic.domain.repository.AuthorRepository;
import eu.upliftingflow.elastic.domain.service.AuthorService;
import eu.upliftingflow.elastic.exception.AuthorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepo;

    @Override
    public Mono<Author> createAuthor(final Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Flux<Author> getAuthors() {
        return authorRepo.findAll();
    }

    @Override
    public Mono<Author> getAuthorById(final String id) {
        return authorRepo.findById(id)
                .switchIfEmpty(Mono.error(AuthorException::new));
    }

    @Override
    public Mono<Void> deleteAuthorById(final String id) {
        return authorRepo.deleteById(id)
                .switchIfEmpty(Mono.error(AuthorException::new));
    }

    @Override
    public Mono<Author> updateAuthor(String id, Author author) {
        author.setId(id);
        return authorRepo.findById(id)
                .map(a -> author)
                .flatMap(authorRepo::save)
                .switchIfEmpty(Mono.error(AuthorException::new));
    }

}
