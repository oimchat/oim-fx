package com.only.common.result;

import java.util.ArrayList;
import java.util.List;

public class Info {

    private boolean success = true;
    private List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
    private List<ErrorInfo> warnings = new ArrayList<ErrorInfo>();

    public boolean isSuccess() {
        return success && isEmpty(errors) && isEmpty(warnings);
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ErrorInfo> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorInfo> errors) {
        this.errors = errors;
    }

    public List<ErrorInfo> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<ErrorInfo> warnings) {
        this.warnings = warnings;
    }

    public void addError(String code, String text) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }
        errors.add(new ErrorInfo(code, text));
    }

    public void addWarning(String code, String text) {
        if (warnings == null) {
            warnings = new ArrayList<ErrorInfo>();
        }
        warnings.add(new ErrorInfo(code, text));
    }

    private boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }
}
