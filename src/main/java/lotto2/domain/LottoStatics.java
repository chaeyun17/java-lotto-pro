package lotto2.domain;

import java.util.HashMap;
import java.util.Map;

public class LottoStatics {

	private Map<String, Integer> rankCounts;
	private double profit;

	private LottoStatics() {
	}

	// TODO: LottoStatics 클래스 분리 필요
	public static LottoStatics of(LottoTickets lottoTickets, WinningLotto winningLotto, Money purchaseMoney) {
		LottoStatics statics = new LottoStatics();
		statics.rankCounts = calculateRankCount(lottoTickets, winningLotto);
		statics.profit = calculateProfit(calculateTotalPrize(statics.rankCounts), purchaseMoney);
		return statics;
	}

	private static Map<String, Integer> calculateRankCount(LottoTickets tickets, WinningLotto winningLotto) {
		Map<String, Integer> map = initRankCounts();
		for (LottoTicket lottoTicket : tickets) {
			Rank rank = winningLotto.getMatchRank(lottoTicket);
			String rankName = rank.name();
			map.put(rankName, map.get(rankName) + 1);
		}
		return map;
	}

	private static Map<String, Integer> initRankCounts() {
		Map<String, Integer> rankCounts = new HashMap<>();
		for (Rank rank : Rank.values()) {
			rankCounts.put(rank.name(), 0);
		}
		return rankCounts;
	}

	private static int calculateTotalPrize(Map<String, Integer> rankCounts) {
		return rankCounts.entrySet()
			.stream()
			.map((entry) ->
				Rank.valueOf(entry.getKey()).getPrize() * entry.getValue())
			.reduce(0, Integer::sum);
	}

	private static double calculateProfit(int totalPrizeMoney, Money money) {
		double result = (double)totalPrizeMoney / money.toInt();
		return Math.floor(result * 100) / 100D;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		LottoStatics that = (LottoStatics)o;

		if (Double.compare(that.profit, profit) != 0)
			return false;
		return rankCounts.equals(that.rankCounts);
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = rankCounts.hashCode();
		temp = Double.doubleToLongBits(profit);
		result = 31 * result + (int)(temp ^ (temp >>> 32));
		return result;
	}

	public double getProfit() {
		return this.profit;
	}

	public int getCount(Rank rank) {
		return this.rankCounts.get(rank.name());
	}
}
