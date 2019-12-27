package com.imaec.hiseoul.model;

import org.simpleframework.xml.Element;

public class Item {
    @Element(required = false)
    private String addr1;
    @Element(required = false)
    private String addr2;
    @Element(required = false)
    private int areacode;
    @Element(required = false)
    private String cat1;
    @Element(required = false)
    private String cat2;
    @Element(required = false)
    private String cat3;
    @Element
    private int contentid;
    @Element(required = false)
    private int contenttypeid;
    @Element(required = false)
    private long createdtime;
    @Element(required = false)
    private String firstimage;
    @Element(required = false)
    private String firstimage2;
    @Element(required = false)
    private Double mapx;
    @Element(required = false)
    private Double mapy;
    @Element(required = false)
    private int mlevel;
    @Element(required = false)
    private long modifiedtime;
    @Element(required = false)
    private int readcount;
    @Element(required = false)
    private int sigungucode;
    @Element(required = false)
    private String tel;
    @Element(required = false)
    private String title;
    @Element(required = false)
    private String zipcode;
    @Element(required = false)
    private String imagename;
    @Element(required = false)
    private String originimgurl;
    @Element(required = false)
    private String serialnum;
    @Element(required = false)
    private String smallimageurl;

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public int getAreacode() {
        return areacode;
    }

    public void setAreacode(int areacode) {
        this.areacode = areacode;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public int getContentid() {
        return contentid;
    }

    public void setContentid(int contentid) {
        this.contentid = contentid;
    }

    public int getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(int contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public long getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(long createdtime) {
        this.createdtime = createdtime;
    }

    public String getFirstimage() {
        return firstimage;
    }

    public void setFirstimage(String firstimage) {
        this.firstimage = firstimage;
    }

    public String getFirstimage2() {
        return firstimage2;
    }

    public void setFirstimage2(String firstimage2) {
        this.firstimage2 = firstimage2;
    }

    public Double getMapx() {
        return mapx;
    }

    public void setMapx(Double mapx) {
        this.mapx = mapx;
    }

    public Double getMapy() {
        return mapy;
    }

    public void setMapy(Double mapy) {
        this.mapy = mapy;
    }

    public int getMlevel() {
        return mlevel;
    }

    public void setMlevel(int mlevel) {
        this.mlevel = mlevel;
    }

    public long getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(long modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public int getReadcount() {
        return readcount;
    }

    public void setReadcount(int readcount) {
        this.readcount = readcount;
    }

    public int getSigungucode() {
        return sigungucode;
    }

    public void setSigungucode(int sigungucode) {
        this.sigungucode = sigungucode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getOriginimgurl() {
        return originimgurl;
    }

    public void setOriginimgurl(String originimgurl) {
        this.originimgurl = originimgurl;
    }

    public String getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(String serialnum) {
        this.serialnum = serialnum;
    }

    public String getSmallimageurl() {
        return smallimageurl;
    }

    public void setSmallimageurl(String smallimageurl) {
        this.smallimageurl = smallimageurl;
    }
}
