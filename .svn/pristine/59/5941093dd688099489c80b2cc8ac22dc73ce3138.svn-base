package com.jsdc.common.minio.vo;

import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.acl.Owner;
import java.util.Date;

/**
 * minio 桶中的对象信息
 * 
 * @author zdq
 */
@Data
@AllArgsConstructor
public class MinioItem {

    private String objectName;
    private Date lastModified;
    private String etag;
    private Long size;
    private String storageClass;
    private Owner owner;
    private String type;

    public MinioItem(Item item) {
        this.objectName = item.objectName();
        this.lastModified = item.lastModified();
        this.etag = item.etag();
        this.size = (long) item.size();
        this.storageClass = item.storageClass();
        this.owner = (Owner) item.owner();
        this.type = item.isDir() ? "directory" : "file";
    }
}
