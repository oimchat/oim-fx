package com.only.json;

import flexjson.Path;

/**
 * @author guyong
 *
 */
public class PathExpression extends flexjson.PathExpression {

    private String[] expression = null;

    public PathExpression(String expr, boolean anInclude) {
        super(expr, anInclude);
        expression = expr.split("\\.");
    }

    public boolean matches(Path path) {
        int exprCurrentIndex = 0;
        int pathCurrentIndex = 0;
        if (isIncluded()) {
            if (path.length() > expression.length) {
                return false;
            }

        } else {
            if (path.length() != expression.length) {
                return false;
            }
        }
        while (pathCurrentIndex < path.length()) {
            if ("*".equals(expression[exprCurrentIndex]) || expression[exprCurrentIndex].equals(path.getPath().get(pathCurrentIndex))) {
                exprCurrentIndex ++;
                pathCurrentIndex ++;
            } else {
                return false;
            }
        }
        return true;
    }
}
