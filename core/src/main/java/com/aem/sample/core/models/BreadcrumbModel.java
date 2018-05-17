package com.aem.sample.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;


/**
 * Model for breadcrumb component
 * 
 * @author Arpan
 */
@Model(adaptables = { SlingHttpServletRequest.class })
public class BreadcrumbModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(BreadcrumbModel.class);
    private static final String CLASS_NAME = BreadcrumbModel.class.getName();
    private List navList = new ArrayList();
    private long level = 3L;
    private long endLevel = 0L;

    @Inject
    @Optional
    private Page currentPage;

    @PostConstruct
    public void activate() {
        LOGGER.debug("Class : " + CLASS_NAME + " : Method : activate() : [Entry]");
        setBreadCrumbItems();
        LOGGER.debug("Class : " + CLASS_NAME + " : Method : activate() : [Exit]");
    }


    private void setBreadCrumbItems() {
        int currentLevel = currentPage.getDepth();
        Boolean hideInBreadCrumb;

        while (level < currentLevel - endLevel) {
            Page trailPage = currentPage.getAbsoluteParent((int) level);
            if (trailPage == null) {
                break;
            }

            hideInBreadCrumb = Boolean.valueOf(trailPage.getProperties().get("hideInBreadCrumb", "false"));
            if (!hideInBreadCrumb) {
                this.navList.add(trailPage);
            }
            level++;
        }
    }

    public List<Page> getNavList() {
        return this.navList;
    }

}
