package de.uni_kassel.vs.cn.generator.cpp;

import com.google.common.base.Objects;
import de.uni_kassel.vs.cn.generator.IConstraintCodeGenerator;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.alica.Variable;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Created by marci on 11.05.17.
 */
@SuppressWarnings("all")
public class XtendTemplates {
  private Map<String, String> protectedRegions;
  
  public void setProtectedRegions(final Map<String, String> regions) {
    this.protectedRegions = regions;
  }
  
  public String behaviourCreatorHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef BEHAVIOURCREATOR_H_");
    _builder.newLine();
    _builder.append("#define BEHAVIOURCREATOR_H_");
    _builder.newLine();
    _builder.append("#include <engine/IBehaviourCreator.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include <memory>");
    _builder.newLine();
    _builder.append("#include <iostream>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class BasicBehaviour;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class BehaviourCreator : public IBehaviourCreator");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("public:");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("BehaviourCreator();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("virtual ~BehaviourCreator();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("virtual shared_ptr<BasicBehaviour> createBehaviour(long behaviourConfId);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif /* BEHAVIOURCREATOR_H_ */");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String behaviourCreatorSource(final List<Behaviour> behaviours) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("using namespace std;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"BehaviourCreator.h\"");
    _builder.newLine();
    _builder.append("#include \"engine/BasicBehaviour.h\"");
    _builder.newLine();
    {
      for(final Behaviour beh : behaviours) {
        _builder.append("#include  \"");
        String _destinationPath = beh.getDestinationPath();
        _builder.append(_destinationPath, "");
        _builder.append("/");
        String _name = beh.getName();
        _builder.append(_name, "");
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("BehaviourCreator::BehaviourCreator()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("BehaviourCreator::~BehaviourCreator()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("shared_ptr<BasicBehaviour> BehaviourCreator::createBehaviour(long behaviourConfId)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("switch(behaviourConfId)");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    {
      for(final Behaviour beh_1 : behaviours) {
        _builder.append("            ");
        _builder.append("case ");
        long _id = beh_1.getId();
        _builder.append(_id, "            ");
        _builder.newLineIfNotEmpty();
        _builder.append("            ");
        _builder.append("// TODO something for behaviour configurations has to be done here");
        _builder.newLine();
        _builder.append("            ");
        _builder.append("return make_shared<");
        String _name_1 = beh_1.getName();
        _builder.append(_name_1, "            ");
        _builder.append(">();");
        _builder.newLineIfNotEmpty();
        _builder.append("            ");
        _builder.append("break;");
        _builder.newLine();
      }
    }
    _builder.append("            ");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("cerr << \"BehaviourCreator: Unknown behaviour requested: \" << behaviourConfId << endl;");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("throw new exception();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("break;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String behaviourHeader(final Behaviour behaviour) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef ");
    String _name = behaviour.getName();
    _builder.append(_name, "");
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.append("#define ");
    String _name_1 = behaviour.getName();
    _builder.append(_name_1, "");
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include \"DomainBehaviour.h\"");
    _builder.newLine();
    _builder.append("/*PROTECTED REGION ID(inc");
    long _id = behaviour.getId();
    _builder.append(_id, "");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_1 = behaviour.getId();
      String _plus = ("inc" + Long.valueOf(_id_1));
      boolean _containsKey = this.protectedRegions.containsKey(_plus);
      if (_containsKey) {
        long _id_2 = behaviour.getId();
        String _plus_1 = ("inc" + Long.valueOf(_id_2));
        String _get = this.protectedRegions.get(_plus_1);
        _builder.append(_get, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("//Add additional includes here");
        _builder.newLine();
      }
    }
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class ");
    String _name_2 = behaviour.getName();
    _builder.append(_name_2, "    ");
    _builder.append(" : public DomainBehaviour");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("public:");
    _builder.newLine();
    _builder.append("            ");
    String _name_3 = behaviour.getName();
    _builder.append(_name_3, "            ");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("virtual ~");
    String _name_4 = behaviour.getName();
    _builder.append(_name_4, "            ");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("virtual void run(void* msg);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("/*PROTECTED REGION ID(pub");
    long _id_3 = behaviour.getId();
    _builder.append(_id_3, "            ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_4 = behaviour.getId();
      String _plus_2 = ("pub" + Long.valueOf(_id_4));
      boolean _containsKey_1 = this.protectedRegions.containsKey(_plus_2);
      if (_containsKey_1) {
        long _id_5 = behaviour.getId();
        String _plus_3 = ("pub" + Long.valueOf(_id_5));
        String _get_1 = this.protectedRegions.get(_plus_3);
        _builder.append(_get_1, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("            ");
        _builder.append("//Add additional public methods here");
        _builder.newLine();
      }
    }
    _builder.append("            ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("protected:");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("virtual void initialiseParameters();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("/*PROTECTED REGION ID(pro");
    long _id_6 = behaviour.getId();
    _builder.append(_id_6, "            ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_7 = behaviour.getId();
      String _plus_4 = ("pro" + Long.valueOf(_id_7));
      boolean _containsKey_2 = this.protectedRegions.containsKey(_plus_4);
      if (_containsKey_2) {
        long _id_8 = behaviour.getId();
        String _plus_5 = ("pro" + Long.valueOf(_id_8));
        String _get_2 = this.protectedRegions.get(_plus_5);
        _builder.append(_get_2, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("            ");
        _builder.append("//Add additional protected methods here");
        _builder.newLine();
      }
    }
    _builder.append("            ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("private:");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION ID(priv");
    long _id_9 = behaviour.getId();
    _builder.append(_id_9, "        ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_10 = behaviour.getId();
      String _plus_6 = ("priv" + Long.valueOf(_id_10));
      boolean _containsKey_3 = this.protectedRegions.containsKey(_plus_6);
      if (_containsKey_3) {
        long _id_11 = behaviour.getId();
        String _plus_7 = ("priv" + Long.valueOf(_id_11));
        String _get_3 = this.protectedRegions.get(_plus_7);
        _builder.append(_get_3, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("            ");
        _builder.append("//Add additional private methods here");
        _builder.newLine();
      }
    }
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("};");
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif /* ");
    String _name_5 = behaviour.getName();
    _builder.append(_name_5, "");
    _builder.append("_H_ */");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  public String behaviourSource(final Behaviour behaviour) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("using namespace std;");
    _builder.newLine();
    _builder.append("#include \"");
    String _destinationPath = behaviour.getDestinationPath();
    _builder.append(_destinationPath, "");
    _builder.append("/");
    String _name = behaviour.getName();
    _builder.append(_name, "");
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("/*PROTECTED REGION ID(inccpp");
    long _id = behaviour.getId();
    _builder.append(_id, "");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("//Add additional includes here");
    _builder.newLine();
    {
      long _id_1 = behaviour.getId();
      String _plus = ("inccpp" + Long.valueOf(_id_1));
      boolean _containsKey = this.protectedRegions.containsKey(_plus);
      if (_containsKey) {
        long _id_2 = behaviour.getId();
        String _plus_1 = ("inccpp" + Long.valueOf(_id_2));
        String _get = this.protectedRegions.get(_plus_1);
        _builder.append(_get, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("    ");
        _builder.append("//Add additional includes here");
        _builder.newLine();
      }
    }
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/*PROTECTED REGION ID(staticVars");
    long _id_3 = behaviour.getId();
    _builder.append(_id_3, "    ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_4 = behaviour.getId();
      String _plus_2 = ("staticVars" + Long.valueOf(_id_4));
      boolean _containsKey_1 = this.protectedRegions.containsKey(_plus_2);
      if (_containsKey_1) {
        long _id_5 = behaviour.getId();
        String _plus_3 = ("staticVars" + Long.valueOf(_id_5));
        String _get_1 = this.protectedRegions.get(_plus_3);
        _builder.append(_get_1, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("    ");
        _builder.append("//initialise static variables here");
        _builder.newLine();
      }
    }
    _builder.append("    ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    String _name_1 = behaviour.getName();
    _builder.append(_name_1, "    ");
    _builder.append("::");
    String _name_2 = behaviour.getName();
    _builder.append(_name_2, "    ");
    _builder.append("() : DomainBehaviour(\"");
    String _name_3 = behaviour.getName();
    _builder.append(_name_3, "    ");
    _builder.append("\")");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION ID(con");
    long _id_6 = behaviour.getId();
    _builder.append(_id_6, "        ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_7 = behaviour.getId();
      String _plus_4 = ("con" + Long.valueOf(_id_7));
      boolean _containsKey_2 = this.protectedRegions.containsKey(_plus_4);
      if (_containsKey_2) {
        long _id_8 = behaviour.getId();
        String _plus_5 = ("con" + Long.valueOf(_id_8));
        String _get_2 = this.protectedRegions.get(_plus_5);
        _builder.append(_get_2, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("        ");
        _builder.append("//Add additional options here");
        _builder.newLine();
      }
    }
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    String _name_4 = behaviour.getName();
    _builder.append(_name_4, "    ");
    _builder.append("::~");
    String _name_5 = behaviour.getName();
    _builder.append(_name_5, "    ");
    _builder.append("()");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION ID(dcon");
    long _id_9 = behaviour.getId();
    _builder.append(_id_9, "        ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_10 = behaviour.getId();
      String _plus_6 = ("dcon" + Long.valueOf(_id_10));
      boolean _containsKey_3 = this.protectedRegions.containsKey(_plus_6);
      if (_containsKey_3) {
        long _id_11 = behaviour.getId();
        String _plus_7 = ("dcon" + Long.valueOf(_id_11));
        String _get_3 = this.protectedRegions.get(_plus_7);
        _builder.append(_get_3, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("        ");
        _builder.append("//Add additional options here");
        _builder.newLine();
      }
    }
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("void ");
    String _name_6 = behaviour.getName();
    _builder.append(_name_6, "    ");
    _builder.append("::run(void* msg)");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION ID(run");
    long _id_12 = behaviour.getId();
    _builder.append(_id_12, "        ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_13 = behaviour.getId();
      String _plus_8 = ("run" + Long.valueOf(_id_13));
      boolean _containsKey_4 = this.protectedRegions.containsKey(_plus_8);
      if (_containsKey_4) {
        long _id_14 = behaviour.getId();
        String _plus_9 = ("run" + Long.valueOf(_id_14));
        String _get_4 = this.protectedRegions.get(_plus_9);
        _builder.append(_get_4, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("        ");
        _builder.append("//Add additional options here");
        _builder.newLine();
      }
    }
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("void ");
    String _name_7 = behaviour.getName();
    _builder.append(_name_7, "    ");
    _builder.append("::initialiseParameters()");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION ID(initialiseParameters");
    long _id_15 = behaviour.getId();
    _builder.append(_id_15, "        ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_16 = behaviour.getId();
      String _plus_10 = ("run" + Long.valueOf(_id_16));
      boolean _containsKey_5 = this.protectedRegions.containsKey(_plus_10);
      if (_containsKey_5) {
        long _id_17 = behaviour.getId();
        String _plus_11 = ("run" + Long.valueOf(_id_17));
        String _get_5 = this.protectedRegions.get(_plus_11);
        _builder.append(_get_5, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("        ");
        _builder.append("//Add additional options here");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/*PROTECTED REGION ID(methods");
    long _id_18 = behaviour.getId();
    _builder.append(_id_18, "    ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_19 = behaviour.getId();
      String _plus_12 = ("methods" + Long.valueOf(_id_19));
      boolean _containsKey_6 = this.protectedRegions.containsKey(_plus_12);
      if (_containsKey_6) {
        long _id_20 = behaviour.getId();
        String _plus_13 = ("methods" + Long.valueOf(_id_20));
        String _get_6 = this.protectedRegions.get(_plus_13);
        _builder.append(_get_6, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("        ");
        _builder.append("//Add additional options here");
        _builder.newLine();
      }
    }
    _builder.append("    ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String utilityFunctionCreatorHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef UTILITYFUNCTIONCREATOR_H_");
    _builder.newLine();
    _builder.append("#define UTILITYFUNCTIONCREATOR_H_");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include <engine/IUtilityCreator.h>");
    _builder.newLine();
    _builder.append("#include <memory>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class UtilityFunctionCreator : public IUtilityCreator");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("public:");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("virtual ~UtilityFunctionCreator();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("UtilityFunctionCreator();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("shared_ptr<BasicUtilityFunction> createUtility(long utilityfunctionConfId);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif /* UTILITYFUNCTIONCREATOR_H_ */");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String utilityFunctionCreatorSource(final List<Plan> plans) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <iostream>");
    _builder.newLine();
    _builder.append("#include \"UtilityFunctionCreator.h\"");
    _builder.newLine();
    {
      for(final Plan p : plans) {
        _builder.append("#include  \"");
        String _destinationPath = p.getDestinationPath();
        _builder.append(_destinationPath, "");
        _builder.append("/");
        String _name = p.getName();
        _builder.append(_name, "");
        long _id = p.getId();
        _builder.append(_id, "");
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("using namespace std;");
    _builder.newLine();
    _builder.append("using namespace alicaAutogenerated;");
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("UtilityFunctionCreator::~UtilityFunctionCreator()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("UtilityFunctionCreator::UtilityFunctionCreator()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("shared_ptr<BasicUtilityFunction> UtilityFunctionCreator::createUtility(long utilityfunctionConfId)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("switch(utilityfunctionConfId)");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    {
      for(final Plan p_1 : plans) {
        _builder.append("            ");
        _builder.append("case ");
        long _id_1 = p_1.getId();
        _builder.append(_id_1, "            ");
        _builder.append(":");
        _builder.newLineIfNotEmpty();
        _builder.append("            ");
        _builder.append("    ");
        _builder.append("return make_shared<UtilityFunction");
        long _id_2 = p_1.getId();
        _builder.append(_id_2, "                ");
        _builder.append(">();");
        _builder.newLineIfNotEmpty();
        _builder.append("            ");
        _builder.append("    ");
        _builder.append("break;");
        _builder.newLine();
      }
    }
    _builder.append("            ");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("cerr << \"UtilityFunctionCreator: Unknown utility requested: \" << utilityfunctionConfId << endl;");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("throw new exception();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("break;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String conditionCreatorHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef CONDITIONCREATOR_H_");
    _builder.newLine();
    _builder.append("#define CONDITIONCREATOR_H_");
    _builder.newLine();
    _builder.newLine();
    _builder.append("using namespace std;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"engine/IConditionCreator.h\"");
    _builder.newLine();
    _builder.append("#include <memory>");
    _builder.newLine();
    _builder.append("#include \"iostream\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class BasicCondition;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class ConditionCreator : public IConditionCreator");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("public:");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ConditionCreator();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("virtual ~ConditionCreator();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("shared_ptr<BasicCondition> createConditions(long conditionConfId);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String conditionCreatorSource(final List<Plan> plans, final List<Condition> conditions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"ConditionCreator.h\"");
    _builder.newLine();
    {
      for(final Plan p : plans) {
        _builder.append("#include  \"");
        String _destinationPath = p.getDestinationPath();
        _builder.append(_destinationPath, "");
        _builder.append("/");
        String _name = p.getName();
        _builder.append(_name, "");
        long _id = p.getId();
        _builder.append(_id, "");
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("using namespace alicaAutogenerated;");
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ConditionCreator::ConditionCreator()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ConditionCreator::~ConditionCreator()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("shared_ptr<BasicCondition> ConditionCreator::createConditions(long conditionConfId)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("switch (conditionConfId)");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    {
      for(final Condition con : conditions) {
        _builder.append("            ");
        _builder.append("case ");
        long _id_1 = con.getId();
        _builder.append(_id_1, "            ");
        _builder.append(":");
        _builder.newLineIfNotEmpty();
        {
          if ((con instanceof PreCondition)) {
            {
              AbstractPlan _abstractPlan = ((PreCondition)con).getAbstractPlan();
              boolean _equals = Objects.equal(_abstractPlan, null);
              if (_equals) {
                _builder.append("            ");
                _builder.append("return make_shared<TransitionCondition");
                long _id_2 = ((PreCondition)con).getId();
                _builder.append(_id_2, "            ");
                _builder.append(">();");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("            ");
                _builder.append("return make_shared<PreCondition");
                long _id_3 = ((PreCondition)con).getId();
                _builder.append(_id_3, "            ");
                _builder.append(">();");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        {
          if ((con instanceof PostCondition)) {
            _builder.append("            ");
            _builder.append("return make_shared<PostCondition");
            long _id_4 = ((PostCondition)con).getId();
            _builder.append(_id_4, "            ");
            _builder.append(">();");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          if ((con instanceof RuntimeCondition)) {
            _builder.append("            ");
            _builder.append("return make_shared<RunTimeCondition");
            long _id_5 = ((RuntimeCondition)con).getId();
            _builder.append(_id_5, "            ");
            _builder.append(">();");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("            ");
        _builder.append("    ");
        _builder.append("break;");
        _builder.newLine();
      }
    }
    _builder.append("            ");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("cerr << \"ConditionCreator: Unknown condition id requested: \" << conditionConfId << endl;");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("throw new exception();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("break;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String constraintCreatorHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef CONSTRAINTCREATOR_H_");
    _builder.newLine();
    _builder.append("#define CONSTRAINTCREATOR_H_");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include <engine/IConstraintCreator.h>");
    _builder.newLine();
    _builder.append("#include <memory>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class ConstraintCreator : public IConstraintCreator");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("public:");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ConstraintCreator();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("virtual ~ConstraintCreator();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("shared_ptr<BasicConstraint> createConstraint(long constraintConfId);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    _builder.append("#endif /* CONSTRAINTCREATOR_H_ */");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String constraintCreatorSource(final List<Plan> plans, final List<Condition> conditions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"ConstraintCreator.h\"");
    _builder.newLine();
    _builder.append("#include <iostream>");
    _builder.newLine();
    _builder.newLine();
    {
      for(final Plan p : plans) {
        _builder.append("#include  \"");
        String _destinationPath = p.getDestinationPath();
        _builder.append(_destinationPath, "");
        _builder.append("/constraints/");
        String _name = p.getName();
        _builder.append(_name, "");
        long _id = p.getId();
        _builder.append(_id, "");
        _builder.append("Constraints.h\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("using namespace std;");
    _builder.newLine();
    _builder.append("using namespace alicaAutogenerated;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ConstraintCreator::ConstraintCreator()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ConstraintCreator::~ConstraintCreator()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("shared_ptr<BasicConstraint> ConstraintCreator::createConstraint(long constraintConfId)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("switch(constraintConfId)");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    {
      for(final Condition c : conditions) {
        {
          if (((c.getVars().size() > 0) || (c.getQuantifiers().size() > 0))) {
            _builder.append("            ");
            _builder.append("case ");
            long _id_1 = c.getId();
            _builder.append(_id_1, "            ");
            _builder.append(":");
            _builder.newLineIfNotEmpty();
            _builder.append("            ");
            _builder.append("return make_shared<Constraint");
            long _id_2 = c.getId();
            _builder.append(_id_2, "            ");
            _builder.append(">();");
            _builder.newLineIfNotEmpty();
            _builder.append("            ");
            _builder.append("break;");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("            ");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("cerr << \"ConstraintCreator: Unknown constraint requested: \" << constraintConfId << endl;");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("throw new exception();");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("break;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String constraintsHeader(final Plan plan) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef ");
    String _name = plan.getName();
    _builder.append(_name, "");
    _builder.append("CONSTRAINT_H_");
    _builder.newLineIfNotEmpty();
    _builder.append("#define ");
    String _name_1 = plan.getName();
    _builder.append(_name_1, "");
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.append("#include \"engine/BasicConstraint.h\"");
    _builder.newLine();
    _builder.append("#include <memory>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("using namespace std;");
    _builder.newLine();
    _builder.append("using namespace alica;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class ProblemDescriptor;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class RunningPlan;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alicaAutogenerated");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    {
      PreCondition _preCondition = plan.getPreCondition();
      boolean _tripleNotEquals = (_preCondition != null);
      if (_tripleNotEquals) {
        {
          PreCondition _preCondition_1 = plan.getPreCondition();
          AbstractPlan _abstractPlan = _preCondition_1.getAbstractPlan();
          boolean _equals = Objects.equal(_abstractPlan, null);
          if (_equals) {
            {
              if (((plan.getPreCondition().getVars().size() > 0) || (plan.getPreCondition().getQuantifiers().size() > 0))) {
                _builder.newLine();
                _builder.append("    ");
                _builder.append("class Constraint");
                PreCondition _preCondition_2 = plan.getPreCondition();
                long _id = _preCondition_2.getId();
                _builder.append(_id, "    ");
                _builder.append(" : public BasicConstraint");
                _builder.newLineIfNotEmpty();
                _builder.append("    ");
                _builder.append("{");
                _builder.newLine();
                _builder.append("    ");
                _builder.append("    ");
                _builder.append("void getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp);");
                _builder.newLine();
                _builder.append("    ");
                _builder.append("};");
                _builder.newLine();
              } else {
                _builder.append("    ");
                _builder.append("class Constraint");
                PreCondition _preCondition_3 = plan.getPreCondition();
                long _id_1 = _preCondition_3.getId();
                _builder.append(_id_1, "    ");
                _builder.append(" : public BasicConstraint");
                _builder.newLineIfNotEmpty();
                _builder.append("    ");
                _builder.append("{");
                _builder.newLine();
                _builder.append("    ");
                _builder.append("    ");
                _builder.append("void getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp);");
                _builder.newLine();
                _builder.append("    ");
                _builder.append("};");
                _builder.newLine();
              }
            }
          }
        }
      }
    }
    {
      RuntimeCondition _runtimeCondition = plan.getRuntimeCondition();
      boolean _tripleNotEquals_1 = (_runtimeCondition != null);
      if (_tripleNotEquals_1) {
        {
          if (((plan.getRuntimeCondition().getVars().size() > 0) || (plan.getRuntimeCondition().getQuantifiers().size() > 0))) {
            _builder.append("    ");
            _builder.append("class Constraint");
            RuntimeCondition _runtimeCondition_1 = plan.getRuntimeCondition();
            long _id_2 = _runtimeCondition_1.getId();
            _builder.append(_id_2, "    ");
            _builder.append(" : public BasicConstraint");
            _builder.newLineIfNotEmpty();
            _builder.append("    ");
            _builder.append("{");
            _builder.newLine();
            _builder.append("    ");
            _builder.append("    ");
            _builder.append("void getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp);");
            _builder.newLine();
            _builder.append("    ");
            _builder.append("};");
            _builder.newLine();
          }
        }
      }
    }
    {
      EList<Transition> _transitions = plan.getTransitions();
      for(final Transition transition : _transitions) {
        {
          PreCondition _preCondition_4 = transition.getPreCondition();
          boolean _tripleNotEquals_2 = (_preCondition_4 != null);
          if (_tripleNotEquals_2) {
            {
              if (((transition.getPreCondition().getVars().size() > 0) || (transition.getPreCondition().getQuantifiers().size() > 0))) {
                _builder.append("    ");
                _builder.append("class Constraint");
                PreCondition _preCondition_5 = transition.getPreCondition();
                long _id_3 = _preCondition_5.getId();
                _builder.append(_id_3, "    ");
                _builder.append(" : public BasicConstraint");
                _builder.newLineIfNotEmpty();
                _builder.append("    ");
                _builder.append("{");
                _builder.newLine();
                _builder.append("    ");
                _builder.append("    ");
                _builder.append("void getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp);");
                _builder.newLine();
                _builder.append("    ");
                _builder.append("};");
                _builder.newLine();
              }
            }
          }
        }
      }
    }
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif /* ");
    String _name_2 = plan.getName();
    _builder.append(_name_2, "");
    _builder.append("CONSTRAINT_H_ */");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  public String constraintsSource(final Plan plan, final IConstraintCodeGenerator constraintCodeGenerator) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"");
    String _destinationPath = plan.getDestinationPath();
    _builder.append(_destinationPath, "");
    _builder.append("/constraints/");
    String _name = plan.getName();
    _builder.append(_name, "");
    long _id = plan.getId();
    _builder.append(_id, "");
    _builder.append("Constraints.h\"");
    _builder.newLineIfNotEmpty();
    _builder.append("using namespace std;");
    _builder.newLine();
    _builder.append("using namespace alica;");
    _builder.newLine();
    _builder.append("/*PROTECTED REGION ID(ch");
    long _id_1 = plan.getId();
    _builder.append(_id_1, "");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_2 = plan.getId();
      String _plus = ("ch" + Long.valueOf(_id_2));
      boolean _containsKey = this.protectedRegions.containsKey(_plus);
      if (_containsKey) {
        long _id_3 = plan.getId();
        String _plus_1 = ("ch" + Long.valueOf(_id_3));
        String _get = this.protectedRegions.get(_plus_1);
        _builder.append(_get, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("        ");
        _builder.append("//Add additional options here");
        _builder.newLine();
      }
    }
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alicaAutogenerated");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("//Plan:");
    String _name_1 = plan.getName();
    _builder.append(_name_1, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("/*");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("* Tasks: ");
    {
      EList<EntryPoint> _entryPoints = plan.getEntryPoints();
      for(final EntryPoint planEntryPoint : _entryPoints) {
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("* - EP:");
        long _id_4 = planEntryPoint.getId();
        _builder.append(_id_4, "    ");
        _builder.append(" : ");
        Task _task = planEntryPoint.getTask();
        String _name_2 = _task.getName();
        _builder.append(_name_2, "    ");
        _builder.append(" (");
        Task _task_1 = planEntryPoint.getTask();
        long _id_5 = _task_1.getId();
        _builder.append(_id_5, "    ");
        _builder.append(")");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("* States:");
    {
      EList<State> _states = plan.getStates();
      for(final State state : _states) {
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("* - ");
        String _name_3 = state.getName();
        _builder.append(_name_3, "    ");
        _builder.append(" (");
        long _id_6 = state.getId();
        _builder.append(_id_6, "    ");
        _builder.append(")");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("* Vars:");
    {
      EList<Variable> _vars = plan.getVars();
      for(final Variable variable : _vars) {
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("* - ");
        String _name_4 = variable.getName();
        _builder.append(_name_4, "    ");
        _builder.append(" (");
        long _id_7 = variable.getId();
        _builder.append(_id_7, "    ");
        _builder.append(") ");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    String _constraintPlanCheckingMethods = constraintCodeGenerator.constraintPlanCheckingMethods(plan);
    _builder.append(_constraintPlanCheckingMethods, "    ");
    _builder.newLineIfNotEmpty();
    {
      EList<State> _states_1 = plan.getStates();
      for(final State state_1 : _states_1) {
        _builder.append("    ");
        String _constraintStateCheckingMethods = constraintCodeGenerator.constraintStateCheckingMethods(state_1);
        _builder.append(_constraintStateCheckingMethods, "    ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String domainBehaviourHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef DomainBehaviour_H_");
    _builder.newLine();
    _builder.append("#define DomainBehaviour_H_");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"engine/BasicBehaviour.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class DomainBehaviour : public BasicBehaviour");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("public:");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("DomainBehaviour(string name);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("virtual ~DomainBehaviour();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("};");
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif /* DomainBehaviour_H_ */");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String domainBehaviourSource() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"DomainBehaviour.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("DomainBehaviour::DomainBehaviour(string name) : BasicBehaviour(name)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("DomainBehaviour::~DomainBehaviour()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String domainConditionHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef DomainBehaviour_H_");
    _builder.newLine();
    _builder.append("#define DomainBehaviour_H_");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"engine/BasicCondition.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class DomainCondition : public BasicCondition");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("public:");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("DomainCondition();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("virtual ~DomainCondition();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("};");
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif /* DomainBehaviour_H_ */");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String domainConditionSource() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"DomainCondition.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alica");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("DomainCondition::DomainCondition() : BasicCondition()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("DomainCondition::~DomainCondition()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String planHeader(final Plan plan) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef ");
    String _name = plan.getName();
    _builder.append(_name, "");
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.append("#define ");
    String _name_1 = plan.getName();
    _builder.append(_name_1, "");
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include \"DomainCondition.h\"");
    _builder.newLine();
    _builder.append("#include \"engine/BasicUtilityFunction.h\"");
    _builder.newLine();
    _builder.append("#include \"engine/UtilityFunction.h\"");
    _builder.newLine();
    _builder.append("#include \"engine/DefaultUtilityFunction.h\"");
    _builder.newLine();
    _builder.append("/*PROTECTED REGION ID(incl");
    long _id = plan.getId();
    _builder.append(_id, "");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_1 = plan.getId();
      String _plus = ("incl" + Long.valueOf(_id_1));
      boolean _containsKey = this.protectedRegions.containsKey(_plus);
      if (_containsKey) {
        long _id_2 = plan.getId();
        String _plus_1 = ("incl" + Long.valueOf(_id_2));
        String _get = this.protectedRegions.get(_plus_1);
        _builder.append(_get, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("//Add additional options here");
        _builder.newLine();
      }
    }
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.append("using namespace alica;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alicaAutogenerated");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/*PROTECTED REGION ID(meth");
    long _id_3 = plan.getId();
    _builder.append(_id_3, "    ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_4 = plan.getId();
      String _plus_2 = ("meth" + Long.valueOf(_id_4));
      boolean _containsKey_1 = this.protectedRegions.containsKey(_plus_2);
      if (_containsKey_1) {
        long _id_5 = plan.getId();
        String _plus_3 = ("meth" + Long.valueOf(_id_5));
        String _get_1 = this.protectedRegions.get(_plus_3);
        _builder.append(_get_1, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("    ");
        _builder.append("//Add additional options here");
        _builder.newLine();
      }
    }
    _builder.append("    ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("class UtilityFunction");
    long _id_6 = plan.getId();
    _builder.append(_id_6, "    ");
    _builder.append(" : public BasicUtilityFunction");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("shared_ptr<UtilityFunction> getUtilityFunction(Plan* plan);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("};");
    _builder.newLine();
    {
      PreCondition _preCondition = plan.getPreCondition();
      boolean _tripleNotEquals = (_preCondition != null);
      if (_tripleNotEquals) {
        _builder.append("    ");
        _builder.append("class PreCondition");
        PreCondition _preCondition_1 = plan.getPreCondition();
        long _id_7 = _preCondition_1.getId();
        _builder.append(_id_7, "    ");
        _builder.append(" : public DomainCondition");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("{");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("bool evaluate(shared_ptr<RunningPlan> rp);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("};");
        _builder.newLine();
      }
    }
    {
      RuntimeCondition _runtimeCondition = plan.getRuntimeCondition();
      boolean _tripleNotEquals_1 = (_runtimeCondition != null);
      if (_tripleNotEquals_1) {
        _builder.append("    ");
        _builder.append("class RunTimeCondition");
        RuntimeCondition _runtimeCondition_1 = plan.getRuntimeCondition();
        long _id_8 = _runtimeCondition_1.getId();
        _builder.append(_id_8, "    ");
        _builder.append(" : public DomainCondition");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("{");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("bool evaluate(shared_ptr<RunningPlan> rp);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("};");
        _builder.newLine();
      }
    }
    {
      EList<State> _states = plan.getStates();
      for(final State s : _states) {
        {
          EList<Transition> _outTransitions = s.getOutTransitions();
          for(final Transition transition : _outTransitions) {
            _builder.append("    ");
            _builder.append("class TransitionCondition");
            PreCondition _preCondition_2 = transition.getPreCondition();
            long _id_9 = _preCondition_2.getId();
            _builder.append(_id_9, "    ");
            _builder.append(" : public DomainCondition");
            _builder.newLineIfNotEmpty();
            _builder.append("    ");
            _builder.append("{");
            _builder.newLine();
            _builder.append("    ");
            _builder.append("    ");
            _builder.append("bool evaluate(shared_ptr<RunningPlan> rp);");
            _builder.newLine();
            _builder.append("    ");
            _builder.append("};");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("} /* namespace alica */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String planSource(final Plan plan, final IConstraintCodeGenerator constraintCodeGenerator) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"");
    String _destinationPath = plan.getDestinationPath();
    _builder.append(_destinationPath, "");
    _builder.append("/");
    String _name = plan.getName();
    _builder.append(_name, "");
    long _id = plan.getId();
    _builder.append(_id, "");
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    _builder.append("using namespace alica;");
    _builder.newLine();
    _builder.append("/*PROTECTED REGION ID(eph");
    long _id_1 = plan.getId();
    _builder.append(_id_1, "");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_2 = plan.getId();
      String _plus = ("eph" + Long.valueOf(_id_2));
      boolean _containsKey = this.protectedRegions.containsKey(_plus);
      if (_containsKey) {
        long _id_3 = plan.getId();
        String _plus_1 = ("eph" + Long.valueOf(_id_3));
        String _get = this.protectedRegions.get(_plus_1);
        _builder.append(_get, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("//Add additional options here");
        _builder.newLine();
      }
    }
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace alicaAutogenerated");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("//Plan:");
    String _name_1 = plan.getName();
    _builder.append(_name_1, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _expressionsPlanCheckingMethods = constraintCodeGenerator.expressionsPlanCheckingMethods(plan);
    _builder.append(_expressionsPlanCheckingMethods, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("/* generated comment");
    _builder.newLine();
    {
      EList<EntryPoint> _entryPoints = plan.getEntryPoints();
      for(final EntryPoint entryPoint : _entryPoints) {
        _builder.append("        ");
        _builder.append("Task: ");
        Task _task = entryPoint.getTask();
        String _name_2 = _task.getName();
        _builder.append(_name_2, "        ");
        _builder.append("  -> EntryPoint-ID: ");
        long _id_4 = entryPoint.getId();
        _builder.append(_id_4, "        ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("shared_ptr<UtilityFunction> UtilityFunction");
    long _id_5 = plan.getId();
    _builder.append(_id_5, "    ");
    _builder.append("::getUtilityFunction(Plan* plan)");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("/*PROTECTED REGION ID(");
    long _id_6 = plan.getId();
    _builder.append(_id_6, "       ");
    _builder.append(") ENABLED START*/");
    _builder.newLineIfNotEmpty();
    {
      long _id_7 = plan.getId();
      String _plus_2 = ("eph" + Long.valueOf(_id_7));
      boolean _containsKey_1 = this.protectedRegions.containsKey(_plus_2);
      if (_containsKey_1) {
        long _id_8 = plan.getId();
        String _get_1 = this.protectedRegions.get(Long.valueOf(_id_8));
        _builder.append(_get_1, "");
        _builder.newLineIfNotEmpty();
      } else {
        {
          boolean _isInstance = Plan.class.isInstance(this);
          if (_isInstance) {
            _builder.append("       ");
            _builder.append("shared_ptr<UtilityFunction> defaultFunction = make_shared<DefaultUtilityFunction>(plan);");
            _builder.newLine();
            _builder.append("       ");
            _builder.append("return defaultFunction;");
            _builder.newLine();
          } else {
            _builder.append("       ");
            _builder.append("return null;");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("        ");
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    {
      EList<State> _states = plan.getStates();
      for(final State state : _states) {
        String _expressionsStateCheckingMethods = constraintCodeGenerator.expressionsStateCheckingMethods(state);
        _builder.append(_expressionsStateCheckingMethods, "");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
}
