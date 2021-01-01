package com.course.server.domain;

public class Test {
    private String linksid;

    public String getLinksid() {
        return linksid;
    }

    public void setLinksid(String linksid) {
        this.linksid = linksid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", linksid=").append(linksid);
        sb.append("]");
        return sb.toString();
    }
}