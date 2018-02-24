package com.only.json;

import java.lang.reflect.Field;
import java.util.List;

import flexjson.transformer.Transformer;

/**
 * @author guyong
 *
 */
@SuppressWarnings("unchecked")
public class JSONSerializer extends flexjson.JSONSerializer {

    private static Field pathExpressionsGetter = null;

    static {
        try {
            pathExpressionsGetter = flexjson.JSONSerializer.class.getDeclaredField("pathExpressions");
            pathExpressionsGetter.setAccessible(true);
            Class<?> clazz = flexjson.JSONContext.class;
            Field field = clazz.getDeclaredField("context");
            field.setAccessible(true);
            field.set(null, new ThreadLocal<JSONContext>(){
                @Override
                protected JSONContext initialValue() {
                    return new JSONContext();
                }
            });

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private List<PathExpression> pathExpressions = null;
    {
        try {
            pathExpressions = (List<PathExpression>) pathExpressionsGetter.get(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public List<PathExpression> getPathExpressions() {
        return pathExpressions;
    }

    public void addTransformer(String key, Transformer transformer) {
        transform(transformer, key);
    }

    protected void addExclude(String field) {
        /*
        int index = field.lastIndexOf('.');
        if (index > 0) {
            PathExpression expression = new PathExpression(field.substring(0, index), true);
            if (!expression.isWildcard()) {
                pathExpressions.add(expression);
            }
        }*/
        pathExpressions.add(new PathExpression(field, false));
    }

    protected void addInclude(String field) {
        pathExpressions.add(new PathExpression(field, true));
    }

    public void setIncludes(List<String> fields) {
        for (String field : fields) {
            addInclude(field);
        }
    }

    public void setExcludes(List<String> fields) {
        for (String field : fields) {
            addExclude(field);
        }
    }
}
