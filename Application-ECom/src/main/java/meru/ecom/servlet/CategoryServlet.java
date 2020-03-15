package meru.ecom.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import meru.app.binding.http.HttpAppRequest;
import meru.app.binding.http.servlet.XHtmlServlet;
import meru.exception.AppException;
import meru.persistence.EntityQuery;
import meru.ui.faces.renderer.html.HtmlFileView;
import app.erp.mdm.catalog.ProductCategory;

public class CategoryServlet extends XHtmlServlet {

    private static final long serialVersionUID = 1L;
    private static final String CATEGORY = "/category/";
    private static final int CATEGORY_DN_INDEX = CATEGORY.length()-1;

    
    private String categoryPath;

    
    @Override
    public void init(ServletConfig config) throws ServletException {
      super.init(config);
      String contextRoot = config.getInitParameter("context-root");
      categoryPath = contextRoot+CATEGORY;
    }
    
    @Override
    protected void get(HttpAppRequest appRequest) throws ServletException,
                                                 IOException {

        String requestURI = appRequest.getResourceURI();

        if (requestURI.endsWith(".xhtml")) {
            super.get(appRequest);
            return;
        }

        String categoryDN = requestURI.substring(requestURI.indexOf(CATEGORY)+CATEGORY_DN_INDEX);

        if (appRequest.containsParameters()) {

           /* if (appRequest.existsParameter("export")) {
                ProductExportAction exportAction = new ProductExportAction(categoryDN);
                exportAction.setRequestContext(mAppService,
                                               mServletContext,
                                               appRequest.getRequest(),
                                               appRequest.getResponse());

                exportAction.perform();
            }*/
        }
        else {

            EntityQuery<ProductCategory> entityQuery =  mEntityDataProvider.createEntityQuery(ProductCategory.class);
            entityQuery.addQueryParameter("qualifiedName", categoryDN);
            
            ProductCategory category = mEntityDataProvider.getFirstMatchingEntity(entityQuery);

            if (category == null) {
                throw new AppException("Unknown category : " + categoryDN,
                                       "Unknown category : " + categoryDN);
            }

            HtmlFileView htmlFileView = new HtmlFileView(categoryPath);

            htmlFileView.setVariable("productCategory",
                                     category);

            appRequest.setResourceURI(categoryPath);
            get(appRequest,
                htmlFileView);

        }
    }
}
