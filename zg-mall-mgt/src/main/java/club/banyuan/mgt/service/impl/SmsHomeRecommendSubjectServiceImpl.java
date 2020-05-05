package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.SmsHomeRecommendSubjectDao;
import club.banyuan.mgt.dao.entity.SmsHomeRecommendSubject;
import club.banyuan.mgt.dao.entity.SmsHomeRecommendSubjectExample;
import club.banyuan.mgt.dto.SmsHomeRecommendSubjectResp;
import club.banyuan.mgt.service.SmsHomeRecommendSubjectService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.SMS_HOME_RECOMMEND_SUBJECT_NOT_EXIST;
@Service
public class SmsHomeRecommendSubjectServiceImpl implements SmsHomeRecommendSubjectService {

    @Autowired
    private SmsHomeRecommendSubjectDao smsHomeRecommendSubjectDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String subjectName, Integer recommendStatus) {
        PageHelper.startPage(pageNum, pageSize);
        SmsHomeRecommendSubjectExample smsHomeRecommendSubjectExample = new SmsHomeRecommendSubjectExample();
        SmsHomeRecommendSubjectExample.Criteria criteria = smsHomeRecommendSubjectExample.createCriteria();
        if (StrUtil.isNotBlank(subjectName)) {
            criteria.andSubjectNameLike(StrUtil.concat(true, "%", subjectName, "%"));
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        smsHomeRecommendSubjectExample.setOrderByClause("`sort` DESC,'id' ASC");
        List<SmsHomeRecommendSubject> smsHomeRecommendSubjects = smsHomeRecommendSubjectDao.selectByExample(smsHomeRecommendSubjectExample);
        PageInfo<SmsHomeRecommendSubject> pageInfo = new PageInfo<>(smsHomeRecommendSubjects);
        List<SmsHomeRecommendSubjectResp> collect = smsHomeRecommendSubjects.stream().map(t -> {
            SmsHomeRecommendSubjectResp smsHomeRecommendSubjectResp = new SmsHomeRecommendSubjectResp();
            BeanUtil.copyProperties(t, smsHomeRecommendSubjectResp);
            return smsHomeRecommendSubjectResp;
        }).collect(Collectors.toList());
//        sorted(Comparator.comparingInt(SmsHomeRecommendSubjectResp::getSort).reversed())
        return ResponsePages.setPages(pageInfo, collect);
    }

    @Override
    public Integer updateSort(Integer sort, Long id) {
        SmsHomeRecommendSubject smsHomeRecommendSubject = smsHomeRecommendSubjectDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(smsHomeRecommendSubject)) {
            throw new RequestFailException(SMS_HOME_RECOMMEND_SUBJECT_NOT_EXIST);
        }
        smsHomeRecommendSubject.setSort(sort);
        return smsHomeRecommendSubjectDao.updateByPrimaryKey(smsHomeRecommendSubject);
    }

    @Override
    public Integer updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        ids.forEach(t -> {
            SmsHomeRecommendSubject smsHomeRecommendSubject = smsHomeRecommendSubjectDao.selectByPrimaryKey(t);
            if (BeanUtil.isEmpty(smsHomeRecommendSubject)) {
                throw new RequestFailException(SMS_HOME_RECOMMEND_SUBJECT_NOT_EXIST);
            }
        });
        return smsHomeRecommendSubjectDao.updateRecommendStatusByIds(ids, recommendStatus);
    }

    @Override
    public Integer delete(List<Long> ids) {
        ids.forEach(t -> {
            SmsHomeRecommendSubject smsHomeRecommendSubject = smsHomeRecommendSubjectDao.selectByPrimaryKey(t);
            if (BeanUtil.isEmpty(smsHomeRecommendSubject)) {
                throw new RequestFailException(SMS_HOME_RECOMMEND_SUBJECT_NOT_EXIST);
            }
        });
        return smsHomeRecommendSubjectDao.deleteByids(ids);
    }

    @Override
    public Integer create(List<SmsHomeRecommendSubject> smsHomeRecommendSubjects) {
        smsHomeRecommendSubjects.forEach(t -> {
            t.setSort(0);
            t.setRecommendStatus(1);
            smsHomeRecommendSubjectDao.insert(t);
        });
        return 1;
    }

}
