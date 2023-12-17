package eu.upliftingflow.elastic.controller.validator;

import eu.upliftingflow.elastic.controller.dto.CourseRequestDto;
import eu.upliftingflow.elastic.domain.repository.AuthorRepository;
import eu.upliftingflow.elastic.exception.CourseException;
import lombok.Data;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Data
@Component
public class CourseAuthorValidator {

    private final AuthorRepository authorRepo;

    public Mono<Boolean> ensureValidCourse(final CourseRequestDto courseDto) {
        return Mono.just(courseDto)
                .flatMapIterable(course -> course.getAuthorIds())
                .flatMap(authorId -> {
                    System.out.println(authorId);
                    var exists = authorRepo.existsById(authorId);
                    exists.subscribe(System.out::println);
                    return exists;
                })
                .hasElement(Boolean.FALSE);
//                .map(result -> {
//                    var res = result.booleanValue() ? null : courseDto;
//                    System.out.println(res);
//                    return res;
//                });
//                .switchIfEmpty(Mono.error(CourseException::new));
    }

}