package club.banyuan.mgt.security;



import club.banyuan.mgt.dao.entity.UmsResource;
import club.banyuan.mgt.service.UmsResourceService;
import cn.hutool.core.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DynamicMetadataSource implements SecurityMetadataSource {

    @Autowired
    private UmsResourceService umsResourceService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String url = filterInvocation.getRequestUrl();
        String path = URLUtil.getPath(url);
        List<UmsResource> resources = umsResourceService.getAllResource();
        AntPathMatcher matcher = new AntPathMatcher();
        return resources.stream().filter(t -> matcher.match(t.getUrl(), path))
                .map(ResourceConfigAttribute::new).collect(
                        Collectors.toList());

//        List<ConfigAttribute> list =new AntPathMatcher();
//        for (UmsResource resource : resources) {
//            if (matcher.match(resource.getUrl(),path)){
//                list.add(new ResourceConfigAttribute(resource));
//            }
//        }
//        return list;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
