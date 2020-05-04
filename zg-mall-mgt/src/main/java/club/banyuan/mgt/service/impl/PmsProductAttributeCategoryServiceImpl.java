package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.PmsProductAttributeCategoryDao;
import club.banyuan.mgt.dao.entity.PmsProductAttributeCategory;
import club.banyuan.mgt.dto.PmsProductAttributeCategoryResp;
import club.banyuan.mgt.service.PmsProductAttributeCategoryService;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryDao pmsProductAttributeCategoryDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PmsProductAttributeCategory> pmsProductAttributeCategories = pmsProductAttributeCategoryDao.selectAll();
        PageInfo<PmsProductAttributeCategory> pageInfo = new PageInfo<>(pmsProductAttributeCategories);
        List<PmsProductAttributeCategoryResp> collect = pmsProductAttributeCategories.stream().map(t -> {
            PmsProductAttributeCategoryResp pmsProductAttributeCategoryResp = new PmsProductAttributeCategoryResp();
            BeanUtil.copyProperties(t, pmsProductAttributeCategoryResp);
            return pmsProductAttributeCategoryResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, collect);
    }
}
