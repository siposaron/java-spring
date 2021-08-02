package eu.upliftingflow.elastic.controller.mapper;

import eu.upliftingflow.elastic.controller.dto.AuthorDto;
import eu.upliftingflow.elastic.controller.dto.AuthorRequestDto;
import eu.upliftingflow.elastic.domain.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorRequestDto authorRequestDto);
    AuthorDto toDto(Author author);
}
