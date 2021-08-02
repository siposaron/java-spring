package eu.upliftingflow.elastic.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"authors"})
public class CourseDto {
    private String id;
    private String title;
    private String description;
    private Set<String> labels;
    private Set<AuthorDto> authors;
}
