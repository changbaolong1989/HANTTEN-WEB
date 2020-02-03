package com.hd.common.util;


import java.beans.PropertyEditorSupport;

public class StringEditor extends PropertyEditorSupport {

    private final String charsToDelete;
    private final boolean emptyAsNull;

    public StringEditor(boolean emptyAsNull) {
        this.charsToDelete = null;
        this.emptyAsNull = emptyAsNull;
    }

    public StringEditor(String charsToDelete, boolean emptyAsNull) {
        this.charsToDelete = charsToDelete;
        this.emptyAsNull = emptyAsNull;
    }

    @Override
    public void setAsText(String text) {
        if (text == null) {
            this.setValue((Object)null);
        } else {
            String value = text.trim();
            if (this.charsToDelete != null) {
                value = org.springframework.util.StringUtils.deleteAny(value, this.charsToDelete);
            }

            if (!(this.emptyAsNull && "".equals(value))) {
                this.setValue(value);
            }
        }

    }
    @Override
    public String getAsText() {
        Object value = this.getValue();
        return value != null ? value.toString() : "";
    }

}