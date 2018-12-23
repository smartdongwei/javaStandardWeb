package com.synway.StandardComparision.pojo;

/**
 * 定义数据库连接的实体类
 */
public class DataSource {
    //    数据源id 唯一性标志
    private String dataId;
    //数据库名称　 描述该数据库的信息
    private String dataName;
//    //数据库是否连通
//    private String DataConnection;
    //数据库的username
    private String userName;
    //数据库的密码
    private String passWord;
    //数据库的jdbcUrl
    private String jdbcUrl;

    //dmp文件来源的用户名
    private String fromUser;
    //dmp文件导入时的用户名
    private String toUser;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "dataId='" + dataId + '\'' +
                ", dataName='" + dataName + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                '}';
    }
}
