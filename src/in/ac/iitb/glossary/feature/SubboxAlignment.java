package in.ac.iitb.glossary.feature;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.fit.cssbox.layout.Box;

import in.ac.iitb.glossary.GlossaryBox;
import in.ac.iitb.glossary.GlossaryConstants;

public class SubboxAlignment extends VisualFeature {

	@Override
	public boolean condition(GlossaryBox gBox) {
		boolean blnSame = true;
		Map<Class<? extends Box>, List<Box>> subboxes = gBox
				.getGroupedSubBoxes();
		Iterator<Class<? extends Box>> subboxIter = subboxes.keySet()
				.iterator();
		while (subboxIter.hasNext()) {
			if (!aligned(subboxes.get(subboxIter.next()))) {
				blnSame = false;
				break;
			}
		}
		return blnSame;
	}

	private boolean aligned(List<Box> subboxes) {
		int minX = 0;
		int maxX = 0;
		int x = 0;
		for (Box subbox : subboxes) {
			x = getX(subbox);
			minX = minX == 0 ? x : x < minX ? x
					: minX;
			maxX = maxX == 0 ? x : x > maxX ? x
					: maxX;
		}
		System.out.println("Max x : " + maxX + ", Min x : "
				+ minX);
		return (maxX - minX) < GlossaryConstants.SUBBOX_ALIGNMENT_THRESHOLD;
	}

	private int getX(Box subbox) {
		return subbox.getContentX();
	}

}
