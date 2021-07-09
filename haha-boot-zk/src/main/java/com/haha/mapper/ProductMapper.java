package com.haha.mapper;

import com.haha.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * @author HaHa
 */
@Mapper
public interface ProductMapper {

    /**
     * getProduct by  id
     *
     * @param id
     * @return Product
     */
    @Select(" select * from product where id=#{id}  ")
    Product getProduct(@Param("id") Integer id);

    /**
     * deductStock 修改库存 by  id
     *
     * @param id
     * @return int
     */
    @Update(" update product set stock=stock-1    where id=#{id}  ")
    int deductStock(@Param("id") Integer id);
}
