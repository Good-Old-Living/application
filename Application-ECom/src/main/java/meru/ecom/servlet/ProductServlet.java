package meru.ecom.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import meru.app.binding.http.HttpAppRequest;
import meru.app.binding.http.servlet.XHtmlServlet;
import meru.erp.mdm.catalog.ProductLineItemDN;
import meru.persistence.EntityQuery;
import meru.ui.faces.renderer.html.HtmlFileView;
import app.erp.mdm.catalog.ProductLineItem;

public class ProductServlet extends XHtmlServlet {

  private static final long serialVersionUID = 1L;
  private static final String PRODUCT = "/product/";
  private static final int PRODUCT_DN_INDEX = PRODUCT.length() - 1;
  
  private String productPath;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    String contextRoot = config.getInitParameter("context-root");
    productPath = contextRoot+PRODUCT;
  }
  
  protected void get(HttpAppRequest appRequest) throws ServletException,
                                               IOException {

    String requestURI = appRequest.getResourceURI();

    if (requestURI.endsWith(".xhtml")) {
      super.get(appRequest);
      return;
    }

    // requestURI = URLDecoder.decode(requestURI,
    // "UTF-8");

    if (!appRequest.containsParameters()) {

      String productDN = requestURI.substring(requestURI.indexOf(PRODUCT)
          + PRODUCT_DN_INDEX);
      // productDN = productDN.replace("&", "%26");
      EntityQuery<ProductLineItem> entityQuery = new ProductLineItemDN(productDN).getEntityQuery(mEntityDataProvider);

      ProductLineItem productItem = mEntityDataProvider.getFirstMatchingEntity(entityQuery);

      if (productItem == null) {
        throw new ServletException("Unknown product '" + productDN + "'");
      }

      appRequest.setResourceURI(productPath);
      HtmlFileView htmlFileView = new HtmlFileView(appRequest.getResourceURI());

      htmlFileView.setVariable("productLineItem",
                               productItem);

      get(appRequest,
          htmlFileView);
    }
    else {

      // if (appRequest.existsParameter("buildIndex")) {
      // ProductIndexBuilder indexBuilder = new ProductIndexBuilder(mAppEngine);
      // indexBuilder.build();
      //
      // }

    }
  }

}
