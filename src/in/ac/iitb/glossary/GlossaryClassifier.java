package in.ac.iitb.glossary;

import in.ac.iitb.glossary.feature.GlossaryBoxArea;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.demo.DOMSource;
import org.fit.cssbox.layout.BlockBox;
import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.BrowserCanvas;
import org.fit.cssbox.layout.ElementBox;
import org.fit.cssbox.layout.ReplacedImage;
import org.w3c.dom.Document;

public class GlossaryClassifier {

	private List<GlossaryBox> glossaryBoxes;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: TextBoxes <url>");
			System.exit(0);
		}

		try {
			GlossaryDocument gDocument = GlossaryDocument
					.createInstance(args[0]);

			// Number of Sub boxes condition
			GlossaryClassifier classifier = new GlossaryClassifier();
			classifier.subBoxes(gDocument.getViewport());

			// Sub boxes of same class condition
			List<GlossaryBox> tempGBoxes = new ArrayList<GlossaryBox>();
			for (GlossaryBox gBox : classifier.getGlossaryBoxes()) {
				gBox.groupSubboxes();
				if (classifier.sameClassCondition(gBox))
					tempGBoxes.add(gBox);
			}
			classifier.setGlossaryBoxes(tempGBoxes);

			GlossaryBoxArea areaCondition = new GlossaryBoxArea();
			tempGBoxes = new ArrayList<GlossaryBox>();
			for (GlossaryBox gBox : classifier.getGlossaryBoxes()) {
				if (areaCondition.condition(gBox))
					tempGBoxes.add(gBox);
			}
			classifier.setGlossaryBoxes(tempGBoxes);

			System.out.println("Number of potential glossary Boxes:"
					+ classifier.getGlossaryBoxes().size());
			for (GlossaryBox gBox : classifier.getGlossaryBoxes()) {
				System.out.println(gBox.toString());
				// for (Box subbox : ((ElementBox) gBox).getSubBoxList())
				// System.out.println(subbox);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean sameClassCondition(GlossaryBox box) {
		boolean blnSameClass = false;
		Iterator<Class<? extends Box>> iter = box.getGroupedSubBoxes().keySet()
				.iterator();
		while (iter.hasNext()) {
			if (box.getGroupedSubBoxes().get(iter.next()).size() >= GlossaryConstants.GLOSSARY_COUNT_THRESHOLD) {
				blnSameClass = true;
				break;
			}
		}
		return blnSameClass;
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

	public List<GlossaryBox> getGlossaryBoxes() {
		if (null == glossaryBoxes)
			glossaryBoxes = new ArrayList<GlossaryBox>();
		return glossaryBoxes;
	}

	public void setGlossaryBoxes(List<GlossaryBox> glossaryBoxes) {
		this.glossaryBoxes = glossaryBoxes;
	}

	private void addGlossaryBox(Box root) {
		GlossaryBox gBox = new GlossaryBox(root);
		getGlossaryBoxes().add(gBox);
	}
}
