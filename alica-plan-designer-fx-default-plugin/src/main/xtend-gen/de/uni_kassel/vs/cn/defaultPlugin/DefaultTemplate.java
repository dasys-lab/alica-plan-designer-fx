package de.uni_kassel.vs.cn.defaultPlugin;

import com.google.common.base.Objects;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.IInhabitable;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.Quantifier;
import de.uni_kassel.vs.cn.planDesigner.alica.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.alica.Variable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Created by marci on 19.05.17.
 */
@SuppressWarnings("all")
public class DefaultTemplate {
  public String expressionsStateCheckingMethods(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Transition> _outTransitions = state.getOutTransitions();
      for(final Transition transition : _outTransitions) {
        {
          PreCondition _preCondition = transition.getPreCondition();
          String _pluginName = _preCondition.getPluginName();
          boolean _equals = Objects.equal(_pluginName, "DefaultPlugin");
          if (_equals) {
            _builder.append("/*");
            _builder.newLine();
            _builder.append("*");
            _builder.newLine();
            _builder.append("* Transition:");
            _builder.newLine();
            _builder.append("*   - Name: ");
            PreCondition _preCondition_1 = transition.getPreCondition();
            String _name = _preCondition_1.getName();
            _builder.append(_name, "");
            _builder.append(", ConditionString: ");
            PreCondition _preCondition_2 = transition.getPreCondition();
            String _conditionString = _preCondition_2.getConditionString();
            _builder.append(_conditionString, "");
            _builder.append(", Comment : ");
            String _comment = transition.getComment();
            _builder.append(_comment, "");
            _builder.newLineIfNotEmpty();
            _builder.append("*");
            _builder.newLine();
            _builder.append("* Plans in State: ");
            {
              EList<AbstractPlan> _plans = state.getPlans();
              for(final AbstractPlan plan : _plans) {
                _builder.newLineIfNotEmpty();
                _builder.append("*   - Plan - (Name): ");
                String _name_1 = plan.getName();
                _builder.append(_name_1, "");
                _builder.append(", (PlanID): ");
                long _id = plan.getId();
                _builder.append(_id, "");
                _builder.append(" ");
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("*");
            _builder.newLine();
            _builder.append("* Tasks: ");
            {
              Plan _inPlan = state.getInPlan();
              EList<EntryPoint> _entryPoints = _inPlan.getEntryPoints();
              for(final EntryPoint planEntryPoint : _entryPoints) {
                _builder.newLineIfNotEmpty();
                _builder.append("*   - ");
                Task _task = planEntryPoint.getTask();
                String _name_2 = _task.getName();
                _builder.append(_name_2, "");
                _builder.append(" (");
                Task _task_1 = planEntryPoint.getTask();
                long _id_1 = _task_1.getId();
                _builder.append(_id_1, "");
                _builder.append(") (Entrypoint: ");
                long _id_2 = planEntryPoint.getId();
                _builder.append(_id_2, "");
                _builder.append(")");
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("*");
            _builder.newLine();
            _builder.append("* States:");
            {
              Plan _inPlan_1 = state.getInPlan();
              EList<State> _states = _inPlan_1.getStates();
              for(final State stateOfInPlan : _states) {
                _builder.newLineIfNotEmpty();
                _builder.append("*   - ");
                String _name_3 = stateOfInPlan.getName();
                _builder.append(_name_3, "");
                _builder.append(" (");
                long _id_3 = stateOfInPlan.getId();
                _builder.append(_id_3, "");
                _builder.append(")");
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("*");
            _builder.newLine();
            _builder.append("* Vars:");
            {
              PreCondition _preCondition_3 = transition.getPreCondition();
              EList<Variable> _vars = _preCondition_3.getVars();
              for(final Variable variable : _vars) {
                _builder.newLineIfNotEmpty();
                _builder.append("*\t- ");
                String _name_4 = variable.getName();
                _builder.append(_name_4, "");
                _builder.append(" (");
                long _id_4 = variable.getId();
                _builder.append(_id_4, "");
                _builder.append(") ");
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("*/");
            _builder.newLine();
            _builder.append("bool TransitionCondition");
            PreCondition _preCondition_4 = transition.getPreCondition();
            long _id_5 = _preCondition_4.getId();
            _builder.append(_id_5, "");
            _builder.append("::evaluate(shared_ptr<RunningPlan> rp)");
            _builder.newLineIfNotEmpty();
            _builder.append(" ");
            _builder.append("{");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("/*PROTECTED REGION ID(");
            long _id_6 = transition.getId();
            _builder.append(_id_6, "\t");
            _builder.append(") ENABLED START*/");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("return false;");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("/*PROTECTED REGION END*/");
            _builder.newLine();
            _builder.newLine();
            _builder.append("}");
            _builder.newLine();
          }
        }
      }
    }
    return _builder.toString();
  }
  
  public String expressionsPlanCheckingMethods(final Plan plan) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((plan.getRuntimeCondition() != null) && Objects.equal(plan.getRuntimeCondition().getPluginName(), "DefaultPlugin"))) {
        _builder.append(" ");
        _builder.append("//Check of RuntimeCondition - (Name): ");
        RuntimeCondition _runtimeCondition = plan.getRuntimeCondition();
        String _name = _runtimeCondition.getName();
        _builder.append(_name, " ");
        _builder.append(", (ConditionString): ");
        RuntimeCondition _runtimeCondition_1 = plan.getRuntimeCondition();
        String _conditionString = _runtimeCondition_1.getConditionString();
        _builder.append(_conditionString, " ");
        _builder.append(", (Comment) : ");
        RuntimeCondition _runtimeCondition_2 = plan.getRuntimeCondition();
        String _comment = _runtimeCondition_2.getComment();
        _builder.append(_comment, " ");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append(" ");
        _builder.append("/*");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("* Available Vars:");
        {
          RuntimeCondition _runtimeCondition_3 = plan.getRuntimeCondition();
          EList<Variable> _vars = _runtimeCondition_3.getVars();
          for(final Variable variable : _vars) {
            _builder.newLineIfNotEmpty();
            _builder.append(" ");
            _builder.append("*\t- ");
            String _name_1 = variable.getName();
            _builder.append(_name_1, " ");
            _builder.append(" (");
            long _id = variable.getId();
            _builder.append(_id, " ");
            _builder.append(")");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("bool RunTimeCondition");
        RuntimeCondition _runtimeCondition_4 = plan.getRuntimeCondition();
        long _id_1 = _runtimeCondition_4.getId();
        _builder.append(_id_1, "");
        _builder.append("::evaluate(shared_ptr<RunningPlan> rp)");
        _builder.newLineIfNotEmpty();
        _builder.append("{");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("/*PROTECTED REGION ID(");
        RuntimeCondition _runtimeCondition_5 = plan.getRuntimeCondition();
        long _id_2 = _runtimeCondition_5.getId();
        _builder.append(_id_2, "    ");
        _builder.append(") ENABLED START*/");
        _builder.newLineIfNotEmpty();
        _builder.append("     ");
        _builder.append("return true;");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("/*PROTECTED REGION END*/");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    {
      if (((plan.getPreCondition() != null) && Objects.equal(plan.getPreCondition().getPluginName(), "DefaultPlugin"))) {
        _builder.append(" ");
        _builder.append("//Check of PreCondition - (Name): ");
        PreCondition _preCondition = plan.getPreCondition();
        String _name_2 = _preCondition.getName();
        _builder.append(_name_2, " ");
        _builder.append(", (ConditionString): ");
        PreCondition _preCondition_1 = plan.getPreCondition();
        String _conditionString_1 = _preCondition_1.getConditionString();
        _builder.append(_conditionString_1, " ");
        _builder.append(" , (Comment) : ");
        PreCondition _preCondition_2 = plan.getPreCondition();
        String _comment_1 = _preCondition_2.getComment();
        _builder.append(_comment_1, " ");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append(" ");
        _builder.append("/*");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("* Available Vars:");
        {
          PreCondition _preCondition_3 = plan.getPreCondition();
          EList<Variable> _vars_1 = _preCondition_3.getVars();
          for(final Variable variable_1 : _vars_1) {
            _builder.newLineIfNotEmpty();
            _builder.append(" ");
            _builder.append("*\t- ");
            String _name_3 = variable_1.getName();
            _builder.append(_name_3, " ");
            _builder.append(" (");
            long _id_3 = variable_1.getId();
            _builder.append(_id_3, " ");
            _builder.append(")");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("bool PreCondition");
        PreCondition _preCondition_4 = plan.getPreCondition();
        long _id_4 = _preCondition_4.getId();
        _builder.append(_id_4, "");
        _builder.append("::evaluate(shared_ptr<RunningPlan> rp)");
        _builder.newLineIfNotEmpty();
        _builder.append("{");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("/*PROTECTED REGION ID(");
        PreCondition _preCondition_5 = plan.getPreCondition();
        long _id_5 = _preCondition_5.getId();
        _builder.append(_id_5, "    ");
        _builder.append(") ENABLED START*/");
        _builder.newLineIfNotEmpty();
        _builder.append("       ");
        _builder.append("//--> \"PreCondition:");
        PreCondition _preCondition_6 = plan.getPreCondition();
        long _id_6 = _preCondition_6.getId();
        _builder.append(_id_6, "       ");
        _builder.append("  not implemented\";");
        _builder.newLineIfNotEmpty();
        _builder.append("       ");
        _builder.append("return true;");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("/*PROTECTED REGION END*/");
        _builder.newLine();
        _builder.append(" ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
  
  public String constraintPlanCheckingMethods(final Plan plan) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((plan.getRuntimeCondition() != null) && Objects.equal(plan.getRuntimeCondition().getPluginName(), "DefaultPlugin"))) {
        _builder.append("/*");
        _builder.newLine();
        _builder.append("* RuntimeCondition - (Name): ");
        RuntimeCondition _runtimeCondition = plan.getRuntimeCondition();
        String _name = _runtimeCondition.getName();
        _builder.append(_name, "");
        _builder.newLineIfNotEmpty();
        _builder.append("* (ConditionString): ");
        RuntimeCondition _runtimeCondition_1 = plan.getRuntimeCondition();
        String _conditionString = _runtimeCondition_1.getConditionString();
        _builder.append(_conditionString, "");
        _builder.newLineIfNotEmpty();
        {
          RuntimeCondition _runtimeCondition_2 = plan.getRuntimeCondition();
          EList<Variable> _vars = _runtimeCondition_2.getVars();
          boolean _tripleNotEquals = (_vars != null);
          if (_tripleNotEquals) {
            _builder.append("* Static Variables: ");
            {
              RuntimeCondition _runtimeCondition_3 = plan.getRuntimeCondition();
              EList<Variable> _vars_1 = _runtimeCondition_3.getVars();
              for(final Variable variable : _vars_1) {
                String _name_1 = variable.getName();
                _builder.append(_name_1, "");
                _builder.append(" ");
              }
            }
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("* Domain Variables:");
        _builder.newLine();
        {
          RuntimeCondition _runtimeCondition_4 = plan.getRuntimeCondition();
          EList<Quantifier> _quantifiers = _runtimeCondition_4.getQuantifiers();
          boolean _tripleNotEquals_1 = (_quantifiers != null);
          if (_tripleNotEquals_1) {
            {
              RuntimeCondition _runtimeCondition_5 = plan.getRuntimeCondition();
              EList<Quantifier> _quantifiers_1 = _runtimeCondition_5.getQuantifiers();
              for(final Quantifier q : _quantifiers_1) {
                _builder.append("* forall agents in ");
                IInhabitable _scope = q.getScope();
                String _name_2 = _scope.getName();
                _builder.append(_name_2, "");
                _builder.append(" let v = ");
                EList<String> _sorts = q.getSorts();
                _builder.append(_sorts, "");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        _builder.append("*");
        _builder.newLine();
        _builder.append("*/");
        _builder.newLine();
        _builder.append("void Constraint");
        RuntimeCondition _runtimeCondition_6 = plan.getRuntimeCondition();
        long _id = _runtimeCondition_6.getId();
        _builder.append(_id, "");
        _builder.append("::getConstraint(shared_ptr<ConstraintDescriptor> c, shared_ptr<RunningPlan> rp) {");
        _builder.newLineIfNotEmpty();
        _builder.append("/*PROTECTED REGION ID(cc");
        RuntimeCondition _runtimeCondition_7 = plan.getRuntimeCondition();
        long _id_1 = _runtimeCondition_7.getId();
        _builder.append(_id_1, "");
        _builder.append(") ENABLED START*/");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("//Proteced");
        _builder.newLine();
        _builder.append("/*PROTECTED REGION END*/");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      if ((((plan.getPreCondition() != null) && Objects.equal(plan.getPreCondition().getPluginName(), "DefaultPlugin")) && ((plan.getPreCondition().getVars().size() > 0) || (plan.getPreCondition().getQuantifiers().size() > 0)))) {
        _builder.append("/*");
        _builder.newLine();
        _builder.append("* PreCondition - (Name): ");
        PreCondition _preCondition = plan.getPreCondition();
        String _name_3 = _preCondition.getName();
        _builder.append(_name_3, "");
        _builder.newLineIfNotEmpty();
        _builder.append("* (ConditionString): ");
        PreCondition _preCondition_1 = plan.getPreCondition();
        String _conditionString_1 = _preCondition_1.getConditionString();
        _builder.append(_conditionString_1, "");
        _builder.newLineIfNotEmpty();
        _builder.append("* Static Variables: ");
        {
          PreCondition _preCondition_2 = plan.getPreCondition();
          EList<Variable> _vars_2 = _preCondition_2.getVars();
          for(final Variable variable_1 : _vars_2) {
            String _name_4 = variable_1.getName();
            _builder.append(_name_4, "");
            _builder.append(" ");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("* Domain Variables:");
        _builder.newLine();
        {
          PreCondition _preCondition_3 = plan.getPreCondition();
          EList<Quantifier> _quantifiers_2 = _preCondition_3.getQuantifiers();
          for(final Quantifier q_1 : _quantifiers_2) {
            _builder.append("* forall agents in ");
            IInhabitable _scope_1 = q_1.getScope();
            String _name_5 = _scope_1.getName();
            _builder.append(_name_5, "");
            _builder.append(" let v = ");
            EList<String> _sorts_1 = q_1.getSorts();
            _builder.append(_sorts_1, "");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("*");
        _builder.newLine();
        _builder.append("*/");
        _builder.newLine();
        _builder.append("void Constraint");
        PreCondition _preCondition_4 = plan.getPreCondition();
        long _id_2 = _preCondition_4.getId();
        _builder.append(_id_2, "");
        _builder.append("::getConstraint(shared_ptr<ConstraintDescriptor> c, shared_ptr<RunningPlan> rp) {");
        _builder.newLineIfNotEmpty();
        _builder.append("/*PROTECTED REGION ID(cc");
        PreCondition _preCondition_5 = plan.getPreCondition();
        long _id_3 = _preCondition_5.getId();
        _builder.append(_id_3, "");
        _builder.append(") ENABLED START*/");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("//Proteced");
        _builder.newLine();
        _builder.append("/*PROTECTED REGION END*/");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    return _builder.toString();
  }
  
  public String constraintStateCheckingMethods(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// State: ");
    String _name = state.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    {
      EList<Transition> _outTransitions = state.getOutTransitions();
      for(final Transition transition : _outTransitions) {
        {
          if ((Objects.equal(transition.getPreCondition().getPluginName(), "DefaultPlugin") && (transition.getPreCondition().getVars().size() > 0))) {
            _builder.append("/*");
            _builder.newLine();
            _builder.append("* Transition:");
            _builder.newLine();
            _builder.append("* - Name: ");
            PreCondition _preCondition = transition.getPreCondition();
            String _name_1 = _preCondition.getName();
            _builder.append(_name_1, "");
            _builder.newLineIfNotEmpty();
            _builder.append("* - ConditionString: ");
            PreCondition _preCondition_1 = transition.getPreCondition();
            String _conditionString = _preCondition_1.getConditionString();
            _builder.append(_conditionString, "");
            _builder.newLineIfNotEmpty();
            _builder.append("*");
            _builder.newLine();
            _builder.append("* Plans in State: ");
            {
              EList<AbstractPlan> _plans = state.getPlans();
              for(final AbstractPlan plan : _plans) {
                _builder.newLineIfNotEmpty();
                _builder.append("* - Plan - (Name): ");
                String _name_2 = plan.getName();
                _builder.append(_name_2, "");
                _builder.append(", (PlanID): ");
                long _id = plan.getId();
                _builder.append(_id, "");
                _builder.append(" ");
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("* Static Variables: ");
            {
              PreCondition _preCondition_2 = transition.getPreCondition();
              EList<Variable> _vars = _preCondition_2.getVars();
              for(final Variable variable : _vars) {
                String _name_3 = variable.getName();
                _builder.append(_name_3, "");
                _builder.append(" ");
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("* Domain Variables:");
            _builder.newLine();
            {
              if (((transition.getPreCondition().getQuantifiers() != null) && (transition.getPreCondition().getQuantifiers().size() > 0))) {
                {
                  PreCondition _preCondition_3 = transition.getPreCondition();
                  EList<Quantifier> _quantifiers = _preCondition_3.getQuantifiers();
                  for(final Quantifier q : _quantifiers) {
                    _builder.append("* forall agents in ");
                    IInhabitable _scope = q.getScope();
                    String _name_4 = _scope.getName();
                    _builder.append(_name_4, "");
                    _builder.append(" let v = ");
                    EList<String> _sorts = q.getSorts();
                    _builder.append(_sorts, "");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("*/");
            _builder.newLine();
            _builder.append("void Constraint");
            PreCondition _preCondition_4 = transition.getPreCondition();
            long _id_1 = _preCondition_4.getId();
            _builder.append(_id_1, "");
            _builder.append("::getConstraint(shared_ptr<ConstraintDescriptor> c, shared_ptr<RunningPlan> rp) {");
            _builder.newLineIfNotEmpty();
            _builder.append("/*PROTECTED REGION ID(cc");
            PreCondition _preCondition_5 = transition.getPreCondition();
            long _id_2 = _preCondition_5.getId();
            _builder.append(_id_2, "");
            _builder.append(") ENABLED START*/");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("//Proteced");
            _builder.newLine();
            _builder.append("/*PROTECTED REGION END*/");
            _builder.newLine();
            _builder.append("}");
            _builder.newLine();
          }
        }
      }
    }
    return _builder.toString();
  }
}
