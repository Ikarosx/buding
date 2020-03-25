package cn.budingcc.filesystem.service;

import cn.budingcc.filesystem.dao.FileSystemRepository;
import cn.budingcc.framework.domain.filesystem.FileSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/2/20 16:33
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FileSystemServiceTest {
    @Autowired
    FileSystemRepository fileSystemRepository;
    
    @Test
    public void findTest(){
        List<FileSystem> all = fileSystemRepository.findAll();
        System.out.println(all);
    }
}
