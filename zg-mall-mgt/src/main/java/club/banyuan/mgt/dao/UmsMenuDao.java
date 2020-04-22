package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.UmsMenu;

import java.util.List;

public interface UmsMenuDao {
    int deleteByPrimaryKey(Long id);

    int insert(UmsMenu record);

    int insertSelective(UmsMenu record);

    UmsMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsMenu record);

    int updateByPrimaryKey(UmsMenu record);

    List<UmsMenu> selectByRoleIds(List<Long> roleIds);

    List<UmsMenu> selectAll();
}