package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private static final String DEFAULT_DELIMITER = ",|:";
	private static final Pattern CUSTOM_REGEX = Pattern.compile("//(.)\n(.*)");
	private static final Pattern NUMBER_FORMAT_REGEX = Pattern.compile("^[0-9]");
	private static final int CUSTOM_REGEX_DELIMITER_IDX = 1;
	private static final int CUSTOM_REGEX_NUMBERS_IDX = 2;

	public static Numbers parse(String input) {
		if (isCustomDelimiter(input)) {
			return customSplit(input);
		}
		return split(input, DEFAULT_DELIMITER);
	}

	private static Numbers customSplit(String input) {
		Matcher m = getCustomMatcher(input);
		if (m.find()) {
			String delimiter = m.group(CUSTOM_REGEX_DELIMITER_IDX);
			String numberStr = m.group(CUSTOM_REGEX_NUMBERS_IDX);
			return split(numberStr, delimiter);
		}
		throw new RuntimeException(ErrorMessage.CUSTOM_SPLIT_ERROR.message());
	}

	private static Numbers split(String input, String delimiter) {
		Numbers numbers = new Numbers();
		String[] splits = input.split(delimiter);
		for (String s : splits) {
			numbers.add(parseNumber(s));
		}
		return numbers;
	}

	private static int parseNumber(String str) {
		Matcher matcher = NUMBER_FORMAT_REGEX.matcher(str);
		if (matcher.find()) {
			return Integer.parseInt(str);
		}
		throw new RuntimeException(ErrorMessage.PARSE_NUMBER_ERROR.message());
	}

	private static boolean isCustomDelimiter(String input) {
		Matcher m = getCustomMatcher(input);
		return m.find();
	}

	private static Matcher getCustomMatcher(String input) {
		return CUSTOM_REGEX.matcher(input);
	}

}
