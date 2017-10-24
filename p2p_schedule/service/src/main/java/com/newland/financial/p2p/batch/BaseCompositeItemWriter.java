package com.newland.financial.p2p.batch;

import com.newland.financial.p2p.common.AopTargetUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

@Log4j
public class BaseCompositeItemWriter<T> implements ItemStreamWriter<T>, InitializingBean {


    private List<ItemWriter<? super T>> delegates;

    private boolean ignoreItemStream = false;

    public void setIgnoreItemStream(boolean ignoreItemStream) {
        this.ignoreItemStream = ignoreItemStream;
    }

    @Override
    public void write(List<? extends T> item) throws Exception {
        for (ItemWriter<? super T> writer : delegates) {
            writer.write(item);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(delegates, "The 'delegates' may not be null");
        Assert.notEmpty(delegates, "The 'delegates' may not be empty");
    }

    public void setDelegates(List<ItemWriter<? super T>> delegates) {
        this.delegates = delegates;
    }

    @Override
    public void close() throws ItemStreamException {
        for (ItemWriter<? super T> writer : delegates) {
            Object source = writer;
            try {
                source = AopTargetUtils.getTarget(writer);
            } catch (Exception e) {
                log.error(e);
            }
            if (!ignoreItemStream && (source instanceof ItemStream)) {
                ((ItemStream) source).close();
            }
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        for (ItemWriter<? super T> writer : delegates) {
            Object source = writer;
            try {
                source = AopTargetUtils.getTarget(writer);
            } catch (Exception e) {
                log.error(e);
            }
            if (!ignoreItemStream && (source instanceof ItemStream)) {
                ((ItemStream) source).open(executionContext);
            }
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        for (ItemWriter<? super T> writer : delegates) {
            Object source = writer;
            try {
                source = AopTargetUtils.getTarget(writer);
            } catch (Exception e) {
                log.error(e);
            }
            if (!ignoreItemStream && (source instanceof ItemStream)) {
                ((ItemStream) source).update(executionContext);
            }
        }
    }
}
