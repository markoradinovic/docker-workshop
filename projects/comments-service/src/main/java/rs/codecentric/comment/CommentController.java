package rs.codecentric.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> saveComment(@RequestBody @Validated Comment comment, HttpServletRequest request) {
        comment.setCommentedBy((User) request.getAttribute("user"));
        return commentService.saveComment(comment);
    }

    @GetMapping(path = "/topic/{topicId}")
    public List<Comment> getCommentsByTopicId(@PathVariable("topicId") String topicId) {
        return commentService.getComments(topicId);
    }

    @GetMapping(path = "/{commentId}")
    public Comment getCommentById(@PathVariable("commentId") String commentId) {
        return commentService.getCommentById(commentId);
    }

}
