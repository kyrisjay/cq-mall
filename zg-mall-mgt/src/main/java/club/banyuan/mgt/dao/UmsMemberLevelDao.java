package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.UmsMemberLevel;

import java.util.List;

public interface UmsMemberLevelDao {
    int deleteByPrimaryKey(Long id);

    int insert(UmsMemberLevel record);

    int insertSelective(UmsMemberLevel record);

    UmsMemberLevel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsMemberLevel record);

    int updateByPrimaryKey(UmsMemberLevel record);

    List<UmsMemberLevel> selectByDefaultStatus(Integer defaultStatus);
}