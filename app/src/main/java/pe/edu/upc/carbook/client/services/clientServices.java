package pe.edu.upc.carbook.client.services;

import pe.edu.upc.carbook.share.constants.Constants;

/**
 * Created by hypnotic on 4/11/2016.
 */

public class clientServices {

    public static String ADVERTS_SOURCES = Constants.URL_SERVER + "/adverts/{1}/{2}";
    public static String ADVERTS_ACCEPT_POSTULATION = Constants.URL_SERVER + "/adverts/acceptPostulation";
    public static String CLIENT_CAR_SOURCES = Constants.URL_SERVER + "/clients/{1}/cars";
}
