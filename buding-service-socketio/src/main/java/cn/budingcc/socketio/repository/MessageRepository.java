package cn.budingcc.socketio.repository;

import cn.budingcc.socketio.domain.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/6/12 12:00
 */
public interface MessageRepository extends JpaRepository<MessageEntity, String> {
}
