package com.github.ricardobaumann.spring_crm.helpers;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@Component
public class Converter {

    @SneakyThrows
    public <T> T convert(Object src, Class<T> destClass) {
        T dest = destClass.newInstance();
        BeanUtils.copyProperties(src,dest);
        return dest;
    }

}
