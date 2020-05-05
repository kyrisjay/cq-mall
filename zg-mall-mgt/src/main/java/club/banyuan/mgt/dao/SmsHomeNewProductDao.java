package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.SmsHomeNewProduct;
import club.banyuan.mgt.dao.entity.SmsHomeNewProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsHomeNewProductDao {
    long countByExample(SmsHomeNewProductExample example);

    int deleteByExample(SmsHomeNewProductExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsHomeNewProduct record);

    int insertSelective(SmsHomeNewProduct record);

    List<SmsHomeNewProduct> selectByExample(SmsHomeNewProductExample example);

    SmsHomeNewProduct selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsHomeNewProduct record, @Param("example") SmsHomeNewProductExample example);

    int updateByExample(@Param("record") SmsHomeNewProduct record, @Param("example") SmsHomeNewProductExample example);

    int updateByPrimaryKeySelective(SmsHomeNewProduct record);

    int updateByPrimaryKey(SmsHomeNewProduct record);

    int updateRecommendStatusByIds(List<Long> ids, Integer recommendStatus);

    int deleteByids(List<Long> ids);
}