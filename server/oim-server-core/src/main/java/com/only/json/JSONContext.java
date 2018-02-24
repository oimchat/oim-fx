package com.only.json;

import java.util.List;

import flexjson.BeanProperty;
import flexjson.PathExpression;

/**
 * @author guyong
 *
 */
public class JSONContext extends flexjson.JSONContext {

    private List<PathExpression> pathExpressions;

    public void setPathExpressions(List<PathExpression> pathExpressions) {
        super.setPathExpressions(pathExpressions);
        this.pathExpressions = pathExpressions;
    }

    public boolean isIncluded(BeanProperty prop) {
        Class<?> propType = prop.getPropertyType();
        if (propType == Class.class) {
            return false;
        }

        PathExpression expression = matches( pathExpressions );
        if (expression != null) {
            return expression.isIncluded();
        }

        Boolean included = prop.isIncluded();
        if( included != null ) {
            return included;
        }

        return false;
    }

    public boolean isIncluded(String key, Object value) {

        PathExpression expression = matches( pathExpressions );
        if( expression != null ) {
            return expression.isIncluded();
        }

        return false;
    }
}
