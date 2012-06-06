package in.ac.iitb.glossary;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.demo.DOMSource;
import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.BrowserCanvas;
import org.fit.cssbox.layout.ReplacedImage;
import org.w3c.dom.Document;

public class GlossaryDocument {

	private static GlossaryDocument gDocument;
	private Box rootBox;
	private Box viewport;

	private GlossaryDocument() {

	}

	public Box getViewport() {
		return viewport;
	}

	public void setViewport(Box viewport) {
		this.viewport = viewport;
	}

	public static GlossaryDocument createInstance(String strUrl)
			throws GlossaryDocumentCreateException {
		if (null == gDocument) {
			gDocument = new GlossaryDocument();

			try {
				// Open the network connection
				URL url = new URL(strUrl);
				URLConnection con = url.openConnection();
				InputStream is = con.getInputStream();

				// Parse the input document using jTidy
				DOMSource parser = new DOMSource(is);
				Document doc = parser.parse();

				// Create the CSS analyzer
				DOMAnalyzer da = new DOMAnalyzer(doc, url);
				da.attributesToStyles(); // convert the HTML presentation
											// attributes
											// to inline styles
				da.addStyleSheet(null, CSSNorm.stdStyleSheet()); // use the
																	// standard
																	// style
																	// sheet
				da.addStyleSheet(null, CSSNorm.userStyleSheet()); // use the
																	// additional
																	// style
																	// sheet
				da.getStyleSheets(); // load the author style sheets

				// Disable image loading
				ReplacedImage.setLoadImages(false);

				// Create the browser canvas of 1000x600 pixels
				BrowserCanvas browser = new BrowserCanvas(da.getRoot(), da,
						new java.awt.Dimension(1000, 600), url);
				gDocument.setRootBox(browser.getRootBox());
				gDocument.setViewport(browser.getViewport());
				is.close();
			} catch (Exception e) {
				throw new GlossaryDocumentCreateException(e);
			}
		}
		return gDocument;
	}

	public Box getRootBox() {
		return rootBox;
	}

	public void setRootBox(Box rootBox) {
		this.rootBox = rootBox;
	}

	public static GlossaryDocument getInstance() {
		return gDocument;
	}

}
