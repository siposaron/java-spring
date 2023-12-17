package eu.upliftingflow.elastic.controller;

import eu.upliftingflow.elastic.controller.dto.AuthorDto;
import eu.upliftingflow.elastic.controller.dto.AuthorRequestDto;
import eu.upliftingflow.elastic.controller.dto.CourseDto;
import eu.upliftingflow.elastic.controller.dto.CourseRequestDto;
import eu.upliftingflow.elastic.controller.mapper.CourseMapper;
import eu.upliftingflow.elastic.controller.validator.CourseAuthorValidator;
import eu.upliftingflow.elastic.domain.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final CourseAuthorValidator validator;

    @PostMapping("/courses")
    public Mono<CourseDto> createCourse(
            // TODO: validate authors
            @Valid @RequestBody CourseRequestDto dto) {
        // TODO: check validator
       var res = validator
                .ensureValidCourse(dto)
                .subscribe();
//                .subscribe(c -> courseService
//                        .createCourse(courseMapper.toEntity(c))
//                        .map(courseMapper::toDto));

       return null;//courseService.createCourse(courseMapper.toEntity(dto)).map(courseMapper::toDto);
    }

    @GetMapping("/courses/{id}")
    public Mono<CourseDto> getCourseById(@PathVariable("id") String id) {
        return courseService.getCourseById(id).map(courseMapper::toDto);
    }

    @GetMapping("/courses")
    public Flux<CourseDto> getCourses(@RequestParam(required = false) String search) {
        return courseService
                .queryCourses(search, PageRequest.of(0, 1))
                .map(courseMapper::toDto);
    }

    @PutMapping("/courses/{id}")
    public Mono<CourseDto> updateCourse(
            @PathVariable("id") String id,
            // TODO: validate authors
            @Valid @RequestBody CourseRequestDto dto) {
        return courseService
                .updateCourse(id, courseMapper.toEntity(dto))
                .map(courseMapper::toDto);
    }

    @DeleteMapping("/courses/{id}")
    public Mono<Void> deleteCourse(
            @PathVariable("id") String id) {
        return courseService.deleteCourseById(id);
    }
}
