package calculator;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AccumulatorTest {

	@ParameterizedTest
	@CsvSource(value = {"6:1:2:3", "3:1:1:1", "6:2:2:2"}, delimiter = ':')
	@DisplayName("숫자들을 입력하면, 모두 합한 값을 반환해야 한다")
	public void accumulate(int expected, int num1, int num2, int num3) {
		// given
		List<Integer> numbers = new ArrayList<>();
		numbers.add(num1);
		numbers.add(num2);
		numbers.add(num3);

		// when
		int result = Accumulator.accumulate(numbers);

		// then
		assertThat(result).isEqualTo(expected);
	}
}
