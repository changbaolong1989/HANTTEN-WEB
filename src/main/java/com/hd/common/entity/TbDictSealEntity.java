package com.hd.common.entity;

public class TbDictSealEntity {
    /**
     * 章类型key
     */
    private String sealTypeId;

    /**
     * 章类型名称
     */
    private String sealTypeText;

    /**
     * 章类型key
     * @return seal__type_id 章类型key
     */
    public String getSealTypeId() {
        return sealTypeId;
    }

    /**
     * 章类型key
     * @param sealTypeId 章类型key
     */
    public void setSealTypeId(String sealTypeId) {
        this.sealTypeId = sealTypeId == null ? null : sealTypeId.trim();
    }

    /**
     * 章类型名称
     * @return seal_type_text 章类型名称
     */
    public String getSealTypeText() {
        return sealTypeText;
    }

    /**
     * 章类型名称
     * @param sealTypeText 章类型名称
     */
    public void setSealTypeText(String sealTypeText) {
        this.sealTypeText = sealTypeText == null ? null : sealTypeText.trim();
    }
}