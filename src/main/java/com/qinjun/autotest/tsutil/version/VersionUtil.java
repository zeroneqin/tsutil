
package com.qinjun.autotest.tsutil.version;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VersionUtil implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1234567890L;


    private String versionNumber = null;
    private String buildNumber = null;
    private String author = null;
    private Date date = null;
    private String hostName = null;


    /**
     * @param versionNumber
     * @param buildNumber
     * @param author
     * @param date
     * @param hostName
     */
    public VersionUtil(String versionNumber, String buildNumber, String author,
                   Date date, String hostName) {
        super();
        this.versionNumber = versionNumber;
        this.buildNumber = buildNumber;
        this.author = author;
        this.date = date;
        this.hostName = hostName;
    }
    /**
     * @return the versionNumber
     */
    public String getVersionNumber() {
        return versionNumber;
    }
    /**
     * @param versionNumber the versionNumber to set
     */
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
    /**
     * @return the buildNumber
     */
    public String getBuildNumber() {
        return buildNumber;
    }
    /**
     * @param buildNumber the buildNumber to set
     */
    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }
    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }
    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }
    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return Returns the String representation of the Version tag
     */
    public String toString(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
        return "VERSION=\"" + versionNumber + "\"BUILD_NUMBER=\"" + buildNumber + "\"AUTHOR=\"" + author + "\"DATE=\"" + simpleDateFormat.format(date) + "\"HOST=\"" + hostName + "\" ";
    }


}
