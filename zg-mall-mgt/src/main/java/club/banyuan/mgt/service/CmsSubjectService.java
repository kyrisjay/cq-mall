package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dto.CmsSubjectResp;

import java.util.List;

public interface CmsSubjectService {
    List<CmsSubjectResp> listAll();

    ResponsePages list(Integer pageNum, Integer pageSize, String keyword);
}
