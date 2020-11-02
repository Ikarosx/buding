package cn.budingcc.filesystem.controller;

import cn.budingcc.filesystem.dao.FileSystemRepository;
import cn.budingcc.framework.domain.filesystem.FileSystem;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Ikarosx
 * @date 2020/9/15 9:37
 */
@Api(tags = "FastDfs接口")
@RestController
@RequestMapping("/filesystem")
public class FdfsController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FileSystemRepository fileSystemRepository;
    @Value("${fdfsUrl}")
    private String fdfsUrl;
    
    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Map uploadFile(MultipartFile file, String fileName) {
        // 设置请求的格式类型
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", file.getResource());
        params.add("fileSystem.businessKey", "buding-user");
        // TODO
        params.add("fileSystem.userId", 1);
        params.add("fileSystem.fileName", fileName);
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(params, null);
        ResponseEntity<Map> entity = restTemplate.postForEntity(fdfsUrl + "/fileSystem", files, Map.class);
        Map map = entity.getBody();
        if (((map.getOrDefault("success", "false"))).equals("false")) {
            return map;
        }
        // 创建文件信息对象
        FileSystem fileSystem = new FileSystem();
        // 文件id
        Map data = (Map) map.get("data");
        fileSystem.setFileId((String) data.get("id"));
        // 文件在文件系统中的路径
        fileSystem.setFilePath((String) data.get("path"));
        // 业务标识
        fileSystem.setBusinessKey("buding");
        if (StringUtils.isEmpty(fileName)) {
            fileName = file.getOriginalFilename();
        }
        // 名称
        fileSystem.setFileName(fileName);
        // 大小
        fileSystem.setFileSize(file.getSize());
        // 文件类型
        fileSystem.setFileType(file.getContentType());
        fileSystemRepository.save(fileSystem);
        return map;
    }
    
    @PostMapping("/delete")
    @ApiOperation("删除文件")
    public ResponseResult deleteFile(String fileSystemId) {
        
        ResponseEntity<ResponseResult> entity = restTemplate.exchange(fdfsUrl + "/fileSystem/" + fileSystemId, HttpMethod.DELETE, null, ResponseResult.class);
        ResponseResult deleteResult = entity.getBody();
        return deleteResult;
    }
}
