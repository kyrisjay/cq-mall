package club.banyuan.mgt.service;


import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.UmsMenu;
import club.banyuan.mgt.dto.UmsMenuResp;
import club.banyuan.mgt.dto.UmsMenuTreeNode;

import java.util.List;

public interface UmsMenuService {

    List<UmsMenuTreeNode> treeList();

    ResponsePages list(Integer pageNum, Integer pageSize, Long menuParentId);

    UmsMenuResp getMenu(Long menuId);

    long update(Long menuId, UmsMenu umsMenu);

    long create(UmsMenu umsMenu);

    long  delete(Long menuId);


}