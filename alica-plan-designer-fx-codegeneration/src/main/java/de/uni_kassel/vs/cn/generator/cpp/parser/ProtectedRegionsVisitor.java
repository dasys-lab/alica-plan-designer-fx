package de.uni_kassel.vs.cn.generator.cpp.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marci on 31.05.17.
 */
public class ProtectedRegionsVisitor extends CommentsBaseVisitor<Void> {

    private Map<String, String> protectedRegions;

    public ProtectedRegionsVisitor() {
        super();
        protectedRegions = new HashMap<>();
    }

    public Map<String, String> getProtectedRegions() {
        return protectedRegions;
    }

    @Override
    public Void visitProtected_region(CommentsParser.Protected_regionContext ctx) {
        protectedRegions.put(ctx.protected_region_header().id.getText(), ctx.content.getText());
        return super.visitProtected_region(ctx);
    }
}
