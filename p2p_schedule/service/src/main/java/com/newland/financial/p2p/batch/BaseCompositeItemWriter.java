package com.newland.financial.p2p.batch;

import com.newland.financial.p2p.common.util.AopTargetUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * BaseCompositeItemWriter.
 *
 * @param <T>
 */
@Log4j
public class BaseCompositeItemWriter<T> implements ItemStreamWriter<T>, InitializingBean {

    /**
     * ItemWriter.
     */
    private List<ItemWriter<? super T>> delegates;
    /**
     * ignoreItemStream.
     */
    private boolean ignoreItemStream = false;

    /**
     * setIgnoreItemStream.
     *
     * @param ignoItemStream ignoreItemStream
     */
    public void setIgnoreItemStream(boolean ignoItemStream) {
        this.ignoreItemStream = ignoItemStream;
    }

    /**
     * write.
     *
     * @param item item
     * @throws Exception
     */
    @Override
    public void write(List<? extends T> item) throws Exception {
        for (ItemWriter<? super T> writer : delegates) {
            writer.write(item);
        }
    }

    /**
     * afterPropertiesSet.
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(delegates, "The 'delegates' may not be null");
        Assert.notEmpty(delegates, "The 'delegates' may not be empty");
    }

    /**
     * setDelegates.
     *
     * @param delegate delegates
     */
    public void setDelegates(List<ItemWriter<? super T>> delegate) {
        this.delegates = delegate;
    }

    /**
     * close.
     *
     * @throws ItemStreamException
     */
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

    /**
     * open.
     *
     * @param executionContext executionContext
     * @throws ItemStreamException
     */
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

    /**
     * update.
     *
     * @param executionContext executionContext
     * @throws ItemStreamException
     */
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
