package rs.codecentric;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import rs.codecentric.comment.Comment;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = Comment.class)
public class CommentsServiceApplicationConfiguration {
}