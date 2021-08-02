package eu.upliftingflow.elastic.domain.repository;

import eu.upliftingflow.elastic.domain.model.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

public interface CourseRepository extends ReactiveElasticsearchRepository<Course, String> {
    Flux<Course> findByTitleLike(final String title, final Pageable page);
    Flux<Course> findByDescriptionLike(final String description, final Pageable page);
    Flux<Course> findByLabels(final String label, final Pageable page);
    Flux<Course> findByAuthors_FirstNameLike(final String authorFirstName, final Pageable page);
}
