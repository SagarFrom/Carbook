package pe.edu.upc.carbook.provider.services;

import pe.edu.upc.carbook.share.constants.Constants;

/**
 * Created by hypnotic on 4/11/2016.
 */

public class ProviderServices {
    public static String SERVICES_SOURCES = Constants.URL_SERVER + "/providers/3/services";
    public static String LOCALS_SOURCES = Constants.URL_SERVER + "/providers/{1}/locals";
    public static String LOCALS_MANAGE = Constants.URL_SERVER + "/providers/locals";
    public static String LOCALS_GALLERY_SOURCES = Constants.URL_SERVER + "/providers/locals/2/photos";
    public static String EDIT_INFO_SOURCES = Constants.URL_SERVER + "/users/";
    public static String ADVERTS_SOURCES = Constants.URL_SERVER + "/adverts/{1}/{2}";
}
