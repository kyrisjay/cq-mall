package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.SmsHomeRecommendProduct;
import club.banyuan.mgt.dao.entity.SmsHomeRecommendProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsHomeRecommendProductDao {
    long countByExample(SmsHomeRecommendProductExample example);

    int deleteByExample(SmsHomeRecommendProductExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsHomeRecommendProduct record);

    int insertSelective(SmsHomeRecommendProduct record);

    List<SmsHomeRecommendProduct> selectByExample(SmsHomeRecommendProductExample example);

    SmsHomeRecommendProduct selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsHomeRecommendProduct record, @Param("example") SmsHomeRecommendProductExample example);

    int updateByExample(@Param("record") SmsHomeRecommendProduct record, @Param("example") SmsHomeRecommendProductExample example);

    int updateByPrimaryKeySelective(SmsHomeRecommendProduct record);

    int updateByPrimaryKey(SmsHomeRecommendProduct record);

    int updateRecommendStatusByIds(List<Long> ids, Integer recommendStatus);

    int deleteByids(List<Long> ids);
}