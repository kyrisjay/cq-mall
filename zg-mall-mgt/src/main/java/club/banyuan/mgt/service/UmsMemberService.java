package club.banyuan.mgt.service;

import club.banyuan.mgt.dto.UmsMemberLevelResp;

import java.util.List;

public interface UmsMemberService {
    List<UmsMemberLevelResp> list(Integer defaultStatus);
}
