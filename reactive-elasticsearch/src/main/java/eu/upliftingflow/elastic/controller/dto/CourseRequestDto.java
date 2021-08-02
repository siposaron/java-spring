package eu.upliftingflow.elastic.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Validated
@Data
public class CourseRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotEmpty
    private Set<String> labels;
    @NotEmpty
    @Valid
    private Set<AuthorDto> authors;
}
