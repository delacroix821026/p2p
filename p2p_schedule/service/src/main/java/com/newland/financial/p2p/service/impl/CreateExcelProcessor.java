package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
import com.newland.financial.p2p.domain.entity.ExcelOrderModel;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.item.ItemProcessor;

@Log4j
public class CreateExcelProcessor implements ItemProcessor<CustomerFlowDebit, ExcelOrderModel> {
    @Override
    public ExcelOrderModel process(CustomerFlowDebit customerFlowDebit) throws Exception {
        ExcelOrderModel excelOrderModel = new ExcelOrderModel();
        excelOrderModel.setDDate(customerFlowDebit.getDDate());
        excelOrderModel.setOddNumbers(customerFlowDebit.getOddNumbers());
        excelOrderModel.setApplyName(customerFlowDebit.getApplyName());
        excelOrderModel.setPhone(customerFlowDebit.getPhone());
        excelOrderModel.setDMoney(customerFlowDebit.getDMoney());
        excelOrderModel.setDetailAdd(customerFlowDebit.getDetailAdd());
        excelOrderModel.setIdentityCard(customerFlowDebit.getIdentityCard());
        excelOrderModel.setIsSend("1");
        log.debug("CreateExcelProcessor:customerFlowDebit=====" +excelOrderModel.toString());
        return excelOrderModel;
    }
}
