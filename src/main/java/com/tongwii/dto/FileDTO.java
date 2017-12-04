package com.tongwii.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the File entity.
 */
@Data
public class FileDTO implements Serializable {

    private String id;

    private String fileName;

    private String filePath;

    private String fileType;

    private String des;

    private int state;

    private String uploadUserId;
}
