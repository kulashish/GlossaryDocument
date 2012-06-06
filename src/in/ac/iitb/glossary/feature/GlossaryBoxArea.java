package in.ac.iitb.glossary.feature;

import in.ac.iitb.glossary.GlossaryBox;
import in.ac.iitb.glossary.GlossaryConstants;
import in.ac.iitb.glossary.GlossaryDocument;

import org.fit.cssbox.layout.Box;

public class GlossaryBoxArea {

	public boolean condition(GlossaryBox gBox) {
		GlossaryDocument gDocument = GlossaryDocument.getInstance();
		Box root = gDocument.getViewport();
		int rootArea = root.getContentHeight() * root.getContentWidth();
		int boxArea = gBox.getHeight() * gBox.getWidth();
		float areaRatio = boxArea / (rootArea * 1.0f);
		System.out.println("Root: " + root);
		System.out.println("Root Area: " + rootArea);
		System.out.println("GBox Area: " + boxArea);
		System.out.println("Area ratio: " + (boxArea / (rootArea * 1.0f)));
		return areaRatio > GlossaryConstants.GLOSSARY_AREARATIO_THRESHOLD;
	}
}
