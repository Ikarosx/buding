package cn.budingcc.cms.mq;

import cn.budingcc.cms.config.RabbitConfig;
import cn.budingcc.cms.dao.CmsPageRepository;
import cn.budingcc.cms.service.CmsSiteService;
import cn.budingcc.framework.domain.cms.CmsPage;
import cn.budingcc.framework.domain.cms.CmsSite;
import cn.budingcc.framework.domain.shop.Good;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/2 20:36
 */
@Component
@Slf4j
public class ThymeleafReceiver {
    private final String GOOD_DETAIL_SITE_ID;
    private final String GOOD_DATA_URL_PREFIX;
    private final String PHYSICAL_PATH = "E:\\wamp\\nginx\\www\\buding\\good\\detail";
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteService cmsSiteService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    
    ThymeleafReceiver(@Value("${buding.rabbitmq.goodDetailSiteId}") final String goodDetailSiteId, @Value("${buding.rabbitmq.goodDataUrlPrefix}") final String goodDataUrlPrefix) {
        this.GOOD_DETAIL_SITE_ID = goodDetailSiteId;
        this.GOOD_DATA_URL_PREFIX = goodDataUrlPrefix;
    }
    
    @RabbitListener(queues = "#{bdThymeleafSaveHtmlToNginxQueue.name}")
    public void saveHtmlToNginxReceive(byte[] bytes) {
        CmsPage cmsPage = SerializationUtils.deserialize(bytes);
        log.debug("保存{}到nginx目录", cmsPage.getPageName());
        String htmlFileId = cmsPage.getHtmlFileId();
        GridFsResource gridFsResource = getGridFsResourceByGridFsId(htmlFileId);
        File file = new File(cmsPage.getPagePhysicalPath());
        try {
            IOUtils.copy(gridFsResource.getInputStream(), new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @RabbitListener(queues = "#{bdThymeleafGoodInsertQueue.name}")
    public void goodInsertReceive(byte[] bytes) {
        Good good = SerializationUtils.deserialize(bytes);
        // 处理cmsPage
        CmsPage cmsPage = new CmsPage();
        cmsPage.setId(good.getId());
        cmsPage.setPageName(good.getId() + ".html");
        cmsPage.setPageAlias(good.getGoodName());
        cmsPage.setCreateTime(new Date());
        cmsPage.setUpdateTime(cmsPage.getCreateTime());
        cmsPage.setSiteId(GOOD_DETAIL_SITE_ID);
        cmsPage.setHtmlFileId(generateGoodDetailHtmlToGridFS(good));
        String dataUrl = GOOD_DATA_URL_PREFIX + "/" + good.getId();
        cmsPage.setDataUrl(dataUrl);
        cmsPage.setPageWebPath("/" + cmsPage.getPageName());
        cmsPage.setPagePhysicalPath(PHYSICAL_PATH + cmsPage.getPageWebPath());
        CmsPage cmsPageSaved = cmsPageRepository.save(cmsPage);
        // 通知
        rabbitTemplate.convertAndSend(RabbitConfig.BD_THYMELEAF_EXCHANGE, RabbitConfig.BD_THYMELEAF_SAVE_HTML_TO_NGINX_ROUTING_KEY, SerializationUtils.serialize(cmsPageSaved));
    }
    
    private String generateGoodDetailHtmlToGridFS(Good good) {
        CmsSite cmsSite = cmsSiteService.getCmsSiteById(GOOD_DETAIL_SITE_ID);
        if (cmsSite == null) {
            // TODO
            ExceptionCast.cast(CommonCodeEnum.FAIL);
        }
        String templateFileId = cmsSite.getTemplateFileId();
        String template = getTemplate(templateFileId);
        String thymeleafFile = thymeleafHtml(template, good);
        String htmlFileId = saveHtmlFile(thymeleafFile, good.getId());
        return htmlFileId;
    }
    
    private String saveHtmlFile(String thymeleafFile, String goodId) {
        InputStream inputStream = new ByteArrayInputStream(thymeleafFile.getBytes());
        ObjectId objectId = gridFsTemplate.store(inputStream, goodId + ".html");
        return objectId.toString();
    }
    
    private String thymeleafHtml(String templateString, Good good) {
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateString);
        configuration.setTemplateLoader(stringTemplateLoader);
        String result = "";
        try {
            Template template = configuration.getTemplate("template");
            result = FreeMarkerTemplateUtils.processTemplateIntoString(template, good);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    private String getTemplate(String templateFileId) {
        GridFsResource resource = getGridFsResourceByGridFsId(templateFileId);
        String content = "";
        try (InputStream inputStream = resource.getInputStream()) {
            content = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    
    private GridFsResource getGridFsResourceByGridFsId(String templateFileId) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query().addCriteria(GridFsCriteria.where("_id").is(templateFileId)));
        return gridFsTemplate.getResource(gridFSFile);
    }
}
