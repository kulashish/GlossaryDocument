package in.ac.iitb.glossary.feature;

import in.ac.iitb.glossary.GlossaryBox;
import in.ac.iitb.glossary.GlossaryConstants;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.fit.cssbox.layout.Box;

public class SameWidthSubboxes extends VisualFeature {

	@Override
	public boolean condition(GlossaryBox gBox) {
		boolean blnSame = true;
		Map<Class<? extends Box>, List<Box>> subboxes = gBox
				.getGroupedSubBoxes();
		Iterator<Class<? extends Box>> subboxIter = subboxes.keySet()
				.iterator();
		while (subboxIter.hasNext()) {
			if (!sameWidth(subboxes.get(subboxIter.next()))) {
				blnSame = false;
				break;
			}
		}
		return blnSame;
	}

	private boolean sameWidth(List<Box> subboxes) {
		int minWidth = 0;
		int maxWidth = 0;
		int width = 0;
		for (Box subbox : subboxes) {
			width = width(subbox);
			minWidth = minWidth == 0 ? width : width < minWidth ? width
					: minWidth;
			maxWidth = maxWidth == 0 ? width : width > maxWidth ? width
					: maxWidth;
		}
//		System.out.println("Max width : " + maxWidth + ", Min width : "
//				+ minWidth);
		return (maxWidth - minWidth) < GlossaryConstants.SUBBOX_WIDTHDIFF_THRESHOLD;
	}

	private int width(Box box) {
		return box.getContentWidth();
	}

}
