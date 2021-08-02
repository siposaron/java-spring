package eu.upliftingflow.elastic.controller;

import eu.upliftingflow.elastic.controller.dto.AuthorDto;
import eu.upliftingflow.elastic.controller.dto.AuthorRequestDto;
import eu.upliftingflow.elastic.controller.mapper.AuthorMapper;
import eu.upliftingflow.elastic.domain.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping("/authors")
    public Flux<AuthorDto> getAuthors() {
        return authorService.getAuthors().map(authorMapper::toDto);
    }

    @GetMapping("/authors/{id}")
    public Mono<AuthorDto> getAuthorById(@PathVariable("id") String id) {
        return authorService.getAuthorById(id).map(authorMapper::toDto);
    }

    @PostMapping("/authors")
    public Mono<AuthorDto> createAuthor(@Valid @RequestBody AuthorRequestDto dto) {
        return authorService.createAuthor(authorMapper.toEntity(dto)).map(authorMapper::toDto);
    }

    @PutMapping("/authors/{id}")
    public Mono<AuthorDto> updateAuthor(
            @PathVariable("id") String id,
            @Valid @RequestBody AuthorRequestDto dto) {
        return authorService.updateAuthor(id, authorMapper.toEntity(dto)).map(authorMapper::toDto);
    }

    @DeleteMapping("/authors/{id}")
    public Mono<Void> deleteAuthorById(@PathVariable("id") String id) {
        return authorService.deleteAuthorById(id);
    }

}
