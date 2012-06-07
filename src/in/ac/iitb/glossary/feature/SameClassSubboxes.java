package in.ac.iitb.glossary.feature;

import java.util.Iterator;

import org.fit.cssbox.layout.Box;

import in.ac.iitb.glossary.GlossaryBox;
import in.ac.iitb.glossary.GlossaryConstants;

public class SameClassSubboxes extends VisualFeature {

	public boolean condition(GlossaryBox gBox) {
		boolean blnSameClass = false;
		Iterator<Class<? extends Box>> iter = gBox.getGroupedSubBoxes()
				.keySet().iterator();
		while (iter.hasNext()) {
			if (gBox.getGroupedSubBoxes().get(iter.next()).size() >= GlossaryConstants.GLOSSARY_COUNT_THRESHOLD) {
				blnSameClass = true;
				break;
			}
		}
		return blnSameClass;
	}
}
