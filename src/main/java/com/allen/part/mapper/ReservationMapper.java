package com.allen.part.mapper;

import com.allen.part.model.entity.Reservation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author KiveAllen
* @description 针对表【reservation】的数据库操作Mapper
* @createDate 2024-11-15 13:48:26
* @Entity com.yupi.yupao.model.domain.Reservation
*/
@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {

}




