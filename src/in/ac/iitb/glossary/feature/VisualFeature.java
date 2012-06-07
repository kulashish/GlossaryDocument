package in.ac.iitb.glossary.feature;

import in.ac.iitb.glossary.GlossaryBox;
import in.ac.iitb.glossary.GlossaryDocument;

import java.util.ArrayList;
import java.util.List;

public abstract class VisualFeature {

	public void executeCondition() {
		GlossaryDocument gDoc = GlossaryDocument.getInstance();
		List<GlossaryBox> tempGBoxes = new ArrayList<GlossaryBox>();
		for (GlossaryBox gBox : gDoc.getGlossaryBoxes()) {
			gBox.groupSubboxes();
			if (condition(gBox))
				tempGBoxes.add(gBox);
		}
		gDoc.setGlossaryBoxes(tempGBoxes);
	}

	public abstract boolean condition(GlossaryBox gBox);

}
