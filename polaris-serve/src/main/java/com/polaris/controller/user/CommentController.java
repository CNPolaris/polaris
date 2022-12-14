package com.polaris.controller.user;

import com.polaris.api.user.CommentApi;
import com.polaris.entity.MallReply;
import com.polaris.entity.RespBean;
import com.polaris.model.user.comment.CommentCreateRequest;
import com.polaris.model.user.comment.CommentListResponse;
import com.polaris.model.user.comment.CommentPageParam;
import com.polaris.model.user.comment.CommentedNoteParam;
import com.polaris.service.MallReplyService;
import com.polaris.utils.JwtTokenUtil;
import com.polaris.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author: polaris
 */
@RestController("UserCommentController")
public class CommentController implements CommentApi {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MallReplyService mallReplyService;

    @Override
    public ResponseEntity<Object> apiCommentCreate(@RequestHeader(value="Authorization", required=true) String authorization,
                                                   @RequestBody(required = false) CommentCreateRequest request) {
        request.setCreateUser(jwtTokenUtil.getUserId(authorization));
        MallReply mallReply = mallReplyService.apiCommentCreate(request);
        return new ResponseEntity<>(RespBean.success(mallReply), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> apiCommentCommentIdDelete(@PathVariable("comment_id") String commentId,
                                                            @RequestHeader(value="Authorization")String authorization) {
        if(mallReplyService.apiCommentDelete(commentId)){
            return new ResponseEntity<>(RespBean.success("删除成功"),HttpStatus.OK);
        }
        return new ResponseEntity<>(RespBean.error("删除失败"),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> apiCommentGetList(@RequestHeader(value="Authorization")String authorization,
                                                 @RequestBody CommentPageParam model) {
        CommentListResponse response = mallReplyService.apiCommentList(model);
        return new ResponseEntity<>(RespBean.success(response),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> apiCommentPersonalGet(@RequestHeader(value="Authorization")String authorization,
                                                        @RequestParam(value = "limit", required = false, defaultValue = "20") Long limit,
                                                        @RequestParam(value = "page", required = false, defaultValue = "1") Long page) {
        CommentedNoteParam param = new CommentedNoteParam();
        param.setPage(PageUtil.getDbPage(page,limit));
        param.setLimit(limit);
        param.setUserId(jwtTokenUtil.getUserId(authorization));
        return new ResponseEntity<>(RespBean.success(mallReplyService.getCommentedNoteList(param)),HttpStatus.OK);
    }
}
