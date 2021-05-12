package com.course.server.dto;


import java.util.HashSet;
import java.util.List;

public class LoginCourseuserDto {

    /**
     * 
     */
    private String id;

    /**
     * 用户名
     */
    private String loginname;

    /**
     * 昵称
     */
    private String name;
    /**
     * 登录凭证
     */
    private String token;
    /**
     * 所有资源，用于前端界面控制
     */
    private List<ResourceDto> resources;

    /**
     * 所有资源中的请求，用于后端接口拦截
     */
    private HashSet<String> requests;

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public HashSet<String> getRequests() {
        return requests;
    }

    public void setRequests(HashSet<String> requests) {
        this.requests = requests;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "LoginCourseuserDto{" +
                "id='" + id + '\'' +
                ", loginname='" + loginname + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}