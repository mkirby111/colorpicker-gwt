package edu.pdx.cs410J.mwk2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("ping")
public interface ColorPicker extends RemoteService {

  /**
   * Returns the current color input
   */
  String setHexColor(String color) throws ValidateInputException;

  String setNameColor(String color) throws ValidateInputException;
}
