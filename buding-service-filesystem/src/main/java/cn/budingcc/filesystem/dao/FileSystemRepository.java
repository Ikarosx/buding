package cn.budingcc.filesystem.dao;

import cn.budingcc.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Ikaros
 * @date 2020/2/20 14:27
 */
public interface FileSystemRepository extends MongoRepository<FileSystem, String> {
}
