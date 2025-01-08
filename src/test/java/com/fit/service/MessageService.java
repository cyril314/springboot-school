package com.fit.service;

import com.fit.base.BaseService;
import com.fit.dao.MessageDao;
import com.fit.entity.Message;
import org.springframework.stereotype.Service;

/**
 * @AUTO 服务实现类
 * @Author AIM
 * @DATE 2025-01-08 12:30:24
 */
@Service
public class MessageService extends BaseService<MessageDao, Message> {
}