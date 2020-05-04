package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.dao.UmsMemberLevelDao;
import club.banyuan.mgt.dao.entity.UmsMemberLevel;
import club.banyuan.mgt.dto.UmsMemberLevelResp;
import club.banyuan.mgt.service.UmsMemberService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private UmsMemberLevelDao umsMemberLevelDao;

    @Override
    public List<UmsMemberLevelResp> list(Integer defaultStatus) {
        List<UmsMemberLevel> umsMemberLevels = umsMemberLevelDao.selectByDefaultStatus(defaultStatus);
        return umsMemberLevels.stream().map(t -> {
            UmsMemberLevelResp umsMemberLevelResp = new UmsMemberLevelResp();
            BeanUtil.copyProperties(t, umsMemberLevelResp);
            return umsMemberLevelResp;
        }).collect(Collectors.toList());
    }
}
