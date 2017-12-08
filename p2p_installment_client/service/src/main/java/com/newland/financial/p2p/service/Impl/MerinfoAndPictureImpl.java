package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IMerinfoAndPicture;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public abstract class MerinfoAndPictureImpl implements IMerinfoAndPicture {
    public IMerinfoAndPicture create(final Throwable cause) {
        return new IMerinfoAndPicture() {

            public String getMerInfoAndPicture(String jsonStr) {
                log.info("*********getMerInfoAndPicture:被熔断***********");
                log.error(cause);
                return "1026";
            }
        };
    }
}
