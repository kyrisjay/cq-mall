package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.OmsOrderReturnApplyDao;
import club.banyuan.mgt.dao.entity.OmsOrderReturnApply;
import club.banyuan.mgt.dao.entity.OmsOrderReturnApplyExample;
import club.banyuan.mgt.dto.OmsOrderReturnApplyResp;
import club.banyuan.mgt.dto.OmsUpdateApplyResp;
import club.banyuan.mgt.service.OmsOrderReturnApplyService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.OMS_ORDER_RETURN_APPLY_NOT_EXIST;

@Service
public class OmsOrderReturnApplyServiceImpl implements OmsOrderReturnApplyService {

    @Autowired
    private OmsOrderReturnApplyDao omsOrderReturnApplyDao;


    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, Long id,
                              Integer status, Date createTime, String handleMan, Date handleTime) {
        PageHelper.startPage(pageNum, pageSize);
        OmsOrderReturnApplyExample omsOrderReturnApplyExample = new OmsOrderReturnApplyExample();
        OmsOrderReturnApplyExample.Criteria criteria = omsOrderReturnApplyExample.createCriteria();
        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (createTime != null) {
            criteria.andCreateTimeEqualTo(createTime);
        }
        if (StrUtil.isNotBlank(handleMan)) {
            criteria.andHandleManLike(StrUtil.concat(true, "%", handleMan, "%"));
        }
        if (handleTime != null) {
            criteria.andHandleTimeEqualTo(handleTime);
        }
        List<OmsOrderReturnApply> omsOrderReturnApplies = omsOrderReturnApplyDao.selectByExample(omsOrderReturnApplyExample);
        PageInfo<OmsOrderReturnApply> pageInfo = new PageInfo<>(omsOrderReturnApplies);
        List<OmsOrderReturnApplyResp> collect = omsOrderReturnApplies.stream().map(t -> {
            OmsOrderReturnApplyResp omsOrderReturnApplyResp = new OmsOrderReturnApplyResp();
            BeanUtil.copyProperties(t, omsOrderReturnApplyResp);
            return omsOrderReturnApplyResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, collect);
    }

    @Override
    public OmsOrderReturnApplyResp getInfo(Long id) {
        OmsOrderReturnApply omsOrderReturnApply = omsOrderReturnApplyDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(omsOrderReturnApply)) {
            throw new RequestFailException(OMS_ORDER_RETURN_APPLY_NOT_EXIST);
        }
        OmsOrderReturnApplyResp omsOrderReturnApplyResp = new OmsOrderReturnApplyResp();
        BeanUtil.copyProperties(omsOrderReturnApply, omsOrderReturnApplyResp);
        return omsOrderReturnApplyResp;
    }

    @Override
    public Integer delete(List<Long> ids) {
        OmsOrderReturnApplyExample omsOrderReturnApplyExample = new OmsOrderReturnApplyExample();
        OmsOrderReturnApplyExample.Criteria criteria = omsOrderReturnApplyExample.createCriteria();
        criteria.andIdIn(ids);
        List<OmsOrderReturnApply> omsOrderReturnApplies = omsOrderReturnApplyDao.selectByExample(omsOrderReturnApplyExample);
        omsOrderReturnApplies.forEach(t -> {
            if (BeanUtil.isEmpty(t)) {
                throw new RequestFailException(OMS_ORDER_RETURN_APPLY_NOT_EXIST);
            }
        });
        return omsOrderReturnApplyDao.deleteByExample(omsOrderReturnApplyExample);
    }

    @Override
    public Integer updateStatus(Long id, OmsUpdateApplyResp omsUpdateApplyResp) {
        Long companyAddressId = omsUpdateApplyResp.getCompanyAddressId();
        String handleMan = omsUpdateApplyResp.getHandleMan();
        String handleNote = omsUpdateApplyResp.getHandleNote();
        String receiveMan = omsUpdateApplyResp.getReceiveMan();
        String receiveNote = omsUpdateApplyResp.getReceiveNote();
        BigDecimal returnAmount = omsUpdateApplyResp.getReturnAmount();
        int status = omsUpdateApplyResp.getStatus();
        return omsOrderReturnApplyDao.updateStatusById(id, companyAddressId, handleMan, handleNote, receiveMan, receiveNote, returnAmount, status);
    }
}
