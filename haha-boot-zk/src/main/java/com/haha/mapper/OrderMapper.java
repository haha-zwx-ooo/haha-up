package com.haha.mapper;

import com.haha.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @author HaHa
 */
@Mapper
public interface OrderMapper {

    /**
     * insert 插入订单记录
     *
     * @param order
     * @return int
     */
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert(" insert into `order`(user_id,pid) values(#{userId},#{pid}) ")
    int insert(Order order);
}
