package eu.upliftingflow.elastic.controller;

import eu.upliftingflow.elastic.controller.dto.AuthorDto;
import eu.upliftingflow.elastic.controller.dto.AuthorRequestDto;
import eu.upliftingflow.elastic.controller.dto.CourseDto;
import eu.upliftingflow.elastic.controller.dto.CourseRequestDto;
import eu.upliftingflow.elastic.controller.mapper.CourseMapper;
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

    @PostMapping("/courses")
    public Mono<CourseDto> createCourse(
            @Valid @RequestBody CourseRequestDto dto) {
        return courseService.createCourse(courseMapper.toEntity(dto)).map(courseMapper::toDto);
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


}
