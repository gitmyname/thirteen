package org.thirteen.authorization.model.vo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Aaron.Sun
 * @description 上下级结构的VO的基类
 * @date Created in 17:18 2018/1/11
 * @modified by
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTreeSortVO extends BaseRecordVO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "显示顺序")
    protected Integer sort;
    @ApiModelProperty(notes = "上级编码")
    protected String parentCode;
    @ApiModelProperty(notes = "上级编码", hidden = true)
    protected List<BaseTreeSortVO> children;

}
