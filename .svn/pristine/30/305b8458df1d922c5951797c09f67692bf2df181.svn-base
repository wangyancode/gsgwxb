package com.jsdc.gsgwxb.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.jsdc.common.minio.service.MinioTemplate;
import com.jsdc.gsgwxb.enums.G;
import com.jsdc.gsgwxb.mapper.sys.SysFileMapper;
import com.jsdc.gsgwxb.model.sys.SysFile;
import com.jsdc.gsgwxb.utils.ResultInfo;
import lombok.Cleanup;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * minio 文件服务
 *
 * @author zdq
 */
@RestController
@RequestMapping("/minio")
public class MinioFileController {

    @Autowired
    private MinioTemplate minioTemplate;

    private final static String spaceName2 = "videowarn";
    @Autowired
    private SysFileMapper sysFileMapper;

    public static String toLowerCaseExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            // 没有扩展名
            return fileName;
        }
        String nameWithoutExtension = fileName.substring(0, dotIndex);
        String extension = fileName.substring(dotIndex + 1);
        return nameWithoutExtension + "." + extension.toLowerCase();
    }

    /**
     * 上传附件
     * //     * @param spaceName minio的空间名称
     *
     * @param file 文件
     */
    @PostMapping("/{spaceName}/importFile")
    public ResultInfo importBugs(MultipartFile file) {
        try {
            String ymd = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000);
            String suffixName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            String filename = MinioFileController.toLowerCaseExtension(ymd + suffixName);

            minioTemplate.putObject(G.MINIO_BUCKET, filename, file.getInputStream());

            Map<String, String> map = new HashMap<>();
            map.put("previewFile", filename);
            map.put("originalFilename", file.getOriginalFilename());
            return ResultInfo.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("上传文件错误!");
        }
    }

    /**
     * 上传附件
     * //     * @param spaceName minio的空间名称
     *
     * @param file 文件
     */
    @PostMapping("/importFile")
    public ResultInfo importFile(MultipartFile file) {
        try {
            String ymd = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000);

            String suffixName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            String filename = MinioFileController.toLowerCaseExtension(ymd + suffixName);
            minioTemplate.putObject(G.MINIO_BUCKET, filename, file.getInputStream());

            Map<String, String> map = new HashMap<>();
            map.put("filename", filename);
            map.put("originalFilename", file.getOriginalFilename());
            return ResultInfo.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("上传文件错误!");
        }
    }

    /**
     * 上传附件
     * //     * @param spaceName minio的空间名称
     *
     * @param file 文件
     */
    @PostMapping("/importSysFile")
    public ResultInfo importSysFile(MultipartFile file) {
        try {
            String ymd = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000);

            String suffixName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            String filename = MinioFileController.toLowerCaseExtension(ymd + suffixName);
            minioTemplate.putObject(G.MINIO_BUCKET, filename, file.getInputStream());

            Map<String, String> map = new HashMap<>();
            map.put("filename", filename);
            map.put("originalFilename", file.getOriginalFilename());

            SysFile sysFile = new SysFile();
            sysFile.setId(IdUtil.simpleUUID());
            sysFile.setBizType(12);
            sysFile.setFileName(filename);
            sysFile.setOriginalName(file.getOriginalFilename());
            sysFile.setFileSize(file.getSize() + "");
            sysFile.setFileType(file.getContentType());
            sysFile.setIsDel(G.ISDEL_NO);
            sysFile.setCreateTime(new Date());
            sysFileMapper.insert(sysFile);

            return ResultInfo.success(sysFile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("上传文件错误!");
        }
    }

    /**
     * 批量上传附件
     * //     * @param spaceName minio的空间名称
     *
     * @param files 文件
     */
    @PostMapping("/batchImportFile")
    public ResultInfo batchImportFile(@RequestParam("files") MultipartFile[] files) {
        try {
            List<Map<String, String>> resultList = new ArrayList<>();
            for (MultipartFile file : files) {
                String ymd = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000);

                String suffixName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
                String filename = MinioFileController.toLowerCaseExtension(ymd + suffixName);
                minioTemplate.putObject(G.MINIO_BUCKET, filename, file.getInputStream());

                Map<String, String> map = new HashMap<>();
                map.put("filename", filename);
                map.put("originalFilename", file.getOriginalFilename());
                resultList.add(map);
            }
            return ResultInfo.success(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("上传文件错误!");
        }
    }

    /**
     * 异步处理上传附件
     * //     * @param spaceName minio的空间名称
     *
     * @param file 文件
     */
    @PostMapping("/importFileAsync")
    public ResultInfo importFileAsync(MultipartFile file) {
        try {
            String ymd = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000);

            String suffixName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            String filename = MinioFileController.toLowerCaseExtension(ymd + suffixName);
            CompletableFuture.runAsync(() -> {
                try {
                    minioTemplate.putObject(G.MINIO_BUCKET, filename, file.getInputStream());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Map<String, String> map = new HashMap<>();
            map.put("filename", filename);
            map.put("originalFilename", file.getOriginalFilename());
            return ResultInfo.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("上传文件错误!");
        }
    }

    /**
     * 下载附件
     */
    @GetMapping("/downMinio")
    public ResultInfo getMinioObject(@RequestParam String fileName,
                                     final HttpServletResponse response) {
        if (StrUtil.isBlank(fileName)) {
            return ResultInfo.error("文件名不能为空");
        }
        try {
            @Cleanup InputStream inputStream = minioTemplate.getObject(G.MINIO_BUCKET, fileName);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }
            byte[] data = os.toByteArray();
            response.resetBuffer();
            response.resetBuffer();
            response.setHeader("Content-Disposition", "attachment");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    /**
     * 下载附件
     */
    @GetMapping("/downSysFileMinio")
    public ResultInfo downSysFileMinio(@RequestParam String fileId,
                                       final HttpServletResponse response) {
        if (StrUtil.isBlank(fileId)) {
            return ResultInfo.error("文件id不能为空");
        }
        try {
            SysFile sysfile = sysFileMapper.selectById(fileId);
            if (sysfile == null) {
                return ResultInfo.error("文件不存在");
            }
            @Cleanup InputStream inputStream = minioTemplate.getObject(G.MINIO_BUCKET, sysfile.getFileName());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }
            byte[] data = os.toByteArray();
            response.resetBuffer();
            response.resetBuffer();
            response.setHeader("Content-Disposition", "attachment");
            response.setHeader("Content-Disposition", "attachment;filename=" + sysfile.getOriginalName());
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    /**
     * 删除附件
     */
    @GetMapping("/{bucketName}/delete")
    public ResultInfo deleteMinioObject(@PathVariable String bucketName, @RequestParam String fileName) {
        try {
            minioTemplate.removeObject(bucketName, fileName);
            return ResultInfo.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("删除文件错误!");
        }
    }

    /**
     * 预览地址
     */
    @GetMapping("/preview")
    public String preview(@RequestParam(name = "bucketName") String bucketName, @RequestParam(name = "fileName") String fileName) {
        System.out.println("bucketName:" + bucketName);
        try {
            return minioTemplate.getPreviewFileUrl(bucketName, fileName);
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping("/previewCheck")
    public void previewCheck(@RequestParam String fileName, HttpServletResponse response, Integer fileId) throws
            IOException {
        @Cleanup InputStream is = minioTemplate.getObject(G.MINIO_BUCKET, fileName);
        // 清空response
        response.reset();
        //2、设置文件下载方式
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/pdf");
        OutputStream outputStream = response.getOutputStream();
        int count = 0;
        byte[] buffer = new byte[1024 * 1024];
        while ((count = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, count);
        }
        outputStream.flush();

    }

    @RequestMapping("/previewFile")
    public void previewFile(@RequestParam String fileName,
                            @RequestParam(required = false) String spaceName,
                            HttpServletResponse response, Integer fileId) throws
            IOException {
        String space = G.MINIO_BUCKET;
        if (StringUtils.isNotBlank(spaceName)) {
            space = spaceName;
        }
        @Cleanup InputStream is = minioTemplate.getObject(space, fileName);
        // 清空response
        response.reset();
        //2、设置文件下载方式
        response.setCharacterEncoding("utf-8");
        setPreviewContentTypeByFileExtension(response, fileName);
//        response.setContentType("application/pdf");
        OutputStream outputStream = response.getOutputStream();
        int count = 0;
        byte[] buffer = new byte[1024 * 1024];
        while ((count = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, count);
        }
        outputStream.flush();

    }

    @RequestMapping("/previewCheck2")
    public void previewCheck2(@RequestParam String fileName, HttpServletResponse response, Integer fileId) throws
            IOException {
        @Cleanup InputStream is = minioTemplate.getObject(MinioFileController.spaceName2, fileName);
        String fileType = fileName.substring(fileName.indexOf(".") + 1);
        // 清空response
        response.reset();
        //2、设置文件下载方式
        response.setContentType("image/" + fileType);
        OutputStream outputStream = response.getOutputStream();
        int count = 0;
        byte[] buffer = new byte[1024 * 1024];
        while ((count = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, count);
        }
        outputStream.flush();
    }


    /**
     * 下载附件
     */
    @GetMapping("/{space}/downMinio")
    public void getMinioObject(@PathVariable String space, @RequestParam String fileName,
                               final HttpServletResponse response) {
        try {
            @Cleanup InputStream inputStream = minioTemplate.getObject(space, fileName);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }
            byte[] data = os.toByteArray();
            response.resetBuffer();
            response.resetBuffer();
            response.setHeader("Content-Disposition", "attachment");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("image/png");
            IOUtils.write(data, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setPreviewContentTypeByFileExtension(HttpServletResponse response, String fileName) {
        String fileExtension = getFileExtension(fileName);
        String contentType = getContentTypeByFileExtension(fileExtension);

        if (contentType != null) {
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "inline");
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return null;
    }

    private String getContentTypeByFileExtension(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "pdf":
                return "application/pdf";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            // Add more cases for other file extensions as needed
            default:
                return null;
        }
    }

}
