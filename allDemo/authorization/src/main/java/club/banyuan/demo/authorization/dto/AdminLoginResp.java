package club.banyuan.demo.authorization.dto;
public class AdminLoginResp {

    /**
     * tokenHead : Bearer
     * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE1ODY5NDA2Nzc5NzUsImV4cCI6MTU4NzU0NTQ3N30.d9slPy9j1SwuYwMz4L5EmZ-VwERuWFP7gqHrwD2WnfTfi4R4AogdAssY26P22bddSz7dx7Pm2ZPiNMN-hz_y-A
     */

    private String tokenHead;
    private String token;

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
