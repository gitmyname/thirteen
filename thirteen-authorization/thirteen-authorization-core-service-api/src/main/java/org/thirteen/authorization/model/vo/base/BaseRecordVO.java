package org.thirteen.authorization.model.vo.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author Aaron.Sun
 * @description 所有包含创建，更新，备注，删除标记信息的VO的基类
 * @date Created in 17:17 2018/1/11
 * @modified by
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseRecordVO extends BaseDeleteVO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "编码")
    protected String code;
    @ApiModelProperty(notes = "名称")
    protected String name;
    @ApiModelProperty(value = "启用标记 0：禁用；1启用", example = "1")
    protected String active;
    @ApiModelProperty(notes = "创建者", hidden = true)
    protected String createBy;
    /**
     * 创建时间
     * <p>
     * Jackson包使用注解 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
     * 格式化前台日期参数注解 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     * <p>
     */
    @ApiModelProperty(notes = "创建时间", hidden = true, example = "1970-01-01 08:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;
    @ApiModelProperty(notes = "更新者", hidden = true)
    protected String updateBy;
    /**
     * 更新时间
     * <p>
     * Jackson包使用注解 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
     * 格式化前台日期参数注解 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     * <p>
     */
    @ApiModelProperty(notes = "更新时间", hidden = true, example = "1970-01-01 08:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateTime;
    @ApiModelProperty(notes = "备注")
    protected String remark;
    @ApiModelProperty(value = "版本号")
    protected Integer version;

}
