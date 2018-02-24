package com.only.json.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.only.json.JSONSerializer;

/**
 * @author guyong
 *
 */
public class JSONView implements View {

    private JSONSerializer serializer = null;

    public JSONView(String[] exp) {
        serializer = new JSONSerializer();
        analyseExpression(exp);
    }

    public String getContentType() {
        return "application/json";
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(getContentType());
        Object result = model.get(JsonModelAndViewResolver.RETURN_VALUE_KEY);
        serializer.serialize(result == null ? "" : result, response.getWriter());
    }

    public String renderToString(Object model) {
        return serializer.serialize(model);
    }

    public JSONSerializer getSerializer() {
        return serializer;
    }

    private void analyseExpression(String[] exp) {
        List<PathInfo> infos = new ArrayList<JSONView.PathInfo>();
        int maxLevel = 0, level;
        for (String e : exp) {
            level = flatPath(e, infos);
            if (level > maxLevel) {
                maxLevel = level;
            }
        }

        for (PathInfo pi : infos) {
            if (pi.isExclude()) {
                for (String item : pi.getResults()) {
                    serializer.exclude(item);
                }

                if (pi.getLevel() > 1) {
                    for (String item : pi.getResults()) {
                        String[] is = item.split("\\.");
                        is[is.length - 1] = "*";
                        serializer.include(join(is, "."));
                    }
                } else {
                    serializer.include("*");
                }
            } else {
                for (String item : pi.getResults()) {
                    serializer.include(item);
                }
            }
        }
    }

    private String join(String[] items, String seprator) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < items.length; i ++) {
            result.append(items[i]).append(".");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private int flatPath(String path, List<PathInfo> pathInfos) {
        String[] paths = path.replaceAll("\\s+", "").split("\\.");
        String last = paths[paths.length - 1];
        boolean exclude = last.charAt(0) == '^';
        PathInfo pi = new PathInfo();
        pi.setExclude(exclude);
        pi.setLevel(paths.length);
        walk(paths, 0, "", pi);
        pathInfos.add(pi);
        return pi.getLevel();
    }

    private void walk(String[] paths, int index, String prefix, PathInfo pi) {
        if (index == paths.length) {
            pi.addResult(prefix);
            return;
        }

        if (!"".equals(prefix)) {
            prefix = prefix + ".";
        }

        String p = paths[index];
        if (p.charAt(0) == '^') {
            p = p.substring(1);
        }
        if (p.charAt(0) == '(') {
            p = p.substring(1, p.length() - 1);
        }

        String[] ps = p.split(",");
        for (String item : ps) {
            walk(paths, index + 1, prefix + item, pi);
        }
    }

    private static class PathInfo {

        private boolean exclude = false;
        private List<String> results = null;
        private int level = 0;

        public PathInfo() {
            results = new ArrayList<String>();
        }

        public boolean isExclude() {
            return exclude;
        }

        public void setExclude(boolean exclude) {
            this.exclude = exclude;
        }

        public List<String> getResults() {
            return results;
        }

        public void addResult(String result) {
            results.add(result);
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

    }
}
