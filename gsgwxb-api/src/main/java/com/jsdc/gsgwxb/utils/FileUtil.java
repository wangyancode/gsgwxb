package com.jsdc.gsgwxb.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

/**
 * 文件处理工具类
 *
 * @author ruoyi
 */
public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";
    private static final String DIR = "C:/wordpath/";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 删除文件夹所有内容
     *
     * @param filePath
     * @return
     */
    public static boolean deleteAllFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (filePath.endsWith(File.separator)) {
                temp = new File(filePath + tempList[i]);
            } else {
                temp = new File(filePath + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                if (!temp.delete()) {
                    System.gc();// 系统进行资源强制回收
                    temp.delete();
                }
            }
            if (temp.isDirectory()) {
                deleteAllFile(filePath + "/" + tempList[i]);// 先删除文件夹里面的文件
                deleteFolder(filePath + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    public static void deleteFolder(String folderPath) {
        try {
            deleteAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public static String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        return base64;
    }

    /**
     * MultipartFile转base64
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static String multipartFileToBase64(MultipartFile multipartFile) throws IOException {
        byte[] imgByteArray = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(imgByteArray);
    }

    //BASE64解码成File文件
    public static void base64ToFile(String destPath, String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath = destPath;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + "/" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param dir void 判断路径是否存在，如果不存在则创建
     */
    public static void mkdirs(String dir) {
        if (StringUtils.isEmpty(dir)) {
            return;
        }
        File file = new File(dir);
        if (!file.isDirectory()) {
            boolean mk = file.mkdirs();
            log.info("创建文件夹：" + dir + "=======" + mk);
        }
    }

    public static boolean checkFileSize(Long len, int size, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    //改变时间的格式
    public static String parseDate(String dateStr) throws java.text.ParseException {
        SimpleDateFormat input_date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        SimpleDateFormat output_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String finalDate = "";
        try {
            Date parse_date = input_date.parse(dateStr);
            finalDate = output_date.format(parse_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalDate;
    }

    /**
     * 获取MultipartFile base64编码值
     */
    public static String multFileToBase64(MultipartFile imgFile) throws Exception {

        byte[] imgByteArray = imgFile.getBytes();
        return Base64.getEncoder().encodeToString(imgByteArray);
    }

    /**
     * <p>通过路径转成base64 字符串</p>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String filePathToBase64(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        try {
            File file = new File(path);
            if (!file.exists()) {
                return null;
            }
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return new BASE64Encoder().encode(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 将本地文件转换了BASE64编码
     *
     * @param imgFile
     * @return
     */
    public static String getImgFileToBase64(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream inputStream = null;
        byte[] buffer = null;
        //读取图片字节数组
        try {
            inputStream = new FileInputStream(imgFile);
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            buffer = new byte[count];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // 关闭inputStream流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        return new BASE64Encoder().encode(buffer);
    }

    //创建本地txt文件
    public static String createTxtFile(String fileNamePrefix) {
        String fileName = fileNamePrefix + ".txt";
        String content = "Hello, this is a sample text file.";
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    //创建本地docx文件
    public static String createDocxFile(String fileNamePrefix) {
        String fileName = fileNamePrefix + ".docx";
        String content = "Hello, this is a sample text file.";
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(content);
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            document.write(out);
            System.out.println("DOCX file created: " + new File(fileName).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    //创建本地png文件
    public static String createPngImageFile(String fileNamePrefix) {
        int width = 100;
        int height = 100;
        String path = fileNamePrefix + ".png";
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        File outputFile = new File(path);

        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            System.out.println("Error creating image file: " + e.getMessage());
        }
        return path;
    }

    //创建本地jpg文件
    public static String createJpgImageFile(String fileNamePrefix) {
        int width = 100;
        int height = 100;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        String path = fileNamePrefix + ".jpg";
        File outputFile = new File(fileNamePrefix + ".jpg");

        try {
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException e) {
            System.out.println("Error creating image file: " + e.getMessage());
        }
        return path;
    }

    //创建本地xlsx文件
    public static String createExcelFile(String fileNamePrefix) {
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();
        String path = fileNamePrefix + ".xlsx";
        // 将工作簿写入文件
        try (FileOutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 确保关闭工作簿以释放资源
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    //创建本地gif文件
    public static String createGifImageFile(String fileNamePrefix) {
        int width = 100;
        int height = 100;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        String path = fileNamePrefix + ".gif";
        File outputFile = new File(path);

        try {
            ImageIO.write(image, "gif", outputFile);
        } catch (IOException e) {
            System.out.println("Error creating image file: " + e.getMessage());
        }
        return path;
    }

    /**
     * 将InputStream转换为文件
     * @param inputStream 输入流
     * @param fileSuffix 文件后缀(如pdf, docx)
     * @param fileName 文件名(不包含后缀)
     * @return 转换后的文件
     * @throws IOException 输入输出异常
     */
    public static File inputStreamConvertFile(InputStream inputStream, String fileSuffix, String fileName) throws IOException {
        // 获取系统临时目录作为输出目录
        String outputDir = System.getProperty("java.io.tmpdir");
        // 校验输入参数
        if (inputStream == null || fileSuffix == null || fileSuffix.trim().isEmpty()) {
            throw new IllegalArgumentException("输入流和文件后缀不能为空");
        }

        // 处理文件后缀(去除可能的点号)
        fileSuffix = fileSuffix.trim();
        if (fileSuffix.startsWith(".")) {
            fileSuffix = fileSuffix.substring(1);
        }

        // 构建完整的文件路径
//        Path outputPath = Paths.get(outputDir, fileName + "." + fileSuffix);
        Path outputPath = Paths.get(outputDir, fileName);

        // 使用try-with-resources确保资源正确关闭
        try (InputStream is = inputStream;
             OutputStream os = Files.newOutputStream(outputPath);
             BufferedInputStream bis = new BufferedInputStream(is);
             BufferedOutputStream bos = new BufferedOutputStream(os)) {

            // 读取输入流并写入到输出流
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush(); // 确保所有数据都被写入

            // 返回创建的文件
            return outputPath.toFile();
        }
    }
}
