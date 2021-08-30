/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.otymko.jos.runtime.context;

import com.github.otymko.jos.compiler.AnnotationDefinition;
import com.github.otymko.jos.compiler.ContextDiscovery;
import com.github.otymko.jos.runtime.IVariable;
import com.github.otymko.jos.runtime.RuntimeContext;
import com.github.otymko.jos.runtime.Variable;
import com.github.otymko.jos.runtime.VariableReference;
import com.github.otymko.jos.runtime.context.global.GlobalContext;
import com.github.otymko.jos.runtime.context.global.SystemGlobalContext;
import com.github.otymko.jos.runtime.machine.MachineInstance;
import com.github.otymko.jos.runtime.machine.info.ConstructorInfo;
import com.github.otymko.jos.runtime.machine.info.MethodInfo;
import com.github.otymko.jos.runtime.machine.info.ParameterInfo;
import com.github.otymko.jos.runtime.machine.info.PropertyInfo;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ContextInitializer {

  public void initialize(MachineInstance machineInstance) {
    machineInstance.implementContext(new GlobalContext());
    machineInstance.implementContext(new SystemGlobalContext());
  }

  public MethodInfo[] getContextMethods(Class<? extends RuntimeContext> targetClass) {
    List<MethodInfo> methods = new ArrayList<>();
    for (var method : targetClass.getMethods()) {
      var contextMethod = method.getAnnotation(ContextMethod.class);
      if (contextMethod == null) {
        continue;
      }
      var parameters = getMethodParameters(method);
      var info = new MethodInfo(contextMethod.name(), contextMethod.alias(),
        method.getReturnType() != void.class, parameters, new AnnotationDefinition[0], method);

      methods.add(info);
    }
    return methods.toArray(new MethodInfo[0]);
  }

  public ParameterInfo[] getMethodParameters(Method method) {
    var length = method.getParameters().length;
    if (length == 0) {
      return new ParameterInfo[0];
    }
    var parameters = new ParameterInfo[length];
    var index = 0;
    for (var parameter : method.getParameters()) {
      var parameterInfo = ParameterInfo.builder()
        .name(parameter.getName())
        .build();

      parameters[index] = parameterInfo;
      index++;
    }
    return parameters;
  }

  public ConstructorInfo[] getConstructors(Class<? extends RuntimeContext> targetClass) {
    List<ConstructorInfo> constructors = new ArrayList<>();
    for (var method : targetClass.getMethods()) {
      var contextMethod = method.getAnnotation(ContextConstructor.class);
      if (contextMethod == null) {
        continue;
      }

      var parameters = getMethodParameters(method);
      var constructor = new ConstructorInfo(parameters, method);
      constructors.add(constructor);
    }


    return constructors.toArray(new ConstructorInfo[0]);
  }

  public PropertyInfo[] getProperties(Class<? extends RuntimeContext> targetClass) {
    List<PropertyInfo> properties = new ArrayList<>();
    for (var field : targetClass.getFields()) {
      var contextProperty = field.getAnnotation(ContextProperty.class);
      if (contextProperty == null) {
        continue;
      }

      var property = new PropertyInfo(contextProperty.name(), contextProperty.alias(),
        contextProperty.accessMode(), field);
      properties.add(property);
    }
    return properties.toArray(new PropertyInfo[0]);
  }

  public IVariable[] getGlobalContextVariables(AttachableContext context) {
    List<IVariable> variables = new ArrayList<>();
    var contexts = ContextDiscovery.getEnumerationContext();
    var index = 0;
    for (var enumContext : contexts){
      var variable = VariableReference.createContextPropertyReference(context, index, enumContext.getContextInfo().getName());
      variables.add(variable);
      index++;
    }
    return variables.toArray(new IVariable[0]);
  }

}
