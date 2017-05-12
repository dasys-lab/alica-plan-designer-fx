import de.uni_kassel.vs.cn.generator.cpp.CPPGeneratorImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 07.11.16.
 */
public class StandaloneCodegenerator {
    public static void main(String[] args) throws IOException {
        EMFModelUtils.initializeEMF();
        List<Plan> allPlans = AllAlicaFiles
                .getInstance()
                .getPlans()
                .stream()
                .map(e -> e.getKey())
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());
        CPPGeneratorImpl cppGenerator = new CPPGeneratorImpl();
        cppGenerator.createUtilityFunctionCreator(allPlans);
    }
}
