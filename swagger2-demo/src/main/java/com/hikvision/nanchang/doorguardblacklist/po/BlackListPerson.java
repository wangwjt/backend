package com.hikvision.nanchang.doorguardblacklist.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* 海康威视数字技术股份有限公司 南昌分公司
* @Description: 黑名单人员 实体
* @author xiexin6  
* @date 2019年3月11日  
*
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("黑名单人员实体")
public class BlackListPerson {

	/**身份证号码*/
	@ApiModelProperty(value = "身份证号码", required = true)
	private String id;

	/**姓名*/
	@ApiModelProperty(value = "姓名", required = true)
	private String name;

	/**种类（0表示是临时黑名单人员（这也是默认值），1表示是永久黑名单人员）*/
	@ApiModelProperty(value = "黑名单人员类型", required = true)
	private Integer type;

	/**描述（只对type=1时有效）*/
	@ApiModelProperty(value = "描述")
	private String description;

}
