package de.unikassel.vs.alica.generator.cpp.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an ANTLR generated Visitor. It creates a {@link Map} of protected regions id and the protected code.
 */
public class ProtectedRegionsVisitor extends de.uni_kassel.vs.generator.cpp.parser.CommentsBaseVisitor<Void> {

    private Map<String, String> protectedRegions;

    public ProtectedRegionsVisitor() {
        super();
        protectedRegions = new HashMap<>();
    }

    /**
     * It returns a map of protected region ids and their respective code
     * @return
     */
    public Map<String, String> getProtectedRegions() {
        return protectedRegions;
    }

    @Override
    public Void visitProtected_region(de.uni_kassel.vs.generator.cpp.parser.CommentsParser.Protected_regionContext ctx) {
        protectedRegions.put(ctx.protected_region_header().id.getText(), ctx.content.getText().substring(1));
        return super.visitProtected_region(ctx);
    }
}
