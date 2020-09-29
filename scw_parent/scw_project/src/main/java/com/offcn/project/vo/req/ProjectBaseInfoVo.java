package com.offcn.project.vo.req;

import com.offcn.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class ProjectBaseInfoVo extends BaseVo {
    @ApiModelProperty("项目之前的临时token")
    private String projectToken;

    @ApiModelProperty("项目的分类id")
    private List<Integer> typeids;

    @ApiModelProperty("项目的标签id")
    private List<Integer> tagids;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("项目简介")
    private String remark;

    @ApiModelProperty(value = "筹资金额",example = "0")
    private Long money;

    @ApiModelProperty(value = "筹资天数",example = "0")
    private Integer day;

    @ApiModelProperty("项目头部图片")
    private String headerImage;

    @ApiModelProperty("项目详情图片")
    private List<String> detailsImage;
}
