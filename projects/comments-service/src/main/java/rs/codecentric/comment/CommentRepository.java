package rs.codecentric.comment;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends ElasticsearchRepository<Comment, String> {

    List<Comment> findByTopicId(String topicId);
}
