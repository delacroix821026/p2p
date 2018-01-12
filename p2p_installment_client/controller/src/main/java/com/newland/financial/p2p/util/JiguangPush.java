package com.newland.financial.p2p.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import lombok.extern.log4j.Log4j;

/**
 * java后台极光推送方式二：使用Java SDK
 */
@Log4j
public class JiguangPush {
    private static String masterSecret = "3c306c0fb6bafa5da00c559a";
    private static String appKey = "9cef87951e8415d9a64b24a0";

    /**
     * 极光推送
     */
    public void jiguangPush(String alias, OrderInfo orderInfo) {
        log.info("对别名" + alias + "的用户推送信息");
        String ALERT = orderInfo.getAccName() + "的分期订单结果：" + orderInfo.getRespMsg();
        PushResult result = push(String.valueOf(alias), ALERT, orderInfo);
        if (result != null && result.isResultOK()) {
            log.info("针对别名" + alias + "的信息推送成功！");
            System.out.print(result);
        } else {
            log.info("针对别名" + alias + "的信息推送失败！");
        }
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     *
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_alias_alert(String alias, String alert, OrderInfo orderInfo) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("resCode", orderInfo.getRespCode())
                                .addExtra("orderId", orderInfo.getOrderId())
                                .addExtra("txnTime", orderInfo.getTxnTime().getTime())
                                .addExtra("accName", orderInfo.getAccName())
                                .addExtra("respMsg", orderInfo.getRespMsg())
                                .addExtra("txnAmt", orderInfo.getTxnAmt())
                                .addExtra("txnTerms", orderInfo.getTxnterms())
                                .addExtra("merName", orderInfo.getMerName())
                                .addExtra("amount", orderInfo.getAmount())
                                .addExtra("poundage", orderInfo.getPoundage())
                                .addExtra("firstNeedPay", orderInfo.getPoundage() + orderInfo.getAmount())
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("resCode", orderInfo.getRespCode())
                                .addExtra("orderId", orderInfo.getOrderId())
                                .addExtra("txnTime", orderInfo.getTxnTime().getTime())
                                .addExtra("accName", orderInfo.getAccName())
                                .addExtra("respMsg", orderInfo.getRespMsg())
                                .addExtra("txnAmt", orderInfo.getTxnAmt())
                                .addExtra("txnTerms", orderInfo.getTxnterms())
                                .addExtra("merName", orderInfo.getMerName())
                                .addExtra("amount", orderInfo.getAmount())
                                .addExtra("poundage", orderInfo.getPoundage())
                                .addExtra("firstNeedPay", orderInfo.getPoundage() + orderInfo.getAmount())
                                .setAlert(alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 极光推送方法(采用java SDK)
     *
     * @param alias
     * @param alert
     * @return PushResult
     */
    public static PushResult push(String alias, String alert, OrderInfo orderInfo) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        PushPayload payload = buildPushObject_android_ios_alias_alert(alias, alert, orderInfo);
        try {
            return jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }
}