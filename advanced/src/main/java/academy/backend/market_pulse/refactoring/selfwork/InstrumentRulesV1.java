package academy.backend.market_pulse.refactoring.selfwork;

/**
 * Задание для самостоятельной работы №2: один и тот же {@code switch} по типу повторяется в трёх
 * методах. Приёмы: Replace Conditional with Polymorphism (или enum с полями), Extract Class. Тесты
 * {@code InstrumentRulesTest} должны оставаться зелёными.
 */

public class InstrumentRulesV1 {
    public int lotSize(String type) {
        return InstrumentType.of(type).lotSize();
    }

    public int settlementDays(String type) {
        return InstrumentType.of(type).settlementDays();
    }

    public String label(String type) {
        return InstrumentType.of(type).label();
    }
}