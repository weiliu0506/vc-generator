package com.vc.api.repository.mapper;

import com.vc.api.repository.entity.TestJson;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TestJsonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestJson record);

    int insertSelective(TestJson record);

    TestJson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestJson record);

    int updateByPrimaryKeyWithBLOBs(TestJson record);

    int selectCount();

    List<TestJson> selectByPage(@Param("page") int page, @Param("limit") int limit);

    List<TestJson> selectByLastId(@Param("lastId") int lastId, @Param("limit") int limit);
}