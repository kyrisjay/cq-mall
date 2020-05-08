package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.dao.entity.OmsCompanyAddress;
import club.banyuan.mgt.dao.entity.OmsCompanyAddressDao;
import club.banyuan.mgt.dto.OmsCompanyAddressResp;
import club.banyuan.mgt.service.OmsCompanyAddressService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsCompanyAddressServiceImpl implements OmsCompanyAddressService {

    @Autowired
    private OmsCompanyAddressDao omsCompanyAddressDao;

    @Override
    public List<OmsCompanyAddressResp> list() {
        List<OmsCompanyAddress> omsCompanyAddresses = omsCompanyAddressDao.selectAll();
        return omsCompanyAddresses.stream().map(t -> {
            OmsCompanyAddressResp omsCompanyAddressResp = new OmsCompanyAddressResp();
            BeanUtil.copyProperties(t, omsCompanyAddressResp);
            return omsCompanyAddressResp;
        }).collect(Collectors.toList());
    }
}
