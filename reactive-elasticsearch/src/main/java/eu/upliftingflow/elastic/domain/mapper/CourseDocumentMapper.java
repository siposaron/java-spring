package eu.upliftingflow.elastic.domain.mapper;

import eu.upliftingflow.elastic.domain.model.Author;
import eu.upliftingflow.elastic.domain.model.Course;
import org.elasticsearch.search.SearchHit;

import java.util.*;
import java.util.stream.Collectors;

public class CourseDocumentMapper {
    public static Course hitToCourse(final SearchHit hit) {
        var map = hit.getSourceAsMap();
        return Course.builder()
                .id(hit.getId())
                .title(map.get("title").toString())
                .description(map.get("description").toString())
                .labels(new HashSet<String>((List<String>)map.get("labels")))
                .authors(extractAuthors(map))
                .build();
    }

    private static Set<Author> extractAuthors(final Map<String, Object> map) {
        final var authorHits = (List<HashMap<String, String>>) map.get("authors");
        return authorHits.stream().map(author -> Author.builder()
                .id(author.get("id"))
                .firstName(author.get("firstName"))
                .lastName(author.get("lastName"))
                .build()).collect(Collectors.toSet());
    }
}
