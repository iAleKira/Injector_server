package filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the filter itself.
 */
public class Filter {

	private String inputPath;
	private String outputPath;
	private String payload;

	/**
	 * Constructor.
	 * 
	 * @param inputPath  - where to get the file from.
	 * @param outputPath - where to store results.
	 * @param payload    - payload to filter for.
	 */
	public Filter(String inputPath, String outputPath, String payload) {
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		this.payload = payload;
	}

	public String getInputPath() {
		return inputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public String getPayload() {
		return payload;
	}

	/**
	 * This method tells whether a string can be filtered or not. If the string
	 * doesn't match the pattern then false is returned. Otherwise the string gets
	 * shortened to the regular expression it contains. Then it checks for strings
	 * that contain a $number (needed to make sure it contains a capture group,
	 * strings that don't contain "\(" which is actually a parenthesis and not a
	 * capture group, and it excludes some other strings that may cause issues
	 * during parsing, such as octects or lookaheads. If the input string complies
	 * to the syntax described in these conditions then true is returned, otherwise
	 * false is.
	 * 
	 * @param inputPattern - The pattern used to compile the input string.
	 * 
	 * @param lineToFilter - line to filter.
	 * 
	 * @return boolean - tells whether the input string can be filtered or not.
	 * 
	 * 
	 */
	public boolean canBeFiltered(Pattern inputPattern, String lineToFilter) {
		if (lineToFilter == null) {
			throw new IllegalArgumentException("Cannot filter a null string.");
		}

		if (inputPattern == null) {
			throw new IllegalArgumentException("Cannot filter with null pattern.");
		}

		if (!lineToFilter.matches("match \\w* m\\|\\W.*\\(.*\\w*.*\\).*\\w")) {
			return false;
		}
		Matcher inputMatcher = inputPattern.matcher(lineToFilter);
		inputMatcher.find();
		String matchService = inputMatcher.group(); // We filter for strings like "match serviceX m|...| ..."
		Pattern patternBars = Pattern.compile("m\\|[\\W].*\\|"); // Shorten the string so we can work into the regex
		Matcher matcherBars = patternBars.matcher(matchService);
		matcherBars.find();
		String mRegex = matcherBars.group(); // m|...|
		String restricted = mRegex.substring(3, mRegex.length() - 1); // extract what's inside |...|. What Nmap sees
		return (includesDollarNum(matchService) && !includesFobiddenCharacters(restricted));
	}

	/**
	 * This method tells whether the input string contains $number characters.
	 * 
	 * @param matchService - string to filter with syntax match serviceX m|...|...".
	 * 
	 * @return hasDollarNum - whether the string includes a $number or not.
	 */

	boolean includesDollarNum(String matchService) {
		Pattern pDollarNum = Pattern.compile("\\$\\d"); // Pattern to include '$1', '$2'.. etc
		Matcher includeDollarNum = pDollarNum.matcher(matchService); // This way we only include strings that contain
		boolean hasDollarNum = includeDollarNum.find();
		return hasDollarNum;
	}

	/**
	 * This method tells whether the input string contains forbidden characters.
	 * Exclusions and reasons:
	 * 
	 * \( : some parentheses are not closed, therefore this creates a problem in
	 * capturing groups.
	 * 
	 * DPR: single string that includes an unclosed parenthesis ), therefore creates
	 * a problem in capturing groups.
	 * 
	 * \x: troublesome for matching: byte value into string.
	 * 
	 * \0 octets can trigger an exception: java.util.regex.PatternSyntaxException:
	 * Illegal octal escape sequence ?: : lookahead are unsupported by the parser.
	 * This case is handled in \x case.
	 * 
	 * @param regexWithoutBars - string contained inside m|...| ...".
	 * 
	 * @return includesForbiddenChars - whether the string includes a forbidden
	 *         characters or not.
	 * 
	 */

	boolean includesFobiddenCharacters(String regexWithoutBars) {
		boolean includesForbiddenChars = (regexWithoutBars.contains("\\(") || regexWithoutBars.contains("DPR")
				|| regexWithoutBars.contains("\\x") || regexWithoutBars.contains("?:"));
		return includesForbiddenChars;
	}
}