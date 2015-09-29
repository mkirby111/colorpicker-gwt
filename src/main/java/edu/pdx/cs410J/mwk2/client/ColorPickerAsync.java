package edu.pdx.cs410J.mwk2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client-side interface to the ping service
 */
public interface ColorPickerAsync {

  /**
   * Return the current input color
   */

  void setHexColor(String color, AsyncCallback<String> asyncCallback);

  void setNameColor(String color, AsyncCallback<String> asyncCallback);
}
