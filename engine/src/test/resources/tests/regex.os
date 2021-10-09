Перем юТест;

Функция ПолучитьСписокТестов(ЮнитТестирование) Экспорт

    юТест = ЮнитТестирование;

    ВсеТесты = Новый Массив;
    //ВсеТесты.Добавить("ТестДолжен_СоздатьОбъектРегулярноеВыражение");
    //ВсеТесты.Добавить("ТестДолжен_ПроверитьМетодСовпадает");
    //ВсеТесты.Добавить("ТестДолжен_ПроверитьСвойство_ИгнорироватьРегистр");
    ВсеТесты.Добавить("ТестДолжен_ПроверитьСвойство_Многострочный");
    Возврат ВсеТесты;

КонецФункции

Процедура ТестДолжен_СоздатьОбъектРегулярноеВыражение() Экспорт

    РегулярноеВыражение = Новый РегулярноеВыражение("a");
    юТест.ПроверитьТип(РегулярноеВыражение, "РегулярноеВыражение", "РегулярноеВыражение");

КонецПроцедуры

Процедура ТестДолжен_ПроверитьМетодСовпадает() Экспорт

    РегулярноеВыражение = Новый РегулярноеВыражение("a");
    Совпало = РегулярноеВыражение.Совпадает("a");
    юТест.ПроверитьИстину(Совпало, "Совпало");

    РегулярноеВыражение = Новый РегулярноеВыражение("\d\d");
    Совпало = РегулярноеВыражение.Совпадает("15");
    юТест.ПроверитьИстину(Совпало, "Совпало 15");

    Совпало = РегулярноеВыражение.Совпадает("q");
    юТест.ПроверитьЛожь(Совпало, "Не Совпало q");

КонецПроцедуры

Процедура ТестДолжен_ПроверитьСвойство_ИгнорироватьРегистр() Экспорт

    ИсходнаяСтрока = "s";

	РегулярноеВыражение = Новый РегулярноеВыражение(ВРег(ИсходнаяСтрока));
	юТест.Проверить(РегулярноеВыражение.ИгнорироватьРегистр, "РегулярноеВыражение.ИгнорироватьРегистр");

	Совпало = РегулярноеВыражение.Совпадает(ИсходнаяСтрока);
	юТест.Проверить(Совпало, "Совпало");

	РегулярноеВыражение.ИгнорироватьРегистр = Ложь;
	юТест.ПроверитьЛожь(РегулярноеВыражение.ИгнорироватьРегистр, "РегулярноеВыражение.ИгнорироватьРегистр ложь");

	Совпало = РегулярноеВыражение.Совпадает(ИсходнаяСтрока);
	юТест.ПроверитьЛожь(Совпало, "Совпало");

КонецПроцедуры

Процедура ТестДолжен_ПроверитьСвойство_Многострочный() Экспорт

    ИсходнаяСтрока = "S" + Символы.ПС + "s";

	РегулярноеВыражение = Новый РегулярноеВыражение("^S");
	юТест.Проверить(РегулярноеВыражение.Многострочный, "РегулярноеВыражение.Многострочный");

	КоллекцияСовпадений = РегулярноеВыражение.НайтиСовпадения(ИсходнаяСтрока);
	юТест.ПроверитьРавенство(2, КоллекцияСовпадений.Количество(), "КоллекцияСовпадений.Количество()");

	РегулярноеВыражение.Многострочный = Ложь;
	юТест.ПроверитьЛожь(РегулярноеВыражение.Многострочный, "РегулярноеВыражение.Многострочный ложь");

	КоллекцияСовпадений = РегулярноеВыражение.НайтиСовпадения(ИсходнаяСтрока);
	юТест.ПроверитьРавенство(1, КоллекцияСовпадений.Количество(), "КоллекцияСовпадений.Количество() Многострочный ложь");

КонецПроцедуры