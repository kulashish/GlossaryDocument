package in.ac.iitb.glossary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.ElementBox;

public class GlossaryBox {
	private Box gBox;
	private Map<Class<? extends Box>, List<Box>> groupedSubBoxes;

	public GlossaryBox(Box box) {
		gBox = box;
	}

	public int getHeight() {
		return gBox.getContentHeight();
	}

	public int getWidth() {
		return gBox.getContentWidth();
	}

	public Map<Class<? extends Box>, List<Box>> getGroupedSubBoxes() {
		if (null == groupedSubBoxes)
			groupedSubBoxes = new HashMap<>();
		return groupedSubBoxes;
	}

	public void addSubBox(Box box) {
		List<Box> subboxes = getGroupedSubBoxes().get(box.getClass());
		if (null == subboxes) {
			subboxes = new ArrayList<>();
			getGroupedSubBoxes().put(box.getClass(), subboxes);
		}
		subboxes.add(box);
	}

	public void groupSubboxes() {
		ElementBox elBox = (ElementBox) gBox;
		for (Box subBox : elBox.getSubBoxList()) {
			if (subBox.isVisible() && !subBox.canSplitInside())
				addSubBox(subBox);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return gBox.toString();
	}

}
