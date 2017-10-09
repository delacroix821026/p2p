package com.newland.financial.p2p.service;

public interface ILenderService {
    Object getLender(String jsonStr);
    Object debit(String jsonStr);
    Object repay(String jsonStr);
    Object findAllDebit(String jsonStr);
    Object findAllRepay(String jsonStr);
    Object findTotalMoney(String jsonStr);
    Object getDebitAndRepaySummary(String jsonStr);
}
