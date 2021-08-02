package eu.upliftingflow.elastic.domain.service;

import eu.upliftingflow.elastic.domain.model.Course;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CourseService {
    /**
     * Creates the course indexed document
     * @param course {@link Course}
     * @return the saved {@link Course}
     */
    Mono<Course> createCourse(final Course course);

    /**
     * Returns all courses
     * @return
     */
    Flux<Course> getCourses();

    /**
     * Returns course by id
     * @return
     */
    Mono<Course> getCourseById(final String id);

    /**
     * Returns courses by title match
     * @return
     */
    Flux<Course> findCourseByTitle(final String title);

    /**
     * Returns courses by description match
     * @return
     */
    Flux<Course> findCourseByDescription(final String description);

    /**
     * Returns courses by author name match
     * @param authorName
     * @return
     */
    Flux<Course> findCourseByAuthor(final String authorName);

    /**
     * Returns courses by label match
     * @return
     */
    Flux<Course> findCourseByLabel(final String label);

    /**
     * Deletes the course by id
     * @return
     */
    Mono<Void> deleteCourseById(final String id);

    /**
     * Updates the course
     * @param id the author id
     * @param course {@link Course}
     * @return the saved {@link Course}
     */
    Mono<Course> updateCourse(final String id, final Course course);

    /**
     * Full text search of courses
     * @param query the query string
     * @param page the pagination
     * @return
     */
    Flux<Course> queryCourses(final String query, final PageRequest page);
}
