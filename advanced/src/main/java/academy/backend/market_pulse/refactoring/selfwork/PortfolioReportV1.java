package academy.backend.market_pulse.refactoring.selfwork;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PortfolioReportV1 {
    private BigDecimal totalCost = BigDecimal.ZERO;
    private BigDecimal totalValue = BigDecimal.ZERO;
    private Holding best = null;
    private Holding worst = null;
    private BigDecimal pnl;
    private StringBuilder sb = new StringBuilder();

    private void calculateFields(Holding h) {
        BigDecimal cost = h.buyPrice().multiply(BigDecimal.valueOf(h.quantity()));
        BigDecimal value = h.currentPrice().multiply(BigDecimal.valueOf(h.quantity()));
        totalCost = totalCost.add(cost);
        totalValue = totalValue.add(value);
        pnl = value.subtract(cost);
    }

    private String getSign(BigDecimal bd) {
        return bd.signum() > 0 ? "+" : "";
    }

    private void renderHolding(Holding h) {
        sb.append(h.ticker()).append(": ").append(h.quantity()).append(" шт., ")
                .append(getSign(pnl)).append(pnl.setScale(2, RoundingMode.HALF_UP)).append("\n");
    }

    private void updateBestAndWorst(Holding h) {
        if (best == null || pnl.compareTo(best.currentPrice().subtract(best.buyPrice())
                .multiply(BigDecimal.valueOf(best.quantity()))) > 0) {
            best = h;
        }
        if (worst == null || pnl.compareTo(worst.currentPrice().subtract(worst.buyPrice())
                .multiply(BigDecimal.valueOf(worst.quantity()))) < 0) {
            worst = h;
        }
    }

    public String render(List<Holding> holdings) {
        sb.append("Портфель\n");
        sb.append("--------\n");
        if (holdings.isEmpty()) {
            sb.append("Пусто\n");
            return sb.toString();
        }

        for (Holding h : holdings) {
            calculateFields(h);
            renderHolding(h);
            updateBestAndWorst(h);
        }

        BigDecimal totalPnl = totalValue.subtract(totalCost);
        String totalSign = getSign(totalPnl);
        sb.append("Итого: ").append(totalValue.setScale(2, RoundingMode.HALF_UP))
                .append(" (").append(totalSign).append(totalPnl.setScale(2, RoundingMode.HALF_UP)).append(")\n");
        sb.append("Лучшая: ").append(best.ticker()).append("\n");
        sb.append("Худшая: ").append(worst.ticker()).append("\n");
        return sb.toString();
    }
}