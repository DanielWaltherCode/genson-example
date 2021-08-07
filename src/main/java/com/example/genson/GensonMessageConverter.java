package com.example.genson;

import com.owlike.genson.Genson;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GensonMessageConverter<T> extends AbstractHttpMessageConverter<T> {

    private static final Genson GENSON = new Genson();

    private final String name;
    private final Class<T> supportedClass;

    protected GensonMessageConverter(String name, Class<T> supportedClass) {
        super(MediaType.APPLICATION_JSON);
        this.name = name;
        this.supportedClass = supportedClass;
    }

    @Override
    protected boolean supports(Class clazz) {
        return supportedClass.isAssignableFrom(clazz);
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return true;
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage)
            throws HttpMessageNotReadableException {
        try {
            // Deserialize incoming message to desired class
            return GENSON.deserialize(inputMessage.getBody(), clazz);
        } catch (IOException e) {
            throw new HttpMessageNotReadableException("Couldn't deserialize message", inputMessage);
        }
    }

    @Override
    protected void writeInternal(T o, HttpOutputMessage outputMessage)
            throws HttpMessageNotWritableException {
        try {
            // Copy serialized object to outputMessage
            StreamUtils.copy(GENSON.serialize(o), StandardCharsets.UTF_8, outputMessage.getBody());
        } catch (IOException e) {
            throw new HttpMessageNotWritableException("Couldn't serialize message", e.getCause());
        }
    }
}
