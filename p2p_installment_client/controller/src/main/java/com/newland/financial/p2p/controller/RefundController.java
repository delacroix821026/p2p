
package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import com.newland.financial.p2p.domain.entity.Refund;
import com.newland.financial.p2p.domain.entity.RefundMsgReq;
import com.newland.financial.p2p.service.IRefundService;
import com.newland.financial.p2p.service.ISendService;
import com.newland.financial.p2p.service.ISignatureIfqService;
import com.newland.financial.p2p.util.FtpClientEntity;
import com.newland.financial.p2p.util.NewMerInfoUtils;
import lombok.extern.log4j.Log4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 退款Controller
 *
 * @author Mxia
 */

@RestController
@Log4j
@RequestMapping("/refund")
public class RefundController {
    /**
     * 注入service.
     */
    @Autowired
    private IRefundService refundService;
    /**
     * 注入service.
     */
    @Autowired
    private ISendService sendService;
    /*** 外发接口.*/
    @Autowired
    private ISignatureIfqService signatureService;
    /**ftp地址.*/
    @Value("${hostName}")
    private String hostName;
    /**ftp端口.*/
    @Value("${port}")
    private int port;
    /**ftp账户名.*/
    @Value("${userName}")
    private String userName;
    /**ftp密码.*/
    @Value("${passWord}")
    private String passWord;

    /**
     * 生成退款单.
     *
     * @param jsonStr 包含订单号
     * @return
     */
    @RequestMapping(value = "/createRefundOrder", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object createRefundOrder(@RequestBody String jsonStr) {
        log.info(jsonStr);
        RefundMsgReq refundMsgReq = refundService.getRefundMsg(jsonStr);
        if (refundMsgReq == null) {
            throw new BaseRuntimeException("2003");
        }
        log.info("client中拿到的refundMsgReq:" + refundMsgReq.toString());
        Refund refund = sendService.sendRefundMsgReq(refundMsgReq);
        refundService.insertRefund(refund);
        return refund;
    }

    /**
     * 退款推送接口.
     *
     * @param jsonStr 推送内容
     * @return success or failed
     */
    @RequestMapping(value = "/receiveRefund", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String receiveRefund(@RequestBody String jsonStr) {
        log.info("------------------------------receiveRefund----------------------------");
        log.info("jsonStr：" + jsonStr);
        //调用Ifpay-client验签
        String respStus = signatureService.signature(jsonStr);
        //判断验签结果，成功则调用server更新还款表HttpServletRequest request
        if ("true".equals(respStus)) {
            log.info("------------------------验签成功，更新退款信息");
            Boolean repayResp = (Boolean) refundService.insertRefund(NewMerInfoUtils.getNewRefund(jsonStr));
            if (repayResp) {
                //更新完成后应答“success”
                log.info("------------------------更新还款推送信息成功");
                return "success";
            }
        } else {
            log.info("------------------------验签失败！！");
        }
        return "failed";
    }

    /**
     * 上传凭证.
     */

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestBody String jsonStr) throws Exception {
        JSONObject param = JSON.parseObject(jsonStr);
        String orderId = param.getString("orderId");
        if (file == null) {
            log.info("------------上传文件为空-----------");
            throw new BaseRuntimeException("2007");
        }
        //存在ftp图片服务器的路径
        String path = "/home/certificate";
        String filename = orderId + ".jpg"; //获得原始的文件名
        InputStream input = file.getInputStream();
        log.info("------------上传文件名-----------" + filename);
        FtpClientEntity a = new FtpClientEntity();
        FTPClient ftp = a.getConnectionFTP(hostName, port, userName, passWord);
        boolean result = a.uploadFile(ftp, path, filename, input);
        a.closeFTP(ftp);
        Map<String, String> map = new HashMap<String, String>();
        if (result) {
            Refund refund = new Refund();
            refund.setPath(path + "/" + filename);
            refund.setVocher("1");
            refund.setSendTime(new Date());
            refund.setOrderId(orderId);
            Boolean b = refundService.updateRefund(refund);
            if (b) {
                map.put("code", "0000");
                map.put("message", "图片上传成功");
            } else {
                throw new BaseRuntimeException("2007");
            }
        } else {
            throw new BaseRuntimeException("2008");
        }
        return map;
    }
}

