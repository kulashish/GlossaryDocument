package in.ac.iitb.glossary;

import in.ac.iitb.glossary.feature.GlossaryBoxArea;
import in.ac.iitb.glossary.feature.Pagination;
import in.ac.iitb.glossary.feature.SameWidthSubboxes;
import in.ac.iitb.glossary.feature.SameClassSubboxes;
import in.ac.iitb.glossary.feature.SubboxAlignment;
import in.ac.iitb.glossary.feature.VisualFeature;

import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.ElementBox;

public class GlossaryClassifier {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: GlossaryClassifier <url>");
			System.exit(0);
		}
		GlossaryClassifier classifier = new GlossaryClassifier();
		classifier.classify(args[0]);
	}

	public void classify(String strURL) {
		try {
			GlossaryDocument gDocument = GlossaryDocument
					.createInstance(strURL);

			// Number of Sub boxes condition
			GlossaryClassifier classifier = new GlossaryClassifier();
			classifier.subBoxes(gDocument.getViewport());
			System.out.println("Subboxes count condition : "
					+ (gDocument.getGlossaryBoxes().size() > 0));

			// Sub boxes of same class condition
			VisualFeature sameclassCondition = new SameClassSubboxes();
			sameclassCondition.executeCondition();
			System.out.println("Subboxes of same class condition : "
					+ (gDocument.getGlossaryBoxes().size() > 0));

			// Ratio of area of glossary box to viewport area
			VisualFeature areaCondition = new GlossaryBoxArea();
			areaCondition.executeCondition();
			System.out.println("Glossary box to viewport area condition : "
					+ (gDocument.getGlossaryBoxes().size() > 0));

			// Same WIDTH subboxes
			VisualFeature sameareaCondition = new SameWidthSubboxes();
			sameareaCondition.executeCondition();
			System.out.println("Subboxes of same area condition : "
					+ (gDocument.getGlossaryBoxes().size() > 0));

			// Aligned subboxes
			VisualFeature subboxesAligned = new SubboxAlignment();
			subboxesAligned.executeCondition();
			System.out.println("Subboxes aligned condition : "
					+ (gDocument.getGlossaryBoxes().size() > 0));

			// Pagination
			Pagination pagination = new Pagination();
			System.out.println("Pagination : " + pagination.condition());

			System.out.println("Number of potential glossary Boxes:"
					+ gDocument.getGlossaryBoxes().size());
			for (GlossaryBox gBox : gDocument.getGlossaryBoxes()) {
				System.out.println(gBox.toString());
				// for (Box subbox : ((ElementBox) gBox).getSubBoxList())
				// System.out.println(subbox);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void subBoxes(Box root) {
		if (root instanceof ElementBox) {
			ElementBox elementBox = (ElementBox) root;
			if (subBoxCondition(root))
				addGlossaryBox(root);
			for (Box subBox : elementBox.getSubBoxList())
				subBoxes(subBox);
		}
	}

	private void addGlossaryBox(Box root) {
		GlossaryDocument.getInstance().addGlossaryBox(root);
	}

	public boolean subBoxCondition(Box root) {
		boolean blnMeetsCondition = false;
		if (root instanceof ElementBox) {
			ElementBox elementBox = (ElementBox) root;
			int numberOfSubboxes = elementBox.getSubBoxNumber();
			if (numberOfSubboxes >= GlossaryConstants.GLOSSARY_COUNT_THRESHOLD)
				blnMeetsCondition = true;
		}
		return blnMeetsCondition;
	}
}
