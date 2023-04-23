package com.yupi.project.model.dto.Interface;

import lombok.Data;

import java.io.Serializable;

@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;


    /**
     * 用户请求参数
     */
    private String userRequestParams;


}
