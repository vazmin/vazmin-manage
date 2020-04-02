package com.github.vazmin.manage.support.config;

import com.fasterxml.jackson.databind.JavaType;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Created by wangzm on 2020/3/31.
 */
public class ProtostuffRedisSerializer<T> implements RedisSerializer<T> {

    private boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    private final Schema<ProtoWrapper> schema;

    private final ProtoWrapper wrapper;

    private final LinkedBuffer buffer;

    public ProtostuffRedisSerializer() {
        this.wrapper = new ProtoWrapper();
        this.schema = RuntimeSchema.getSchema(ProtoWrapper.class);
        this.buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        wrapper.data = t;
        try {
            return ProtostuffIOUtil.toByteArray(wrapper, schema, buffer);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (isEmpty(bytes)) {
            return null;
        }
        ProtoWrapper newMessage = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, newMessage, schema);
        return (T) newMessage.data;
    }

    private static class ProtoWrapper {
        private Object data;
    }
}
