package eu.upliftingflow.elastic.domain.service.impl;

import eu.upliftingflow.elastic.domain.mapper.CourseDocumentMapper;
import eu.upliftingflow.elastic.domain.model.Course;
import eu.upliftingflow.elastic.domain.repository.CourseRepository;
import eu.upliftingflow.elastic.domain.service.CourseService;
import eu.upliftingflow.elastic.exception.CourseException;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;

    private final ReactiveElasticsearchClient client;

    @Override
    public Mono<Course> createCourse(final Course course) {
        return courseRepo.save(course);
    }

    @Override
    public Flux<Course> getCourses() {
        return courseRepo.findAll();
    }

    @Override
    public Mono<Course> getCourseById(String id) {
        return courseRepo.findById(id)
                .switchIfEmpty(Mono.error(CourseException::new));
    }

    @Override
    public Flux<Course> findCourseByTitle(final String title) {
        // TODO: add paging
        return courseRepo.findByTitleLike(title, PageRequest.ofSize(20))
                .switchIfEmpty(Mono.error(CourseException::new));
    }

    @Override
    public Flux<Course> findCourseByDescription(final String description) {
        // TODO: add paging
        return courseRepo.findByDescriptionLike(description, PageRequest.ofSize(20))
                .switchIfEmpty(Mono.error(CourseException::new));
    }

    @Override
    public Flux<Course> findCourseByLabel(final String label) {
        // TODO: add paging
        return courseRepo.findByLabels(label, PageRequest.ofSize(20))
                .switchIfEmpty(Mono.error(CourseException::new));
    }

    @Override
    public Flux<Course> findCourseByAuthor(final String authorName) {
        // TODO: add paging
        return courseRepo.findByAuthors_FirstNameLike(authorName, PageRequest.ofSize(20))
                .switchIfEmpty(Mono.error(CourseException::new));
    }

    @Override
    public Flux<Course> queryCourses(final String query, final PageRequest page) {
        final var searchRequest = new SearchRequest();
        searchRequest
                .indices("courses")
                .source(SearchSourceBuilder.searchSource()
                        .query(QueryBuilders.multiMatchQuery(
                                query,
                    "title",
                                "description",
                                "labels",
                                "authors.firstName",
                                "authors.lastName"))
                        .from(page.getPageNumber())
                        .size(page.getPageSize()));
        return client.search(searchRequest)
                .map(CourseDocumentMapper::hitToCourse)
                .switchIfEmpty(Mono.error(CourseException::new));
    }

    @Override
    public Mono<Void> deleteCourseById(String id) {
        return courseRepo.deleteById(id)
                .switchIfEmpty(Mono.error(CourseException::new));
    }

    @Override
    public Mono<Course> updateCourse(String id, Course course) {
        course.setId(id);
        return courseRepo.findById(id)
                .map(c -> course)
                .flatMap(courseRepo::save)
                .switchIfEmpty(Mono.error(CourseException::new));
    }
}
