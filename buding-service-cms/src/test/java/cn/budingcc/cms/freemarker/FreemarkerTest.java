package cn.budingcc.cms.freemarker;

import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ikaros
 * @date 2020/3/1 18:29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {
    @Autowired
    GridFsTemplate gridFsTemplate;
    
    @Test
    public void freemarkerTest() {
        Map<String, String> map = new LinkedHashMap<String, String>() {{
            put("description", "我老婆");
            put("goodName", "伊卡洛斯");
            put("price", "666.0");
            put("username", "Ikaros");
            put("imageUrl", "group1/M00/00/00/dU4Lkl5ZF-OAago3AAQeOvTYbxw364.png,group1/M00/00/00/dU4Lkl5ZF-2AC6xNAASTMaEo31I669.png");
        }};
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query().addCriteria(GridFsCriteria.where("_id").is("5e5bb3387908c365f15fab19")));
        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        String content = "";
        
        try {
            InputStream inputStream = resource.getInputStream();
            content = IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", content);
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            String template = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("template"), map);
            File file = new File("bd_header.html");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(template);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
