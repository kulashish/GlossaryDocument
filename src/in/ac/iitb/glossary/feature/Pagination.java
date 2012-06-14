package in.ac.iitb.glossary.feature;

import java.util.regex.Pattern;

import in.ac.iitb.glossary.GlossaryDocument;

public class Pagination {
	private static final String PAGE_REGEX = "[\\d\\s]+-[\\d\\s]+of[\\d\\s]+";
	private static Pattern pagePattern;
	static {
		pagePattern = Pattern.compile(PAGE_REGEX);
	}

	public boolean condition() {
		GlossaryDocument gDoc = GlossaryDocument.getInstance();
		String allText = gDoc.getViewport().getText();
		return condition(allText);
	}

	public boolean condition(String str) {
		return pagePattern.matcher(str).find();
	}

	public static void main(String args[]) {
		Pagination p = new Pagination();
		System.out
				.println(p
						.condition("11 - 20 of 110 results for: COMM "
								+ "print preview printer friendly page"
								+ "COMM 118S: Entrepreneurial Communication (COMM 218S)"
								+ "New business ventures are often incubated on college campuses. What makes the difference between a successful and unsuccessful entrepreneur-communication. Specifically, the entrepreneur's ability to communicate their vision to potential investors, employees, and customers. This seminar will explore successful and unsuccessful entrepreneurial communication. Students will learn the basics of persuasive oral and written communication, and then apply these principles to their own ideas. This course will help you to develop confidence in your speaking and writing as an entrepreneur through presentations and assignments, lectures and discussions, guest speakers, simulated activities, and video recorded feedback. In this course you will learn to: - Create communication strategies at an individual and organizational level - Develop clearly organized and effective presentations and documents - Diagnose and expand your personal writing and oral delivery style - Adapt your delivery style to different material and audiences - Enhance oral delivery through effective visual aids"));
	}
}
