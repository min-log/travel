package com.example.travel.service.travel;

import com.example.travel.domain.Comments;
import com.example.travel.dto.travel.CommentsDTO;
import com.example.travel.repository.travel.CommentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Service
public class CommentsServiceImpl implements CommentsService{

    final CommentsRepository commentsRepository;

    @Override
    public CommentsDTO createComments(CommentsDTO commentsDTO) {

        Comments comments = commentsDTOToEntity(commentsDTO);
        log.info("저장 하려는 객체 : {}",comments);
        Comments save = commentsRepository.save(comments);
        CommentsDTO result = commentsEntityToDTO(save);
        log.info(result.getCommNo());
        return result;
    }

    @Override
    public Long deleteComments(Long no) {
        Comments comments = commentsRepository.getOne(no);
        commentsRepository.delete(comments);

        return no;
    }


    @Override
    public List<CommentsDTO> getCommentsList(Long categoryNo) {
        List<CommentsDTO> list = new ArrayList<>();
        try{
            List<Comments> commentsByCommCategory = commentsRepository.findCommentsByCommCategory(categoryNo);
            if (commentsByCommCategory != null){
                for (int i = 0;i < commentsByCommCategory.size();i++){
                    CommentsDTO commentsDTO = commentsEntityToDTO(commentsByCommCategory.get(i));
                    list.add(commentsDTO);
                }
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
