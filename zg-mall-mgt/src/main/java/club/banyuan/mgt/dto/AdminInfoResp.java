package club.banyuan.mgt.dto;

import java.util.List;

public class AdminInfoResp {
    /**
     * roles : ["TEST"]
     * icon : null
     * menus : [{"id":1,"parentId":0,"createTime":"2020-02-02T06:50:36.000+0000","title":"商品","level":0,"sort":0,"name":"pms","icon":"product","hidden":0}]
     * username : productAdmin
     */

    private String icon;
    private String username;
    private List<String> roles;
    private List<AdminMenusResp> menus;

    public Object getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<AdminMenusResp> getMenus() {
        return menus;
    }

    public void setMenus(List<AdminMenusResp> menus) {
        this.menus = menus;
    }
}
