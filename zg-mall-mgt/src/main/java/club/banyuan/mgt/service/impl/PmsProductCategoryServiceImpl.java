package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.dao.PmsProductCategoryDao;
import club.banyuan.mgt.dao.entity.PmsProductCategory;
import club.banyuan.mgt.dto.PmsProductCategoryResp;
import club.banyuan.mgt.service.PmsProductCategoryService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {


    @Autowired
    private PmsProductCategoryDao pmsProductCategoryDao;


    @Override
    public List<PmsProductCategoryResp> listWithChildren() {
        List<PmsProductCategory> pmsProductCategories = pmsProductCategoryDao.selectAll();
        List<PmsProductCategoryResp> list = new ArrayList<>();
        pmsProductCategories.forEach(t -> {
            if (t.getParentId() == 0) {
                PmsProductCategoryResp pmsProductCategoryResp = new PmsProductCategoryResp();
                BeanUtil.copyProperties(t, pmsProductCategoryResp);
                pmsProductCategoryResp.setChildren(new ArrayList<>());
                list.add(pmsProductCategoryResp);
            }
        });

        list.forEach(parent -> {
            pmsProductCategories.forEach(child -> {
                if (parent.getId().equals(child.getParentId())) {
                    PmsProductCategoryResp pmsProductCategoryResp = new PmsProductCategoryResp();
                    BeanUtil.copyProperties(child, pmsProductCategoryResp);
                    parent.getChildren().add(pmsProductCategoryResp);
                }
            });
        });
        return list;
    }


}
