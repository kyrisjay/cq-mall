package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.SmsHomeRecommendSubject;

import java.util.List;

public interface SmsHomeRecommendSubjectService {
    ResponsePages list(Integer pageNum, Integer pageSize, String subjectName, Integer recommendStatus);

    Integer updateSort(Integer sort, Long id);

    Integer updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    Integer delete(List<Long> ids);

    Integer create(List<SmsHomeRecommendSubject> smsHomeRecommendSubjects);
}
