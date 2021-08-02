package eu.upliftingflow.elastic.controller.mapper;

import eu.upliftingflow.elastic.controller.dto.CourseDto;
import eu.upliftingflow.elastic.controller.dto.CourseRequestDto;
import eu.upliftingflow.elastic.domain.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    // TODO: add a DTO with authorIds, map the authorIds to Authors when create / update
    Course toEntity(CourseRequestDto dto);
    CourseDto toDto(Course course);
}
