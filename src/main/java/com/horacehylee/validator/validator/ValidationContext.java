package com.horacehylee.validator.validator;

import java.util.Objects;

class ValidationContext {
    private String sourcePath;

    ValidationContext(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    String getSourcePath() {
        return sourcePath;
    }

    ValidationContext appendToSourcePath(String append) {
        return new ValidationContext(sourcePath + append);
    }

    ValidationContext addTargetProperty(String targetProperty) {
        if(targetProperty == null || targetProperty.isEmpty()) {
            return this;
        }
        return appendToSourcePath("." + targetProperty);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!ValidationContext.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final ValidationContext other = (ValidationContext) obj;
        return Objects.equals(this.sourcePath, other.sourcePath);
    }
}
