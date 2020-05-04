package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.PmsBrandDao;
import club.banyuan.mgt.dao.entity.PmsBrand;
import club.banyuan.mgt.dao.entity.PmsBrandExample;
import club.banyuan.mgt.dto.PmsBrandResp;
import club.banyuan.mgt.service.PmsBrandService;
import club.banyuan.mgt.service.PmsProductService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    private PmsBrandDao pmsBrandDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        PmsBrandExample.Criteria criteria = pmsBrandExample.createCriteria();
        if (StrUtil.isNotBlank(keyword)) {
            criteria.andNameLike(StrUtil.concat(true, "%", keyword, "%"));
        }
        List<PmsBrand> pmsBrands = pmsBrandDao.selectByExample(pmsBrandExample);
        PageInfo<PmsBrand> pageInfo = new PageInfo<>(pmsBrands);
        List<PmsBrandResp> pmsBrandRespList = pmsBrands.stream().map(t -> {
            PmsBrandResp pmsBrandResp = new PmsBrandResp();
            BeanUtil.copyProperties(t, pmsBrandResp);
            return pmsBrandResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, pmsBrandRespList);
    }
}
