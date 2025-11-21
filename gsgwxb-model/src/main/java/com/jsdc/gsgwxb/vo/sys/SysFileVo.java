package com.jsdc.gsgwxb.vo.sys;

import com.jsdc.gsgwxb.model.sys.SysFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysFileVo extends SysFile {

    private Integer pageNo = 0;

    private Integer pageSize = 10;
    private String startTime;

    private String endTime;

}