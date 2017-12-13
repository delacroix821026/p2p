package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IMerinfoAndPicture;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Log4j
public class MerinfoAndPictureImpl implements FallbackFactory<IMerinfoAndPicture> {
    public IMerinfoAndPicture create(final Throwable cause) {
        return new IMerinfoAndPicture() {
            public String getMerInfoAndPicture(@RequestBody String jsonStr) {
                return "1026";
            }
        };
    }
}
