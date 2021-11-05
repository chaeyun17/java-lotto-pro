package lotto.domain;

public class LottoRank {

	public static final int RANK_1ST = 1;
	public static final int RANK_2ST = 2;
	public static final int RANK_3ST = 3;
	public static final int RANK_4ST = 4;
	public static final int RANK_5ST = 5;
	private static final int LOTTO_1ST_MATCH_CNT = 6;
	private static final int LOTTO_2ST_MATCH_CNT = 5;
	private static final int LOTTO_3ST_MATCH_CNT = 5;
	private static final int LOTTO_4ST_MATCH_CNT = 4;
	private static final int LOTTO_5ST_MATCH_CNT = 3;

	private int rank;

	public LottoRank(LottoNumber winningNumber, LottoNumber number) {
		int matchCnt = winningNumber.getMatchCount(number);
		boolean isMatchBonus = LottoNumber.isContainBonusNumber(winningNumber, number);
		setRank(matchCnt, isMatchBonus);
	}

	public LottoRank(int rankNumber) {
		if (rankNumber < 1 || rankNumber > 5) {
			throw new IllegalArgumentException("순위는 1~4등만 존재합니다");
		}
		this.rank = rankNumber;
	}

	public int getRank() {
		return this.rank;
	}

	private void setRank(int matchCount, boolean isMatchBonus) {
		if (matchCount == LOTTO_1ST_MATCH_CNT) {
			this.rank = RANK_1ST;
			return;
		}
		if (matchCount == LOTTO_2ST_MATCH_CNT && isMatchBonus) {
			this.rank = RANK_2ST;
			return;
		}
		if (matchCount == LOTTO_3ST_MATCH_CNT) {
			this.rank = RANK_3ST;
			return;
		}
		if (matchCount == LOTTO_4ST_MATCH_CNT) {
			this.rank = RANK_4ST;
			return;
		}
		if (matchCount == LOTTO_5ST_MATCH_CNT) {
			this.rank = RANK_5ST;
		}
	}

	public boolean isFirst() {
		return this.rank == RANK_1ST;
	}

	public boolean isSecond() {
		return this.rank == RANK_2ST;
	}

	public boolean isThird() {
		return this.rank == RANK_3ST;
	}

	public boolean isFourth() {
		return this.rank == RANK_4ST;
	}

	public boolean isFifth() {
		return this.rank == LottoRank.RANK_5ST;
	}
}
