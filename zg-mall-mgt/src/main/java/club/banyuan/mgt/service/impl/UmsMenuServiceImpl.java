package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.dao.UmsMenuDao;
import club.banyuan.mgt.dao.entity.UmsMenu;
import club.banyuan.mgt.dto.UmsMenuTreeNode;
import club.banyuan.mgt.service.UmsMenuService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UmsMenuServiceImpl implements UmsMenuService {

    @Autowired
    private UmsMenuDao umsMenuDao;


    @Override
    public List<UmsMenuTreeNode> treeList() {
        List<UmsMenu> umsMenus = umsMenuDao.selectAll();
        List<UmsMenuTreeNode> rlt = new ArrayList<>();

        umsMenus.forEach(t -> {
            if (t.getParentId() == 0) {
                UmsMenuTreeNode umsMenuTreeNode = new UmsMenuTreeNode();
                BeanUtil.copyProperties(t, umsMenuTreeNode);
                umsMenuTreeNode.setChildren(new ArrayList<>());
                rlt.add((umsMenuTreeNode));
            }
        });
        rlt.forEach(parent -> {
            umsMenus.forEach(subNode -> {
                if (parent.getId().longValue() == subNode.getParentId()) {
                    UmsMenuTreeNode umsMenuTreeNode = new UmsMenuTreeNode();
                    BeanUtil.copyProperties(subNode, umsMenuTreeNode);
                    parent.getChildren().add(umsMenuTreeNode);
                }
            });
        });

        return rlt;
    }
}
