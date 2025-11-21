package com.jsdc.gsgwxb.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import okhttp3.*;
import okio.Buffer;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * ClassName: DifyConfig
 * Description:
 * date: 2025/5/12 10:14
 *
 * @author bn
 */
public class DifyConfig {

    public static String APIKEY = "dataset-Y1kz3E7LZK7KWCH8iHSUCc9H";
    private static final int TIMEOUT = 30000; // 30秒超时

    // createDatasets
    private static final String API_CREATEDATASETS_URL = "http://172.168.100.6/v1/datasets";

    // delDatasets
    private static final String API_DELDATASETS_URL = "http://172.168.100.6/v1/datasets/%s";
    // updateDatasets
    private static final String API_UPDATE_DATASETS_URL = "http://172.168.100.6/v1/datasets/";


    // 处理HTTP响应
    public static JSONObject handleResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity responseEntity = response.getEntity();
        JSONObject data = JSON.parseObject(EntityUtils.toString(responseEntity));
        System.out.println(data);
        if (data.containsKey("status") && 409 == data.getInteger("status") || 400 == data.getInteger("status")) {
            throw new RuntimeException();
        } else {
            data.put("code", 200);
            data.put("msg", "创建成功！");
            return data;
        }
    }

    // 创建带超时设置的HttpClient
    public static CloseableHttpClient createHttpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT)
                .build();
        return HttpClients.custom().setDefaultRequestConfig(config).build();
    }

    /**
     * 创建知识库
     */
    public static JSONObject createDatasets(String name, String description) {
        CreateDatasetsParams datasetsParams = new CreateDatasetsParams();
        datasetsParams.setName(name);
        datasetsParams.setDescription(description);

        JSONObject result = new JSONObject();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_CREATEDATASETS_URL);
            httpPost.setHeader("Authorization", "Bearer " + APIKEY);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(datasetsParams), "UTF-8"));


            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                JSONObject data = JSON.parseObject(EntityUtils.toString(responseEntity));
                System.out.println(data);
                if (data.containsKey("status") && 409 == data.getInteger("status")) {
                    result.put("code", 500);
                    result.put("msg", data.getString("message"));
                    return result;
                } else {
                    data.put("code", 200);
                    data.put("msg", "创建成功！");
                    return data;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "创建失败！");
            return result;
        }
    }

    /**
     * 删除知识库
     *
     * @param datasetId
     * @return
     */
    public static void delDatasets(String datasetId) {
        // 构建请求URL
        String url = String.format(API_DELDATASETS_URL, datasetId);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setHeader("Authorization", "Bearer " + APIKEY);

            httpClient.execute(httpDelete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查Dataset是否存在
     *
     * @param datasetId 要检查的Dataset ID
     * @return 如果存在返回true，否则返回false
     * @throws IOException 如果请求过程中发生错误
     */
    public static boolean checkDatasetExists(String datasetId) throws IOException {
        Request request = new Request.Builder()
                .url("http://172.168.100.6/v1/datasets/" + datasetId)
                .get()
                .addHeader("Authorization", "Bearer " + APIKEY)
                .build();

        try (Response response = createDebugClient().newCall(request).execute()) {
            if (response.code() == 404) {
                System.err.println("Dataset不存在: " + datasetId);
                return false;
            }

            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "无错误详情";
                System.err.println("检查Dataset存在性失败: " + response.code() + " " + response.message());
                System.err.println("错误详情: " + errorBody);
                throw new IOException("检查Dataset存在性失败: " + response);
            }

            System.out.println("Dataset存在: " + datasetId);
            return true;
        }
    }

    /**
     * 更新知识库的名称和备注
     *
     * @param datasetId   知识库ID
     * @param name        新的知识库名称
     * @param description 新的知识库备注（可为null）
     * @return 更新结果JSON字符串
     * @throws IOException 如果请求失败
     */
    public static JSONObject updateDatasetNameAndDescription(String datasetId, String name, String description) throws IOException {
        // 验证Dataset是否存在
        if (!checkDatasetExists(datasetId)) {
            throw new IllegalArgumentException("无法更新不存在的Dataset: " + datasetId);
        }
        JSONObject result = new JSONObject();
        // 构建请求数据，只包含name和description字段
        JSONObject data = new JSONObject();
        data.put("name", name);

        // 如果description不为null，则添加到请求中
        if (description != null) {
            data.put("description", description);
        }

        String json = data.toString();
        System.out.println("更新知识库名称和备注的JSON: " + json);

        // 构建请求
        RequestBody body = RequestBody.create(MediaType.get("application/json"), json);
        Request request = new Request.Builder()
                .url("http://172.168.100.6/v1/datasets/" + datasetId)
                .patch(body)
                .addHeader("Authorization", "Bearer " + APIKEY)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = createDebugClient().newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "无错误详情";
                System.err.println("更新知识库失败: " + response.code() + " " + response.message());
                System.err.println("错误详情: " + errorBody);
                throw new IOException("更新知识库失败: " + response);
            }
            result.put("code", 200);
            result.put("msg", "更新成功！");
            return result;
        }
    }

    // 定义允许的文件名模式：只包含字母、数字、中文、点号、短横线和下划线
    private static final Pattern VALID_FILENAME_PATTERN =
            Pattern.compile("[^a-zA-Z0-9\\u4e00-\\u9fa5.-_]");

    // 替换无效字符为指定的替换字符（默认为下划线）
    public static String sanitize(String filename, String replacement) {
        if (filename == null || filename.trim().isEmpty()) {
            return "unnamed_file";
        }

        // 处理文件扩展名
        String namePart = filename;
        String extPart = "";

        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            namePart = filename.substring(0, dotIndex);
            extPart = filename.substring(dotIndex);
        }

        // 替换无效字符
        String sanitizedName = VALID_FILENAME_PATTERN.matcher(namePart)
                .replaceAll(replacement);

        // 防止连续的替换字符
        sanitizedName = sanitizedName.replaceAll(replacement + "+", replacement);

        // 防止文件名以替换字符开头或结尾
        sanitizedName = sanitizedName.replaceAll("^" + replacement + "+", "");
        sanitizedName = sanitizedName.replaceAll(replacement + "+$", "");

        // 如果处理后的文件名是空的，使用默认名称
        if (sanitizedName.isEmpty()) {
            sanitizedName = "unnamed_file";
        }

        return sanitizedName + extPart;
    }

    // 使用默认替换字符（下划线）
    public static String sanitize(String filename) {
        return sanitize(filename, "_");
    }

    //添加文档到知识库
    public static JSONObject createByFile(String datasetId, File file) {
        JSONObject result = new JSONObject();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = "http://172.168.100.6/v1/datasets/" + datasetId + "/document/create-by-file";
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", "Bearer " + APIKEY);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("data", JSON.toJSONString(new CreateFileParams()),
                    ContentType.TEXT_PLAIN.withCharset(StandardCharsets.UTF_8));
            builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, sanitize(file.getName()));

            httpPost.setEntity(builder.build());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    JSONObject data = JSON.parseObject(EntityUtils.toString(responseEntity));
                    System.out.println(data);
                    if (data.containsKey("status") && 409 == data.getInteger("status")) {
                        result.put("code", 500);
                        result.put("msg", data.getString("message"));
                        return result;
                    } else {
                        data.put("code", 200);
                        data.put("msg", "创建成功！");
                        return data;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "创建失败！");
            return result;
        }
        result.put("code", 200);
        result.put("msg", "创建成功！");
        return result;
    }
    public static String deleteDocument(String datasetId, String documentId) throws IOException {
        if (StrUtil.isEmpty(documentId)){
            return null;
        }
        Request request = new Request.Builder()
                .url("http://172.168.100.6/v1/datasets/" + datasetId + "/documents/" + documentId)
                .delete()
                .addHeader("Authorization", "Bearer " + APIKEY)
                .build();

        try (Response response = createDebugClient().newCall(request).execute()) {
            if (response.code() == 404) {
                String errorBody = response.body() != null ? response.body().string() : "无错误详情";

                // 判断是dataset不存在还是document不存在
                if (checkDatasetExists(datasetId)) {
                    throw new IOException("文档不存在: " + documentId);
                } else {
                    throw new IOException("知识库不存在: " + datasetId);
                }
            }

            if (!response.isSuccessful()) {
                throw new IOException("删除文档失败: " + response);
            }

            return "文档删除成功";
        }
    }



    // 删除知识库中的文档
//    public static JSONObject deleteDocument(String datasetId, String documentId) throws IOException {
//        String url = "http://172.168.100.6/v1/datasets/" + datasetId + "/document/" + documentId;
//        try (CloseableHttpClient httpClient = createHttpClient()) {
//            HttpDelete httpDelete = new HttpDelete(url);
//            httpDelete.setHeader("Authorization", "Bearer " + APIKEY);
//            JSONObject result = new JSONObject();
//            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
//                HttpEntity responseEntity = response.getEntity();
//                JSONObject data = JSON.parseObject(EntityUtils.toString(responseEntity));
//                System.out.println(data);
//                if (data.containsKey("status") && 409 == data.getInteger("status")) {
//                    result.put("code", 500);
//                    result.put("msg", data.getString("message"));
//                    return result;
//                } else {
//                    data.put("code", 200);
//                    data.put("msg", "创建成功！");
//                    return data;
//                }
//            }
//        }
//    }

    private static String buildSimplifiedDocumentData() {
        try {
            JSONObject data = new JSONObject();
            data.put("indexing_technique", "economy"); // 使用更基础的索引技术

            JSONObject processRule = new JSONObject();
            JSONObject rules = new JSONObject();

            // 简化预处理规则
            JSONArray preProcessingRules = new JSONArray();
            JSONObject rule1 = new JSONObject();
            rule1.put("id", "remove_extra_spaces");
            rule1.put("enabled", true);
            preProcessingRules.put(rule1);

            // 使用更通用的分块策略
            JSONObject segmentation = new JSONObject();
            segmentation.put("separator", "\n\n"); // 使用空行为分隔符
            segmentation.put("max_tokens", 1000); // 增加分块大小
            segmentation.put("overlap", 100); // 添加块重叠，提高上下文连贯性

            rules.put("pre_processing_rules", preProcessingRules);
            rules.put("segmentation", segmentation);
            processRule.put("rules", rules);
            processRule.put("mode", "custom");

            data.put("process_rule", processRule);
            return data.toString();
        } catch (Exception e) {
            throw new RuntimeException("构建简化JSON数据失败", e);
        }
    }

    // 通过文件创建文档（修复版本）
    public static JSONObject createDocumentByFile(String datasetId, File file) throws IOException {
//        File file = new File(filePath);

        // 验证文件有效性
        if (!file.exists()) {
            throw new IOException("文件不存在: " + file.getAbsolutePath());
        }

        if (file.isDirectory()) {
            throw new IOException("不能上传目录，请提供单个文件路径: " + file.getAbsolutePath());
        }

        if (!isFileSupported(file.getAbsolutePath())) {
            throw new IOException("不支持的文件类型: " + file.getAbsolutePath());
        }

        // 规范化文件路径
        file = file.getCanonicalFile();

        // 检查文件名是否包含可能引起问题的字符
        if (file.getName().contains(";") || file.getName().contains(",")) {
            System.err.println("警告: 文件名包含特殊字符，可能导致解析问题: " + file.getName());
        }

//        String jsonData = buildDefaultDocumentData();
        String jsonData = buildSimplifiedDocumentData();

        // 使用最简构建方式，确保只添加一个文件
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        // 添加 data 参数
        builder.addFormDataPart("data", null,
                RequestBody.create(MediaType.get("application/json"), jsonData));

        // 添加 file 参数
        builder.addFormDataPart("file", file.getName(),
                RequestBody.create(MediaType.get(detectMimeType(file.getName())), file));

        RequestBody requestBody = builder.build();
        JSONObject result = new JSONObject();
        Request request = new Request.Builder()
                .url("http://172.168.100.6/v1/datasets/" + datasetId + "/document/create-by-file")
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + APIKEY)
                .addHeader("Content-Type", "multipart/form-data; charset=utf-8")
                .build();

        try (Response response = createDebugClient().newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "无错误详情";
                System.err.println("创建文档失败: " + response.code() + " " + response.message());
                System.err.println("错误详情: " + errorBody);
                throw new IOException("创建文档失败: " + response);
            }

            String responseBody = response.body().string();
            System.out.println("创建文档成功，响应: " + responseBody);

            // 从响应中解析文档ID
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            String documentId = jsonObject.getJSONObject("document").getString("id");

            result.put("code", 200);
            result.put("documentId", documentId);
            result.put("msg", "文档上传成功！");
            return result;
        }
    }

    // 构建默认文档处理参数
    private static String buildDefaultDocumentData() {
        try {
            JSONObject data = new JSONObject();
            data.put("indexing_technique", "high_quality");

            // 添加可能缺失的必需参数
            data.put("document_type", "text"); // 根据实际需求调整

            JSONObject processRule = new JSONObject();
            JSONObject rules = new JSONObject();

            JSONArray preProcessingRules = new JSONArray();
            JSONObject rule1 = new JSONObject();
            rule1.put("id", "remove_extra_spaces");
            rule1.put("enabled", true);

            JSONObject rule2 = new JSONObject();
            rule2.put("id", "remove_urls_emails");
            rule2.put("enabled", true);

            preProcessingRules.put(rule1);
            preProcessingRules.put(rule2);

            JSONObject segmentation = new JSONObject();
            segmentation.put("separator", "###");
            segmentation.put("max_tokens", 500);

            rules.put("pre_processing_rules", preProcessingRules);
            rules.put("segmentation", segmentation);
            processRule.put("rules", rules);
            processRule.put("mode", "custom");

            data.put("process_rule", processRule);
            return data.toString();
        } catch (Exception e) {
            throw new RuntimeException("构建JSON数据失败", e);
        }
    }

    // 创建带有调试拦截器的客户端
    public static OkHttpClient createDebugClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        // 打印请求基本信息
//                        System.out.println("\n发送请求: " + request.method() + " " + request.url());

                        // 打印请求头
//                        Headers headers = request.headers();
//                        for (int i = 0; i < headers.size(); i++) {
//                            System.out.println(headers.name(i) + ": " + headers.value(i));
//                        }

                        // 打印请求体（以二进制形式）
                        if (request.body() != null) {
                            Buffer buffer = new Buffer();
                            request.body().writeTo(buffer);
                            String body = buffer.readUtf8();
//                            System.out.println("请求体内容:");
//                            System.out.println(body);

                            // 检查是否有多个文件上传的迹象
                            int fileParamCount = 0;
                            String lowerCaseBody = body.toLowerCase();
                            int index = 0;
                            while ((index = lowerCaseBody.indexOf("filename=", index)) != -1) {
                                fileParamCount++;
                                index += 9; // "filename=".length()
                            }

                            if (fileParamCount > 1) {
                                System.err.println("警告: 请求体中检测到 " + fileParamCount + " 个文件参数");
                            }
                        }

                        return chain.proceed(request);
                    }
                })
                .build();
    }

    // 检测文件的MIME类型
    private static String detectMimeType(String fileName) {
        String lowerCaseName = fileName.toLowerCase();
        if (lowerCaseName.endsWith(".txt")) return "text/plain";
        if (lowerCaseName.endsWith(".pdf")) return "application/pdf";
        if (lowerCaseName.endsWith(".docx"))
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (lowerCaseName.endsWith(".doc")) return "application/msword";
        if (lowerCaseName.endsWith(".md")) return "text/markdown";
        return "application/octet-stream"; // 默认二进制流
    }

    // 安全编码文件名
//    private static String encodeFileName(String fileName) {
//        try {
//            return URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
//        } catch (UnsupportedEncodingException e) {
//            // 这种情况不会发生，因为UTF-8是标准编码
//            return fileName;
//        }
//    }

    // 检查文件类型是否支持
    private static boolean isFileSupported(String filePath) {
        String[] supportedExtensions = {".txt", ".pdf", ".docx", ".doc", ".md"};
        String lowerCasePath = filePath.toLowerCase();
        for (String ext : supportedExtensions) {
            if (lowerCasePath.endsWith(ext)) {
                return true;
            }
        }
        System.err.println("不支持的文件类型: " + filePath);
        System.err.println("支持的类型: .txt, .pdf, .docx, .doc, .md");
        return false;
    }

    public static void createByFile() {

//        System.out.println(JSON.toJSONString(new CreateFileParams()));
        String datasetId = "d3c48553-e123-4608-9a6e-cb9bb6500737";

        String filePath1 = "C:\\Users\\Administrator\\AppData\\Local\\Temp\\sds.docx";

        String url = "http://172.168.100.6/v1/datasets/" + datasetId + "/document/create-by-file";

        // 创建HttpClient实例
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建HttpPost请求
            HttpPost httpPost = new HttpPost(url);

            // 设置请求头
            httpPost.setHeader("Authorization", "Bearer " + APIKEY);

            // 构建多部分请求体
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("data", JSON.toJSONString(new CreateFileParams()), ContentType.TEXT_PLAIN.withCharset(StandardCharsets.UTF_8));
            File file = new File(filePath1);
            builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, filePath1.substring(filePath1.lastIndexOf('/') + 1));
            // 设置请求体
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);
            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // 输出响应内容
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String responseString = EntityUtils.toString(responseEntity);
                    System.out.println("Response: " + responseString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        try {
            deleteDocument("658bae15-0afb-4178-98bb-a1b73ba13959","da5d7f60-0f07-45ac-a78c-8526e688882a");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        difyConfig.delDatasets("d37c82b4-17e3-4d3c-9b08-b8246e4f148f");

//        JSONObject jsonObject=(JSONObject) JSON.toJSON(new CreateFileParams());
//        difyConfig.createByFile();
//        System.out.println();
    }


}

@Data
class CreateDatasetsParams {

    // 知识库名称（必填）
    public String name;

    // 知识库描述（选填）
    public String description;

    // 索引模式（选填，建议填写） high_quality高质量 economy经济
    public String indexing_technique = "high_quality";

    // 权限（选填，默认 only_me） only_me 仅自己 all_team_members 所有团队成员 partial_members 部分团队成员
    public String permission = "all_team_members";

    // Provider（选填，默认 vendor）
    public String provider = "vendor";
}

@Data
class CreateFileParams {

    //索引方式
    public String indexing_technique = "high_quality";

    public JSONObject process_rule = JSONObject.parseObject("{\n" +
            "        \"rules\": {\n" +
            "            \"pre_processing_rules\": [\n" +
            "                {\n" +
            "                    \"id\": \"remove_extra_spaces\", \n" +
            "                    \"enabled\": true\n" +
            "                }, \n" +
            "                {\n" +
            "                    \"id\": \"remove_urls_emails\", \n" +
            "                    \"enabled\": true\n" +
            "                }\n" +
            "            ], \n" +
            "            \"segmentation\": {\n" +
            "                \"separator\": \"###\", \n" +
            "                \"max_tokens\": 500\n" +
            "            }\n" +
            "        }, \n" +
            "        \"mode\": \"custom\"\n" +
            "    }");


    public JSONObject retrieval_model = JSONObject.
            parseObject("{\"search_method\":\"hybrid_search\",\"reranking_enable\":true," +
                    "\"reranking_model\":{\"reranking_provider_name\":\"netease-youdao\",\"reranking_model_name\":\"bce-reranker-base_v1\"}," +
                    "\"top_k\":\"2\",\"score_threshold_enabled\":false}");
}


