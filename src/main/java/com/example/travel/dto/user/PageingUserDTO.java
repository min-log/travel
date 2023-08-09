package com.example.travel.dto.user;

import com.example.travel.dto.travel.CategoryDTO;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Data
public class PageingUserDTO {
    // 페이지에 전달할 값
    private Page<UserDTO> pageInfo;

    //현제 페이지 번호
    private int page;
    private int size;


    //총페이지 번호
    private int totalPage;
    //시작페이지 번호,끝페이지 번호
    private int start;
    private int end;

    //이전 다음 버튼
    private boolean prev;
    private boolean next;

    //페이지 번호 목록
    private List<Integer> pageList;


    public PageingUserDTO(Page<UserDTO> pageInfo){
        totalPage = pageInfo.getTotalPages();
        makePageList(pageInfo.getPageable());

    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/5.0))*5;
        start = tempEnd - 4;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }

}
