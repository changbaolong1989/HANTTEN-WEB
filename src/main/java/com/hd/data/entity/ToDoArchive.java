package com.hd.data.entity;

public class ToDoArchive {
    /**
     * 
     */
    private String contractId;

    /**
     * 
     * @return contract_id 
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 
     * @param contractId 
     */
    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }
}