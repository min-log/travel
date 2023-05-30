package com.example.travel.service.user;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserSaveDTO;
import com.example.travel.dto.user.UserSaveResultDTO;
import com.example.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;


    @Override
    public UserTravel userSave(UserSaveDTO userSaveDTO) {
        log.info("userSaveDTO : {}" , userSaveDTO);
        UserTravel entity = dtoToEntity(userSaveDTO);
        UserTravel result = userRepository.save(entity);
        log.info("result : {}" , result);
        return result;
    }

    @Override
    public UserSaveResultDTO userGetNo(Long no) {
        UserTravel entity = userRepository.getUserByUserNo(no);
        return entityToDto(entity);
    }

    @Override
    public int userGetId(String id) {
        UserTravel result = userRepository.getUserByUserId(id);
        log.info("뽑아진 ID 정보: {}",result);
        if ( result == null){
            //성공
            return 0;
        }else{
            //실패
            return 5;
        }

    }

    @Override
    public UserSaveResultDTO userModitfy(UserSaveDTO userSaveDTO) {

        UserSaveDTO dto = UserSaveDTO.builder()
                .userNo(userSaveDTO.getUserNo())
                .userId(userSaveDTO.getUserId())
                .userEmail(userSaveDTO.getUserEmail())
                .userPassword(userSaveDTO.getUserPassword())
                .userName(userSaveDTO.getUserName())
                .userBirthday(userSaveDTO.getUserBirthday())
                .userGender(userSaveDTO.getUserGender())
                .userPhone(userSaveDTO.getUserPhone())
                .address(userSaveDTO.getAddress())
                .addressPostcode(userSaveDTO.getAddressPostcode())
                .addressDetail(userSaveDTO.getAddressDetail())
                .addressExtra(userSaveDTO.getAddressExtra())
                .userImg(userSaveDTO.getUserImg())
                .build();
        UserTravel entity = dtoToEntity(dto);
        UserTravel entitySave = userRepository.save(entity);

        return entityToDto(entitySave);
    }
}
