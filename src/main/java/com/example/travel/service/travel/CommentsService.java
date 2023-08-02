package com.example.travel.service.travel;


import com.example.travel.domain.Comments;
import com.example.travel.dto.travel.CommentsDTO;

import java.util.List;

public interface CommentsService {

    public CommentsDTO createComments(CommentsDTO commontsDTO);
    public List<CommentsDTO> getCommentsList(Long categoryNo);

    default CommentsDTO commentsEntityToDTO(Comments comments){
        CommentsDTO result = CommentsDTO.builder()
                .commId(comments.getCommId())
                .commCategory(comments.getCommCategory())
                .commUser(comments.getCommUser())
                .commUserNo(comments.getCommUserNo())
                .commCont(comments.getCommCont())
                .build();
        return result;
    }

    default Comments commentsDTOToEntity(CommentsDTO comments){
        Comments result = Comments.builder()
                .commId(comments.getCommId())
                .commCategory(comments.getCommCategory())
                .commUser(comments.getCommUser())
                .commUserNo(comments.getCommUserNo())
                .commCont(comments.getCommCont())
                .build();
        return result;
    }
}
