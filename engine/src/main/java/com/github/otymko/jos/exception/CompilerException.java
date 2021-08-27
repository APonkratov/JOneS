/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.otymko.jos.exception;

/**
 * Исключение при компиляции
 */
public class CompilerException extends EngineException {

  public CompilerException(String message) {
    super(message);
  }

  public static CompilerException symbolNotFoundException(String identifier) {
    var message = String.format("Неизвестный символ: %s", identifier);
    return new CompilerException(message);
  }

  public static CompilerException tooManyMethodArgumentsException() {
    var message = "Слишком много фактических параметров";
    return new CompilerException(message);
  }

  public static CompilerException tooFewMethodArgumentsException() {
    var message = "Недостаточно фактических параметров";
    return new CompilerException(message);
  }

  public static CompilerException notSupportedException() {
    var message = "Не поддерживается";
    return new CompilerException(message);
  }

  public static CompilerException notImplementedException(String what) {
    var message = String.format("'%s' не реализовано", what);
    return new CompilerException(message);
  }

  public static CompilerException notNotSupportedExpressionOperator(String operator) {
    var message = String.format("Оператор %s не поддерживается", operator);
    return new CompilerException(message);
  }

  public static CompilerException methodNotFound(String methodName) {
    var message = String.format("Метод '%s' не найден", methodName);
    return new CompilerException(message);
  }

  public static CompilerException mismatchedRaiseExpression() {
    var message = "Оператор 'ВызватьИсключение' без параметров может использоваться только в блоке 'Исключение'";
    return new CompilerException(message);
  }

}
