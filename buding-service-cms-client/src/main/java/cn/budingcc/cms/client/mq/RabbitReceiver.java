package cn.budingcc.cms.client.mq;

import cn.budingcc.cms.client.feign.CmsSiteClient;
import cn.budingcc.framework.domain.cms.CmsPage;
import cn.budingcc.framework.domain.cms.CmsSite;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author Ikaros
 * @date 2020/3/3 12:48
 */
@Component
public class RabbitReceiver {
    @Autowired
    CmsSiteClient cmsSiteClient;
    @Autowired
    GridFsTemplate gridFsTemplate;
    
    @RabbitListener(queues = "#{bdThymeleafSaveHtmlToNginxQueue.name}")
    public void htmlSaveReceive(byte[] bytes) {
        CmsPage cmsPage = SerializationUtils.deserialize(bytes);
        String siteId = cmsPage.getSiteId();
        CmsSite cmsSite = cmsSiteClient.getCmsSiteById(siteId);
        // 获取实际物理路径
        String physicalPath = cmsSite.getServerPhysicalPath() + cmsSite.getSitePhysicalPath() + cmsPage.getPagePhysicalPath();
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query().addCriteria(GridFsCriteria.where("_id").is(cmsPage.getHtmlFileId())));
        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            System.out.println(physicalPath);
            File file = new File(cmsPage.getPageName());
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}
