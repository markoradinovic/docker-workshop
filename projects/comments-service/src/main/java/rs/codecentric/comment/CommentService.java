package rs.codecentric.comment;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Map<String, String> saveComment(Comment comment) {
        Map<String, String> result = Maps.newHashMap();
        comment.setCommentedAt(new Date());
        String commentId = commentRepository.save(comment).getId();
        result.put("commentId", commentId);
        return result;
    }

    public List<Comment> getComments(String topicId) {
        return commentRepository.findByTopicId(topicId);
    }

    public Comment getCommentById(String commentId) {
        return commentRepository.findOne(commentId);
    }
}
