package gr.york.mobiledev2026.data.model;

import java.util.Date;

public class Description {
    private Date published;
    private String summary;
    private String content;

    public Description(Date published, String summary, String content) {
        this.published = published;
        this.summary = summary;
        this.content = content;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
