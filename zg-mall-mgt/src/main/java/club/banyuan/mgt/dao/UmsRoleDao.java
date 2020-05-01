package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.UmsRole;
import club.banyuan.mgt.dao.entity.UmsRoleExample;
import club.banyuan.mgt.dao.entity.UmsRoleMenuRelation;
import club.banyuan.mgt.dto.UmsRoleResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsRoleDao {
    long countByExample(UmsRoleExample example);

    int deleteByExample(UmsRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsRole record);

    int insertSelective(UmsRole record);

    List<UmsRole> selectByExample(UmsRoleExample example);

    UmsRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsRole record, @Param("example") UmsRoleExample example);

    int updateByExample(@Param("record") UmsRole record, @Param("example") UmsRoleExample example);

    int updateByPrimaryKeySelective(UmsRole record);

    int updateByPrimaryKey(UmsRole record);

    List<UmsRole> selectByAdminId(Long adminId);

    int deleteRoleMenuRelationByRoleId(Long roleId);

    int insertRoleMenuRelation(UmsRoleMenuRelation relation);

    List<UmsRole> listAll();

    void insertAdminRoleRelationByAdminId(Long adminId, List<Long> roleIds);

    void deleteAdminROleRelationByAdminId(Long adminId);
}