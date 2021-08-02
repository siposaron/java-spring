package eu.upliftingflow.elastic.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Data
public class AuthorRequestDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
