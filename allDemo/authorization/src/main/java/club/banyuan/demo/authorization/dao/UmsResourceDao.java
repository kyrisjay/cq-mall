package club.banyuan.demo.authorization.dao;

import club.banyuan.demo.authorization.dao.entity.UmsResource;
import java.util.List;

public interface UmsResourceDao {
    int deleteByPrimaryKey(Long id);

    int insert(UmsResource record);

    int insertSelective(UmsResource record);

    UmsResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsResource record);

    int updateByPrimaryKey(UmsResource record);

    List<UmsResource> selectAll();
}