package lotto.domain;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class LottoTicket {

	private final Set<LottoNumber> lottoNumbers;

	private LottoTicket(Set<LottoNumber> lottoNumbers) {
		this.lottoNumbers = lottoNumbers;
	}

	public static LottoTicket of(Collection<Integer> numbersList) {
		Set<LottoNumber> numbersSet = numbersList.stream()
			.map(LottoNumber::of)
			.collect(Collectors.toSet());
		validate(numbersList, numbersSet);
		return new LottoTicket(numbersSet);
	}

	public static LottoTicket of(Set<LottoNumber> lottoNumbersSet) {
		return new LottoTicket(lottoNumbersSet);
	}

	private static void validate(Collection<Integer> numbersList, Set<LottoNumber> numbersSet) {
		if (numbersList.size() != LottoNumber.LOTTO_NUMBER_SIZE) {
			throw new IllegalArgumentException(
				ErrorMessage.LOTTO_NUMBER_COUNT_INVALID.value());
		}
		if (numbersList.size() != numbersSet.size()) {
			throw new IllegalArgumentException(
				ErrorMessage.LOTTO_NUMBER_NOT_DUPLICATE.value());
		}
	}

	public int getMatchCount(LottoTicket other) {
		int matchCnt = 0;
		for (LottoNumber number : other.lottoNumbers) {
			matchCnt += this.contains(number) ? 1 : 0;
		}
		return matchCnt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		LottoTicket ticket = (LottoTicket)o;

		return Objects.equals(lottoNumbers, ticket.lottoNumbers);
	}

	@Override
	public int hashCode() {
		return lottoNumbers != null ? lottoNumbers.hashCode() : 0;
	}

	public boolean contains(LottoNumber number) {
		return this.lottoNumbers.contains(number);
	}

	public Collection<LottoNumber> getNumbers() {
		return this.lottoNumbers;
	}

}
