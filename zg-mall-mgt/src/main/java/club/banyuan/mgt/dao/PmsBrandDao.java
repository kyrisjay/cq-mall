package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.PmsBrand;
import club.banyuan.mgt.dao.entity.PmsBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsBrandDao {
    long countByExample(PmsBrandExample example);

    int deleteByExample(PmsBrandExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsBrand record);

    int insertSelective(PmsBrand record);

    List<PmsBrand> selectByExample(PmsBrandExample example);

    PmsBrand selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsBrand record, @Param("example") PmsBrandExample example);

    int updateByExample(@Param("record") PmsBrand record, @Param("example") PmsBrandExample example);

    int updateByPrimaryKeySelective(PmsBrand record);

    int updateByPrimaryKey(PmsBrand record);

    List<PmsBrand> selectAll();

    Integer updateShowStatusByBrandIds(List<Long> brandIds, Integer showStatus);

    Integer updateFactoryStatusByBrandIds(List<Long> brandIds, Integer factoryStatus);
}