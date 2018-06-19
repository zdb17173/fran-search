package org.fran.microservice.search.es.index.db.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class PublishNews implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="instance_id")
    private Long instanceId;
    @Column(name="parent_id")
    private Long parentId;
    @Column
    private Long editor1;

    private String editor1Name;
    @Column
    private Long editor2;
    @Column
    private Long editor3;
    @Column
    private Long author1;
    @Column
    private Long author2;
    @Column
    private Long author3;
    @Column
    private Long repoter1;
    @Column
    private Long repoter2;
    @Column
    private Long repoter3;
    @Column
    private Integer category;
    @Column(name="news_type")
    private Integer newsType;
    @Column
    private String headline;
    @Column(name="like_num")
    private Integer likeNum;
    @Column(name="publish_date")
    private Date publishDate;
    @Column(name="news_detail")
    private String newsDetail;
    @Column(name="news_preview")
    private String newsPreview;
    @Column
    private String city;
    @Column
    private String longitude;
    @Column
    private String latitude;
    @Column
    private String distance;
    @Column(name="share_url")
    private String shareUrl;
    @Column
    private String source;
    @Column(name="source_url")
    private String sourceUrl;
    @Column
    private Integer status;

    private String content;

    @Column(name="headline_frontpage")
    private String headlineFrontpage;

    @Column(name="news_signed")
    private String newsSigned;

    @Column(name="category_name")
    private String categoryName;

    @Column(name="mini_cover_image")
    private String miniCoverImage;

    @Column(name="cover_type")
    private Integer coverType;

    @Column(name="cover_json")
    private String coverJson;

    @Transient
    private List<NewsSection> sections;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEditor1Name() {
        return editor1Name;
    }

    public void setEditor1Name(String editor1Name) {
        this.editor1Name = editor1Name;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getEditor1() {
        return editor1;
    }

    public void setEditor1(Long editor1) {
        this.editor1 = editor1;
    }

    public Long getEditor2() {
        return editor2;
    }

    public void setEditor2(Long editor2) {
        this.editor2 = editor2;
    }

    public Long getEditor3() {
        return editor3;
    }

    public void setEditor3(Long editor3) {
        this.editor3 = editor3;
    }

    public Long getAuthor1() {
        return author1;
    }

    public void setAuthor1(Long author1) {
        this.author1 = author1;
    }

    public Long getAuthor2() {
        return author2;
    }

    public void setAuthor2(Long author2) {
        this.author2 = author2;
    }

    public Long getAuthor3() {
        return author3;
    }

    public void setAuthor3(Long author3) {
        this.author3 = author3;
    }

    public Long getRepoter1() {
        return repoter1;
    }

    public void setRepoter1(Long repoter1) {
        this.repoter1 = repoter1;
    }

    public Long getRepoter2() {
        return repoter2;
    }

    public void setRepoter2(Long repoter2) {
        this.repoter2 = repoter2;
    }

    public Long getRepoter3() {
        return repoter3;
    }

    public void setRepoter3(Long repoter3) {
        this.repoter3 = repoter3;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getNewsDetail() {
        return newsDetail;
    }

    public void setNewsDetail(String newsDetail) {
        this.newsDetail = newsDetail;
    }

    public String getNewsPreview() {
        return newsPreview;
    }

    public void setNewsPreview(String newsPreview) {
        this.newsPreview = newsPreview;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Integer getStatus() {
        return status;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<NewsSection> getSections() {
        return sections;
    }

    public void setSections(List<NewsSection> sections) {
        this.sections = sections;
    }

    public String getHeadlineFrontpage() {
        return headlineFrontpage;
    }

    public void setHeadlineFrontpage(String headlineFrontpage) {
        this.headlineFrontpage = headlineFrontpage;
    }

    public String getNewsSigned() {
        return newsSigned;
    }

    public void setNewsSigned(String newsSigned) {
        this.newsSigned = newsSigned;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMiniCoverImage() {
        return miniCoverImage;
    }

    public void setMiniCoverImage(String miniCoverImage) {
        this.miniCoverImage = miniCoverImage;
    }

    public Integer getCoverType() {
        return coverType;
    }

    public void setCoverType(Integer coverType) {
        this.coverType = coverType;
    }

    public String getCoverJson() {
        return coverJson;
    }

    public void setCoverJson(String coverJson) {
        this.coverJson = coverJson;
    }
}