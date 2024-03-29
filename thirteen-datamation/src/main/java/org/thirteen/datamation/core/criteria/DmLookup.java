package org.thirteen.datamation.core.criteria;

import java.io.Serializable;

/**
 * @author Aaron.Sun
 * @description 关联查询入参对象基类（仿mongodb的lookup）
 * @date Created in 13:31 2020/12/30
 * @modified by
 */
public class DmLookup implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 关联表code
     */
    private String from;
    /**
     * 来源表关联字段
     */
    private String localField;
    /**
     * 关联表关联字段
     */
    private String foreignField;
    /**
     * 结果集别名
     */
    private String as;
    /**
     * 是否需要分解，扁平化输出（是否为一对一）
     */
    private Boolean unwind;

    private DmLookup() {
        this.unwind = false;
    }

    public static DmLookup of(String from) {
        return new DmLookup().from(from);
    }

    private DmLookup from(String from) {
        this.from = from;
        return this;
    }

    public DmLookup localField(String localField) {
        this.localField = localField;
        return this;
    }

    public DmLookup foreignField(String foreignField) {
        this.foreignField = foreignField;
        return this;
    }

    public DmLookup as(String as) {
        this.as = as;
        return this;
    }

    public DmLookup unwind(Boolean unwind) {
        this.unwind = unwind;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLocalField() {
        return localField;
    }

    public void setLocalField(String localField) {
        this.localField = localField;
    }

    public String getForeignField() {
        return foreignField;
    }

    public void setForeignField(String foreignField) {
        this.foreignField = foreignField;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public Boolean getUnwind() {
        return unwind;
    }

    public void setUnwind(Boolean unwind) {
        this.unwind = unwind;
    }
}
